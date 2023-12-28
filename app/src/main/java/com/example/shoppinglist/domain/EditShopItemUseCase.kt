package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem (shopItem: ShopItem, pos: Int) {
        shopListRepository.editShopItem(shopItem, pos)
    }
}