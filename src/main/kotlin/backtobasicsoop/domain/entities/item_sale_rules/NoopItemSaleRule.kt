package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.shared.Either

class NoopItemSaleRule : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Either<Error, Unit> = noopResult

    companion object {
        val noopResult = Either.right(Unit)
    }
}