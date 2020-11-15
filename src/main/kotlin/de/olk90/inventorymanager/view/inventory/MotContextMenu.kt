package de.olk90.inventorymanager.view.inventory

import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import de.olk90.inventorymanager.model.InventoryItemModel
import de.olk90.inventorymanager.view.common.messages
import javafx.scene.control.ContextMenu
import tornadofx.*

class MotContextMenu(model: InventoryItemModel) : ContextMenu() {

    init {
        item(messages("contextmenu.disableMot")) {
            enableWhen(model.motRequired)
            action {
                getInventoryControllerInstance().disableMot()
            }
        }
        item(messages("contextmenu.enableMot")) {
            disableWhen(model.motRequired)
            action {
                getInventoryControllerInstance().enableMot()
            }
        }
    }
}