package com.kobayashi.refrigeratormanager.entity

import androidx.room.*
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "door",
        foreignKeys = [ForeignKey(
            entity = Refrigerator::class,
            parentColumns = ["id"],
            childColumns = ["ref_id"]
        )],
        indices = [Index(value = ["door_id"], unique = true), Index(value = ["ref_id"])]
)
class Door {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "door_id")
    var doorId: Int = 0

    @ColumnInfo(name = "ref_id")
    var refId: Int = 0

    @ColumnInfo(name = "door_num")
    var doorNum: Int = 0

    @ColumnInfo(name = "door_name")
    var doorName: String? = null

    @ColumnInfo(name = "del_flg")
    var delFlg: Boolean = true

    @ColumnInfo(name = "ins_date")
    var insDate: Date? = null

    @ColumnInfo(name = "upd_date")
    var updDate: Date? = null
}