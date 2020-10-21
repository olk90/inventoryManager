package org.olk90.inventorymanager.view.inventory

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.scene.control.TableView
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.model.InventoryItem
import org.olk90.inventorymanager.view.common.PersonConverter
import org.olk90.inventorymanager.view.common.icon
import tornadofx.*
import java.time.format.DateTimeFormatter

class LendingFragment(private val table: TableView<InventoryItem>) : Fragment() {

    val controller: InventoryController by inject()

    override val root = borderpane {
        center {
            form {
                fieldset {
                    field("Lending date") {
                        datepicker(controller.model.lendingDate) {
                            setOnAction {
                                if (value != null) {
                                    controller.model.lendingDateString.value = value.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                } else {
                                    controller.model.lendingDateString.value = ""
                                }
                            }
                        }
                    }
                    field("Lender") {
                        choicebox(controller.model.lender, ObjectStore.persons.map { it.id }.asObservable()) {
                            converter = PersonConverter()
                            fitToParentWidth()
                        }
                    }
                }
                fieldset {
                    buttonbar {
                        button {
                            tooltip("Save")
                            addClass("icon-only")
                            graphic = icon(OctIcon.CHECK)
                            enableWhen(controller.model.dirty)
                            action {
                                if (table.selectedItem != null) {
                                    controller.save(true)
                                    close()
                                } else {
                                    error("No item selected")
                                }
                            }
                        }
                        button {
                            tooltip("Reset")
                            addClass("icon-only")
                            graphic = icon(OctIcon.X)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.model.rollback()
                            }
                        }
                    }
                }
            }
        }
    }
}
