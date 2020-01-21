package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.shared.Result

class MaximumQuantityPerSaleRule(private val maximumQuantityPerSale: Int) : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Result<Unit> =
        if (quantity > maximumQuantityPerSale) Result.Error.Invalid("Quantity not allowed") else Result.Success(Unit)
}