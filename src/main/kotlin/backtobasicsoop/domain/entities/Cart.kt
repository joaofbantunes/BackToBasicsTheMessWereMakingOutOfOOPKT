package backtobasicsoop.domain.entities

import backtobasicsoop.shared.Result

class Cart private constructor(val id: CartId, private val itemsMap: HashMap<ItemId, CartItem>) {
    val items: Collection<CartItem>
        get() = itemsMap.values

    fun addItemToCart(item: Item, quantity: Int): Result<CartItem>
    {
        if (itemsMap.containsKey(item.id))
        {
            return Result.Error.Invalid("Item ${item.id} already in the cart.")
        }

        val cartItem = CartItem(item.id, quantity)
        itemsMap[item.id] = cartItem
        return Result.Success(cartItem)
    }

    fun updateItemInCart(item: Item, quantity: Int): Result<CartItem>
    {
        if (!itemsMap.containsKey(item.id))
        {
            return Result.Error.Invalid("Item ${item.id} not in the cart.")
        }

        val cartItem = CartItem(item.id, quantity)
        itemsMap[item.id] = cartItem
        return Result.Success(cartItem)
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