package com.example.shoppinglist.domain

class InsertShopItemUseCase (private val shopListRepository: ShopListRepository) {

    fun insertShopItem(shopItem : ShopItem, pos: Int) {
        shopListRepository.insertShopItem(shopItem, pos)
    }
}