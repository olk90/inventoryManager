package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.model.InventoryItem
import impl.org.controlsfx.autocompletion.SuggestionProvider
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.TableView


fun getInventoryControllerInstance(classLoader: ClassLoader): InventoryController {
    val resource = classLoader.getResource("fxml/inventoryView.fxml")
    val loader = FXMLLoader(resource)
    return loader.getController()
}

class InventoryController {

    val provider: SuggestionProvider<String> = SuggestionProvider.create(ObjectStore.categories)

    @FXML
    lateinit var inventoryTable: TableView<InventoryItem>

    fun updateProvider() {
        provider.clearSuggestions()
        provider.addPossibleSuggestions(ObjectStore.categories)
    }

    fun reloadTableItems() {
        println("Not yet implemented")
    }

}