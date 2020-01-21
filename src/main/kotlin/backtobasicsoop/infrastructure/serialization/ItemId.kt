package backtobasicsoop.infrastructure.serialization

import backtobasicsoop.domain.entities.ItemId
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.util.*


class ItemIdSerializer : JsonSerializer<ItemId>() {
    override fun serialize(value: ItemId, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(value.value.toString())
    }
}

class ItemIdDeserializer : JsonDeserializer<ItemId>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ItemId {
        return ItemId(UUID.fromString(p.valueAsString))
    }
}