package com.kobayashi.refrigeratormanager.repository

import com.kobayashi.refrigeratormanager.dao.GenreMstDao
import com.kobayashi.refrigeratormanager.entity.GenreMst

class GenreMstRepository(private val genreMstDao: GenreMstDao) {

    fun selectAll(): List<GenreMst>{
        return genreMstDao.selectAll()
    }

    suspend fun insert(entity: GenreMst){
        genreMstDao.insert(entity)
    }
}