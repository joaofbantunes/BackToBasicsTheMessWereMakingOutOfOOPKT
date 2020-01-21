package backtobasicsoop.domain.services.post_add_item_to_cart_listeners

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.CartItem
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.services.PostAddItemToCartListener

class CompositePostAddItemToCartListener(private val listeners: Sequence<PostAddItemToCartListener>) :
    PostAddItemToCartListener {
    override fun onAdded(cart: Cart, item: Item, cartItem: CartItem) =
        listeners.forEach { it.onAdded(cart, item, cartItem) }
}