package backtobasicsoop.domain.entities

data class CartItem(val itemId: ItemId, val quantity: Int) {
    init {
        if (quantity <= 0) {
            throw IllegalArgumentException("Quantity must be greater than 0")
        }
    }
}