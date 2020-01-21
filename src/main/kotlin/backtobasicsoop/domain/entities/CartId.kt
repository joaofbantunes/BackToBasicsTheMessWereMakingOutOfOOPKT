package backtobasicsoop.domain.entities

import java.util.*

// TODO: couldn't get serialization/deserialization to work with inline classes, try again later
/*inline*/ data class CartId(val value: UUID) {
    companion object {
        fun new() : CartId = CartId(UUID.randomUUID())
    }
}