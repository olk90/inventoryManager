package org.olk90.inventorymanager.view.inventory

import de.jensd.fx.glyphs.octicons.OctIcon
import org.olk90.inventorymanager.logic.controller.InventoryController
import org.olk90.inventorymanager.logic.controller.ObjectStore
import org.olk90.inventorymanager.view.common.icon
import tornadofx.*

class InventoryDataFragment(private val create: Boolean = false) : Fragment() {

    val controller: InventoryController by inject()

    override val root = borderpane {
        center {
            form {
                val title = if (create) "Add item" else "Edit item"
                fieldset(title) {
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
                        choicebox(controller.model.lender, ObjectStore.persons) {
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
                                if (create) {
                                    controller.add()
                                } else {
                                    controller.save()
                                }
                                close()
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
