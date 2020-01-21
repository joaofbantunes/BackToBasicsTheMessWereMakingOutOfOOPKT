package backtobasicsoop.domain.data

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.CartId


interface CartRepository {
    fun get(id: CartId): Cart?

    fun save(cart: Cart): Cart

    fun delete(id: CartId)
}