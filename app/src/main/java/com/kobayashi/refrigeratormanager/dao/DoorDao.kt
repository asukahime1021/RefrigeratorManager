package com.kobayashi.refrigeratormanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobayashi.refrigeratormanager.entity.Door

@Dao
interface DoorDao {
    @Query("SELECT * FROM door WHERE ref_id = :refId ORDER BY door_num")
    fun selectAllByRefId(refId: Int): List<Door>

    @Insert
    fun insert(door: Door)
}