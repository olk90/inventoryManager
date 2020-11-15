package de.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.GlyphIcon
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.jensd.fx.glyphs.octicons.OctIcon
import de.jensd.fx.glyphs.octicons.OctIconView
import de.olk90.inventorymanager.logic.controller.ObjectStore
import de.olk90.inventorymanager.model.GUIConstants
import de.olk90.inventorymanager.model.InventoryItem
import javafx.geometry.Pos
import javafx.scene.control.ListCell
import javafx.scene.control.TableColumn
import javafx.scene.paint.Color
import javafx.util.StringConverter
import tornadofx.*
import java.text.MessageFormat
import java.util.*


fun icon(icon: GlyphIcons, color: Color = c(GUIConstants.DEFAULT_COLOR.color), size: Int = 17): GlyphIcon<*> {

    val iconView: GlyphIcon<*>

    if (icon is FontAwesomeIcon) {
        iconView = FontAwesomeIconView(icon).apply { glyphSize = size; fill = color }
    } else {
        iconView = OctIconView(icon as OctIcon).apply { glyphSize = size; fill = color }
    }

    return iconView

}

fun TableColumn<out Any, out Any>.align(alignment: Pos) {
    this.style += "-fx-alignment: $alignment;"
}

fun messages(key: String, vararg args: Any?): String {
    val bundle: ResourceBundle = ResourceBundle.getBundle("Messages", FX.locale)
    return MessageFormat.format(bundle.getString(key), *args)
}

class PersonConverter : StringConverter<Number>() {

    private val map = mutableMapOf<String, Number>()

    override fun toString(personId: Number): String {
        return if (personId as Int > -1) {
            val person = ObjectStore.persons.find { it.id == personId }!!
            val name = person.getFullName()
            map[name] = personId
            name
        } else {
            ""
        }
    }

    override fun fromString(name: String?): Number? {
        return map[name]
    }
}

class ItemListCell : ListCell<InventoryItem>() {

    override fun updateItem(item: InventoryItem?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if (empty || item == null || item.name.isEmpty()) {
            null
        } else {
            item.name
        }
    }

}
