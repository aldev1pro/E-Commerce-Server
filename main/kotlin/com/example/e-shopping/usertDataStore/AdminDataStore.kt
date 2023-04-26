package com.example.`e-shopping`.usertDataStore

import com.example.`e-shopping`.myData.InformationForAdmin
import com.example.storeDetailsDatabase
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface AdminDataSource{
    suspend fun getStoreOder(): CoroutineFindPublisher<InformationForAdmin>
    suspend fun addStoreDetail(store: InformationForAdmin):Boolean
}
class AdminDataStoreImpl:AdminDataSource{
    override suspend fun getStoreOder(): CoroutineFindPublisher<InformationForAdmin> {
        return storeDetailsDatabase.find()
    }

    override suspend fun addStoreDetail(store: InformationForAdmin): Boolean {
        return storeDetailsDatabase.insertOne(store).wasAcknowledged()
    }

}