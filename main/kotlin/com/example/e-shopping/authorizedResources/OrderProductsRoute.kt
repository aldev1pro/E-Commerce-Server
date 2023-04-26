package com.example.`e-shopping`.authorizedResources

import com.example.CURRENTCAPITALOFTHEBUSINESS
import com.example.`e-shopping`.myData.*
import com.example.`e-shopping`.usertDataStore.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.orderProductRoute(
    userDataSource: UserDataSource,
    storeDataSource: StoreDataSource,
    clientComputedOderSource: ClientComputedOderDataSource,
    adminDataSource: AdminDataSource
) {
    routing {
        authenticate("auth-jwt"){

            post("/orderProducts") {
                val request = call.receiveNullable<OrderProducts>() ?:kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val principal = call.principal<JWTPrincipal>()
                val getEmailFromJWTPrincipal = principal!!.payload.getClaim("email").asString()

                val client = userDataSource.checkEmail(getEmailFromJWTPrincipal)




                var totalAmountPurchasedByACustomer = 0
                var profitDerivedFromSales = 0
                val itemsAndPacket = mutableListOf<ProductsAndCartons>()
                val updateItemsPacket = mutableListOf<ProductsAndCartons>()

                var flag = false
                var cartons = 0
               forLoop@ for((name,packets) in request.cart){
                    storeDataSource.getPriceOfProduct(name)?.let{
                        flag = if(it.noOfCartons >= packets){
                             cartons =  it.noOfCartons - packets
                            true
                        }else{
                            call.respond(status = HttpStatusCode.BadRequest, message = "There is not enough cartons for product ${it.productName} in the store")
                            false
                        }
                        val multiply = it.basePrice * packets
                        totalAmountPurchasedByACustomer+=multiply
                        val profit = it.profit * packets
                        profitDerivedFromSales+=profit
                    }
                   if(flag) {
                       updateItemsPacket.add(ProductsAndCartons(name,cartons))
                       itemsAndPacket.add(ProductsAndCartons(name, packets))
                   }else {
                       break@forLoop
                   }
                }
                if(flag){
                val itemsAndPacketHolder = itemsAndPacket.toList()
                userDataSource.checkEmail(getEmailFromJWTPrincipal)?.account?.let {

                    if(it > totalAmountPurchasedByACustomer) {
                        val balance = it - totalAmountPurchasedByACustomer
                        userDataSource.updateAccount(getEmailFromJWTPrincipal,balance)
                        call.respond(status = HttpStatusCode.OK, message ="Purchased successfully dear customer ${client?.firstname}")
                    }else{
                        call.respond(status = HttpStatusCode.BadRequest, message = "Not enough money in your Account")
                    }

                }
                    for(i in updateItemsPacket){
                        storeDataSource.updateCartonsPurchasedPerProduct(i.product,i.carton)
                    }

               // val client = userDataSource.checkEmail(getEmailFromJWTPrincipal)

                    clientComputedOderSource.addClientOder( client =
                    ComputedClientOrderDetails(
                        listOf(
                            ClientsOrder(
                                firstname = client!!.firstname,
                                lastname = client.lastname,
                                place = client.place,
                                ward = client.ward,
                                telephone = client.telephone,
                                email = client.email,
                                noOfCartonsAndProductsBought = itemsAndPacketHolder,
                                totalAmountPurchased = totalAmountPurchasedByACustomer
                            )
                        )
                    )
                    )
                }




            }
        }
    }

}

