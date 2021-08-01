package de.olk90.inventorymanager.logic.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.BorderPane

class MainController {

    @FXML
    lateinit var mainView: BorderPane


    fun openNewDatabase() {

    }

    fun openPersonsView() {
        val resource = javaClass.classLoader.getResource("fxml/personsView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource)
    }

    fun openInventoryView() {
        val resource = javaClass.classLoader.getResource("fxml/inventoryView.fxml")
        mainView.center = FXMLLoader.load<Parent>(resource)
    }
}