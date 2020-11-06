package de.olk90.inventorymanager

import javafx.stage.Stage
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import de.olk90.inventorymanager.view.common.InventoryWorkspace
import de.olk90.inventorymanager.view.person.PersonView
import tornadofx.*

class InventoryManagerApp : App(InventoryWorkspace::class) {

    private val controller: WorkspaceController by inject()

    override fun start(stage: Stage) {
        stage.width = 1600.0
        stage.height = 900.0

        try {
            // load or create config.json to setup history
            controller.loadConfigFile()
            // if the history was updated properly, open the last file again
            if (Config.pathProperty.value != Config.userHome.absolutePath) {
                controller.openDataContainer(Config.pathProperty.value)
            }

            super.start(stage)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBeforeShow(view: UIComponent) {
        workspace.dock<PersonView>()
        (workspace as InventoryWorkspace).enablePersonView.set(false)
    }
}