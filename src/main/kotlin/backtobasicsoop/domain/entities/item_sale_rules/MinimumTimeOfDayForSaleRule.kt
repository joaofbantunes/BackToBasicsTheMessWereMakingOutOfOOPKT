package backtobasicsoop.domain.entities.item_sale_rules

import backtobasicsoop.domain.Error
import backtobasicsoop.domain.entities.Cart
import backtobasicsoop.domain.entities.Item
import backtobasicsoop.domain.entities.ItemSaleRule
import backtobasicsoop.domain.entities.TimeOfDay
import backtobasicsoop.shared.Either

class MinimumTimeOfDayForSaleRule(private val minimumTimeOfDay: TimeOfDay) : ItemSaleRule {
    override fun validate(cart: Cart, item: Item, quantity: Int): Either<Error, Unit> =
        if (minimumTimeOfDay > TimeOfDay.now) Either.left(Error.Invalid("Can't buy that yet!")) else Either.right(Unit)
}