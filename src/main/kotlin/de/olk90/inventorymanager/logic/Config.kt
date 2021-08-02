package de.olk90.inventorymanager.logic

import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import java.io.File
import java.nio.file.Paths

import de.olk90.inventorymanager.model.FileExtension
import java.util.*
import kotlin.collections.ArrayList

object Config {

    val jsonFilters = arrayOf(
            FileChooser.ExtensionFilter("JSON file", "*${FileExtension.JSON.extension}")
    )

    val userHome = File(System.getProperty("user.home"))
    val pathProperty = SimpleStringProperty(this, "path", userHome.toString())
    val identifierProperty = SimpleStringProperty(this, "identifier", "")
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