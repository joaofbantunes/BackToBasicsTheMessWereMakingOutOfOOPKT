package backtobasicsoop.domain.entities

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.shared.Result


interface ItemSaleRule {
    fun validate(cart: Cart, item: Item, quantity: Int): Result<Unit>
}