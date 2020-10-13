package org.olk90.inventorymanager

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.stage.Stage
import org.olk90.inventorymanager.view.InventoryView
import org.olk90.inventorymanager.view.PersonView
import org.olk90.inventorymanager.view.icon
import tornadofx.*

class InventoryWorkspace : Workspace() {

    init {
        // remove obsolete buttons
        forwardButton.hide()
        backButton.hide()
        refreshButton.hide()

        // custom buttons
        button {
            tooltip("Open person overview")
            addClass("icon-only")
            graphic = icon(OctIcon.PERSON)
            action {
                if (workspace.dockedComponent !is PersonView) {
                    workspace.dock<PersonView>()
                }
            }
        }
        button {
            tooltip("Open inventory overview")
            addClass("icon-only")
            graphic = icon(OctIcon.TOOLS)
            action {
                if (workspace.dockedComponent !is InventoryView) {
                    workspace.dock<InventoryView>()
                }
            }
        }
    }
}

class InventoryManagerApp : App(InventoryWorkspace::class) {

    override fun start(stage: Stage) {
        stage.width = 1600.0
        stage.height = 900.0

        super.start(stage)
    }

    override fun onBeforeShow(view: UIComponent) {
        workspace.dock<PersonView>()
    }
}