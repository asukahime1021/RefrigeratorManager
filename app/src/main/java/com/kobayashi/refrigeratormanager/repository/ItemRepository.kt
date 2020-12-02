package com.kobayashi.refrigeratormanager.repository

import com.kobayashi.refrigeratormanager.dao.ItemsDao
import com.kobayashi.refrigeratormanager.entity.Items

class ItemRepository(private val refId: Int, private val itemsDao: ItemsDao) {

    suspend fun getAllItems(): MutableList<Items> {
        return itemsDao.selectAllByRefIdLive(refId)
    }

    suspend fun update(items: Items) {
        return itemsDao.update(items)
    }

    suspend fun insert(items: Items) {
        return itemsDao.insert(items)
    }

    suspend fun selectById(itemsId: Int): Items{
        return itemsDao.selectById(refId, itemsId)
    }

    suspend fun getCount(itemsId: Int): Int{
        return itemsDao.getCount(refId, itemsId)
    }
}