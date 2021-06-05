package sample;

import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;



public class AlertBox {

    public static void display(String message){
        Stage window = new Stage();

        //IMPORTANT the opened window is in focus, can't do anything else until closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        //alignment
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500,100);
        window.setScene(scene);

        //show and wait to block everything else
        window.showAndWait();
    }
}
