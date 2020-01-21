package backtobasicsoop.domain.services.post_add_item_to_cart_listeners

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.CartItem
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemId
import backtobasicsoop.domain.services.ItemNotifier
import backtobasicsoop.domain.services.PostAddItemToCartListener

class WatchlistNotifierListener(private val notifier: ItemNotifier, itemsInWatchlist: Sequence<ItemId>) :
    PostAddItemToCartListener {

    private val watchlist: Set<ItemId> = itemsInWatchlist.toSet()

    override fun onAdded(cart: Cart, item: Item, cartItem: CartItem) {
        if (watchlist.contains(item.id)) {
            notifier.notify(item.id)
        }
    }
}