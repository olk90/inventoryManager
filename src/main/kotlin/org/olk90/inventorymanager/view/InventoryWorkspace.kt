package org.olk90.inventorymanager.view

import de.jensd.fx.glyphs.octicons.OctIcon
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