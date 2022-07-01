module pck.rcserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens pck.rcserver to javafx.fxml;
    exports pck.rcserver;
}