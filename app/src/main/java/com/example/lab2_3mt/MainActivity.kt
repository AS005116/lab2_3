package com.example.lab2_3mt

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.lab2_3mt.database.AppDatabase
import com.example.lab2_3mt.database.ProductFromDatabase
import com.example.lab2_3mt.models.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database"
        ).build()

        doAsync {
            var products : List<Product> =
                    db.productDao().getAll().map {
                        Product(
                                it.title,
                                it.price,
                                it.description)
                    }
            if(InternetConnection.isOnline(this@MainActivity))
            {
                val json = URL("https://finepointmobile.com/data/products.json")
                        .readText()
                products = Gson().fromJson(json, Array<Product>::class.java).toList()
                if (db.productDao().getAll().isEmpty()){
                    products.forEach{
                        db.productDao().insertAll(ProductFromDatabase(
                                null,
                                it.title,
                                it.price,
                                it.description))
                    }
                }
                else{
                    products.forEach{
                        db.productDao().updateAll(ProductFromDatabase(
                                null,
                                it.title,
                                it.price,
                                it.description))
                    }
                }
            }
            uiThread {
                if (products.isEmpty()){
                    AlertDialog.Builder(this@MainActivity)
                            .setMessage("Don't have information")
                            .setPositiveButton("Ok"){p0,p1 ->
                            }
                            .create()
                            .show()
                }
                else {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = ProductsAdapter(products)
                    }
                }
            }
        }
    }
}