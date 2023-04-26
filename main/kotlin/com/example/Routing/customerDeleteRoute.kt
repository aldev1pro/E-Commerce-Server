package com.example.Routing

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.customerDeleteRoute(){
    routing {
        post("/delete") {
            val display = call.receive<StoredPassword>()

            val find = itemsList.find {
                display.password == it.password
            }
            var index = 0
            for(i in customerList){
                if(i.password == find?.password){
                    index = i.id
                }
            }
            if(display.password == find?.password) {
                customerList.removeAt(index)
                call.respond("Customer not doing business with us anymore at index:$index $" )
            }

        }

    }

}