package backtobasicsoop.domain.entities

import java.util.*

// TODO: couldn't get serialization/deserialization to work with inline classes, try again later
/*inline*/ data class ItemId(val value: UUID){
    companion object {
        fun new() : ItemId = ItemId(UUID.randomUUID())
    }
}