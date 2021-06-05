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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class CancelTickets {

    static Stage window = new Stage();

    static TableView<Passenger> table = new TableView<>();
    static int seatNo1, wlNo1;
    static String currTno,  currDate,currClass ;


    public static void display(){

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        BackgroundFill background_fill = new BackgroundFill(Color.web("#ffe0bd"),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        Label label1 = new Label("Cancel tickets");
        label1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label1.setTextFill(Color.web("#003153", 1));

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
                    if(resultSet.next()){
                        seatNo1 = Integer.parseInt(resultSet.getString(9));
                        wlNo1 = Integer.parseInt(resultSet.getString(10));
                        currTno = resultSet.getString(8);
                        currDate = resultSet.getString(5);
                        currClass = resultSet.getString(6);
                    }

                    String classCol = "";
                    if(currClass.equals("Sleeper")){
                        classCol = "sleeper";
                    }
                    else if(currClass.equals("AC 2 tier")){
                        classCol = "ac_two";
                    }
                    else if(currClass.equals("AC 3 tier")){
                        classCol = "ac_three";
                    }
                    else if(currClass.equals("First class")){
                        classCol = "first_class";
                    }
                    else if(currClass.equals("Chair car")){
                        classCol = "cc";
                    }
                    else if(currClass.equals("AC Chair car")){
                        classCol = "ac_cc";
                    }
                    String sql1 = "DELETE FROM passengers WHERE pid ='" + pidInput +"';";

                    PreparedStatement preparedStmt = connection.prepareStatement(sql1);
                    preparedStmt.execute();
                    AlertBox.display("Ticket cancelled successfully.");
                    ObservableList<Passenger> passengers = FXCollections.observableArrayList();

                    String sql2 = "SELECT * FROM passengers WHERE pnr='"+ pnrInput + "';";
                    //Statement statement1 = null;

                    String sql3 = "UPDATE passengers SET waitlist_number = waitlist_number - 1 " +
                            "WHERE waitlist_number > "+ wlNo1 +" AND train_number='" + currTno + "' AND traveldate='" + currDate + "' AND class='"+ currClass + "';";
                    String sql4 = "UPDATE passengers SET seat_number = '"+ seatNo1 +
                            "' WHERE waitlist_number = 0 AND seat_number = 0 AND train_number='" + currTno + "' AND traveldate='" + currDate + "' AND class='"+ currClass + "';";
                    String sql5 = "UPDATE traininfo SET "+ classCol + "= "+ classCol+ "+1 " +
                            "WHERE train_number='" + currTno + "' AND datetravel='" + currDate + "';";

                    String sql6 = "SELECT * FROM passengers WHERE waitlist_number > 0 AND train_number='" + currTno + "' AND traveldate='" + currDate  + "' AND class='"+ currClass + "';";

                    try {

                        ResultSet resultSet2 = statement.executeQuery(sql6);
                        if(!resultSet2.next() && wlNo1==0){
                            PreparedStatement pstmt2 = connection.prepareStatement(sql5);
                            //statement.executeUpdate(sql3);
                            int rowAffected2 = pstmt2.executeUpdate();
                            System.out.println(rowAffected2);
                        }
                        else{
                            PreparedStatement pstmt = connection.prepareStatement(sql3);
                            //statement.executeUpdate(sql3);
                            int rowAffected = pstmt.executeUpdate();
                            System.out.println(rowAffected);
                            //AlertBox.display("Updated.");
                            if(wlNo1 == 0) {

                                PreparedStatement pstmt1 = connection.prepareStatement(sql4);
                                int rowAffected1 = pstmt1.executeUpdate();
                                System.out.println(rowAffected1);
                          }
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
        layout.setBackground(background);


        Scene scene = new Scene(layout, 900,900);
        window.setScene(scene);

        window.show();

    }
}
