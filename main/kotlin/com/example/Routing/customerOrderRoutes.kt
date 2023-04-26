package com.example.Routing

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.customerOderRoutes(){

    routing {
        post("/order"){
            val order = call.receive<ItemsList>()
            val find = customerList.find {
                order.password == it.password
            }?:return@post call.respondText("You are not a customer. Please SignIn")
            var matchName = ""
            for(i in customerList){
                if(i.password == find.password){
                    matchName = i.firstName
                }
            }
            val(pass,cont) = ItemsList(order.password,order.contents)
            var item = ""
            var quantity = 0
            for((i,v) in cont){
                item = i
                quantity = v
            }
           itemsList.add(
                ItemsList(
                    password = pass,
                    contents = listOf(
                        OderItem(Item = item, quantity = quantity)
                    )))
            call.respondText("Purchase successful $matchName")

        }

        post("/display"){
            val display = call.receive<StoredPassword>()

            val find = itemsList.find {
                display.password == it.password
            }
            if(display.password == find?.password) {
                call.respond("Your purchased items: ${find.contents}")
            }else{
                call.respondText("Data not found")
            }

        }

    }
}