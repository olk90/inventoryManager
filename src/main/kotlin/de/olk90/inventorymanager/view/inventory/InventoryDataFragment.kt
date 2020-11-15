package de.olk90.inventorymanager.view.inventory

import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.controller.InventoryController
import de.olk90.inventorymanager.view.common.icon
import de.olk90.inventorymanager.view.common.messages
import tornadofx.*

class InventoryDataFragment(private val create: Boolean = false) : Fragment() {

    val controller: InventoryController by inject()

    override val root = borderpane {

        maxWidth = 400.0
        minWidth = 400.0


        center {
            form {
                val title = if (create) messages("label.addItem") else messages("label.editItem")
                fieldset(title) {
                    field(messages("inventoryItem.name")) {
                        textfield(controller.model.name).validator {
                            if (it.isNullOrBlank()) error(messages("error.validation.name")) else null
                        }
                    }
                    field(messages("inventoryItem.available")) {
                        checkbox(property = controller.model.available)
                    }
                    field(messages("inventoryItem.nextMot")) {
                        label.contextMenu = MotContextMenu(controller.model)
                        spinner(property = controller.model.nextMot, enableScroll = true, editable = true) {
                            fitToParentSize()
                            val factory = NextMotSpinnerValueFactory()
                            factory.converter = MotConverter()
                            valueFactory = factory

                            enableWhen(controller.model.motRequired)
                        }
                    }
                    field(messages("inventoryItem.category")) {
                        val textField = CategoryTextField()
                        add(textField)
                    }
                    field(messages("inventoryItem.info")) {
                        textarea(controller.model.info)
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
                                if (create) {
                                    controller.add()
                                    close()
                                } else {
                                    controller.save()
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
