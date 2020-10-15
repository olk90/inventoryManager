package org.olk90.inventorymanager.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.geometry.Pos
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.model.InventoryItem
import tornadofx.*

class InventoryView : View("Inventory Overview") {

    private val controller: InventoryController by inject()

    override val root = borderpane {
        center {
            tableview(controller.inventoryItems) {
                columnResizePolicy = SmartResize.POLICY

                column("Name", InventoryItem::nameProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Available", InventoryItem::availableProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Lending Date", InventoryItem::lendingDateProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }
                column("Lender", InventoryItem::lenderProperty).apply {
                    align(Pos.CENTER)
                    pctWidth(25.0)
                }

                // Update the person inside the view model on selection change
                controller.model.rebindOnChange(this) {
                    item = it ?: InventoryItem()
                }
            }
        }
        right {
            form {
                fieldset("Add item") {
                    field("Name") {
                        textfield(controller.model.name)
                    }
                    field("Available") {
                        checkbox(property = controller.model.available)
                    }
                    field("Lending date") {
                        datepicker(controller.model.lendingDate)
                    }
                    field("Lender") {
                        choicebox(controller.model.lender, controller.persons)
                    }
                    buttonbar {
                        button {
                            tooltip("Save")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.SAVE)
                            enableWhen(controller.model.dirty)
                            action {
                                controller.save()
                            }
                        }
                        button {
                            tooltip("Reset")
                            addClass("icon-only")
                            graphic = icon(FontAwesomeIcon.REMOVE)
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
