package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.logic.Config
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class WorkspaceController {

    @FXML
    lateinit var mainView: BorderPane


    fun openNewDatabase() {
        val resource = javaClass.classLoader.getResource("fxml/databaseFragment.fxml")
        val fragment = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
        val secondaryScene = Scene(fragment, 300.0, 170.0)

        val newWindow = Stage()
        newWindow.scene = secondaryScene
        newWindow.show()
    }

    fun openPersonsView() {
        val resource = javaClass.classLoader.getResource("fxml/personsView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
    }

    fun openInventoryView() {
        val resource = javaClass.classLoader.getResource("fxml/inventoryView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource, Config.getResourceBundle())
    }

}