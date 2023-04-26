package com.example.`e-shopping`.usertDataStore

import com.example.`e-shopping`.myData.ComputedClientOrderDetails
import com.example.clientOrderDatabase
import org.litote.kmongo.coroutine.CoroutineFindPublisher


interface ClientComputedOderDataSource{
    suspend fun getClientOder():CoroutineFindPublisher<ComputedClientOrderDetails>
    suspend fun addClientOder(client: ComputedClientOrderDetails):Boolean
    suspend fun delete(client: ComputedClientOrderDetails):Boolean
}
class ClientComputedOderDataStoreImpl:ClientComputedOderDataSource{
    override suspend fun getClientOder(): CoroutineFindPublisher<ComputedClientOrderDetails> {
        return clientOrderDatabase.find()
    }

    override suspend fun addClientOder(client: ComputedClientOrderDetails): Boolean {
        return clientOrderDatabase.insertOne(client).wasAcknowledged()
    }
    override suspend fun delete(client: ComputedClientOrderDetails):Boolean{
        var flag = false
         for(i in client.customers) {
           flag =  clientOrderDatabase.deleteOneById(i.id).wasAcknowledged()
         }
        return flag
    }

}