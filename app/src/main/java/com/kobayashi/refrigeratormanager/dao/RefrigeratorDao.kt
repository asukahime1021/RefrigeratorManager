package com.kobayashi.refrigeratormanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kobayashi.refrigeratormanager.entity.Refrigerator

@Dao
interface RefrigeratorDao {

    @Query("SELECT COUNT(*) FROM refrigerator")
    fun selectCount(): Int

    @Query("SELECT * FROM refrigerator WHERE id = :refId")
    fun selectById(refId: Int): Refrigerator?

    @Query("SELECT * FROM refrigerator WHERE ref_name = :refName")
    fun selectByName(refName: String): Refrigerator?

    @Query("SELECT * FROM refrigerator WHERE del_flg = 0")
    fun selectAllLive(): LiveData<List<Refrigerator>>

    @Insert
    fun insert(refrigerator: Refrigerator)

    @Update
    fun update(refrigerator: Refrigerator): Int

    @Delete
    fun delete(refrigerator: Refrigerator)

    @Query("DELETE FROM refrigerator")
    fun deleteAll()
}