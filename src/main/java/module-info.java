module inventoryManager.main {

    requires kotlin.stdlib;
    requires kotlin.reflect;

    requires klaxon;

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens de.olk90.inventorymanager.logic to kotlin.reflect, klaxon;
    opens de.olk90.inventorymanager.model to kotlin.reflect, klaxon;
    opens de.olk90.inventorymanager.logic.controller to javafx.graphics, javafx.fxml;

    exports de.olk90.inventorymanager.app;

}