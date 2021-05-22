package sample;

import Connectivity.ConnectionClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class CancelTickets {

    static Stage window = new Stage();

    static TableView<Passenger> table = new TableView<>();

    public static void display(){

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        Label label1 = new Label("Cancel tickets");
        label1.setFont(new Font("Arial", 30));
        label1.setTextFill(Color.web("#ff0000", 1));

        Label label = new Label("Enter pnr");
        TextField pnr = new TextField();
        pnr.setPrefWidth(300);
        pnr.setMaxWidth(300);

        Label label2 = new Label("Enter the passenger id whose ticket is to be cancelled");
        TextField pid = new TextField();
        pid.setPrefWidth(300);
        pid.setMaxWidth(300);


        Button button = new Button("Check passenger details");


        //train number
        TableColumn<Passenger, String> numberColumn = new TableColumn<>("Train Number");
        numberColumn.setMinWidth(200);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("train_number"));

        //pid
        TableColumn<Passenger, String> idColumn = new TableColumn<>("Passenger ID");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));

        //passenger name
        TableColumn<Passenger, String> nameColumn = new TableColumn<>("Passenger Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //age
        TableColumn<Passenger, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setMinWidth(200);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        //gender
        TableColumn<Passenger, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(200);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        //class
        TableColumn<Passenger, String> classColumn = new TableColumn<>("Class");
        classColumn.setMinWidth(200);
        classColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //date
        TableColumn<Passenger, String> dateColumn = new TableColumn<>("Date of travel");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("travelDate"));

        //pnr
        TableColumn<Passenger, String> pnrColumn = new TableColumn<>("PNR");
        pnrColumn.setMinWidth(200);
        pnrColumn.setCellValueFactory(new PropertyValueFactory<>("pnr"));

        //seat_number
        TableColumn<Passenger, String> seatColumn = new TableColumn<>("Seat number");
        seatColumn.setMinWidth(200);
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat_number"));

        //class
        TableColumn<Passenger, String> wlColumn = new TableColumn<>("Waitlist number");
        wlColumn.setMinWidth(200);
        wlColumn.setCellValueFactory(new PropertyValueFactory<>("wl_number"));

        table.getColumns().addAll(numberColumn, idColumn, nameColumn, ageColumn, genderColumn, classColumn, dateColumn, pnrColumn, seatColumn, wlColumn);


        Button button2 = new Button("Cancel ticket");


        button.setOnAction(e -> {

            ObservableList<Passenger> passengers = FXCollections.observableArrayList();
            String pnrInput = pnr.getText();
            String sql = "SELECT * FROM passengers WHERE pnr='"+ pnrInput + "';";
            Statement statement = null;


            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                System.out.println("yes");
                if(!resultSet.isBeforeFirst()){
                    AlertBox.display("Enter a valid pnr number");

                }else{

                    while(resultSet.next()){

                        String currSeat, wlNo;

                        if(Integer.parseInt(resultSet.getString(9)) != 0){
                            currSeat = resultSet.getString(9);
                            wlNo = "-";

                        }
                        else{
                            currSeat = "-";
                            wlNo = resultSet.getString(10);
                        }

                        passengers.add(new Passenger(resultSet.getString(8), resultSet.getString(1), resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4),
                                resultSet.getString(6), resultSet.getString(7),
                                resultSet.getString(5), currSeat , wlNo));
                    }


                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            table.setItems(passengers);


        });

        button2.setOnAction(e -> {

            String pidInput = pid.getText();
            String pnrInput = pnr.getText();
            String sql = "SELECT * FROM passengers WHERE pnr='"+ pnrInput + "' AND pid='"+ pidInput +"';";
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                if (!resultSet.isBeforeFirst()){
                    AlertBox.display("Enter a valid passenger id");

                }else {
                    String sql1 = "DELETE FROM passengers WHERE pid ='" + pidInput +"';";
                    PreparedStatement preparedStmt = connection.prepareStatement(sql1);
                    preparedStmt.execute();
                    AlertBox.display("Ticket cancelled successfully.");
                    ObservableList<Passenger> passengers = FXCollections.observableArrayList();

                    String sql2 = "SELECT * FROM passengers WHERE pnr='"+ pnrInput + "';";
                    //Statement statement1 = null;
                    try {
                        statement = connection.createStatement();
                        ResultSet resultSet1 = statement.executeQuery(sql2);
                        System.out.println("yes");


                        while(resultSet1.next()){

                            String currSeat, wlNo;

                            if(Integer.parseInt(resultSet1.getString(9)) != 0){
                                currSeat = resultSet1.getString(9);
                                wlNo = "-";

                            }
                            else{
                                currSeat = "-";
                                wlNo = resultSet1.getString(10);
                            }

                            passengers.add(new Passenger(resultSet1.getString(8), resultSet1.getString(1), resultSet1.getString(2),
                                    resultSet1.getString(3), resultSet1.getString(4),
                                    resultSet1.getString(6), resultSet1.getString(7),
                                    resultSet1.getString(5), currSeat , wlNo));
                        }


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    table.setItems(passengers);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });


        //Layout
        VBox layout = new VBox(30);
        layout.setPadding(new Insets(10,10,10,10));

        layout.getChildren().addAll(label1, label, pnr,  button, label2, pid, table, button2);


        Scene scene = new Scene(layout, 900,900);
        window.setScene(scene);

        window.show();

    }
}
