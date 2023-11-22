module thedrake.thedrake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens thedrake.animation to javafx.fxml;
    exports thedrake.animation;
    exports thedrake.thedrake;
    opens thedrake.thedrake to javafx.fxml;
}