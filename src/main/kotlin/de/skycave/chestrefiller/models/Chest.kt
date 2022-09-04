package de.skycave.chestrefiller.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.bukkit.Location

class Chest {

    @BsonId
    lateinit var id: ObjectId
    lateinit var name: String
    lateinit var location: Location

    constructor()

    constructor(id: ObjectId, name: String, location: Location) {
        this.id = id
        this.name = name
        this.location = location
    }

}