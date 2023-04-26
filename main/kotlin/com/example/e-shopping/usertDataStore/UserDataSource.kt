package com.example.`e-shopping`.usertDataStore

import com.example.`e-shopping`.myData.ProductInformation
import com.example.`e-shopping`.myData.UserInformationSignUp
import com.example.storeDatabase
import com.example.userDatabase
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

interface UserDataSource {
  // suspend fun getAllUserData(email:String):UserInformation?
   suspend fun addUser(user:UserInformationSignUp):Boolean
   suspend fun checkEmail(email:String):UserInformationSignUp?
   suspend fun updateAccount(email: String,account: Int):Boolean
    fun getAllUsers():CoroutineFindPublisher<UserInformationSignUp>

}

class UserDataSourceImpl:UserDataSource{

//    override suspend fun getAllUserData(email: String): UserInformation? {
//        return userDatabase.findOne(UserInformation::email eq email)
//    }
    override suspend fun addUser(user: UserInformationSignUp): Boolean {
        return userDatabase.insertOne(user).wasAcknowledged()
    }
    override suspend fun checkEmail(email:String):UserInformationSignUp? {
        return userDatabase.findOne(UserInformationSignUp::email eq email)
    }
    //col.updateOne(friend::name eq "Paul", setValue(friend::name, "John"))
    override suspend fun updateAccount(email: String,account:Int):Boolean {
       return userDatabase.updateOne(UserInformationSignUp::email eq email, setValue(UserInformationSignUp::account,account)).wasAcknowledged()
    }
    override fun getAllUsers():CoroutineFindPublisher<UserInformationSignUp> {
        return userDatabase.find()
    }

}



