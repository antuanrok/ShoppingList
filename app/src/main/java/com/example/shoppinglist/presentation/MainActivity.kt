package com.example.shoppinglist.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterSL: ShopListAdapter
    private lateinit var rvShopList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        setupRecyclerView(rvShopList)
        setupOnLongClickListener()
        setupOnClickListener()
        setupOnSwiped(rvShopList)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapterSL.shopList = it
        }
    }

    private fun setupRecyclerView(rvShopList: RecyclerView) {
        with(rvShopList) {
            adapterSL = ShopListAdapter()
            adapter = adapterSL
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ITEM_VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ITEM_VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        /*adapterSL.onShopItemLongClickListener = object : ShopListAdapter.OnShopItemLongClickListener {
            override fun onShopItemLongClick(shopItem: ShopItem) {
               viewModel.changeEnElement(shopItem)
            }
        }*/
    }

    private fun setupOnSwiped(rvShopList: RecyclerView?) {
        val simpleCallBack =
            object : SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = adapterSL.shopList.get(viewHolder.adapterPosition)
                    viewModel.deleteElement(item)
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun  setupOnClickListener() {
        adapterSL.onShopItemClickListener = {
            //viewModel.changeEnElement(it)
            //Toast.makeText(context, "Id = ${it.id}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setupOnLongClickListener() {
        adapterSL.onShopItemLongClickListener = {
            viewModel.changeEnElement(it)
        }
    }

}