package com.kobayashi.refrigeratormanager.entity

import androidx.room.*

@Entity(tableName = "genre_mst",
        indices = [Index("id")])
data class GenreMst(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "genre_name")
    val genreName: String,

    @ColumnInfo(name = "del_flg")
    val delFlg: Boolean
) {
}