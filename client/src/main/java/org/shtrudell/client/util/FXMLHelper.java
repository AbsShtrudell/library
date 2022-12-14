package org.shtrudell.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.shtrudell.client.ClientApp;

import java.io.IOException;

public class FXMLHelper {

    private static final String fxmlFolder = "/org/shtrudell/client/fxml/";

    public static Parent loadFXML(String fxml) throws IOException {
        return makeLoader(fxml).load();
    }

    public static FXMLLoader makeLoader(String fxml) {
        return new FXMLLoader(ClientApp.class.getResource(fxmlFolder + fxml + ".fxml"));
    }
}
