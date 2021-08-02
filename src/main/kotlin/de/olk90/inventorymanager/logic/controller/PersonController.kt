package de.olk90.inventorymanager.logic.controller

import javafx.fxml.FXMLLoader

fun getPersonControllerInstance(classLoader: ClassLoader): PersonController {
    val resource = classLoader.getResource("fxml/personsView.fxml")
    val loader = FXMLLoader(resource)
    return loader.getController()
}

class PersonController {

    fun reloadTableItems() {
        println("Not yet implemented")
    }

}