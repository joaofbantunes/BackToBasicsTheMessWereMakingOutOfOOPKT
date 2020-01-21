package backtobasicsoop.domain.services

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.CartItem
import backtobasicsoop.domain.entities.Item


interface PostAddItemToCartListener {
    fun onAdded(cart: Cart, item: Item, cartItem: CartItem)
}