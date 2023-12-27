package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
             Log.d("Test",it.toString())
             for (i in 0 until it.size) {
               if (it.get(i).id== 0) {
                    viewModel.deleteElement(it.get(i))
                }
                 if (it.get(i).id== 1 && it.get(i).enabled == true) {
                     viewModel.changeEnElement(it.get(i))
                 }
             }
        }

    }
}