package com.example.`e-shopping`.authorizedResources

import com.example.CURRENTCAPITALOFTHEBUSINESS
import com.example.`e-shopping`.myData.InformationForAdmin
import com.example.`e-shopping`.myData.ProductsAndCartons
import com.example.`e-shopping`.usertDataStore.AdminDataSource
import com.example.`e-shopping`.usertDataStore.ClientComputedOderDataSource
import com.example.`e-shopping`.usertDataStore.StoreDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.adminStoreDetailsRoute(
    storeDataSource: StoreDataSource,
    clientComputedOderSource: ClientComputedOderDataSource,
) {
    routing {
        authenticate("auth-jwt") {

            get("/storeDetails") {
                val itemsLeft = mutableListOf<ProductsAndCartons>()
                for (i in storeDataSource.getProducts().toList()) {
                    CURRENTCAPITALOFTHEBUSINESS += i.basePrice * i.noOfCartons
                    itemsLeft.add(ProductsAndCartons(i.productName, i.noOfCartons))
                }

                var salesProfit = 0
                for (i in clientComputedOderSource.getClientOder().toList()) {
                    for (j in i.customers) {
                        for ((k, v) in j.noOfCartonsAndProductsBought) {
                            val getProduct = storeDataSource.getPriceOfProduct(k)
                            getProduct?.let {
                                val a = it.profit * v
                                salesProfit+= a
                            }
                        }
                    }
                }

                val displayStoreDetails = InformationForAdmin(
                    capitalOfTheBusiness = CURRENTCAPITALOFTHEBUSINESS,
                    profitFromSales = salesProfit,
                    displayLeftItems = itemsLeft
                )
                call.respond(message = displayStoreDetails, status = HttpStatusCode.OK)


            }
        }
    }
}
