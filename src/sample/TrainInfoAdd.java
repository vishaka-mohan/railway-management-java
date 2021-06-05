package sample;
import Connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class TrainInfoAdd  {

    static ComboBox<String> sourceComboBox;
    static ComboBox<String> destinationComboBox;
    static Stage window ;


    public static void display() {
        window = new Stage();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        BackgroundFill background_fill = new BackgroundFill(Color.web("#ffe0bd"),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        Label label = new Label("Add trains");
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label.setTextFill(Color.web("#003153", 1));


        Label label1 = new Label("Train number");
        Label label2 = new Label("Train name");
        Label label3 = new Label("Source");
        Label label4 = new Label("Destination");
        Label label5 = new Label("Days");
        Label label6 = new Label("Departure");
        Label label7 = new Label("Arrival");
        Label label8 = new Label("Distance");
        Label label9 = new Label("Duration");


        TextField tno = new TextField();
        tno.setPrefWidth(300);
        tno.setMaxWidth(300);
        TextField tname = new TextField();
        tname.setPrefWidth(300);
        tname.setMaxWidth(300);
        TextField departure = new TextField();
        departure.setPrefWidth(300);
        departure.setMaxWidth(300);
        TextField arrival = new TextField();
        arrival.setPrefWidth(300);
        arrival.setMaxWidth(300);
        TextField distance = new TextField();
        distance.setPrefWidth(300);
        distance.setMaxWidth(300);
        TextField duration = new TextField();
        duration.setPrefWidth(300);
        duration.setMaxWidth(300);

        Button button = new Button("Add train");

        sourceComboBox = new ComboBox<String>();
        sourceComboBox.getItems().addAll(
                "Howrah",
                "Kharagpur",
                "Asansol",
                "Chennai Central",
                "Chhatrapati Shivaji Terminus",
                "Katpadi",
                "New Delhi");
        sourceComboBox.setPromptText("Select source");

        destinationComboBox = new ComboBox<String>();
        destinationComboBox.getItems().addAll(
                "Howrah",
                "Kharagpur",
                "Asansol",
                "Chennai Central",
                "Chhatrapati Shivaji Terminus",
                "Katpadi",
                "New Delhi");
        destinationComboBox.setPromptText("Select destination");

        sourceComboBox.setEditable(true);
        destinationComboBox.setEditable(true);

        CheckBox box1 = new CheckBox("Sunday");
        CheckBox box2 = new CheckBox("Monday");
        CheckBox box3 = new CheckBox("Tuesday");
        CheckBox box4 = new CheckBox("Wednesday");
        CheckBox box5 = new CheckBox("Thursday");
        CheckBox box6 = new CheckBox("Friday");
        CheckBox box7 = new CheckBox("Saturday");




        button.setOnAction( e -> {
            String daysInput = handleOptions(box1, box2, box3, box4, box5, box6, box7);
            String tnoInput = tno.getText();
            String tnameInput = tname.getText();
            String sourceInput = sourceComboBox.getValue();
            String destinationInput = destinationComboBox.getValue();
            String departureInput = departure.getText();
            String arrivalInput = arrival.getText();
            String distanceInput = distance.getText();
            String durationInput = duration.getText();
            Statement statement = null;

            String sql1 = "SELECT * FROM trainjourney WHERE train_number='"+ tnoInput + "';";
            Statement statement1 = null;

                try {
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql1);
                    if (resultSet.next() ) {

                        AlertBox.display("This train number already exists.");

                    }
                    else{
                        try {
                            statement = connection.createStatement();
                            String sql = "INSERT INTO trainjourney VALUES('"+ tnoInput +"','"+ tnameInput +  "','" +  sourceInput +  "','"+
                                    destinationInput + "','" + daysInput + "','" + departureInput + "','" + arrivalInput + "','" + distanceInput + "','" + durationInput +"');";
                            statement.executeUpdate(sql);
                            System.out.println("data entered.");
                            AlertBox.display("Train added successfully!");
                            tname.setText("");
                            tno.setText("");
                            departure.setText("");
                            arrival.setText("");
                            distance.setText("");
                            duration.setText("");


                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

        });



        //Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, label1, tno, label2, tname, label3, sourceComboBox, label4, destinationComboBox,
                label5, box1, box2 , box3,box4,box5,box6,box7, label6, departure, label7, arrival, label8, distance, label9, duration, button);
        //layout.setBackground(background);
        ScrollPane sp = new ScrollPane();
        sp.setContent(layout);



        Scene scene = new Scene(sp, 800,700);
        window.setScene(scene);

        window.show();

    }


    private static String handleOptions(CheckBox box1, CheckBox box2, CheckBox box3, CheckBox box4, CheckBox box5, CheckBox box6, CheckBox box7){
        String[] allDays = new String[7];
        String m="";
        int i=0;

        if(box1.isSelected()){
            allDays[i++] = "Sunday";
        }

        if(box2.isSelected()){
            allDays[i++] = "Monday";
        }

        if(box3.isSelected()){
            allDays[i++] = "Tuesday";
        }
        if(box4.isSelected()){
            allDays[i++] = "Wednesday";
        }
        if(box5.isSelected()){
            allDays[i++] = "Thursday";
        }
        if(box6.isSelected()){
            allDays[i++] = "Friday";
        }
        if(box7.isSelected()){
            allDays[i++] = "Saturday";
        }

        for (int j = 0; j < i-1; j++) {
            System.out.println(allDays[j]);
            m += allDays[j];
            m += ",";

        }
        m += allDays[i-1];
        System.out.println(m);
        return m;

    }



}
