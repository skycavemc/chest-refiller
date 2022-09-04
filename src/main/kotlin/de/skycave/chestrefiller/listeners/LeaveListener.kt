package de.skycave.chestrefiller.listeners

import de.skycave.chestrefiller.ChestRefiller
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class LeaveListener(private val main: ChestRefiller): Listener {

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        main.chestSetMode.remove(event.player)
    }

    @EventHandler
    fun onKick(event: PlayerKickEvent) {
        main.chestSetMode.remove(event.player)
    }

}