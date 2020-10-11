package org.olk90.inventorymanager

import org.olk90.inventorymanager.view.UserView
import tornadofx.*

class InventoryWorkspace: Workspace() {

    init {
        dock<UserView>()
    }
}

class InventoryManagerApp: App(InventoryWorkspace::class) {

}