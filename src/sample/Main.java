package sample;

import Connectivity.ConnectionClass;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    Scene scene1, scene2, scene3, scene4, scene5;
    String genderInput="male";

    @Override
    public void start(Stage stage) throws Exception{

        Stage window = stage;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        window.setTitle("Indian Railways");
        // create a scene
        BackgroundFill background_fill = new BackgroundFill(Color.web("#ffe0bd"),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);



        // create a label
        Label label = new Label("Welcome to Indian Railways");
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label.setTextFill(Color.web("#003153", 1));

        Label labelReg = new Label("Register Here");
        labelReg.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        labelReg.setTextFill(Color.web("#003153", 1));

        Label labelLogin = new Label("Login Here");
        labelLogin.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        labelLogin.setTextFill(Color.web("#003153", 1));


        Button button = new Button("Register");
        Button button1 = new Button("Login");
        Button buttonAdmin = new Button("Admin Login");


        //Scene 4 after login
        Label labelAdmin = new Label("Welcome to Indian Railways Admin Panel.\nYou are currently logged in.");
        labelAdmin.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        labelAdmin.setTextFill(Color.web("#003153", 1));




        Menu updateTrainInfo = new Menu("Update Train Information");

        MenuItem addTrains = new MenuItem("Add new trains");
        addTrains.setOnAction(e -> {
            TrainInfoAdd.display();
        });
        MenuItem addAvailability = new MenuItem("Add seat availability info");

        addAvailability.setOnAction(e -> {
            SeatsInfoAdd.display();
        });
        updateTrainInfo.getItems().addAll(addTrains, addAvailability);





        Menu quitAdmin = new Menu("Quit");
        MenuItem logOutAdmin = new MenuItem("Log Out");
        logOutAdmin.setOnAction(e -> {
            window.setScene(scene1);
        });
        MenuItem exitPageAdmin = new MenuItem("Exit");
        exitPageAdmin.setOnAction(e -> {
            window.close();
        });
        quitAdmin.getItems().addAll(logOutAdmin, exitPageAdmin);

        MenuBar menuBarAdmin = new MenuBar();
        menuBarAdmin.getMenus().addAll(updateTrainInfo, quitAdmin);

        BorderPane layout5 = new BorderPane();
        layout5.setTop(menuBarAdmin);
        layout5.setCenter(labelAdmin);
        layout5.setBackground(background);

        scene5 = new Scene(layout5, 900,500);

        Label l1 = new Label("Admin Password");
        PasswordField p1 = new PasswordField();
        Button adminBack = new Button("Back");
        p1.setPrefWidth(300);
        p1.setMaxWidth(300);

        Button logAdmin = new Button("Log in");

        VBox lay = new VBox(30);
        lay.setPadding(new Insets(20,10,10,20));
        lay.getChildren().addAll(adminBack,l1, p1, logAdmin);
        lay.setBackground(background);

        Scene adminLogin = new Scene(lay, 300,300);


        adminBack.setOnAction(e -> {
            window.setScene(scene1);
        });

        buttonAdmin.setOnAction(e -> {
            window.setScene(adminLogin);

        });

        logAdmin.setOnAction(e -> {
            String passInput = p1.getText();
            if(passInput.equals("admin")){
                window.setScene(scene5);

            }else {
                AlertBox.display("Incorrect password.");
            }
        });

        // create a AnchorPane
        AnchorPane root = new AnchorPane();



        // anchor to the register button
        AnchorPane.setTopAnchor(button, 145.0);
        AnchorPane.setLeftAnchor(button, 150.0);
        AnchorPane.setRightAnchor(button, 150.0);
        AnchorPane.setBottomAnchor(button, 170.0);


        // anchor to the login button
        AnchorPane.setTopAnchor(button1, 200.0);
        AnchorPane.setLeftAnchor(button1, 150.0);
        AnchorPane.setRightAnchor(button1, 150.0);
        AnchorPane.setBottomAnchor(button1, 110.0);

        // anchor to the login button
        AnchorPane.setTopAnchor(buttonAdmin, 255.0);
        AnchorPane.setLeftAnchor(buttonAdmin, 150.0);
        AnchorPane.setRightAnchor(buttonAdmin, 150.0);
        AnchorPane.setBottomAnchor(buttonAdmin, 50.0);

        //anchor to the label
        AnchorPane.setTopAnchor(label, 50.0);
        AnchorPane.setLeftAnchor(label, 50.0);


        root.getChildren().addAll(label, button, button1, buttonAdmin);
        // set background
        root.setBackground(background);


        //scene1
        scene1 = new Scene(root, 550, 350);



        //register layout
        Label label1 = new Label("Name");
        Label label2 = new Label("Email");
        Label label3 = new Label("Contact");
        Label label4 = new Label("Gender");
        Label label5 = new Label("Password");

        TextField name = new TextField();
        name.setPrefWidth(300);
        name.setMaxWidth(300);
        TextField email = new TextField();
        email.setPrefWidth(300);
        email.setMaxWidth(300);
        TextField contact = new TextField();
        contact.setPrefWidth(300);
        contact.setMaxWidth(300);
        PasswordField password = new PasswordField();
        password.setPrefWidth(300);
        password.setMaxWidth(300);


        TilePane r = new TilePane();
        RadioButton r1 = new RadioButton("male");
        RadioButton r2 = new RadioButton("female");
        RadioButton r3 = new RadioButton("others");
        r1.setSelected(true);
        r.getChildren().addAll(r1,r2,r3);

        ToggleGroup tg = new ToggleGroup();
        r1.setToggleGroup(tg);
        r2.setToggleGroup(tg);
        r3.setToggleGroup(tg);



        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)tg.getSelectedToggle();

                if (rb != null) {
                    String s = rb.getText();
                    genderInput = s;

                    // change the label
                    //l2.setText(s + " selected");
                }
            }
        });


        Button goBackReg = new Button("Back");
        goBackReg.setOnAction(e -> window.setScene(scene1));
        Label alertReg = new Label();

        Button button2 =new Button("Register");
        button2.setOnAction(e -> {
            String nameInput = name.getText();
            String emailInput = email.getText();
            String contactInput = contact.getText();
            String passwordInput = password.getText();


            if(nameInput.equals("") || emailInput.equals("") || contactInput.equals("")
                || passwordInput.equals("")){
                alertReg.setText("Please fill all the fields");

            }
            else
                {
                    String sql = "SELECT * FROM users WHERE email='"+ emailInput+ "';";
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sql);
                        if (resultSet.next() ) {

                            alertReg.setText("That email is already registered.");


                        }else{
                            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(emailInput);
                            if(!matcher.matches())
                            {
                                alertReg.setText("Not a valid email.");

                            }else{

                                String regexPass = "^(?=.*[0-9])"
                                        + "(?=.*[a-z])(?=.*[A-Z])"
                                        + "(?=.*[@#$%^&+=*])"
                                        + "(?=\\S+$).{8,20}$";

                                // Password regex
                                Pattern p = Pattern.compile(regexPass);
                                Matcher m = p.matcher(passwordInput);

                                if(!m.matches()){
                                    alertReg.setText("Password should contain 8-20 characters" +
                                    ", at least one upper case, lower case, digit and special character");
                                }
                                else{
                                    Pattern patternMobile = Pattern.compile("(0/91)?[7-9][0-9]{9}");
                                    Matcher matcherMobile = patternMobile.matcher(contactInput);

                                    if(!matcherMobile.matches()){
                                        alertReg.setText("Invalid mobile number.");
                                    }
                                    else{
                                        String sql1 = "INSERT INTO users VALUES('"+ nameInput +"','"+ emailInput +  "','" +
                                                contactInput +  "','"+
                                                genderInput + "','" + passwordInput  +"');";

                                        Statement statement1 = null;
                                        try {
                                            statement1 = connection.createStatement();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                        try {
                                            statement1.executeUpdate(sql1);
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                        System.out.println("Data entered");
                                        AlertBox.display("User registered successfully!");
                                        try {
                                            Thread.sleep(5000);
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        }
                                        window.setScene(scene3);
                                    }

                                }

                            }

                        }
                 } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

            }

        });

        VBox layout1 = new VBox(20);
        layout1.setPadding(new Insets(20,10,10,20));
        layout1.getChildren().addAll(goBackReg, labelReg, label1, name, label2,email, label3,contact,
                                    label4, r,label5, password, button2, alertReg);



        layout1.setBackground(background);

        scene2 = new Scene(layout1, 700,650);


        //login layout
        Label label6 = new Label("Email");
        Label label7 = new Label("Password");

        TextField emailLogin = new TextField();
        emailLogin.setPrefWidth(300);
        emailLogin.setMaxWidth(300);
        PasswordField passwordLogin = new PasswordField();
        passwordLogin.setPrefWidth(300);
        passwordLogin.setMaxWidth(300);


        Button goBackLogin = new Button("Back");

        Label loginAlert = new Label();

        goBackLogin.setOnAction(e -> window.setScene(scene1));

        VBox layout2 = new VBox(20);
        layout2.setPadding(new Insets(30,10,10,30));


        layout2.setBackground(background);
        //login
        Button button3 =new Button("Login");
        button3.setOnAction(e -> {
            String emailInputLogin = emailLogin.getText();
            String passwordInputLogin = passwordLogin.getText();
            String sql = "SELECT * FROM users WHERE email='"+ emailInputLogin+ "';";
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                if (!resultSet.next() ) {
                    System.out.println("no data");
                    System.out.println("Not registered.");
                    AlertBox.display("User not registered.");

                    window.setScene(scene2);

                }
                else {

                    System.out.println("Record found");
                    String passwordUser = resultSet.getString(5);

                    if (passwordInputLogin.equals(passwordUser)) {
                        System.out.println("yep.");
                        window.setScene(scene4);
                    } else {
                        loginAlert.setText("Password incorrect.");

                    }

                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        layout2.getChildren().addAll(goBackLogin,labelLogin, label6, emailLogin, label7, passwordLogin, button3, loginAlert);
        scene3 = new Scene(layout2, 600,400);





        button.setOnAction(e -> {
            name.setText("");
            email.setText("");
            contact.setText("");
            password.setText("");
            window.setScene(scene2);
        });
        button1.setOnAction(e -> {
            emailLogin.setText("");
            passwordLogin.setText("");
            window.setScene(scene3);

        });

        //Scene 4 after login
        InputStream stream = new FileInputStream("C:\\Users\\MOHAN\\IdeaProjects\\RailwayManagement\\src\\indiarailway.jpg");
        Label label8 = new Label("Welcome to Indian Railways.\nYou are currently logged in.");
        label8.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        label8.setTextFill(Color.web("#003153", 1));
        Image image = new Image(stream);
        //Creating the image view
        ImageView imageView = new ImageView();
        //Setting image to the image view
        imageView.setImage(image);
        //Setting the image view parameters
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(680);
        imageView.setPreserveRatio(true);
        VBox lay1 = new VBox(20);
        lay1.getChildren().addAll(label8, imageView);
        lay1.setBackground(background);
        lay1.setPadding(new Insets(10,10,10,10));
        //Group im = new Group(imageView);






        Menu trainInfo = new Menu("Train Information");

        MenuItem checkTrains = new MenuItem("Check trains");
        checkTrains.setOnAction(e -> {
            TrainInfo.display();
        });
        MenuItem checkAvailability = new MenuItem("Check availability");
        trainInfo.getItems().addAll(checkTrains, checkAvailability);
        checkAvailability.setOnAction(e -> {
            SeatsInfo.display();
        });



        Menu bookingInfo = new Menu("Booking Information");

        MenuItem bookTickets = new MenuItem("Book tickets");
        bookTickets.setOnAction(e -> {
            SeatsInfo.display();
        });
        MenuItem cancelTickets = new MenuItem("Cancel tickets");
        cancelTickets.setOnAction(e -> {
            CancelTickets.display();
        });
        MenuItem printTickets = new MenuItem("Generate tickets");
        printTickets.setOnAction(e -> {
            TicketGenerate.display();
        });
        bookingInfo.getItems().addAll(bookTickets, cancelTickets, printTickets);





        Menu quit = new Menu("Quit");
        MenuItem logOut = new MenuItem("Log Out");
        logOut.setOnAction(e -> {
            window.setScene(scene1);
        });
        MenuItem exitPage = new MenuItem("Exit");
        exitPage.setOnAction(e -> {
            window.close();
        });
        quit.getItems().addAll(logOut, exitPage);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(trainInfo, bookingInfo, quit);

        BorderPane layout3 = new BorderPane();
        layout3.setTop(menuBar);
        layout3.setCenter(lay1);


        scene4 = new Scene(layout3, 700,700);



        // set initial scene
        window.setScene(scene1);


        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
