module surveyor {
    requires java.logging;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires fxtriangulate;
    exports surveyor;
    opens surveyor to javafx.fxml;
}
