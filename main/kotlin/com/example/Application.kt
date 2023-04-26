package com.example

import com.example.`e-shopping`.authorizedResources.adminStoreDetailsRoute
import com.example.`e-shopping`.authorizedResources.customerOrderRoute
import com.example.`e-shopping`.authorizedResources.getProductsRoute
import com.example.`e-shopping`.authorizedResources.orderProductRoute
import com.example.`e-shopping`.hashPassword.HashPasswordImpl
import com.example.`e-shopping`.myData.*
import com.example.`e-shopping`.routing.configureJWT
import com.example.`e-shopping`.routing.customerSignInRoute
import com.example.`e-shopping`.routing.customerSignUpRoute
import com.example.`e-shopping`.tokenGenerator.TokenGeneratorImpl
import com.example.`e-shopping`.usertDataStore.AdminDataStoreImpl
import com.example.`e-shopping`.usertDataStore.ClientComputedOderDataStoreImpl
import com.example.`e-shopping`.usertDataStore.StoreDataSourceImpl
import com.example.`e-shopping`.usertDataStore.UserDataSourceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val userDatabase = KMongo.createClient().getDatabase("userInfo").coroutine.getCollection<UserInformationSignUp>()
val storeDatabase = KMongo.createClient().getDatabase("userInfo").coroutine.getCollection<ProductInformation>()
val clientOrderDatabase = KMongo.createClient().getDatabase("userInfo").coroutine.getCollection<ComputedClientOrderDetails>()
val storeDetailsDatabase = KMongo.createClient().getDatabase("userInfo").coroutine.getCollection<InformationForAdmin>()
//const val BASE_URL = "http://127.0.0.1:8080"
var CURRENTCAPITALOFTHEBUSINESS = 0
const val BASE_URL = "http://192.168.0.119:8080"
//const val BASE_URL = "http://192.168.0.127:8080"
//const val BASE_URL = "http://127.0.0.1:8080"
fun main(args:Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    val tokenConfiguration = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").toString(),
        expiresIn = 365 * 1000L * 60L * 24L,
        secret = System.getenv("SECRET-KEY"),
    )
    val userDataSource = UserDataSourceImpl()
    val hashPassword = HashPasswordImpl()
    val tokenConfig = TokenGeneratorImpl()
    val storeDataSource = StoreDataSourceImpl()
    val clientComputedOderDataSource = ClientComputedOderDataStoreImpl()
    val adminDataSource  = AdminDataStoreImpl()

    configureSerialization()
    configureJWT(tokenConfiguration)
    customerSignUpRoute(userDataSource, hashPassword,storeDataSource)
    customerSignInRoute(userDataSource, hashPassword, tokenConfiguration, tokenConfig,storeDataSource)
    getProductsRoute(storeDataSource)
    orderProductRoute(userDataSource,storeDataSource,clientComputedOderDataSource,adminDataSource)
    customerOrderRoute(clientComputedOderDataSource)
    adminStoreDetailsRoute(storeDataSource,clientComputedOderDataSource)
   // clientComputedOderDataSource.delete()


    @Serializable
    data class Shop(val title:String, val image:String)
    @Serializable
    data class ShopImages(val title:String, val image:String)
    val dshop = KMongo.createClient().getDatabase("shop").coroutine.getCollection<ShopImages>()

     fun getImage(): CoroutineFindPublisher<ShopImages> {
       // return dshop.find(ShopImages::title eq name)
        return dshop.find()
    }
     suspend fun addImage(shopImages: ShopImages):Boolean {
       // return dshop.insertOne(shopImages).wasAcknowledged()
        return dshop.insertMany(listOf(shopImages)).wasAcknowledged()
    }

    routing{

        val sol = UserDataSourceImpl()
        val nol = StoreDataSourceImpl()
        get("/update"){
//            for(i in clientComputedOderDataSource.getClientOder().toList()) {
//                for(j in i.customers){
//                    j.
//                    clientComputedOderDataSource.delete(client = ComputedClientOrderDetails(customers = ClientsOrder))
//                }
//            }
            call.respond(sol.getAllUsers().toList())

           // val client1 = userDataSource.checkEmail("k@gmail.com")
         //   call.respond("ward = ${client1!!.ward}, telephone = ${client1.telephone}, account = ${client1.account}, age = ${client1.age}")


//            var profit = 0
//            for(i in nol.getProducts().toList()){
//                 profit += i.basePrice * i.noOfCartons
//            }
          //  call.respond("The capital of the business is $profit")
//            nol.updateCartonsPurchasedPerProduct("Biscuit",17)
//            nol.updateCartonsPurchasedPerProduct("Cerelack",17)
//            nol.updateCartonsPurchasedPerProduct("Chewing Gum",17)

        }

        get("/update1"){
            nol.updateCartonsPurchasedPerProduct("Nescafe",20)
            call.respond(status = HttpStatusCode.OK, message = "updated")
            //if(sol.updateAccount("9ld@gmail.com",100000))
        // call.respond("success") else call.respond("failure")

        }

        get("/test"){
          //  call.respond("working")
            val dd = nol.addProducts(nol.productList)
           // val add = addImage(ShopImages(title = "biscuit",image = "${BASE_URL}images/biscuit.jpg"))
            if(dd){
                call.respond("Image stored successfully")
            }else{
                call.respond("Try again")
            }
        }
        get("/show"){
            //val a = getImage()
            val a = nol.getProducts()
            call.respond(a.toList())
            }

        }





    routing {
        static("/images") {
            staticBasePackage = "storeProducts"
            resources(".")
        }
    }


}




































































