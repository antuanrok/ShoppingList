package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llShopList = findViewById(R.id.ll_shop_list);
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it);
        }

    }

    private fun showList(list: List<ShopItem>) {
        llShopList.removeAllViews();
        for (shopItem in list) {
            val id = if (shopItem.enabled) {
                R.layout.item_shop_enabled;
            } else {
                R.layout.item_shop_disabled;
            }
            val view = LayoutInflater.from(this).inflate(id,llShopList,false);
            val tv_boug = view.findViewById<TextView>(R.id.tv_boug)
            val tv_price = view.findViewById<TextView>(R.id.tv_price)
            tv_boug.text = shopItem.name
            tv_price.text = shopItem.count.toString()
            view.setOnLongClickListener{
                val pos = list.indexOf(shopItem)
                viewModel.changeEnElement(shopItem,pos)
                true
            }
            llShopList.addView(view);
        }
    }
}