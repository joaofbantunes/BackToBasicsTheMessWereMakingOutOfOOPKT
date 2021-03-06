package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.shared.Either

class MaximumQuantityPerSaleRule(private val maximumQuantityPerSale: Int) : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Either<Error, Unit> =
        if (quantity > maximumQuantityPerSale) Either.left(Error.Invalid("Quantity not allowed")) else Either.right(Unit)
}