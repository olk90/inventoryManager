package de.olk90.inventorymanager.view.inventory

import de.jensd.fx.glyphs.octicons.OctIcon
import javafx.scene.control.TableView
import de.olk90.inventorymanager.logic.controller.InventoryController
import de.olk90.inventorymanager.logic.controller.ObjectStore
import de.olk90.inventorymanager.model.InventoryItem
import de.olk90.inventorymanager.view.common.PersonConverter
import de.olk90.inventorymanager.view.common.icon
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*
import java.time.format.DateTimeFormatter

class LendingFragment(private val table: TableView<InventoryItem>) : Fragment() {

    val controller: InventoryController by inject()

    override val root = borderpane {
        center {
            form {
                fieldset {
                    field(messages("inventoryItem.lendingDate")) {
                        datepicker(controller.model.lendingDate) {
                            showWeekNumbersProperty().value = true
                            setOnAction {
                                if (value != null) {
                                    controller.model.lendingDateString.value = value.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                } else {
                                    controller.model.lendingDateString.value = ""
                                }
                            }
                            validator {
                                if (it == null) error(messages("error.validation.lendingDate")) else null
                            }
                        }
                    }
                    field(messages("inventoryItem.lender")) {
                        choicebox(controller.model.lender, ObjectStore.persons.map { it.id }.asObservable()) {
                            converter = PersonConverter()
                            fitToParentWidth()
                        }
                    }
                }
                fieldset {
                    buttonbar {
                        button {
                            tooltip(messages("tooltip.save"))
                            addClass("icon-only")
                            graphic = icon(OctIcon.CHECK)
                            enableWhen(controller.model.dirty.and(controller.model.validationContext.valid))
                            action {
                                if (table.selectedItem != null) {
                                    controller.save(true)
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
                            enableWhen(controller.model.dirty)
                            action {
                                controller.model.rollback()
                            }
                        }
                    }
                }
            }

            controller.model.validate()
        }
    }
}
