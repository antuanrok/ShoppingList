package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.presentation.ShopItemActivity.Companion.newIntentAddItem
import com.example.shoppinglist.presentation.ShopItemActivity.Companion.newIntentEditItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ActivityInterractor {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterSL: ShopListAdapter
    private lateinit var rvShopList: RecyclerView
    private var shopItemContainer: FragmentContainerView? = null
    private var countF = 0


    override fun onFragmentClosed() {
        if (orientIsLand()) {
            countF--
            if (countF == 0) {
                shopItemContainer?.background =
                    ContextCompat.getDrawable(applicationContext, R.drawable.buy_girl)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopItemContainer = findViewById(R.id._shop_item_container)
        setupRecyclerView(rvShopList)
        setupOnLongClickListener()
        setupOnClickListener()
        setupOnSwiped(rvShopList)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adapterSL.submitList(it)
        }

        countF = 0;

        val butAddItem = findViewById<FloatingActionButton>(R.id._but_add_shop_item)
        butAddItem.setOnClickListener {
            if (!orientIsLand()) {
                val intent = newIntentAddItem(this)
                startActivity(intent)
            } else {
                shopItemContainer?.background =
                    ContextCompat.getDrawable(applicationContext, R.drawable.custom_background)
                chooseRightRegim(MODE_ADD, ShopItem.UNDEFINED_ID)
            }
        }
    }


    private fun chooseRightRegim(mode: String, id: Int) {
        val fragment = when (mode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(id)
            else -> throw RuntimeException("Unknown screen mode $mode")
        }
        countF++
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id._shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
                    val item = adapterSL.currentList[viewHolder.adapterPosition]
                    viewModel.deleteElement(item)
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun orientIsLand(): Boolean {
        return shopItemContainer != null
    }

    private fun setupOnClickListener() {
        adapterSL.onShopItemClickListener = {
            val id = it.id
            if (!orientIsLand()) {
                val intent = newIntentEditItem(this, id)
                //     Toast.makeText(this, id,Toast.LENGTH_LONG).show()
                startActivity(intent)
            } else {
                shopItemContainer?.background =
                    ContextCompat.getDrawable(applicationContext, R.drawable.custom_background)
                chooseRightRegim(MODE_EDIT, id)
            }
        }
    }


    private fun setupOnLongClickListener() {
        adapterSL.onShopItemLongClickListener = {
            viewModel.changeEnElement(it)
        }
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
    }

}