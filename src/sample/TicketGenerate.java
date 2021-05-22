package sample;
import Connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.management.GarbageCollectorMXBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class TicketGenerate {

    static TableView<Passenger> table;
    static String currTno;
    static String currDate;

    public static void display(){

        Stage window = new Stage();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        //window in focus
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Tickets");
        window.setMinWidth(250);

        Label label = new Label("Your ticket");
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.web("#ff0000", 1));

        //journey details
        Label label1 = new Label("Train number: ");
        label1.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label1, 0 , 0);
        Label tno = new Label();
        tno.setFont(new Font("Arial", 20));
        GridPane.setConstraints(tno, 1 , 0);
        Label label2 = new Label("Train name: ");
        label2.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label2, 0 , 1);
        Label tname = new Label();
        tname.setFont(new Font("Arial", 20));
        GridPane.setConstraints(tname, 1 , 1);
        Label label3 = new Label("Source");
        label3.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label3, 0,2);
        Label source = new Label();
        source.setFont(new Font("Arial", 20));
        GridPane.setConstraints(source, 1,2);
        Label label4 = new Label("Destination");
        label4.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label4, 0 , 3);
        Label destination = new Label();
        destination.setFont(new Font("Arial", 20));
        GridPane.setConstraints(destination, 1,3);
        Label label5 = new Label("Departure: ");
        label5.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label5, 0, 4);
        Label departure = new Label();
        departure.setFont(new Font("Arial", 20));
        GridPane.setConstraints(departure, 1,4);
        Label label6 = new Label("Arrival: ");
        label6.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label6, 0, 5);
        Label arrival = new Label();
        arrival.setFont(new Font("Arial", 20));
        GridPane.setConstraints(arrival, 1, 5);
        Label label7 = new Label("Duration: ");
        label7.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label7, 0,6);
        Label duration = new Label();
        duration.setFont(new Font("Arial", 20));
        GridPane.setConstraints(duration, 1,6);
        Label label8 = new Label("Travel date: ");
        label8.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label8, 0,7);
        Label travelDate = new Label();
        travelDate.setFont(new Font("Arial", 20));
        GridPane.setConstraints(travelDate, 1,7);
        Label label9 = new Label("Distance: ");
        label9.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label9, 0,8);
        Label distance = new Label();
        distance.setFont(new Font("Arial", 20));
        GridPane.setConstraints(distance, 1,8);


        //passenger details
        Label label10 = new Label("PNR Number");
        label10.setFont(new Font("Arial", 20));
        GridPane.setConstraints(label10, 0,9);
        Label pnrNo = new Label();
        pnrNo.setFont(new Font("Arial", 20));
        GridPane.setConstraints(pnrNo, 1,9);


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

        //seat_number
        TableColumn<Passenger, String> seatColumn = new TableColumn<>("Seat number");
        seatColumn.setMinWidth(200);
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat_number"));

        //class
        TableColumn<Passenger, String> wlColumn = new TableColumn<>("Waitlist number");
        wlColumn.setMinWidth(200);
        wlColumn.setCellValueFactory(new PropertyValueFactory<>("wl_number"));


        table = new TableView<>();
        table.getColumns().addAll( idColumn, nameColumn, ageColumn, genderColumn, classColumn, seatColumn, wlColumn);
        GridPane.setConstraints(table,0 ,10);


        //scene 2
        GridPane grid = new GridPane();
        //10px padding all sides
        grid.setPadding(new Insets(10,10,10,10));

        //vertical and horizontal spacing
        grid.setHgap(5);
        grid.setVgap(8);
        grid.getChildren().addAll( label1,label2, label3, label4, label5, label6, label7, label8, label9, label10,
                                   tno,tname,source, departure,destination,arrival,duration,travelDate,distance,pnrNo, table );
        Scene scene1 = new Scene(grid, 1200,1200);




        //enter pnr
        Label label11 = new Label("Enter PNR to generate tickets: ");
        TextField pnr = new TextField();
        pnr.setPrefWidth(300);
        pnr.setMaxWidth(300);
        Button button = new Button("Generate ticket");

        button.setOnAction(e -> {
            ObservableList<Passenger> passengers = FXCollections.observableArrayList();
            String pnrInput = pnr.getText();
            String sql = "SELECT * FROM passengers WHERE pnr='"+ pnrInput + "';";

            Statement statement = null;

            try{
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                System.out.println("yes");
                if(!resultSet.isBeforeFirst()){
                    AlertBox.display("Enter a valid pnr number");

            }else{

                while(resultSet.next()){
                    currTno = resultSet.getString(8);
                    currDate = resultSet.getString(5);

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
                String sql1 = "SELECT * FROM trainjourney WHERE train_number='"+ currTno + "';";
                ResultSet resultSet1 = statement.executeQuery(sql1);
                while (resultSet1.next()){
                    tno.setText(resultSet1.getString(1));
                    tname.setText(resultSet1.getString(2));
                    source.setText(resultSet1.getString(3));
                    destination.setText(resultSet1.getString(4));
                    departure.setText(resultSet1.getString(6));
                    arrival.setText(resultSet1.getString(7));
                    distance.setText(resultSet1.getString(8));
                    duration.setText(resultSet1.getString(9));
                    travelDate.setText(currDate);
                    pnrNo.setText(pnrInput);



                }

                table.setItems(passengers);





                window.setScene(scene1);




            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        });

















        //scene 1
        VBox layout = new VBox(10);

        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll( label11, pnr, button);

        Scene scene = new Scene(layout, 300,500);
        window.setScene(scene);

        window.show();








    }
}
