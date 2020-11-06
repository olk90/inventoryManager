package de.olk90.inventorymanager.view.person

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.scene.control.ListView
import de.olk90.inventorymanager.logic.controller.ObjectStore
import de.olk90.inventorymanager.logic.controller.getInventoryControllerInstance
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.common.ItemListCell
import de.olk90.inventorymanager.view.common.icon
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*
import java.time.format.DateTimeFormatter

class MultiLendingFragment : Fragment() {

    private val controller = getInventoryControllerInstance()

    private lateinit var listView: ListView<InventoryItem>

    override val root = borderpane {
        center {
            form {
                fieldset {
                    field(messages("inventoryItem.lendingDate")) {
                        datepicker(controller.multiLendingModel.lendingDate) {
                            showWeekNumbersProperty().value = true
                            setOnAction {
                                if (value != null) {
                                    controller.multiLendingModel.lendingDateString.value = value.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                } else {
                                    controller.multiLendingModel.lendingDateString.value = ""
                                }
                            }
                        }
                    }
                    field(messages("label.devices")) {
                        listview(ObjectStore.inventoryItems.filter { it.available }.asObservable()) {
                            listView = this
                            multiSelect(true)
                            setCellFactory {
                                ItemListCell()
                            }
                            setOnMouseClicked {
                                controller.multiLendingModel.items.value.clear()
                                val selectedItems = selectionModel.selectedItems
                                controller.multiLendingModel.items.value.addAll(selectedItems)
                            }
                        }
                    }
                }
                fieldset {
                    buttonbar {
                        button {
                            tooltip(messages("tooltip.save"))
                            addClass("icon-only")
                            graphic = icon(OctIcon.CHECK)
                            enableWhen(controller.multiLendingModel.dirty)
                            action {
                                val selectedItems = listView.selectionModel.selectedItems
                                if (!selectedItems.isNullOrEmpty()) {
                                    controller.lendMultipleItems()
                                    close()
                                } else {
                                    error(messages("error.header.device"))
                                }
                            }
                        }
                        button {
                            tooltip(messages("tooltip.reset"))
                            addClass("icon-only")
                            graphic = icon(OctIcon.X)
                            enableWhen(controller.multiLendingModel.dirty)
                            action {
                                controller.multiLendingModel.rollback()
                                close()
                            }
                        }
                    }
                }
            }
        }
    }
}