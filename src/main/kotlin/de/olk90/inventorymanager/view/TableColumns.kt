package de.olk90.inventorymanager.view

import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.geometry.Pos
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView


fun <S> TableView<S>.addIndexColumn(startIndex: Int = 1): TableColumn<S, Int> {
    return TableColumn<S, Int>("#").apply {
        style += "-fx-alignment: ${Pos.CENTER};"
        isSortable = false
        minWidth = 50.0
        maxWidth = 50.0
        val columns = this@addIndexColumn.columns
        columns.add(0, this)
        setCellValueFactory {
            ReadOnlyObjectWrapper(items.indexOf(it.value) + startIndex)
        }
    }
}
