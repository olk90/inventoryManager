package org.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import org.olk90.inventorymanager.logic.HistoryEntry
import org.olk90.inventorymanager.logic.controller.WorkspaceController
import tornadofx.*

class HistoryFragment : Fragment("Recently used data containers") {

    private val controller: WorkspaceController by inject()
    private var listView: ListView<HistoryEntry> by singleAssign()

    override val root = borderpane {

        center {
            listview(controller.history) {
                listView = this
                setCellFactory {
                    object : ListCell<HistoryEntry?>() {
                        override fun updateItem(entry: HistoryEntry?, empty: Boolean) {
                            super.updateItem(entry, empty)
                            text = if (empty || entry == null || entry.filePath.isEmpty()) {
                                null
                            } else {
                                entry.filePath
                            }
                        }
                    }
                }

            }
        }

        bottom {
            buttonbar {
                button {
                    tooltip("Open selected file")
                    addClass("icon-only")
                    graphic = icon(OctIcon.CHECK)
                    enableWhen(listView.selectionModel.selectedItemProperty().isNotNull)
                    action {
                        if (listView.selectedItem != null) {
                            controller.openDataContainer(listView.selectedItem!!.filePath)
                        }
                        close()
                    }
                }
                button {
                    tooltip("Cancel")
                    addClass("icon-only")
                    graphic = icon(OctIcon.X)
                    action {
                        close()
                    }
                }
            }
        }
    }
}
