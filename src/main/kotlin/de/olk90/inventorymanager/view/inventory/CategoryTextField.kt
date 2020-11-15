package de.olk90.inventorymanager.view.inventory

import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import javafx.scene.control.TextField
import org.controlsfx.control.textfield.TextFields
import tornadofx.*

class CategoryTextField : TextField() {

    init {
        bind(getInventoryControllerInstance().model.category)
        TextFields.bindAutoCompletion(this, getInventoryControllerInstance().provider)
    }
}