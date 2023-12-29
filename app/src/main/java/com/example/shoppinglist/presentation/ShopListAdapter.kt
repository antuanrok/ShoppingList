package com.example.shoppinglist.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {


   // var onShopItemLongClickListener: OnShopItemLongClickListener? = null

    var onShopItemLongClickListener: ((ShopItem)->Unit)? = null
    var onShopItemClickListener: ((ShopItem)->Unit)? = null

            var shopList = listOf<ShopItem>()
        set(value) {
            val diffCallback = ShopListDiffCallback(shopList,value)
            val diffRes = DiffUtil.calculateDiff(diffCallback)
            diffRes.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val lay = when(viewType) {
            ITEM_VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            ITEM_VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            lay,
            parent,
            false
        );
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList.get(position)
        holder.tv_boug.text = "${shopItem.name}"
        holder.tv_price.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        /*if (shopItem.enabled) {
            holder.tv_boug.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.holo_red_light))
        } else {
            holder.tv_boug.setTextColor(ContextCompat.getColor(holder.itemView.context,android.R.color.white))
        }*/
    }

    override fun getItemCount(): Int {
        return shopList.size
    }



    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList.get(position)
        return if (shopItem.enabled) {
            ITEM_VIEW_TYPE_ENABLED
        }else {
            ITEM_VIEW_TYPE_DISABLED
        }
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_boug = view.findViewById<TextView>(R.id.tv_boug)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
    }



    interface  OnShopItemLongClickListener{
        fun onShopItemLongClick(shopItem: ShopItem)
    }

    companion object {
        const val ITEM_VIEW_TYPE_ENABLED = 0
        const val ITEM_VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 5
    }
}