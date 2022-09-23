package de.skycave.chestrefiller.utils

import org.bukkit.Location

object FormattingUtils {
    fun locationAsString(location: Location): String {
        return "x: " + location.x + ", y: " + location.y + ", z: " + location.z
    }
}