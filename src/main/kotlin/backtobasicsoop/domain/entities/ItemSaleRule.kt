package backtobasicsoop.domain.entities

import backtobasicsoop.domain.Error
import backtobasicsoop.shared.Either

interface ItemSaleRule {
    fun validate(cart: Cart, item: Item, quantity: Int): Either<Error, Unit>
}