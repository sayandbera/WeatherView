package com.sayanx.weatherview.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl")
data class Favourite(

    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String

)
