package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


   // var shopList = MutableLiveData<List<ShopItem>>()
   var shopList = getShopListUseCase.getShopList()

    /*fun getShopList() {
        //val list = getShopListUseCase.getShopList()
        //shopList.postValue(list)
        getShopListUseCase.getShopList()
    }*/

    fun deleteElement(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnElement(shopItem: ShopItem) {
        val shopItem_t = ShopItem(shopItem.name,shopItem.count,!shopItem.enabled,shopItem.id)
        //val shopItem_t2 = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopItem_t)
    }

}