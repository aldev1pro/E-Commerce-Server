package com.example.`e-shopping`.usertDataStore

import com.example.BASE_URL
import com.example.`e-shopping`.myData.ProductInformation
import com.example.`e-shopping`.myData.UserInformationSignUp
import com.example.storeDatabase
import com.example.userDatabase
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.delay
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

interface StoreDataSource{
    fun getProducts(): CoroutineFindPublisher<ProductInformation>
    suspend fun addProducts(product: List<ProductInformation>):Boolean
    suspend fun getPriceOfProduct(productName:String):ProductInformation?
    suspend fun updateCartonsPurchasedPerProduct(productName: String,cartons: Int):Boolean
}

class StoreDataSourceImpl:StoreDataSource{

    override fun getProducts(): CoroutineFindPublisher<ProductInformation> {
        return storeDatabase.find()
    }

    override suspend fun addProducts(product: List<ProductInformation>): Boolean {
        return storeDatabase.insertMany(product).wasAcknowledged()
    }

    override suspend fun getPriceOfProduct(productName: String):ProductInformation? {
        return storeDatabase.findOne(ProductInformation::productName eq productName)
    }

    override suspend fun updateCartonsPurchasedPerProduct(productName: String,cartons:Int):Boolean {
        return storeDatabase.updateOne(ProductInformation::productName eq productName, setValue(ProductInformation::noOfCartons,cartons )).wasAcknowledged()
    }

    val productList = listOf(
    ProductInformation(
        productName = "Biscuit",
        imageUrl = "http://192.168.0.119:8080/images/biscuit.jpg",
       // imageUrl = "http://192.168.0.119:8080/images/biscuit.jpg",
        noOfCartons = 70,
        basePrice = 500,
        profit = 40),
    ProductInformation(
        productName = "Cerelack",
        imageUrl = "$BASE_URL/images/Cerelack.jpg",
        noOfCartons = 70,
        basePrice = 2500,
        profit = 300),
    ProductInformation(
        productName = "Chewing Gum",
        imageUrl = "$BASE_URL/images/chewingGum.jpg",
        noOfCartons = 70,
        basePrice = 500,
        profit =  40),
    ProductInformation(
        productName = "Cooking Oil",
        imageUrl = "$BASE_URL/images/CookingOil.jpg",
        noOfCartons = 70,
        basePrice = 2300,
        profit =  300),
    ProductInformation(
        productName = "CornFlakes",
        imageUrl = "$BASE_URL/images/CornFlakes.jpg",
        noOfCartons = 70,
        basePrice = 1200,
        profit =  60),
    ProductInformation(
        productName = "CowBell Milk powder",
        imageUrl = "$BASE_URL/images/CowbelPowder.jpg",
        noOfCartons = 70,
        basePrice = 700,
        profit =  30),
    ProductInformation(
        productName = "Diapers",
        imageUrl = "$BASE_URL/images/Diapers.jpg",
        noOfCartons = 70,
        basePrice = 1000,
        profit =  70),
    ProductInformation(
        productName = "DonSimon",
        imageUrl = "$BASE_URL/images/DonSimon.jpg",
        noOfCartons = 70,
        basePrice = 1700,
        profit = 200),
    ProductInformation(
        productName = "Laundry Detergent",
        imageUrl = "$BASE_URL/images/LaundyDetergent.jpg",
        noOfCartons = 70,
        basePrice = 900,
        profit =  65),
    ProductInformation(
        productName = "Lipton",
        imageUrl = "$BASE_URL/images/Lipton.jpg",
        noOfCartons = 70,
        basePrice = 1000,
        profit = 50),
    ProductInformation(
        productName = "Milk Banana",
        imageUrl = "$BASE_URL/images/MilkBanana.jpg",
        noOfCartons = 70,
        basePrice = 2300,
        profit =  125),
    ProductInformation(
        productName = "Cowbell Can",
        imageUrl = "$BASE_URL/images/CowbellTin.jpg",
        noOfCartons = 70,
        basePrice = 1200,
        profit =  90),
    ProductInformation(
        productName = "Mosquito Coil",
        imageUrl = "$BASE_URL/images/MosquitorCoil.jpg",
        noOfCartons = 70,
        basePrice = 1500,
        profit =  120),
    ProductInformation(
        productName = "Nescafe ",
        imageUrl = "$BASE_URL/images/Nescafe.jpg",
        noOfCartons = 70,
        basePrice = 700,
        profit =  40),
    ProductInformation(
        productName = "Onion",
        imageUrl = "$BASE_URL/images/Onion.jpg",
        noOfCartons = 80,
        basePrice = 1000,
        profit =  80),
    ProductInformation(
        productName = "Peak Can",
        imageUrl = "$BASE_URL/images/PeakTin.jpg",
        noOfCartons = 70,
        basePrice = 1700,
        profit =  250),
    ProductInformation(
        productName = "Sardines",
        imageUrl = "$BASE_URL/images/Sardinez.jpg",
        noOfCartons = 70,
        basePrice = 1300,
        profit =  75),
    ProductInformation(
        productName = "Carrot Soap",
        imageUrl = "$BASE_URL/images/SoapCarrot.jpg",
        noOfCartons =70,
        basePrice = 650,
        profit =  40),
    ProductInformation(
        productName = "Super Glue",
        imageUrl = "$BASE_URL/images/SuperGlue.jpg",
        noOfCartons = 70,
        basePrice = 550,
        profit =  40),
    ProductInformation(
        productName = "Sweet Potato",
        imageUrl = "$BASE_URL/images/Sweetpotato.jpg",
        noOfCartons = 70,
        basePrice = 750,
        profit =  90),
    ProductInformation(
        productName = "Sweets",
        imageUrl = "$BASE_URL/images/Sweets.jpg",
        noOfCartons = 70,
        basePrice = 300,
        profit =  20),
    ProductInformation(
        productName = "Normal Milk Can",
        imageUrl = "$BASE_URL/images/TinMilk.jpg",
        noOfCartons = 70,
        basePrice = 850,
        profit =  120),
    ProductInformation(
        productName = "ToothBrush",
        imageUrl = "$BASE_URL/images/Toothbrush.jpg",
        noOfCartons = 70,
        basePrice = 460,
        profit =  45),
    ProductInformation(
        productName = "ToothPaste",
        imageUrl = "$BASE_URL/images/Toothpaste.jpg",
        noOfCartons = 70,
        basePrice = 500,
        profit =  30),

    )
}
