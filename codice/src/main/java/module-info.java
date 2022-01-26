module com.prog3.ipt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.prog3.ipt to javafx.fxml;
    exports com.prog3.ipt.Model;
    opens com.prog3.ipt.Model to javafx.fxml;
    exports com.prog3.ipt.Controller;
    opens com.prog3.ipt.Controller to javafx.fxml;
    exports com.prog3.ipt;
}