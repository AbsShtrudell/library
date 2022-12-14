module org.shtrudell.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.shtrudell.common;
    requires lombok;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.weathericons;

    opens org.shtrudell.client to javafx.fxml;
    exports org.shtrudell.client;
    exports org.shtrudell.client.fxml;
    exports org.shtrudell.client.net;
    opens org.shtrudell.client.fxml to javafx.fxml;
    exports org.shtrudell.client.util;
    opens org.shtrudell.client.util to javafx.fxml;
}