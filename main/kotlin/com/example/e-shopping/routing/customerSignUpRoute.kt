package com.example.`e-shopping`.routing

import com.example.`e-shopping`.hashPassword.HashPassword
import com.example.`e-shopping`.myData.UserInformationSignUp
import com.example.`e-shopping`.usertDataStore.StoreDataSource
import com.example.`e-shopping`.usertDataStore.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.customerSignUpRoute(
    userDataSource: UserDataSource,
    hashPassword: HashPassword,
    storeDataSource: StoreDataSource
) {
    routing {
        post("/signUp") {
//            try {
//                call.receiveNullable<AuthSignUp>()
//            }catch (e:Exception){
//                call.respond("NULL value is not required")
//            }
            val request = call.receiveNullable<UserInformationSignUp>()?:kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val areFieldBlank = request.firstname.isBlank()||request.lastname.isBlank()||request.password.isBlank()
            val isPwTooShort = request.password.length < 8
            if(areFieldBlank || isPwTooShort){
                call.respond(status = HttpStatusCode.BadRequest, message = "names cannot be empty/password too short")
                return@post
            }
            val checkEmail = userDataSource.checkEmail(request.email)
            if(checkEmail?.email == request.email){
                call.respond(HttpStatusCode.BadRequest , message = "Try another email. This email has been taken")
            }
            val saltedhash = hashPassword.generateSaltedHash(request.password)

            val user = UserInformationSignUp(
                firstname = request.firstname,
                lastname = request.lastname,
                telephone = request.telephone,
                age = request.age,
                place = request.place,
                ward = request.ward,
                email = request.email,
                password = saltedhash.hashValue,
                salt = saltedhash.saltValue,
                account = request.account
            )
            val wasAcknowledge = userDataSource.addUser(user)
            if(!wasAcknowledge){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val products = storeDataSource.getProducts()
          //  val principal = call.principal<JWTPrincipal>()
           // val email = principal!!.payload.getClaim("email").asString()
            // call.respond("This is from the JWTPrincipal $email")
            call.respond(status = HttpStatusCode.OK, message = products.toList())
          //  call.respond(status = HttpStatusCode.OK, message = "Account Successfully created")


        }
    }
}