package org.olk90.inventorymanager

import javafx.stage.Stage
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import org.olk90.inventorymanager.view.common.InventoryWorkspace
import org.olk90.inventorymanager.view.person.PersonView
import tornadofx.*
import tornadofx.FX.Companion.messages
import java.lang.Exception
import java.util.*

class InventoryManagerApp : App(InventoryWorkspace::class) {

    private val controller: WorkspaceController by inject()

    init {
        messages = ResourceBundle.getBundle("Messages")
    }

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
    }
}