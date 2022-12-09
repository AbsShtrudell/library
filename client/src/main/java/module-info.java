module org.shtrudell.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.shtrudell.common;

    opens org.shtrudell.client to javafx.fxml;
    exports org.shtrudell.client;
    exports org.shtrudell.client.fxml;
    exports org.shtrudell.client.net;
    opens org.shtrudell.client.fxml to javafx.fxml;
}