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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatsInfo {

    static Stage window = new Stage();
    static TableView<AvailabilityInfo> table = new TableView<>();
    static String genderInput1 = "male";
    static String genderInput2 = "male";
    static String genderInput3 = "male";
    static String genderInput4 = "male";

    static ComboBox<String> classComboBox;
    static String currDate = "";
    static String currTno = "";
    static int pnrno;

    public static void display(){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        Label label1 = new Label("Check availability");
        label1.setFont(new Font("Arial", 30));
        label1.setTextFill(Color.web("#ff0000", 1));

        Label label = new Label("Train number");

        Label label2 = new Label("Travel Date");
        DatePicker d = new DatePicker();

        TextField tno = new TextField();
        tno.setPrefWidth(300);
        tno.setMaxWidth(300);

        Button button = new Button("Check availability!");

        //train number
        TableColumn<AvailabilityInfo, String> numberColumn = new TableColumn<>("Train Number");
        numberColumn.setMinWidth(200);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("train_number"));

        //train name
        TableColumn<AvailabilityInfo, String> nameColumn = new TableColumn<>("Train Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("train_name"));

        //sleeper seats
        TableColumn<AvailabilityInfo, String> sleeperColumn = new TableColumn<>("Sleeper");
        sleeperColumn.setMinWidth(200);
        sleeperColumn.setCellValueFactory(new PropertyValueFactory<>("sleeper"));

        //ac two seats
        TableColumn<AvailabilityInfo, String> acTwoColumn = new TableColumn<>("AC 2 tier");
        acTwoColumn.setMinWidth(200);
        acTwoColumn.setCellValueFactory(new PropertyValueFactory<>("ac_two"));

        //ac three seats
        TableColumn<AvailabilityInfo, String> acThreeColumn = new TableColumn<>("AC 3 tier");
        acThreeColumn.setMinWidth(200);
        acThreeColumn.setCellValueFactory(new PropertyValueFactory<>("ac_three"));

        //first class seats
        TableColumn<AvailabilityInfo, String> firstColumn = new TableColumn<>("First Class");
        firstColumn.setMinWidth(200);
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("first_class"));

        //chair car seats
        TableColumn<AvailabilityInfo, String> ccColumn = new TableColumn<>("CC");
        ccColumn.setMinWidth(200);
        ccColumn.setCellValueFactory(new PropertyValueFactory<>("cc"));

        //ac cc seats
        TableColumn<AvailabilityInfo, String> acccColumn = new TableColumn<>("AC CC");
        acccColumn.setMinWidth(200);
        acccColumn.setCellValueFactory(new PropertyValueFactory<>("ac_cc"));


        table.getColumns().addAll(numberColumn, nameColumn, sleeperColumn, acTwoColumn, acThreeColumn, firstColumn, ccColumn, acccColumn);

        Button button1 = new Button("Book tickets");




        button.setOnAction(e -> {
            ObservableList<AvailabilityInfo> trains = FXCollections.observableArrayList();
            String trainNo = tno.getText();
            LocalDate dateInput = d.getValue();

            String sql = "SELECT * FROM traininfo WHERE train_number='"+ trainNo +  "' AND datetravel='" + dateInput.toString() +"';";
            String sql1 = "SELECT * FROM trainjourney WHERE train_number='"+ trainNo + "';";
            Statement statement = null;
            Statement statement1 = null;



            try {
                statement = connection.createStatement();
                statement1 = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ResultSet resultSet1 = statement1.executeQuery(sql1);

                String trainName = "";



                    while (resultSet.next() && resultSet1.next()){

                        trainName = resultSet1.getString(2);
                        trains.add(new AvailabilityInfo(trainNo, trainName, resultSet.getString(2),
                                resultSet.getString(3), resultSet.getString(4),
                                resultSet.getString(5), resultSet.getString(6),
                                resultSet.getString(7)));


                    }
                    if(trains.isEmpty()){
                        AlertBox.display("Train doesn't exist. Check train number!");

                    }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            table.setItems(trains);

        });



        //passenger deets

        Label dateOfTravel = new Label("Date of travel");
        Label pclass = new Label("Class");

        Label p1 = new Label("Passenger 1 details");
        Label p2 = new Label("Passenger 2 details");
        Label p3 = new Label("Passenger 3 details");
        Label p4 = new Label("Passenger 4 details");



        Label pname1 = new Label("Name");
        Label pname2 = new Label("Name");
        Label pname3 = new Label("Name");
        Label pname4 = new Label("Name");

        Label page1 = new Label("Age");
        Label page2 = new Label("Age");
        Label page3 = new Label("Age");
        Label page4 = new Label("Age");

        Label pg1 = new Label("Gender");
        Label pg2 = new Label("Gender");
        Label pg3 = new Label("Gender");
        Label pg4 = new Label("Gender");



        TextField name1 = new TextField();
        TextField name2 = new TextField();
        TextField name3 = new TextField();
        TextField name4 = new TextField();


        TextField age1 = new TextField();
        TextField age2 = new TextField();
        TextField age3 = new TextField();
        TextField age4 = new TextField();


        TilePane r1 = new TilePane();
        RadioButton rm1 = new RadioButton("male");
        RadioButton rf1 = new RadioButton("female");
        RadioButton ro1 = new RadioButton("others");
        rm1.setSelected(true);
        r1.getChildren().addAll(rm1,rf1,ro1);
        ToggleGroup tg1 = new ToggleGroup();
        rm1.setToggleGroup(tg1);
        rf1.setToggleGroup(tg1);
        ro1.setToggleGroup(tg1);
        tg1.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg1.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();
                    genderInput1 = s;

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });

        TilePane r2 = new TilePane();
        RadioButton rm2 = new RadioButton("male");
        RadioButton rf2 = new RadioButton("female");
        RadioButton ro2 = new RadioButton("others");
        rm2.setSelected(true);
        r2.getChildren().addAll(rm2,rf2,ro2);
        ToggleGroup tg2 = new ToggleGroup();
        rm2.setToggleGroup(tg2);
        rf2.setToggleGroup(tg2);
        ro2.setToggleGroup(tg2);
        tg2.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg2.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();
                    genderInput2 = s;

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });

        TilePane r3 = new TilePane();
        RadioButton rm3 = new RadioButton("male");
        RadioButton rf3 = new RadioButton("female");
        RadioButton ro3 = new RadioButton("others");
        rm3.setSelected(true);
        r3.getChildren().addAll(rm3,rf3,ro3);
        ToggleGroup tg3 = new ToggleGroup();
        rm3.setToggleGroup(tg3);
        rf3.setToggleGroup(tg3);
        ro3.setToggleGroup(tg3);
        tg3.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg3.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();
                    genderInput3 = s;

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });

        TilePane r4 = new TilePane();
        RadioButton rm4 = new RadioButton("male");
        RadioButton rf4 = new RadioButton("female");
        RadioButton ro4 = new RadioButton("others");
        rm4.setSelected(true);
        r4.getChildren().addAll(rm4,rf4,ro4);
        ToggleGroup tg4 = new ToggleGroup();
        rm4.setToggleGroup(tg4);
        rf4.setToggleGroup(tg4);
        ro4.setToggleGroup(tg4);
        tg4.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg4.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();
                    genderInput4 = s;

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });






        Label label7 = new Label("Book here");
        label7.setFont(new Font("Arial", 30));
        label7.setTextFill(Color.web("#ff0000", 1));


        classComboBox = new ComboBox<String>();
        classComboBox.getItems().addAll(
                "Sleeper",
                "AC 2 tier",
                "AC 3 tier",
                "First class",
                "Chair car",
                "AC Chair car");
        classComboBox.setPromptText("Select class");
        Button button2 = new Button("Book now");




        button2.setOnAction(e -> {
            String pnameInput1 = name1.getText();
            String pnameInput2 = name2.getText();
            String pnameInput3 = name3.getText();
            String pnameInput4 = name4.getText();

            String pageInput1 = age1.getText();
            String pageInput2 = age2.getText();
            String pageInput3 = age3.getText();
            String pageInput4 = age4.getText();

            String classInput = classComboBox.getValue();
            Statement statement ;



            try {
                String sql2 = "SELECT IFNULL(MAX(pnr), 0) AS MaxX FROM passengers";
                statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql2);
                rs.next();
                pnrno = rs.getInt(1);
                System.out.println("PNR: "+ pnrno);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }




            if(!pnameInput1.equals("")){

                try {


                    String sql1 = "SELECT IFNULL(MAX(pid), 0) AS MaxP FROM passengers";

                    statement = connection.createStatement();
                    ResultSet rs1 = statement.executeQuery(sql1);
                    rs1.next();
                    int c = rs1.getInt(1);
                    System.out.println("PID: "+ c);

                    String classCol = "";
                    if(classInput.equals("Sleeper")){
                        classCol = "sleeper";
                    }
                    else if(classInput.equals("AC 2 tier")){
                        classCol = "ac_two";
                    }
                    else if(classInput.equals("AC 3 tier")){
                        classCol = "ac_three";
                    }
                    else if(classInput.equals("First class")){
                        classCol = "first_class";
                    }
                    else if(classInput.equals("Chair car")){
                        classCol = "cc";
                    }
                    else if(classInput.equals("AC Chair car")){
                        classCol = "ac_cc";
                    }

                    String sqll = "SELECT " + classCol + " FROM traininfo WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                    ResultSet rss1 = statement.executeQuery(sqll);
                    rss1.next();
                    String seats = rss1.getString(1);
                    int sNo  = Integer.parseInt(seats);
                    System.out.println(seats);


                    int wl_no=-1;
                    if(sNo > 0){
                        wl_no = -1;
                    }
                    else{
                        String sqlll = "SELECT IFNULL(MAX(waitlist_number), 0) AS MaxWL FROM passengers WHERE train_number='" + currTno + "' AND traveldate='" + currDate + "';";
                        ResultSet rsss1 = statement.executeQuery(sqlll);
                        rsss1.next();

                        int wlNo = rsss1.getInt(1);
                        wl_no = wlNo;
                        sNo = 0;
                        System.out.println("WL: "+ wlNo);

                    }

                    String sql = "INSERT INTO passengers VALUES('"+ (c+1) +"','"+ pnameInput1 +  "','" +  pageInput1 +  "','"+
                            genderInput1 + "','" + currDate + "','" + classInput + "','" + (pnrno+1) + "','"+ currTno + "','"+ sNo  +"','"+ (wl_no+1)  +"');";

                    statement.executeUpdate(sql);
                    //AlertBox.display("Passenger details recorded.");
                    if(wl_no == -1){
                        String sqlUpdate = "UPDATE traininfo " +
                                "SET " + classCol +"= "+ classCol + "-1 " + "WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                        statement.executeUpdate(sqlUpdate);
                        System.out.println("updated");
                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }

            if(!pnameInput2.equals("")){

                try {




                    String sql2 = "SELECT IFNULL(MAX(pid), 0) AS MaxP FROM passengers";

                    statement = connection.createStatement();
                    ResultSet rs2 = statement.executeQuery(sql2);
                    rs2.next();
                    int c1 = rs2.getInt(1);
                    System.out.println("PID: "+ c1);

                    String classCol = "";
                    if(classInput.equals("Sleeper")){
                        classCol = "sleeper";
                    }
                    else if(classInput.equals("AC 2 tier")){
                        classCol = "ac_two";
                    }
                    else if(classInput.equals("AC 3 tier")){
                        classCol = "ac_three";
                    }
                    else if(classInput.equals("First class")){
                        classCol = "first_class";
                    }
                    else if(classInput.equals("Chair car")){
                        classCol = "cc";
                    }
                    else if(classInput.equals("AC Chair car")){
                        classCol = "ac_cc";
                    }

                    String sqll = "SELECT " + classCol + " FROM traininfo WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                    ResultSet rss1 = statement.executeQuery(sqll);
                    rss1.next();
                    String seats = rss1.getString(1);
                    int sNo  = Integer.parseInt(seats);
                    System.out.println(seats);


                    int wl_no=-1;
                    if(sNo > 0){
                        wl_no = -1;
                    }
                    else{
                        String sqlll = "SELECT IFNULL(MAX(waitlist_number), 0) AS MaxWL FROM passengers WHERE train_number='" + currTno + "' AND traveldate='" + currDate + "';";
                        ResultSet rsss1 = statement.executeQuery(sqlll);

                        rsss1.next();
                        int wlNo = rsss1.getInt(1);
                        wl_no = wlNo;
                        sNo = 0;
                        System.out.println("WL: "+ wlNo);

                    }

                    String sql22 = "INSERT INTO passengers VALUES('"+ (c1+1) +"','"+ pnameInput2 +  "','" +  pageInput2 +  "','"+
                            genderInput2 + "','" + currDate + "','" + classInput + "','" + (pnrno+1) + "','"+ currTno + "','"+ sNo  +"','"+ (wl_no+1)  +"');";
                    statement.executeUpdate(sql22);
                    //AlertBox.display("Passenger details recorded.");
                    if(wl_no == -1){
                        String sqlUpdate = "UPDATE traininfo " +
                                "SET " + classCol +"= "+ classCol + "-1 " + "WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                        statement.executeUpdate(sqlUpdate);
                        System.out.println("updated");
                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }


            if(!pnameInput3.equals("")){

                try {




                    String sql3 = "SELECT IFNULL(MAX(pid), 0) AS MaxP FROM passengers";

                    statement = connection.createStatement();
                    ResultSet rs3 = statement.executeQuery(sql3);
                    rs3.next();
                    int c2 = rs3.getInt(1);
                    System.out.println("PID: "+ c2);

                    String classCol = "";
                    if(classInput.equals("Sleeper")){
                        classCol = "sleeper";
                    }
                    else if(classInput.equals("AC 2 tier")){
                        classCol = "ac_two";
                    }
                    else if(classInput.equals("AC 3 tier")){
                        classCol = "ac_three";
                    }
                    else if(classInput.equals("First class")){
                        classCol = "first_class";
                    }
                    else if(classInput.equals("Chair car")){
                        classCol = "cc";
                    }
                    else if(classInput.equals("AC Chair car")){
                        classCol = "ac_cc";
                    }

                    String sqll = "SELECT " + classCol + " FROM traininfo WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                    ResultSet rss1 = statement.executeQuery(sqll);
                    rss1.next();
                    String seats = rss1.getString(1);
                    int sNo  = Integer.parseInt(seats);
                    System.out.println(seats);


                    int wl_no=-1;
                    if(sNo > 0){
                        wl_no = -1;
                    }
                    else{
                        String sqlll = "SELECT IFNULL(MAX(waitlist_number), 0) AS MaxWL FROM passengers WHERE train_number='" + currTno + "' AND traveldate='" + currDate + "';";
                        ResultSet rsss1 = statement.executeQuery(sqlll);

                        rsss1.next();
                        int wlNo = rsss1.getInt(1);
                        wl_no = wlNo;
                        sNo = 0;
                        System.out.println("WL: "+ wlNo);

                    }

                    String sql33 = "INSERT INTO passengers VALUES('"+ (c2+1)+"','"+ pnameInput3 +  "','" +  pageInput3 +  "','"+
                            genderInput3 + "','" + currDate + "','" + classInput + "','" + (pnrno+1) + "','"+ currTno + "','"+ sNo  +"','"+ (wl_no+1)  +"');";
                    statement.executeUpdate(sql33);
                    //AlertBox.display("Passenger details recorded.");
                    if(wl_no == -1){
                        String sqlUpdate = "UPDATE traininfo " +
                                "SET " + classCol +"= "+ classCol + "-1 " + "WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                        statement.executeUpdate(sqlUpdate);
                        System.out.println("updated");
                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }

            if(!pnameInput4.equals("")){

                try {




                    String sql4 = "SELECT IFNULL(MAX(pid), 0) AS MaxP FROM passengers";

                    statement = connection.createStatement();
                    ResultSet rs4 = statement.executeQuery(sql4);
                    rs4.next();
                    int c3 = rs4.getInt(1);
                    System.out.println("PID: "+ c3);

                    String classCol = "";
                    if(classInput.equals("Sleeper")){
                        classCol = "sleeper";
                    }
                    else if(classInput.equals("AC 2 tier")){
                        classCol = "ac_two";
                    }
                    else if(classInput.equals("AC 3 tier")){
                        classCol = "ac_three";
                    }
                    else if(classInput.equals("First class")){
                        classCol = "first_class";
                    }
                    else if(classInput.equals("Chair car")){
                        classCol = "cc";
                    }
                    else if(classInput.equals("AC Chair car")){
                        classCol = "ac_cc";
                    }

                    String sqll = "SELECT " + classCol + " FROM traininfo WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                    ResultSet rss1 = statement.executeQuery(sqll);
                    rss1.next();
                    String seats = rss1.getString(1);
                    int sNo  = Integer.parseInt(seats);
                    System.out.println(seats);


                    int wl_no=-1;
                    if(sNo > 0){
                        wl_no = -1;
                    }
                    else{
                        String sqlll = "SELECT IFNULL(MAX(waitlist_number), 0) AS MaxWL FROM passengers WHERE train_number='" + currTno + "' AND traveldate='" + currDate + "';";
                        ResultSet rsss1 = statement.executeQuery(sqlll);

                        rsss1.next();
                        int wlNo = rsss1.getInt(1);
                        wl_no = wlNo;
                        sNo = 0;
                        System.out.println("WL: "+ wlNo);

                    }

                    String sql44 = "INSERT INTO passengers VALUES('"+ (c3+1) +"','"+ pnameInput4 +  "','" +  pageInput4 +  "','"+
                            genderInput4 + "','" + currDate + "','" + classInput + "','" + (pnrno+1) + "','"+ currTno + "','"+ sNo  +"','"+ (wl_no+1)  +"');";
                    statement.executeUpdate(sql44);
                    //AlertBox.display("Passenger details recorded.");

                    if(wl_no == -1){
                        String sqlUpdate = "UPDATE traininfo " +
                                "SET " + classCol +"= "+ classCol + "-1 " + "WHERE train_number='"+ currTno + "' AND datetravel='" + currDate + "';";
                        statement.executeUpdate(sqlUpdate);
                        System.out.println("updated");
                    }



                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }








            AlertBox.display("Successfully booked! Your PNR number is: "+ (pnrno+1)+" Generate ticket now.");
            name1.setText("");
            name2.setText("");
            name3.setText("");
            name4.setText("");

            age1.setText("");
            age2.setText("");
            age3.setText("");
            age4.setText("");




        });

        //new scene booking
        VBox layout1 = new VBox(30);
        layout1.setPadding(new Insets(10,10,10,10));

        layout1.getChildren().addAll(label7, pclass, classComboBox, p1, pname1, name1, page1, age1, pg1, r1,
                p2, pname2, name2, page2, age2, pg2, r2,
                p3, pname3, name3, page3, age3, pg3, r3,
                p4, pname4, name4, page4, age4, pg4, r4, button2);

        ScrollPane sp = new ScrollPane();
        sp.setContent(layout1);


        Scene scene1 = new Scene(sp, 1500,900);


        button1.setOnAction(e -> {
            String trainNo = tno.getText();
            String dateInput = d.getValue().toString();
            String sql = "SELECT * FROM traininfo WHERE train_number='"+ trainNo +  "' AND datetravel='" + dateInput +"';";
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                if(!resultSet.next()){
                    AlertBox.display("Train doesn't exist. Check train number!");

                }else{
                    currDate = resultSet.getString(8);
                    currTno = resultSet.getString(1);
                    window.setScene(scene1);


                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });





        //Layout
        VBox layout = new VBox(30);
        layout.setPadding(new Insets(10,10,10,10));

        layout.getChildren().addAll(label1, label , tno, label2, d,button, table, button1);



        Scene scene = new Scene(layout, 700,700);
        window.setScene(scene);

        window.show();













    }

}
