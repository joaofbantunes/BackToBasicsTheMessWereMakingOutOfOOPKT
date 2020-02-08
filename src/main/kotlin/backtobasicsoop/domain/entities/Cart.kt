package backtobasicsoop.domain.entities

import backtobasicsoop.domain.Error
import backtobasicsoop.shared.Either

class Cart private constructor(val id: CartId, private val itemsMap: HashMap<ItemId, CartItem>) {
    val items: Collection<CartItem>
        get() = itemsMap.values

    fun addItemToCart(item: Item, quantity: Int): Either<Error, CartItem>
    {
        if (itemsMap.containsKey(item.id))
        {
            return Either.left(Error.Invalid("Item ${item.id} already in the cart."))
        }

        val cartItem = CartItem(item.id, quantity)
        itemsMap[item.id] = cartItem
        return Either.right(cartItem)
    }

    fun updateItemInCart(item: Item, quantity: Int): Either<Error, CartItem>
    {
        if (!itemsMap.containsKey(item.id))
        {
            return Either.left(Error.Invalid("Item ${item.id} not in the cart."))
        }

        val cartItem = CartItem(item.id, quantity)
        itemsMap[item.id] = cartItem
        return Either.right(cartItem)
    }

    fun removeItemFromCart(itemId: ItemId) : Unit
    {
        itemsMap.remove(itemId)
    }

    companion object {
        fun new(): Cart = Cart(CartId.new(), HashMap())

        fun from(id: CartId, items: Sequence<CartItem>): Cart = Cart(id, HashMap(items.map { Pair(it.itemId, it) }.toMap()))
    }
}