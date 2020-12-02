package com.kobayashi.refrigeratormanager.repository

import com.kobayashi.refrigeratormanager.dao.RefrigeratorDao
import com.kobayashi.refrigeratormanager.entity.Refrigerator

class RefrigeratorRepository(private val refDao: RefrigeratorDao) {

    val allRefs = refDao.selectAllLive()

    suspend fun insert(ref: Refrigerator){
        refDao.insert(ref)
    }

    suspend fun update(ref: Refrigerator): Int{
        return refDao.update(ref)
    }

    suspend fun delete(ref: Refrigerator){
        refDao.delete(ref)
    }

    suspend fun deleteAll(){
        refDao.deleteAll()
    }

    suspend fun getRefrigeratorById(id: Int): Refrigerator?{
        return refDao.selectById(id)
    }

    fun getRefrigeratorByName(refName: String): Refrigerator?{
        return refDao.selectByName(refName)
    }

    fun getRefCount(): Int{
        return refDao.selectCount()
    }
}