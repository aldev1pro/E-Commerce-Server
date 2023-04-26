package com.example.Routing

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.customerUpdateRoute(){
    routing {
        put("/update"){
            val update = call.receive<Customer>()
            val find = customerList.find {
                update.id == it.id
            }?:return@put call.respondText("id not found. No entry match for updates")

            if(update.id == find.id){
                customerList.add(update)
                call.respond("Data entry updated $update")
            }else{
                call.respond("Error try again")
            }
        }
    }
}