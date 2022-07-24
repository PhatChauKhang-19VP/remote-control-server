module pck.rcserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires system.hook;
    requires jna;
    requires jna.platform;

    opens pck.rcserver to javafx.fxml;
    exports pck.rcserver;
    exports pck.rcserver.controller;
}