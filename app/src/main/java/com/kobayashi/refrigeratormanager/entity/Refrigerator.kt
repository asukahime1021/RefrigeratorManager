package com.kobayashi.refrigeratormanager.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Refrigerator (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "ref_name")
    var refName: String = "",
    @ColumnInfo(name = "door_count")
    var doorCount: Int = 0,
    @ColumnInfo(name = "del_flg")
    var delFlg: Boolean = true,
    @ColumnInfo(name = "ins_date")
    var insDate: Date?,
    @ColumnInfo(name = "upd_date")
    var updDate: Date?
)