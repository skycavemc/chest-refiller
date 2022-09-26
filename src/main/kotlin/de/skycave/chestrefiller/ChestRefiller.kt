package de.skycave.chestrefiller

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import de.skycave.chestrefiller.codecs.ChestCodecProvider
import de.skycave.chestrefiller.codecs.ChestTemplateCodecProvider
import de.skycave.chestrefiller.codecs.ItemStackCodec
import de.skycave.chestrefiller.codecs.LocationCodec
import de.skycave.chestrefiller.commands.ChestRefillCommand
import de.skycave.chestrefiller.listeners.InteractListener
import de.skycave.chestrefiller.listeners.LeaveListener
import de.skycave.chestrefiller.models.Chest
import de.skycave.chestrefiller.models.ChestTemplate
import de.skycave.skycavelib.annotations.Prefix
import de.skycave.skycavelib.data.MessageRegistry
import de.skycave.skycavelib.models.SkyCavePlugin
import org.bson.codecs.configuration.CodecRegistries
import org.bukkit.entity.Player

@Prefix("&fSky&3Cave &8» ")
class ChestRefiller: SkyCavePlugin() {

    val messages = MessageRegistry(this)
    val chestSetMode = HashMap<Player, String>()
    lateinit var mongoClient: MongoClient
        private set
    lateinit var chests: MongoCollection<Chest>
        private set
    lateinit var chestTemplates: MongoCollection<ChestTemplate>
        private set

    override fun onEnable() {
        super.onEnable()
        registerMessages()

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

    private fun registerMessages() {
        val messages = mapOf(
            "player-only" to "&cDu musst ein Spieler sein.",
            "command-unknown" to "&cUnbekannter Befehl. Siehe /crefill help",

            "chest-create-syntax" to "&e/crefill chest create <Name>",
            "chest-create-start" to "&aKlicke nun eine Kiste an, um sie einzurichten. &7Zum Abbrechen Befehl erneut " +
                    "eingeben oder &c/crefill abort&7.",
            "chest-create-exists" to "&cEine Kiste mit dem Name %name existiert bereits.",
            "chest-create-overlap" to "&cAn dieser Stelle wurde bereits eine Kiste mit dem Name %name erstellt.",
            "chest-create-success" to "&aDie Kiste wurde erfolgreich unter dem Name %name gespeichert.",

            "chest-abort" to "&cErstellung der Kiste abgebrochen.",
            "chest-abort-error" to "&cEs wurde noch keine Erstellung einer Kiste eingeleitet.",

            "chest-create-help" to "&e/crefill chest create <Name> &8» &8Erstellt eine neue Kiste",
            "chest-abort-help" to "&e/crefill chest abort &8» &8Bricht die Erstellung ab",

            "chest-list-entry" to "&e%name &8: &a%location",
        )
        this.messages.registerMany(messages)
    }

}