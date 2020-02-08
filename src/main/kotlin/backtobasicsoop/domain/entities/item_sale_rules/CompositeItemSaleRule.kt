package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.shared.Either

class CompositeItemSaleRule(private val rules: Sequence<ItemSaleRule>) : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Either<Error, Unit> {
        for (rule in rules) {
            val result = rule.validate(cart, item, quantity)

            if (result is Either.Left) {
                return result
            }
        }
        return Either.right(Unit)
    }
}