package de.olk90.inventorymanager.logic.datahelpers

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
//    var model = WorkspaceViewModel()

    val configDirectory: File = Paths.get(System.getProperty("user.home"), ".inventoryManager").toFile()

    fun getResourceBundle(): ResourceBundle {
        val locale = Locale.getDefault()
        return ResourceBundle.getBundle("Messages", locale)
    }
}

// helpers to keep track of the used data containers
data class HistoryEntry(val filePath: String, var lastAccessTime: Long)
data class History(val history: ArrayList<HistoryEntry>)