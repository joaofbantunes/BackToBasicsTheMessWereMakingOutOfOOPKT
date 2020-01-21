package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.domain.entities.TimeOfDay
import backtobasicsoop.shared.Result

class MinimumTimeOfDayForSaleRule(private val minimumTimeOfDay: TimeOfDay) : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Result<Unit> =
        if (minimumTimeOfDay > TimeOfDay.now) Result.Error.Invalid("Can't buy that yet!") else Result.Success(Unit)
}