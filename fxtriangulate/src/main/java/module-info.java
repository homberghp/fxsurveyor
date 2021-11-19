
module fxtriangulate {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires java.base;
    exports fxtriangulate ;

   opens fxtriangulate to javafx.fxml;
}
