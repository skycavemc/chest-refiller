package de.skycave.chestrefiller.commands

import com.mongodb.client.model.Filters
import de.skycave.chestrefiller.ChestRefiller
import de.skycave.chestrefiller.utils.FormattingUtils
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ChestRefillChestSubcommand: java.util.function.BiFunction<CommandSender, Array<out String>, Boolean> {

    private val main = JavaPlugin.getPlugin(ChestRefiller::class.java)

    override fun apply(sender: CommandSender, args: Array<out String>): Boolean {
        when (args[1].lowercase()) {
            "create" -> {
                if (sender !is Player) {
                    main.messages.get("player-only").send(sender)
                    return true
                }
                if (main.chestSetMode.containsKey(sender)) {
                    main.messages.get("chest-abort").send(sender)
                    main.chestSetMode.remove(sender)
                    return true
                }
                if (args.size < 3) {
                    main.messages.get("chest-create-syntax").send(sender)
                    return true
                }
                val name = args[2].lowercase()
                val chest = main.chests.find(Filters.eq("name", name)).first()
                if (chest != null) {
                    main.messages.get("chest-create-exists").replace("%name", name).send(sender)
                    return true
                }
                main.chestSetMode[sender] = name
                main.messages.get("chest-create-start").send(sender)
                return true
            }
            "abort" -> {
                if (sender !is Player) {
                    main.messages.get("player-only").send(sender)
                    return true
                }
                if (main.chestSetMode.containsKey(sender)) {
                    main.messages.get("chest-abort").send(sender)
                    main.chestSetMode.remove(sender)
                    return true
                }
                main.messages.get("chest-abort-error").send(sender)
                return true
            }
            "list" -> {
                val chests = main.chests.find()
                for (chest in chests) {
                    main.messages.get("chest-list-entry")
                        .replace("%name", chest.name)
                        .replace("%location", FormattingUtils.locationAsString(chest.location))
                        .send(sender)
                }
                return true
            }
            "remove" -> {
                TODO("remove command")
            }
            "refill" -> {
                TODO("refill command")
            }
            else -> {
                main.messages.get("command-unknown").send(sender)
                return true
            }
        }
    }

}