package org.olk90.inventorymanager.view

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.stage.FileChooser
import org.olk90.inventorymanager.logic.Config
import org.olk90.inventorymanager.logic.Config.jsonFilters
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import org.olk90.inventorymanager.model.FileExtension
import tornadofx.*
import java.io.File

class InventoryWorkspace : Workspace() {

    private val controller: WorkspaceController by inject()

    init {
        // remove obsolete buttons
        forwardButton.hide()
        backButton.hide()
        refreshButton.hide()

        // custom buttons
        button {
            tooltip("Create new data container")
            addClass("icon-only")
            graphic = icon(OctIcon.DATABASE)
            action {
                openInternalWindow(DataContainerFragment::class, closeButton = false)
            }
        }

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

        button {
            tooltip("Recently used data containers")
            addClass("icon-only")
            graphic = icon(OctIcon.HISTORY)
            action {
                openInternalWindow(HistoryFragment::class, closeButton = false)
            }
        }
    }
}