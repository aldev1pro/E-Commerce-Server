package com.example.`e-shopping`.routing

import com.example.`e-shopping`.hashPassword.HashPassword
import com.example.`e-shopping`.myData.*
import com.example.`e-shopping`.tokenGenerator.TokenGenerator
import com.example.`e-shopping`.usertDataStore.StoreDataSource
import com.example.`e-shopping`.usertDataStore.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.customerSignInRoute(
    userDataSource: UserDataSource,
    hashPassword: HashPassword,
    tokenConfig: TokenConfig,
    tokenGenerator: TokenGenerator,
    //for demonstration,
    storeDataSource: StoreDataSource

){
    routing {
        post("/signIn"){
            val request = call.receiveNullable<AuthSignIn>()?:kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val user = userDataSource.checkEmail(request.email)
            if (user?.email == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val verifyPassword = hashPassword.verifySaltedHash(
                request.password,
                saltedHash = SaltedHash(
                    saltValue = user.salt,
                    hashValue = user.password
                )
            )
            if (!verifyPassword) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    message = "Incorrect email/password. Try again")
                return@post
            }

            val token = tokenGenerator.generate(
                tokenConfig = tokenConfig,
                CustomClaim(
                    name = "email",
                    value = user.email.toString()
                )
            )
            //for demonstration
 //           val products = storeDataSource.getProducts()
//            call.respond(status = HttpStatusCode.OK, message = products.toList())
//            call.respond(
//                HttpStatusCode.OK,
//                message = products.toList()
//            )
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponseResource(
                    token = token
                )
            )

        }
    }
}