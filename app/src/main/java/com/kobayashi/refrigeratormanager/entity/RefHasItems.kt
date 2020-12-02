package com.kobayashi.refrigeratormanager.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RefHasItems (
    @Embedded val ref: Refrigerator,
    @Relation(parentColumn = "id", entityColumn = "ref_id")
    val itemList: List<Items>
)