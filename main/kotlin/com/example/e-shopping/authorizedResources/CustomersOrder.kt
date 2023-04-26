package com.example.`e-shopping`.authorizedResources

import com.example.`e-shopping`.usertDataStore.ClientComputedOderDataSource
import com.example.`e-shopping`.usertDataStore.StoreDataSource
import com.example.clientOrderDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.customerOrderRoute(clientDataSource: ClientComputedOderDataSource) {
    routing {
        authenticate("auth-jwt") {
            get("/clients") {
              val client =  clientDataSource.getClientOder()
//                val products = storeDataSource.getProducts()
//                val principal = call.principal<JWTPrincipal>()
//                val email = principal!!.payload.getClaim("email").asString()
//                // call.respond("This is from the JWTPrincipal $email")
                call.respond(client.toList())

            }
        }


    }
}