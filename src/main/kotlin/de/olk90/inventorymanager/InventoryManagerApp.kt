package de.olk90.inventorymanager

import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage

//import de.olk90.inventorymanager.logic.Config
//import de.olk90.inventorymanager.logic.controller.WorkspaceController
//import de.olk90.inventorymanager.view.common.InventoryWorkspace
//import de.olk90.inventorymanager.view.person.PersonView
//import javafx.scene.image.Image
//import javafx.stage.Stage
//import tornadofx.*
//
//class InventoryManagerApp : App(InventoryWorkspace::class) {
//
//    private val controller: WorkspaceController by inject()
//
//    override fun start(stage: Stage) {
//        stage.width = 1600.0
//        stage.height = 900.0
//
//        try {
//            // load or create config.json to setup history
//            controller.loadConfigFile()
//            // if the history was updated properly, open the last file again
//            if (Config.pathProperty.value != Config.userHome.absolutePath) {
//                controller.openDataContainer(Config.pathProperty.value)
//            }
//
//            super.start(stage)
//            stage.icons.add(Image("icon.png"))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onBeforeShow(view: UIComponent) {
//        workspace.dock<PersonView>()
//        (workspace as InventoryWorkspace).enablePersonView.set(false)
//    }
//}

class InventoryManagerApp : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Inventory Manager"
        val btn = Button()
        btn.text = "Hello JavaFX!"
        btn.onAction = EventHandler { Platform.exit() }
        val root: Pane = StackPane()
        root.children.add(btn)
        primaryStage.scene = Scene(root, 1600.0, 900.0)
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(InventoryManagerApp::class.java)
        }
    }
}
