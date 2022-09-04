package de.skycave.chestrefiller

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import de.skycave.chestrefiller.annotations.Prefix
import de.skycave.chestrefiller.codecs.ChestCodecProvider
import de.skycave.chestrefiller.codecs.ChestTemplateCodecProvider
import de.skycave.chestrefiller.codecs.ItemStackCodec
import de.skycave.chestrefiller.codecs.LocationCodec
import de.skycave.chestrefiller.commands.ChestRefillCommand
import de.skycave.chestrefiller.listeners.InteractListener
import de.skycave.chestrefiller.listeners.LeaveListener
import de.skycave.chestrefiller.models.Chest
import de.skycave.chestrefiller.models.ChestTemplate
import de.skycave.chestrefiller.models.SkyCavePlugin
import org.bson.codecs.configuration.CodecRegistries
import org.bukkit.entity.Player

@Prefix("&fSky&3Cave &8» ")
class ChestRefiller: SkyCavePlugin() {

    val chestSetMode = HashMap<Player, String>()
    lateinit var mongoClient: MongoClient
        private set
    lateinit var chests: MongoCollection<Chest>
        private set
    lateinit var chestTemplates: MongoCollection<ChestTemplate>
        private set

    override fun onEnable() {
        super.onEnable()

        // Initialize database
        val registry = CodecRegistries.fromRegistries(
            CodecRegistries.fromCodecs(ItemStackCodec(), LocationCodec()),
            CodecRegistries.fromProviders(ChestCodecProvider(), ChestTemplateCodecProvider()),
            MongoClientSettings.getDefaultCodecRegistry()
        )
        val settings = MongoClientSettings.builder().codecRegistry(registry).build()
        mongoClient = MongoClients.create(settings)
        val db = mongoClient.getDatabase("chest_refiller")
        chests = db.getCollection("chests", Chest::class.java)
        chestTemplates = db.getCollection("chest_templates", ChestTemplate::class.java)

        // Register commands
        registerCommand("chestrefill", ChestRefillCommand(this))

        // Register events
        registerEvents(
            InteractListener(this),
            LeaveListener(this)
        )

        TODO("Refill chests")
    }



}