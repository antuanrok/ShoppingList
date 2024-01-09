package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {


    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val lay = when (viewType) {
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
        val shopItem = getItem(position)//shopList.get(position)
        holder.tv_boug.text = "${shopItem.name}"
        holder.tv_price.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }


    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)//shopList.get(position)
        return if (shopItem.enabled) {
            ITEM_VIEW_TYPE_ENABLED
        } else {
            ITEM_VIEW_TYPE_DISABLED
        }
    }


    companion object {
        const val ITEM_VIEW_TYPE_ENABLED = 0
        const val ITEM_VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 5
    }
}