package de.skycave.chestrefiller.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.bukkit.inventory.ItemStack

class ChestTemplate {

    @BsonId
    lateinit var id: ObjectId
    lateinit var name: String
    lateinit var items: List<ItemStack>

    constructor()

    constructor(id: ObjectId, name: String, items: List<ItemStack>) {
        this.id = id
        this.name = name
        this.items = items
    }

}
