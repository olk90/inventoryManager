package de.olk90.inventorymanager

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class InventoryManagerApp : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Inventory Manager"

        val resource = javaClass.classLoader.getResource("fxml/mainView.fxml")
        val root = FXMLLoader.load<Parent>(resource)

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
