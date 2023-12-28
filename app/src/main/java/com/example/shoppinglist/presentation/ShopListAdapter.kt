package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private val shopList = listOf<ShopItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled,parent,false);
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList.get(position)
        holder.tv_boug.text = shopItem.name
        holder.tv_price.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener{
            //val pos = list.indexOf(shopItem)
            //viewModel.changeEnElement(shopItem,position)
            true
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder (view:View): RecyclerView.ViewHolder (view) {
        val tv_boug = view.findViewById<TextView>(R.id.tv_boug)
        val tv_price = view.findViewById<TextView>(R.id.tv_price)
    }
}