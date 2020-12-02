package com.kobayashi.refrigeratormanager.dao

import androidx.room.*
import com.kobayashi.refrigeratormanager.entity.Items

@Dao
interface ItemsDao {
    @Query("SELECT * FROM items WHERE ref_id = :refId AND del_flg = 0")
    suspend fun selectAllByRefIdLive(refId: Int) : MutableList<Items>

    @Query("SELECT * FROM items WHERE ref_id = :refId AND door_num = :doorNum")
    fun selectAllByRefIdDoorId(refId: Int, doorNum: Int): List<Items>

    @Query("SELECT * FROM items WHERE ref_id = :refId AND expire_date > datetime('now', '-2 days')")
    fun selectByExpireDate(refId: Int): MutableList<Items>

    @Query("SELECT * FROM items WHERE ref_id = :refId AND item_id = :itemId")
    fun selectById(refId: Int, itemId: Int): Items

    @Query("SELECT COUNT(*) FROM items WHERE ref_id = :refId AND item_id = :itemId AND del_flg = 'false'")
    fun getCount(refId: Int, itemId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Items)

    @Insert
    fun insertAll(itemsList: List<Items>)

    @Update
    fun update(items: Items)

    @Update
    fun updateAll(itemsList: List<Items>)
}