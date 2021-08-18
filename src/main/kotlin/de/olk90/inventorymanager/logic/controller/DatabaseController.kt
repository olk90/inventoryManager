package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.files.writeToFile
import de.olk90.inventorymanager.model.FileExtension
import de.olk90.inventorymanager.view.exceptionDialog
import de.olk90.inventorymanager.view.messages
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import javafx.stage.Window
import java.io.File

class DatabaseController {

    @FXML
    lateinit var newDbDialog: BorderPane

    @FXML
    lateinit var identifierField: TextField

    fun writeFile(event: ActionEvent) {
        val fileChooser = FileChooser().apply {
            extensionFilters.addAll(Config.jsonFilters)
            initialDirectory = Config.userHome
        }

        val currentWindow = getCurrentWindow(event)

        var file = fileChooser.showSaveDialog(currentWindow)
        if (file != null) {
            try {
                if (!file.name.endsWith(FileExtension.JSON.extension)) {
                    file = File(file.absolutePath + FileExtension.JSON.extension)
                }
                writeToFile(file)
            } catch (e: Exception) {
                exceptionDialog(messages("error.header.exception"), e)
            }
        }
    }

    fun cancel() {
        newDbDialog.scene.window.hide()
    }

    private fun getCurrentWindow(event: ActionEvent): Window {
        val node = event.target as Node
        return node.scene.window
    }
}