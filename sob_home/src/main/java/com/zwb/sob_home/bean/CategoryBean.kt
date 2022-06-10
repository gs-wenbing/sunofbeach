package com.zwb.sob_home.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "channel")
data class CategoryBean(
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    var id: String,
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var categoryName: String,
    @ColumnInfo(name = "order")
    var order: Int=1,
    val description: String="",
    val createTime: String="",
    val pyName: String="",
    @ColumnInfo(name = "is_my")
    var isMy:Boolean = true
)