//
//    configureSerialization()
//    customerCreateRouting()
//    customerOderRoutes()
//    customerDeleteRoute()
//    customerUpdateRoute()
//    install(AutoHeadResponse)
//    install(PartialContent)
//
//    routing {
//        get("/download") {
//          //  val file = File("file/darboe.jpg")
//          //  val file = File("file/Must learn programming languages.mp4")
//            val file = File("file/AlBaqarah.mp3")
//
//            call.response.header(
//                HttpHeaders.ContentDisposition,
//                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "Planguage.mp3")
//                    .toString()
//            )
//            call.respondFile(file)
//        }
//
//
//
//
//        get("/movie") {
//            // get filename from request url
//           // val filename = call.parameters["name"]!!
//            val filename = "code.jpg"
//            // construct reference to file
//            // ideally this would use a different filename
//            val file = File("/resources/rabbits/$filename")
//            if(file.exists()) {
//                call.respondFile(file)
//            }
//            else call.respondText("File not Found")
//        }
//        get("/fileOpen"){
//            val file = File("/resources/rabbits/great.txt")
//            call.response.header(
//                HttpHeaders.ContentDisposition,
//                ContentDisposition.Inline.withParameter(
//                    ContentDisposition.Parameters.FileName,"downloadableFile.txt"
//                ).toString()
//            )
//            call.respondFile(file)
//           // call.respondText("File downloading")
//        }
//        get("/fileDownload"){
//           // val file = File("rabbits/yoga/code.jpg")
//            val file = File("file/code.png")
//
//            call.response.header(
//                HttpHeaders.ContentDisposition,
//                ContentDisposition.Attachment.withParameter(
//                    ContentDisposition.Parameters.FileName,"code.png"
//                ).toString()
//            )
//            if(file.exists()) {
//                call.respond(file)
//            }else{
//                call.respondText("File downloading")
//            }
//            // call.respondText("File downloading")
//        }
//
//
//
//
//
//        static("/images"){
//           // resources("rabbits")
//          //  resource("darboe.jpg")
//            staticBasePackage = "rabbits"
//            defaultResource("img.png")
//            resource("amado.jpg")
//            resources(".")
//        }
//    }
//
//    install(RequestValidation){
//        validate<Person> { body->
//            if (body.name != "Riles")
//                ValidationResult.Invalid("name not found")
//            else ValidationResult.Valid
//        }
//        validate<Person>{body->
//            if(body.age <30)
//                ValidationResult.Invalid("Age is too small")
//            else ValidationResult.Valid
//        }
//    }
//    install(StatusPages) {
//        exception<RequestValidationException> { call, cause ->
//            call.respondText("accepted: ${cause.reasons.joinToString()} ")
//           // call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
//        }
//
//       // statusFile(HttpStatusCode.Unauthorized, HttpStatusCode.PaymentRequired, filePattern = "error#.html")
//
//    }
//    routing {
//
//        post("/sel") {
//            val result = call.receive<Person>()
//            val type = call.request.contentType()
//           // call.respondText("The content type: $type")
//            call.respondText("accepted ${result.name}")
//
//            if(result.name == "Riles") {
//                call.respondText("accepted ${result.name}")
//            }else{
//                call.respondText("bad")
//            }
//        }
//
//
//
//        val list = listOf<Person>(
//            Person("Mike",2,"Lagos"),
//            Person("Joe",3,"Banjul"),
//            Person("Riles",4,"Jokadu"),
//            Person("Bozer",5,"Amsterdam")
//        )
//
//        get("/watch") {
//            call.respond(list)
//        }
//        get("/gel/{name?}") {
//            if(call.parameters["name"] == "Riles") return@get call.respondText("Love Riles")
//            else {
//                call.respondText("Not good")
//            }
//
//            if (call.parameters["name"] == "Riles") {
//                call.respondText("hacking is not crime if used to help people ")
//            } else {
//                call.respondText("Not found")
//            }
//
//        }
//        get("del/{name?}"){
//            if(call.parameters["name"] == "joe") {
//                call.respondText("Hello Albani")
//            }else{
//                call.respondText("Not Found")
//
//            }
//        }
//        route("/customer") {
//            route("/shipment"){
//
//                get(""){
//                    call.respondText("Testing ktor")
//                }
//                post{
//
//                }
//            }
//        }
//        get("/res/{name?}"){
//            val result = call.request.queryParameters.get(name = "name")
//            if(result == "Joe"){
//                call.respondText("This is the path: $result")
//            }else{
//                call.respondText("Not Found", status = HttpStatusCode.BadRequest)
//            }
//        }
//
//
//    }
//}






































