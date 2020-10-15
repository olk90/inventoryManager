package org.olk90.inventorymanager

import javafx.stage.Stage
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.view.InventoryWorkspace
import org.olk90.inventorymanager.view.PersonView
import tornadofx.*

class InventoryManagerApp : App(InventoryWorkspace::class) {

    override fun start(stage: Stage) {
        stage.width = 1600.0
        stage.height = 900.0

        // load or create config.json to setup history
        Config.loadConfigFile()

        super.start(stage)
    }

    override fun onBeforeShow(view: UIComponent) {
        workspace.dock<PersonView>()
    }
}