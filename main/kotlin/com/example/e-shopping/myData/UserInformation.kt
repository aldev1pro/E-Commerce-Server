package com.example.`e-shopping`.myData

import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.allStatusCodes
import io.ktor.server.http.*
import io.ktor.server.util.*
import io.ktor.util.date.*
import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.io.Serial
import java.time.Instant

@Serializable
data class UserInformationSignUp(
    val firstname:String = "",
    val lastname:String = "",
    val age:Int,
    val place:String = "",
    val ward:String = "",
    val telephone:String = "",
    val email:String = "",
    val password: String = "",
    val salt:String = "",
    val account:Int,
    @BsonId val id: String = ObjectId().toString(),
    @Contextual
    val date: String = Instant.now().toHttpDateString(),
  //  @Contextual
  //  val statusCode: HttpStatusCode.Companion = HttpStatusCode
)
@Serializable
data class ProductInformation(
    val productName:String,
    val imageUrl:String,
    val noOfCartons:Int,
    val basePrice:Int,
    val profit:Int,
    @BsonId val id: String = ObjectId().toString()
    )

data class TokenConfig(
    val issuer:String,
    val audience:String,
    val expiresIn:Long,
    val secret:String
)
data class CustomClaim(
    val name:String,
    val value:String
)
data class SaltedHash(
    val saltValue:String,
    val hashValue:String
)
@Serializable
data class AuthSignUp(
    val firstname:String,
    val lastname:String,
    val telephone:String,
    val password:String,
    val age:Int,
    val place:String,
    val ward:String,
    val email:String,
    val account: Int,
)
@Serializable
data class AuthSignIn(
    val email: String,
    val password: String
)
@Serializable
data class AuthResponseResource(
    val token:String
)
@Serializable
data class Cart(val nameOfProduct:String,val noOfCartons:Int)

@Serializable
data class OrderProducts(
    val cart:List<Cart>
)
@Serializable
data class ClientsOrder(
    val firstname: String = "",
    val lastname: String = "",
    val place: String = "",
    val ward: String = "",
    val telephone:String,
    val email: String = "",
    val noOfCartonsAndProductsBought:List<ProductsAndCartons> =listOf(ProductsAndCartons("",0)),
    val totalAmountPurchased:Int,
    @BsonId val id: String = ObjectId().toString()
)
@Serializable
data class ComputedClientOrderDetails(val customers: List<ClientsOrder>)
@Serializable
data class ProductsAndCartons(val product:String = "", val carton:Int)
@Serializable
data class InformationForAdmin(
    val capitalOfTheBusiness:Int,
    val profitFromSales:Int,
    val displayLeftItems: List<ProductsAndCartons>
)