package com.example.lab2_3mt.database

import androidx.room.*
import com.example.lab2_3mt.models.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductFromDatabase")
    fun getAll(): List<ProductFromDatabase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: ProductFromDatabase)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(products: ProductFromDatabase)
}