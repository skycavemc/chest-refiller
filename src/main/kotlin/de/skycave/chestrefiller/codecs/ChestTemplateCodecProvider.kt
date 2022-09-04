package de.skycave.chestrefiller.codecs

import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry

class ChestTemplateCodecProvider: CodecProvider {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> get(clazz: Class<T>?, registry: CodecRegistry?): Codec<T>? {
        registry ?: return null
        if (clazz == ChestTemplateCodec::class.java) {
            return ChestTemplateCodec(registry) as Codec<T>
        }
        return null
    }

}