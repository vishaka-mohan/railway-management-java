package sample;

import Connectivity.ConnectionClass;
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

public class TrainInfo {

    static ComboBox<String> sourceComboBox;
    static ComboBox<String> destinationComboBox;
    static TableView<Journey> table;

    public static void display(){

        Stage window = new Stage();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        BackgroundFill background_fill = new BackgroundFill(Color.web("#ffe0bd"),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);


        //window in focus
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Train information");
        window.setMinWidth(250);

        Label label3 = new Label("Check train information");
        label3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label3.setTextFill(Color.web("#003153", 1));

        Label label = new Label("Source");
        Label label1 = new Label("Destination");
        Label label2 = new Label("Travel date");

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
        //new ComboBoxAutoComplete<String>(sourceComboBox);


        destinationComboBox.setEditable(true);

        DatePicker d = new DatePicker();
        d.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        Button button = new Button("Search for trains!");
        Label label4 = new Label();
        //train number
        TableColumn<Journey, String> numberColumn = new TableColumn<>("Train Number");
        numberColumn.setMinWidth(200);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("train_number"));

        //train name
        TableColumn<Journey, String> nameColumn = new TableColumn<>("Train Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("train_name"));

        //source
        TableColumn<Journey, String> sourceColumn = new TableColumn<>("Source");
        sourceColumn.setMinWidth(200);
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        //destination
        TableColumn<Journey, String> destColumn = new TableColumn<>("Destination");
        destColumn.setMinWidth(200);
        destColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        //days
        TableColumn<Journey, String> daysColumn = new TableColumn<>("Days");
        daysColumn.setMinWidth(200);
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));

        //departure
        TableColumn<Journey, String> departureColumn = new TableColumn<>("Departure");
        departureColumn.setMinWidth(200);
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));

        //arrival
        TableColumn<Journey, String> arrivalColumn = new TableColumn<>("Arrival");
        arrivalColumn.setMinWidth(200);
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));

        //distance
        TableColumn<Journey, String> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setMinWidth(200);
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        //duration
        TableColumn<Journey, String> durationColumn = new TableColumn<>("Duration(in hours)");
        durationColumn.setMinWidth(200);
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        table = new TableView<>();
        table.getColumns().addAll(numberColumn, nameColumn, sourceColumn, destColumn, daysColumn, departureColumn, arrivalColumn, distanceColumn, durationColumn);




        button.setOnAction(e -> {

            String source = sourceComboBox.getValue();
            String destination = destinationComboBox.getValue();
            LocalDate i = d.getValue();
            DayOfWeek day = i.getDayOfWeek();
            System.out.println(i.getDayOfWeek());
            ObservableList<Journey> trains = FXCollections.observableArrayList();

            String sql = "SELECT * FROM trainjourney WHERE source='"+ source + "' AND destination='"+
                           destination + "';";
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                String m1 = "";
                while (resultSet.next() ) {
                    String message= "";
                    message += resultSet.getString(5);
                    String[] daysArray = message.split(",");

                    for(String name : daysArray){
                        if(name.toLowerCase().equals(day.toString().toLowerCase())){
                            System.out.println(name);
                            m1 += resultSet.getString(1);
                            trains.add(new Journey(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7),
                                    resultSet.getString(8), resultSet.getString(9)));

                            break;

                        }


                    }

                    System.out.println(resultSet.getString(2));


                }

                table.setItems(trains);



                //label4.setText(m1);


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll( label3, label, sourceComboBox, label1, destinationComboBox
                                        ,label2, d, button, table);
        layout.setBackground(background);

        Scene scene = new Scene(layout, 1800,900);
        window.setScene(scene);

        window.show();






    }

}
