module com.prog3.ipt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.prog3.ipt to javafx.fxml;
    exports com.prog3.ipt.Model;
    opens com.prog3.ipt.Model to javafx.fxml;
    exports com.prog3.ipt.Controller;
    opens com.prog3.ipt.Controller to javafx.fxml;
    exports com.prog3.ipt;
    exports com.prog3.ipt.Model.TravelDocumentClasses;
    opens com.prog3.ipt.Model.TravelDocumentClasses to javafx.fxml;
    exports com.prog3.ipt.Model.CitizenClasses;
    opens com.prog3.ipt.Model.CitizenClasses to javafx.fxml;
    exports com.prog3.ipt.Controller.TravelDocumentsManagement;
    opens com.prog3.ipt.Controller.TravelDocumentsManagement to javafx.fxml;
    exports com.prog3.ipt.Model.PaymentMethodClasses;
    opens com.prog3.ipt.Model.PaymentMethodClasses to javafx.fxml;
    exports com.prog3.ipt.Model.FacadeClasses;
    opens com.prog3.ipt.Model.FacadeClasses to javafx.fxml;
    exports com.prog3.ipt.Model.LineRide;
    opens com.prog3.ipt.Model.LineRide to javafx.fxml;

}