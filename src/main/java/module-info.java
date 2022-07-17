module pck.rcserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens pck.rcserver to javafx.fxml;
    exports pck.rcserver;
}