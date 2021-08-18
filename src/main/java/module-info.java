module inventoryManager.main {

    requires kotlin.stdlib;
    requires kotlin.reflect;

    requires klaxon;

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens de.olk90.inventorymanager;
    opens de.olk90.inventorymanager.logic;
    opens de.olk90.inventorymanager.model;
    opens de.olk90.inventorymanager.logic.controller;

}