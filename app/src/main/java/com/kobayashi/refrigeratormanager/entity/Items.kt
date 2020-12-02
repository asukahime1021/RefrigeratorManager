package com.kobayashi.refrigeratormanager.entity

import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(tableName = "items",
        foreignKeys = [
            ForeignKey(entity = Refrigerator::class, parentColumns = ["id"], childColumns = ["ref_id"])
        ],
        indices = [Index("ref_id"), Index("item_id")]
)
class Items: Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var itemId: Int = 0

    @ColumnInfo(name = "ref_id")
    var refId: Int = 0

    @ColumnInfo(name = "door_num")
    var doorNum: Int = 0

    @ColumnInfo(name = "genre_num")
    var genreNum: Int = 0

    @ColumnInfo(name = "item_name")
    var itemName: String = ""

    @ColumnInfo(name = "memo")
    var memo: String = ""

    @ColumnInfo(name = "remain_amount")
    var remainAmount: Int = 0

    @ColumnInfo(name = "expire_date")
    var expireDate: Date? = null

    @ColumnInfo(name = "del_flg")
    var delFlg: Boolean = true

    @ColumnInfo(name = "ins_date")
    var insDate: Date? = null

    @ColumnInfo(name = "upd_date")
    var updDate: Date? = null
}