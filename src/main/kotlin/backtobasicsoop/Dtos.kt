package backtobasicsoop

import backtobasicsoop.domain.entities.ItemId

data class AddItemToCartRequestDto(val itemId: ItemId, val quantity: Int)