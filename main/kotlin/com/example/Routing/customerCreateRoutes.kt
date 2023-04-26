package com.example.Routing

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

//import org.litote.kmongo.reactivestreams.KMongo

//import org.litote.kmongo.coroutine.coroutine
//import org.litote.kmongo.reactivestreams.KMongo


@Serializable
data class Customer(val firstName:String, val lastName:String, val password:String,val email:String,val id:Int)
val customerList = mutableListOf<Customer>()
@Serializable
data class OderItem(val Item:String,val quantity:Int)
@Serializable
data class ItemsList(val password:String, val contents:List<OderItem>)
val itemsList = mutableListOf<ItemsList>()
@Serializable
data class StoredPassword(val password:String)
val s = KMongo.createClient().coroutine
fun Application.customerCreateRouting(){

    val map = mapOf(
        "Orange" to 5,
        "Banana" to 15,
        "Lemon" to 4,
        "Bread" to 10,
        "Juice" to 5,
        "Cassava" to 25,
        "Egg" to 12,
        "Groundnut" to 15,
        "Pencil" to 3,
        "Book" to 17,
        "Rubber" to 4,
        "Shaper" to 6,
    )
     routing{
         post("/customer") {
             val customer = call.receive<Customer>()
             if(customer.firstName.isBlank() || customer.lastName.isBlank()){
                 call.respondText("Name cannot be empty")
                 return@post
             }
             if(customer.password.isBlank() || customer.password.length < 3){
                 call.respondText("Password too short")
                 return@post
             }
             //just for demo purpose: synchronized is used for locking
             //to prevent accessing something simultaneously by multiple threads
             synchronized(this@routing) {
                 customerList.add(
                     Customer(
                         customer.firstName,
                         customer.lastName,
                         customer.password,
                         customer.email,
                         customer.id
                     )
                 )
             }
             call.respond("These are the items in our Store: items and their corresponding prices: \n$map")

         }
     }
}