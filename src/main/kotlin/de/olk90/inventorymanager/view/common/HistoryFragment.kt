package de.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.HistoryEntry
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import tornadofx.*

class HistoryFragment : Fragment(messages("label.usedContainers")) {

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
                    tooltip(messages("tooltip.openFile"))
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
                    tooltip(messages("tooltip.cancel"))
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
