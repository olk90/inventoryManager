package de.olk90.inventorymanager

import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage


class InventoryManagerApp : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Inventory Manager"
        primaryStage.icons.add(Image("icon.png"))

        try {
            val resource = ResourceLoader.loadUrl("mainView.fxml")
            val loader = FXMLLoader(resource)
            val root = loader.load<Parent>()

            val controller = loader.getController<WorkspaceController>()

            // load or create config.json to setup history
            controller.loadConfigFile()
            // if the history was updated properly, open the last file again
            if (controller.pathProperty.value != Config.userHome.absolutePath) {
                controller.openDataContainer(controller.pathProperty.value)
            }

            primaryStage.scene = Scene(root, 1600.0, 900.0)
            primaryStage.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(InventoryManagerApp::class.java)
        }
    }
}

/*
 * workaround necessary to run app outside gradle
 * (s. https://github.com/javafxports/openjdk-jfx/issues/236)
 *
 * As of version 16 a warning is received, if the module is not
 * named (s. https://stackoverflow.com/a/67854230)
 */
fun main(args: Array<String>) {
    InventoryManagerApp.main(args)
}