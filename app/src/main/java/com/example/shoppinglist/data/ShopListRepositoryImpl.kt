package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.util.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()

    private val shopList = mutableListOf<ShopItem>()
    //private val shopList = sortedSetOf<ShopItem>({o1,o2 -> o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init {
     for (i in 0..100) {
         val t = kotlin.random.Random.nextBoolean()
         val shopItem = ShopItem("Name $i",5,t)
         addShopItem(shopItem)
     }
    }
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem);
        updateList()
    }


    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun insertShopItem(shopItem: ShopItem, pos: Int) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(pos,shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem, pos: Int) {
        val oldEl= getShopItem(shopItem.id)
        shopList.remove(oldEl)
        insertShopItem(shopItem,pos)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        //updateList()
        return shopListLD
    }

    private fun updateList(){
        shopListLD.postValue(shopList.toList())
    }
}