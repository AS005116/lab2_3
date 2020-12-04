package com.example.lab2_3mt.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductFromDatabase (
    @PrimaryKey(autoGenerate = true) val uId: Int?,
    @ColumnInfo val title: String,
    @ColumnInfo val price: Double,
    @ColumnInfo val description: String

)