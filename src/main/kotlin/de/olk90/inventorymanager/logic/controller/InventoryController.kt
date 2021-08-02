package de.olk90.inventorymanager.logic.controller

import de.olk90.inventorymanager.model.InventoryItem
import javafx.fxml.FXML
import javafx.scene.control.TableView


class InventoryController {

    @FXML
    lateinit var inventoryTable: TableView<InventoryItem>

}