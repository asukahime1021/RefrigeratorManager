package com.kobayashi.refrigeratormanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobayashi.refrigeratormanager.entity.GenreMst

@Dao
interface GenreMstDao {
    @Query("SELECT * FROM GENRE_MST WHERE del_flg = 'false'")
    fun selectAll(): List<GenreMst>

    @Insert
    fun insert(entity : GenreMst)
}