package org.olk90.inventorymanager.view.inventory

import javafx.scene.control.TextField
import org.controlsfx.control.textfield.TextFields
import org.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import tornadofx.*

class CategoryTextField : TextField() {

    init {
        bind(getInventoryControllerInstance().model.category)
        TextFields.bindAutoCompletion(this, getInventoryControllerInstance().provider)
    }
}