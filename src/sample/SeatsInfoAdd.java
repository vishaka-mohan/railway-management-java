
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

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class SeatsInfoAdd {

    static ComboBox<String> sourceComboBox;
    static ComboBox<String> destinationComboBox;
    static Stage window ;


    public static void display()  {
        window = new Stage();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        BackgroundFill background_fill = new BackgroundFill(Color.web("#ffe0bd"),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        Label label = new Label("Add new train details/ Update existing train details");
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label.setTextFill(Color.web("#003153", 1));

        Label label1 = new Label("Train number");
        Label label2 = new Label("Sleeper seats");
        Label label3 = new Label("AC Two tier seats");
        Label label4 = new Label("AC Three tier seats");
        Label label5 = new Label("First Class seats");
        Label label6 = new Label("Chair car seats");
        Label label7 = new Label("AC Chair car seats");


        TextField tno = new TextField();
        tno.setPrefWidth(300);
        tno.setMaxWidth(300);
        TextField sleeperSeats = new TextField();
        sleeperSeats.setPrefWidth(300);
        sleeperSeats.setMaxWidth(300);
        TextField acTwoSeats = new TextField();
        acTwoSeats.setPrefWidth(300);
        acTwoSeats.setMaxWidth(300);
        TextField acThreeSeats = new TextField();
        acThreeSeats.setPrefWidth(300);
        acThreeSeats.setMaxWidth(300);
        TextField firstSeats = new TextField();
        firstSeats.setPrefWidth(300);
        firstSeats.setMaxWidth(300);
        TextField ccSeats = new TextField();
        ccSeats.setPrefWidth(300);
        ccSeats.setMaxWidth(300);
        TextField accSeats = new TextField();
        accSeats.setPrefWidth(300);
        accSeats.setMaxWidth(300);

        DatePicker d = new DatePicker();
        d.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        Label label8 = new Label("Date");


        Button button = new Button("Add/Update train details");

        button.setOnAction(e -> {

            String tnoInput = tno.getText();
            String sleeperSeatsInput = sleeperSeats.getText();
            String acTwoSeatsInput = acTwoSeats.getText();
            String acThreeSeatsInput = acThreeSeats.getText();
            String firstSeatsInput = firstSeats.getText();
            String ccSeatsInput = ccSeats.getText();
            String accSeatsInput = accSeats.getText();
            LocalDate dateInput = d.getValue();
            DayOfWeek day = dateInput.getDayOfWeek();

            try {
                String sql1 = "SELECT * FROM traininfo WHERE train_number='"+ tnoInput + "' AND datetravel='" + dateInput.toString() +"';";
                String sql2 = "SELECT * FROM trainjourney WHERE train_number='"+ tnoInput + "';";
                Statement statement ;
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql1);

                if(resultSet.next()){

                   String sql3 = "UPDATE traininfo SET sleeper='" + sleeperSeatsInput + "', ac_two='" + acTwoSeatsInput + "', ac_three='" + acThreeSeatsInput
                           + "', first_class='" + firstSeatsInput + "', cc='"+ ccSeatsInput + "', ac_cc='" + accSeatsInput + "', datetravel= '"+
                           dateInput.toString() + "' WHERE train_number='"+tnoInput + "' AND datetravel='" + dateInput.toString() +"';";
                    PreparedStatement pstmt = connection.prepareStatement(sql3);
                    //statement.executeUpdate(sql3);
                    int rowAffected = pstmt.executeUpdate();
                    System.out.println(rowAffected);
                    AlertBox.display("Updated.");
                    tno.setText("");
                    sleeperSeats.setText("");
                    acTwoSeats.setText("");
                    acThreeSeats.setText("");
                    firstSeats.setText("");
                    ccSeats.setText("");
                    accSeats.setText("");

                }else {
                    if(!statement.executeQuery(sql2).next()){
                        AlertBox.display("Invalid train number. Doesn't exist.");
                    }
                    else{
                        if(tnoInput.equals("")){
                            AlertBox.display("Enter a train number.");
                        }else{


                            String sql = "INSERT INTO traininfo VALUES('"+ tnoInput +"','"+ sleeperSeatsInput +  "','" +  acTwoSeatsInput +  "','"+
                                    acThreeSeatsInput + "','" + firstSeatsInput + "','" + ccSeatsInput + "','" + accSeatsInput + "','"+ dateInput.toString() +"');";
                            statement = connection.createStatement();
                            statement.executeUpdate(sql);
                            AlertBox.display("Train details added successfully!");
                            tno.setText("");
                            sleeperSeats.setText("");
                            acTwoSeats.setText("");
                            acThreeSeats.setText("");
                            firstSeats.setText("");
                            ccSeats.setText("");
                            accSeats.setText("");
                        }

                    }



                }




            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });




        //Layout
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, label1, tno, label2, sleeperSeats, label3, acTwoSeats, label4, acThreeSeats, label5, firstSeats, label6, ccSeats,
                label7, accSeats, label8, d, button);
        layout.setBackground(background);

        Scene scene = new Scene(layout, 900,900);
        window.setScene(scene);

        window.show();

    }




}
