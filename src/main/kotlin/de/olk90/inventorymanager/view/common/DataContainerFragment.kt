package de.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.octicons.OctIcon
import de.olk90.inventorymanager.logic.Config
import de.olk90.inventorymanager.logic.controller.WorkspaceController
import de.olk90.inventorymanager.model.DataContainer
import de.olk90.inventorymanager.model.DataContainerModel
import de.olk90.inventorymanager.model.FileExtension
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class DataContainerFragment : Fragment(messages("label.newContainer")) {

    private val controller: WorkspaceController by inject()
    val model = DataContainerModel(DataContainer())

    override val root = borderpane {
        center {
            form {
                fieldset {
                    field(messages("label.identifier")) {
                        textfield(model.identifier)
                    }
                }
            }
        }

        bottom {
            buttonbar {
                button {
                    tooltip(messages("tooltip.createContainer"))
                    addClass("icon-only")
                    graphic = icon(OctIcon.CHECK)
                    action {
                        val fileChooser = FileChooser().apply {
                            extensionFilters.addAll(Config.jsonFilters)
                            initialDirectory = Config.userHome
                        }

                        var file = fileChooser.showSaveDialog(currentWindow)
                        if (file != null) {
                            try {
                                if (!file.name.endsWith(FileExtension.JSON.extension)) {
                                    file = File(file.absolutePath + FileExtension.JSON.extension)
                                }
                                val content = controller.buildDcFile(
                                        model.identifier.value,
                                        emptyList(),
                                        emptyList(),
                                        emptyList()
                                )
                                Config.model.pathProperty.value = file.absolutePath
                                File(file.absolutePath).writeText(content)
                                controller.openDataContainer(file.absolutePath)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
