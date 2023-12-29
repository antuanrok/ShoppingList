package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class ShopListDiffCallback (
    private val oldList: List<ShopItem>,
    private val newList: List<ShopItem>
):DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return  newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       /* return oldList[oldItemPosition].enabled == newList[newItemPosition].enabled &&
                oldList[oldItemPosition].name == newList[newItemPosition].name &&
                 oldList[oldItemPosition].count == newList[newItemPosition].count*/
        return oldList[oldItemPosition] == newList[newItemPosition] // data class метлод equals переопределён, сравнение по полям
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}