package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.shared.Result

class NoopItemSaleRule : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Result<Unit> = noopResult

    companion object {
        val noopResult = Result.Success(Unit);
    }
}