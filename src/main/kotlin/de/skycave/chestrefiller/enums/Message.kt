package de.skycave.chestrefiller.enums

import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.models.ChatMessage
import org.bukkit.plugin.java.JavaPlugin

enum class Message(private val message: String) {

    PLAYER_ONLY("&cDu musst ein Spieler sein."),
    COMMAND_UNKNOWN("&cUnbekannter Befehl. Siehe /crefill help"),

    CHEST_CREATE_SYNTAX("&e/crefill chest create <Name>"),
    CHEST_CREATE_START("&aKlicke nun eine Kiste an, um sie einzurichten. &7Zum Abbrechen Befehl erneut eingeben" +
        " oder &c/crefill abort&7."),
    CHEST_CREATE_EXISTS("&cEine Kiste mit dem Name %name existiert bereits."),
    CHEST_CREATE_OVERLAP("&cAn dieser Stelle wurde bereits eine Kiste mit dem Name %name erstellt."),
    CHEST_CREATE_SUCCESS("&aDie Kiste wurde erfolgreich unter dem Name %name gespeichert."),

    CHEST_ABORT("&cErstellung der Kiste abgebrochen."),
    CHEST_ABORT_ERROR("&cEs wurde noch keine Erstellung einer Kiste eingeleitet."),

    CHEST_CREATE_HELP("&e/crefill chest create <Name> &8» &8Erstellt eine neue Kiste"),
    CHEST_ABORT_HELP("&e/crefill chest abort &8» &8Bricht die Erstellung ab"),
    ;

    fun get(): ChatMessage {
        return ChatMessage(JavaPlugin.getPlugin(ChestRefiller::class.java), message)
    }

}