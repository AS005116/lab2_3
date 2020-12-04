package com.example.lab2_3mt.models

import com.google.gson.annotations.SerializedName

data class Product(
        @SerializedName("name")
        val title: String,
        val price: Double,
        val description: String
)