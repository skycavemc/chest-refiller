package de.skycave.chestrefiller.utils

import org.bukkit.Location

object FormattingUtils {

    /**
     * Easily format a location as string
     * @param location The location to format
     * @return The location formatted as user-friendly readable string
     */
    fun locationAsString(location: Location): String {
        return "world: ${location.world.name}, x: ${location.x}, y: ${location.y}, z: ${location.z}"
    }

}