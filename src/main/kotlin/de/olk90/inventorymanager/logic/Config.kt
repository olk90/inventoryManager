package de.olk90.inventorymanager.logic

import de.olk90.inventorymanager.model.FileExtension
import javafx.stage.FileChooser
import java.io.File
import java.nio.file.Paths
import java.util.*

object Config {

    val jsonFilters = arrayOf(
            FileChooser.ExtensionFilter("JSON file", "*${FileExtension.JSON.extension}")
    )

    val userHome = File(System.getProperty("user.home"))

    val configDirectory: File = Paths.get(System.getProperty("user.home"), ".inventoryManager").toFile()

    const val MOT_PATTERN = "MMM/yyyy"

    const val PERSISTENT_MOT_PATTERN = "d/MMM/yyyy"

}

// helpers to keep track of the used data containers
class HistoryEntry(val filePath: String, var lastAccessTime: Long)
class History(val history: ArrayList<HistoryEntry>)