/*
  data class Login (
        var username:String,
        val grade:Int,
        val likes:String,
        val place:String = "",
        val password:String,
        val age:Int,
        val ward:String = "",
        @BsonId val id:ObjectId = ObjectId())
    val user = KMongo.createClient().getDatabase("userInfo").coroutine.getCollection<Login>()
    @Serializable
    data class AuthRequest(val username:String,
                           val grade:Int,
                           val likes:String,
                           val place:String,
                           val password:String,
                           val age:Int,
                           val ward:String
    )
    @Serializable
    data class Angry(val username:String,
//                     val grade:Int,
//                     val place:String,
//                     val password:String,
//                     val likes:String,
//                     val age:Int,
//                     val ward:String
                     )

    suspend fun getUser(name:String):Login?{
        return user.findOne(Login::username eq name)
    }
    suspend fun add(data:Login):Boolean{
        return user.insertOne(data).wasAcknowledged()
    }

    routing {
        post("/log"){
          val request = call.receiveNullable<AuthRequest>()
           if(request?.username!!.isBlank()){
               call.respond(status = HttpStatusCode.Forbidden, message = "Not valid")
           }else {
               val data = Login(request.username,request.grade,request.likes,request.place,request.password,request.age,request.ward)
               add(data)
               //user.insertOne(Login(request.username, request.password))
               call.respond(HttpStatusCode.OK)
           }
        }
        post("/logout"){
            val request = call.receive<Angry>()
            val nol = getUser(request.username)
           // val nol = user.findOne(Login::username eq request?.username)
            if(nol?.username == null){
                call.respond("bad request")
            }else{
                call.respond("data is ${nol.password}")
            }
        }
    }
 */