package backtobasicsoop.infrastructure.data

import backtobasicsoop.domain.data.CartRepository
import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.CartId
import java.util.*
import kotlin.collections.HashMap

class InMemoryCartRepository : CartRepository{
    private val carts: HashMap<CartId, Cart> = hashMapOf(
        CartId(UUID.fromString("240cc697-8120-47c5-8fb4-1ff7ccd7232a")) to
        Cart.from(
            CartId(UUID.fromString("240cc697-8120-47c5-8fb4-1ff7ccd7232a")),
            sequenceOf())
    )

    override fun get(id: CartId): Cart? {
        val cart = carts[id] ?: return null
        return Cart.from(cart.id, cart.items.asSequence())
    }

    override fun save(cart: Cart): Cart {
        carts[cart.id] = Cart.from(cart.id, cart.items.asSequence())
        return cart
    }

    override fun delete(id: CartId) {
        carts.remove(id)
    }
}