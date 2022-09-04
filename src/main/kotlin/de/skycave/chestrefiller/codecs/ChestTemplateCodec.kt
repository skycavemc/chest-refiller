package de.skycave.chestrefiller.codecs

import de.skycave.chestrefiller.models.ChestTemplate
import org.bson.BsonReader
import org.bson.BsonType
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry
import org.bukkit.inventory.ItemStack

class ChestTemplateCodec(codecRegistry: CodecRegistry): Codec<ChestTemplate> {

    private val itemStackCodec: Codec<ItemStack>

    init {
        itemStackCodec = codecRegistry.get(ItemStack::class.java)
    }

    override fun encode(writer: BsonWriter?, value: ChestTemplate?, encoderContext: EncoderContext?) {
        writer ?: return
        if (value == null) {
            writer.writeNull()
            return
        }

        writer.writeStartDocument()
        writer.writeName("name")
        writer.writeString(value.name)
        writer.writeName("items")
        writer.writeStartArray()
        for (item in value.items) {
            itemStackCodec.encode(writer, item, encoderContext)
        }
        writer.writeEndArray()
        writer.writeEndDocument()
    }

    override fun decode(reader: BsonReader?, decoderContext: DecoderContext?): ChestTemplate {
        val chestTemplate = ChestTemplate()
        reader ?: return chestTemplate

        reader.readStartDocument()
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            when (reader.readName()) {
                "_id" -> chestTemplate.id = reader.readObjectId()
                "name" -> chestTemplate.name = reader.readString()
                "items" -> {
                    val items = ArrayList<ItemStack>()
                    reader.readStartArray()
                    while (reader.readBsonType() == BsonType.BINARY) {
                        items.add(itemStackCodec.decode(reader, decoderContext))
                    }
                    reader.readEndArray()
                    chestTemplate.items = items
                }
                else -> reader.skipValue()
            }
        }
        reader.readEndDocument()
        return chestTemplate
    }

    override fun getEncoderClass(): Class<ChestTemplate> {
        return ChestTemplate::class.java
    }

}