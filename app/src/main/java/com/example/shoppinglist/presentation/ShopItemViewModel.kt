package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.GetShopListUseCase
import com.example.shoppinglist.domain.ShopItem
import java.util.concurrent.CountDownLatch

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private var _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private var _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private var _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private var _canCloseAcivity = MutableLiveData<Unit>()
    val canCloseAcivity: LiveData<Unit>
        get() = _canCloseAcivity


    // var shopList = MutableLiveData<List<ShopItem>>()
    // var shopList = getShopListUseCase.getShopList()

    /*fun getShopList() {
        //val list = getShopListUseCase.getShopList()
        //shopList.postValue(list)
        getShopListUseCase.getShopList()
    }*/

    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.postValue(item)
    }

    fun addElement(name: String?, count: String?) {
        val name_t = parseName(name)
        val count_t = parseCount(count)
        val fieldsOk = validateInput(name_t, count_t)
        if (fieldsOk) {
            val shopItem = ShopItem(name_t, count_t, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editElement(name: String?, count: String?) {
        val name_t = parseName(name)
        val count_t = parseCount(count)
        val fieldsOk = validateInput(name_t, count_t)
        if (fieldsOk) {
            _shopItem.value?.let {
                val item = it.copy(name=name_t,count = count_t)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }


    }

    private fun parseCount(input: String?): Int {
        return try {
            input?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }

    }

    private fun parseName(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var res = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            res = false
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            res = false
        }
        return res
    }

    fun resetErrorInputName() {
        _errorInputName.postValue(false)
    }

    fun resetErrorInputCount() {
        _errorInputCount.postValue(false)
    }

    fun finishWork() {
        _canCloseAcivity.postValue(Unit)
    }

}