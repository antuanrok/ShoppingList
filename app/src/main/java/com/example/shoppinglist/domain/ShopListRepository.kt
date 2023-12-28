package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun addShopItem(shopItem : ShopItem)
    fun deleteShopItem (shopItem : ShopItem)
    fun editShopItem (shopItem: ShopItem)
    fun editShopItem (shopItem: ShopItem, pos: Int)
    fun getShopItem (id: Int) : ShopItem
    fun getShopList(): LiveData<List<ShopItem>>
    fun insertShopItem(shopItem: ShopItem, pos: Int)
}