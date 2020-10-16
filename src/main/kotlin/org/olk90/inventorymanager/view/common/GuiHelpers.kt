package org.olk90.inventorymanager.view.common

import de.jensd.fx.glyphs.GlyphIcon
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.jensd.fx.glyphs.octicons.OctIcon
import de.jensd.fx.glyphs.octicons.OctIconView
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.paint.Color
import org.olk90.inventorymanager.model.GUIConstants
import tornadofx.*

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