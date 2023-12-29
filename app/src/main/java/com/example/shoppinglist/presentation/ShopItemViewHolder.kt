package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tv_boug = view.findViewById<TextView>(R.id.tv_boug)
    val tv_price = view.findViewById<TextView>(R.id.tv_price)
}