package view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

import Entites.*;
import Model.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;

public class Controller {

    Model database;
    ArrayList<Book> allBooks;
    ArrayList<Book> cartBooks;
    User currentUser;
    HashMap<String, String> searchElements;

    ArrayList<User> users;
    ArrayList<Order> orders;
    ArrayList<Author> authors;
    ArrayList<Publisher> publishers;
    ArrayList<Button> userButtons;
    TextField currentCount[];
    Button addToCartButton[];
    int HORIZONTAL_SHIFT = 1130/3;
    int LIMIT = 50;
    boolean addedToCart = false;
    boolean isNowCart = false;
    int VERTICAL_SHIFT = 210;
    int MARGIN = 15;
    int BOOKS_PER_ROW = 3;
    int pageNum = 0;
    String MANAGER_CODE = "1234";

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField addressTextbox;

    @FXML
    private ScrollPane booksPane;

    @FXML
    private PasswordField confirmPasswordTextbox;

    @FXML
    private TextField emailTextbox;

    @FXML
    private TextField firstNameTextbox;

    @FXML
    private Button buttonIncreaseBPP;

    @FXML
    private Button buttondecreaseBPP;

    @FXML
    private ToolBar toolBar;

    @FXML
    private TextField lastNameTextbox;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView notValidIcon;

    @FXML
    private PasswordField passwordTextbox;

    @FXML
    private TextField rEmailTextbox;

    @FXML
    private PasswordField rPasswordTextbox;

    @FXML
    private Label pageNumLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button getAuthorsButtonToolbar;

    @FXML
    private Button addAuthorButtonToolbar;

    @FXML
    Label errorLoginLabel;

    @FXML
    private Button addBookButtonToolbar;

    @FXML
    private Button viewOrdersButtonToolbar;

    @FXML
    private Button viewReportButtonToolbar;

    @FXML
    private Button clickProfileButtonToolbar;

    @FXML
    private Button viewUsersButtonToolbar;

    @FXML
    private Button checkoutButton;

    @FXML
    private Label slogan;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private Button findCertainBook;

    @FXML
    private Label labelInfo;

    @FXML
    private TextField searchAuthorTextbox;

    @FXML
    private CheckBox isManagerCheckBox;

    @FXML
    private Button publishersButtonToolbar;

    @FXML
    private Button addPublisherButtonToolbar;

    @FXML
    private PasswordField secretCodeTextField;

    @FXML
    private ImageView validIconManager;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox searchCategoryTextbox;

    @FXML
    private TextField searchTitleTextbox;

    @FXML
    private TextField telefonTextbox;

    @FXML
    private ImageView validIcon;
    private int pageNumUsers = 0;

    @FXML
    void viewReport(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("view Reports");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);

        Button report1 = new Button("Total Sales");
        report1.setFocusTraversable(true);
        report1.getStyleClass().add("mybutton");
        report1.setTextFill(Color.WHITE);

        Button report2 = new Button("Top 5 Customers");
        report2.setFocusTraversable(true);
        report2.getStyleClass().add("mybutton");
        report2.setTextFill(Color.WHITE);

        Button report3 = new Button("Top 10 Selling Books");
        report3.setFocusTraversable(true);
        report3.getStyleClass().add("mybutton");
        report3.setTextFill(Color.WHITE);
        report1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JasperReports reports = new JasperReports();
                reports.getTotalSales();
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        report2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JasperReports reports = new JasperReports();
                reports.getTop5Customers();
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        report3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JasperReports reports = new JasperReports();
                reports.getTop10SellingBooks();
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        vbx.getChildren().addAll(report1, report2, report3);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }

    @FXML
    void loginClicked(MouseEvent event) {
        isNowCart = false;
        if(emailTextbox.isVisible()) {
            try {
                establishNewConncetion();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String email = emailTextbox.getText();
            String password = passwordTextbox.getText();
            if(!email.equals("admin") & !email.equals("")) {
                currentUser = database.logIn(email, password);

            }
            if(currentUser == null && !(email.equals("admin") && password.equals("admin")) &&
                    !(email.equals("") && password.equals(""))) {
                showErrorLogIn();
            } else {
                //database.emptyCart(currentUser.getUserId());
                showMarket();
                prepareBooks(allBooks);
            }
        } else {
            showLogin();
        }
    }
    @FXML
    void registerClicked(MouseEvent event) {
        if(rEmailTextbox.isVisible()) {
            try {
                establishNewConncetion();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String email = rEmailTextbox.getText();
            String password = rPasswordTextbox.getText();
            String telefon = telefonTextbox.getText();
            String firstName = firstNameTextbox.getText();
            String lastName = lastNameTextbox.getText();
            String address = addressTextbox.getText();

            User newUser = new User();
            newUser.setEmailAddress(email);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setPhoneNumber(telefon);
            newUser.setShippingAddress(address);
            if(validIconManager.isVisible()) {
                newUser.setPromoted(true);
            } else {
                newUser.setPromoted(false);
            }
            if(database.register(newUser)) {
                currentUser = database.logIn(newUser.getEmailAddress() , newUser.getPassword());
                showMarket();
                prepareBooks(allBooks);
            } else {
                showErrorLogIn();
            }
        } else {
            showRegister();
        }
    }
    @FXML
    void checkPasswordMatch(Event event) {
        String password = rPasswordTextbox.getText();
        String passwordMatch = confirmPasswordTextbox.getText();
        if(password.equals(passwordMatch) && !password.equals("")) {
            validIcon.setVisible(true);
            notValidIcon.setVisible(false);
        } else if(!password.equals("")){
            validIcon.setVisible(false);
            notValidIcon.setVisible(true);
        }
    }
    @FXML
    void validateManagerCode(KeyEvent event) {
        String password = secretCodeTextField.getText();
        if(password.equals(MANAGER_CODE)) {
            validIconManager.setVisible(true);
        } else {
            validIconManager.setVisible(false);
        }
    }
    @FXML
    void increaseBPP(MouseEvent event) {
        if(BOOKS_PER_ROW != 8) {
            BOOKS_PER_ROW++;
            HORIZONTAL_SHIFT = (1130 / BOOKS_PER_ROW);
            if(isNowCart) {
                prepareBooks(cartBooks);
            } else {
                prepareBooks(allBooks);
            }
        }
    }
    @FXML
    void findOneBook(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Search for a book");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);
        TextField id = new TextField();
        TextField title = new TextField();

        title.setPromptText("Enter the title of the book");
        title.setFocusTraversable(false);
        id.setPromptText("Enter id of the book");
        id.setFocusTraversable(false);

        Button search1 = new Button("Search by id");
        search1.setFocusTraversable(true);
        search1.getStyleClass().add("mybutton");
        search1.setTextFill(Color.WHITE);
        search1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showCertainBook(database.getBookById(Integer.valueOf(id.getText())));
            }
        });

        Button search2 = new Button("Search by Title");
        search2.setFocusTraversable(true);
        search2.getStyleClass().add("mybutton");
        search2.setTextFill(Color.WHITE);
        search2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String titlee = title.getText();
                showCertainBook(database.getBookByTitle(titlee));
            }
        });

        vbx.getChildren().addAll(id, search1, title, search2);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }
    @FXML
    void getNextPage(ActionEvent event) {
        showMarket();
        prepareBooks(database.getBooksByPage(++pageNum, LIMIT, new HashMap<>()));
        pageNumLabel.setText(String.valueOf(pageNum));
    }
    @FXML
    void getPrevPage(ActionEvent event) {
        showMarket();
        if (pageNum != 0) {
            prepareBooks(database.getBooksByPage(--pageNum, LIMIT, new HashMap<>()));
        }
        pageNumLabel.setText(String.valueOf(pageNum));
    }
    @FXML
    void decreaseBPP(MouseEvent event) {
        if(BOOKS_PER_ROW != 1) {
            BOOKS_PER_ROW--;
            HORIZONTAL_SHIFT = (1130 / BOOKS_PER_ROW);
            if(isNowCart) {
                prepareBooks(cartBooks);
            } else {
                prepareBooks(allBooks);
            }
        }
    }
    @FXML
    void clickCart(ActionEvent event) {
        isNowCart = true;
        prepareBooks(cartBooks);
        searchAuthorTextbox.setVisible(false);
        searchButton.setVisible(false);
        searchCategoryTextbox.setVisible(false);
        searchTitleTextbox.setVisible(false);

        checkoutButton.setVisible(true);
    }
    @FXML
    void proceedToCheckout(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Master Card");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);

        HBox mcard = new HBox();
        TextField prt1 = new TextField();
        prt1.setPromptText("XXXX");
        prt1.setFocusTraversable(false);
        Label sep1 = new Label("-");
        Label sep2 = new Label("-");
        Label sep3 = new Label("-");
        sep1.setFont(Font.font("Cambria", 30));
        sep1.setTextFill(Color.WHITE);
        sep2.setFont(Font.font("Cambria", 30));
        sep3.setFont(Font.font("Cambria", 30));
        sep2.setTextFill(Color.WHITE);
        sep3.setTextFill(Color.WHITE);
        TextField prt2 = new TextField();
        prt2.setPromptText("XXXX");
        prt2.setFocusTraversable(false);
        TextField prt3 = new TextField();
        prt3.setPromptText("XXXX");
        prt3.setFocusTraversable(false);
        TextField prt4 = new TextField();
        prt4.setPromptText("XXXX");
        prt4.setFocusTraversable(false);
        prt1.setMaxWidth(70);
        prt2.setMaxWidth(70);
        prt3.setMaxWidth(70);
        prt4.setMaxWidth(70);
        mcard.setAlignment(Pos.CENTER);
        mcard.setMinWidth(442);
        vbx.setSpacing(1);

        mcard.getChildren().addAll(prt1,sep1,prt2,sep2, prt3,sep3,prt4);
        Button done = new Button("Purchase");
        done.setFocusTraversable(true);
        done.getStyleClass().add("mybutton");
        done.setTextFill(Color.WHITE);
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String masterCard = prt1.getText() + "-" +
                                    prt2.getText() + "-" +
                                    prt3.getText() + "-" +
                                    prt4.getText();
                if(database.checkCreditCard(currentUser.getUserId(), masterCard)) {
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    database.checkout(currentUser.getUserId());
                    cartBooks = new ArrayList<>();
                    prepareBooks(cartBooks);
                } else {
                    done.setText("Wrong MasterCard - Try Again");
                }

            }
        });
        vbx.getChildren().addAll(mcard, done);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
        prepareBooks(cartBooks);
    }
    @FXML
    void clickHome(ActionEvent event) throws InterruptedException {
        isNowCart = false;
        showMarket();
        pageNum = 0;
        pageNumLabel.setText(String.valueOf(pageNum));
        allBooks = database.getBooksByPage(pageNum, LIMIT, new HashMap<>());
        prepareBooks(allBooks);
    }
    @FXML
    void clickLogout(ActionEvent event) {
        database.emptyCart(currentUser.getUserId());
        currentUser = null;
        cartBooks = null;
        showLogin();
    }
    @FXML
    void addPublisher(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Add publisher");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);

        TextField pubName = new TextField();
        pubName.setPromptText("Enter the name of the publisher");
        pubName.setFocusTraversable(false);

        TextField pubAddress = new TextField();
        pubAddress.setPromptText("Address of the publisher");
        pubAddress.setFocusTraversable(false);

        TextField phone = new TextField();
        phone.setPromptText("Phone Number");
        phone.setFocusTraversable(false);

        Button done = new Button("Add Publisher");
        done.setFocusTraversable(true);
        done.getStyleClass().add("mybutton");
        done.setTextFill(Color.WHITE);
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Publisher pub = new Publisher();
                pub.setAddress(pubAddress.getText());
                pub.setName(pubName.getText());
                pub.setPhone(phone.getText());
                database.addPublisher(pub);
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        vbx.getChildren().addAll(pubName, pubAddress, phone, done);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }
    @FXML
    void viewPublishers(ActionEvent event) {
        ScrollPane c = new ScrollPane();
        AnchorPane container = new AnchorPane();
        Scene secondScene = new Scene(c, 1200, 800);
        c.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        System.out.println();
        secondScene.getStylesheets().add("style.css");
        container.setMinWidth(1200);
        container.setMinHeight(800);
        VBox secondaryLayout = new VBox();
        secondaryLayout.setMinWidth(1200);
        secondaryLayout.setMinHeight(800);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setSpacing(3);
        try {
            Image image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
            ImageView wallpaper = new ImageView(image);
            wallpaper.setFitHeight(800);
            wallpaper.setFitWidth(1200);
            wallpaper.setX(0);
            wallpaper.setY(0);
            //       container.getChildren().add(wallpaper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pageNumUsers = 0;
        publishers = new ArrayList<>();
        publishers = database.getPublishersByPage(pageNumUsers, LIMIT);
        userButtons = new ArrayList<>();

        preparePublishers(secondaryLayout);
        c.setContent(container);
        container.getChildren().add(secondaryLayout);
        Stage newWindow = new Stage();
        newWindow.setTitle("Publishers");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(382);
        newWindow.setY(109);

        newWindow.show();
    }

    private void preparePublishers(VBox secondaryLayout) {
        secondaryLayout.getChildren().clear();
        Button search = new Button("Search publishers by ID");
        search.getStyleClass().add("mybutton");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        secondaryLayout.getChildren().add(search);
        for (int i = 0; i < publishers.size(); i++) {
            Button userbutton = new Button();
            userbutton.setMinHeight(60);
            userbutton.setMinWidth(1200);
            userButtons.add(userbutton);
            String userOnButton = publishers.get(i).getName();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + publishers.get(i).getPublisherId();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + publishers.get(i).getPhone();
            userOnButton = userOnButton + "\t\t";
            //todo uncomment to show pub address
            //userOnButton = userOnButton + publishers.get(i).getAddress();
            userbutton.setTextFill(Color.WHITE);
            userbutton.getStyleClass().add("mybutton");
            userbutton.setTextAlignment(TextAlignment.LEFT);
            userbutton.setId(String.valueOf(i));
            userbutton.setFont(Font.font("Cambria", 23));
            userbutton.setText(userOnButton);
            userbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });
            secondaryLayout.getChildren().add(userbutton);

        }
        Button bnext = new Button(">>");
        bnext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                publishers = new ArrayList<>();
                publishers = database.getPublishersByPage(++pageNumUsers, LIMIT);
                preparePublishers(secondaryLayout);
            }
        });
        Button bprev = new Button("<<");
        bprev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pageNumUsers != 0) {
                    publishers = new ArrayList<>();
                    publishers = database.getPublishersByPage(--pageNumUsers, LIMIT);
                    preparePublishers(secondaryLayout);
                }
            }
        });
        secondaryLayout.getChildren().addAll(bnext, bprev);
    }
    private void prepareAuthors(VBox secondaryLayout) {
        secondaryLayout.getChildren().clear();
        Button search = new Button("Search authors by ID");
        search.getStyleClass().add("mybutton");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        secondaryLayout.getChildren().add(search);
        for (int i = 0; i < authors.size(); i++) {
            Button userbutton = new Button();
            userbutton.setMinHeight(60);
            userbutton.setMinWidth(1200);
            userButtons.add(userbutton);
            String userOnButton = authors.get(i).getName();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + authors.get(i).getAuthorId();
            userbutton.setTextFill(Color.WHITE);
            userbutton.getStyleClass().add("mybutton");
            userbutton.setTextAlignment(TextAlignment.LEFT);
            userbutton.setId(String.valueOf(i));
            userbutton.setFont(Font.font("Cambria", 23));
            userbutton.setText(userOnButton);
            userbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });
            secondaryLayout.getChildren().add(userbutton);

        }
        Button bnext = new Button(">>");
        bnext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                authors = new ArrayList<>();
                authors = database.getAuthorsByPage(++pageNumUsers, LIMIT);
                prepareAuthors(secondaryLayout);
            }
        });
        Button bprev = new Button("<<");
        bprev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pageNumUsers != 0) {
                    authors = new ArrayList<>();
                    authors = database.getAuthorsByPage(--pageNumUsers, LIMIT);
                    prepareAuthors(secondaryLayout);
                }
            }
        });
        secondaryLayout.getChildren().addAll(bnext, bprev);
    }

    @FXML
    void addBook(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Book");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);
        TextField title = new TextField();
        TextField bookid = new TextField();
        TextField authors = new TextField();
        TextField publisherid = new TextField();
        TextField price = new TextField();
        TextField threshold = new TextField();
        TextField count = new TextField();
        TextField dateOfPublishing = new TextField();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Art",
                        "Science",
                        "Geography",
                        "History",
                        "Religion"
                );
        ComboBox category = new ComboBox(options);
        category.setPromptText("Select Category");

        title.setPromptText("Enter the title of the book");
        title.setFocusTraversable(false);
        publisherid.setPromptText("Enter the publisher ID");
        publisherid.setFocusTraversable(false);
        bookid.setPromptText("Enter the ISPN of the book");
        bookid.setFocusTraversable(false);
        authors.setPromptText("Enter author(s) of the book separated with spaces");
        authors.setFocusTraversable(false);
        threshold.setPromptText("Enter Threshold of the book");
        threshold.setFocusTraversable(false);
        price.setPromptText("How much is that book?");
        price.setFocusTraversable(false);
        count.setPromptText("How many books do we have?");
        count.setFocusTraversable(false);
        dateOfPublishing.setPromptText("Date of publishing in (YYYY-MM-DD)");
        dateOfPublishing.setFocusTraversable(false);

        Button done = new Button("Done");
        done.setFocusTraversable(true);
        done.getStyleClass().add("mybutton");
        done.setTextFill(Color.WHITE);
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Book book = new Book();
                book.setBookId(Integer.valueOf(bookid.getText()));
                book.setPublisherId(Integer.valueOf(publisherid.getText()));
                book.setTitle(title.getText());
                book.setQuantity(Integer.valueOf(count.getText()));
                book.setCategory((String) category.getValue());
                book.setSellingPrice(Float.valueOf(price.getText()));
                book.setPubYear(dateOfPublishing.getText());
                book.setThreshold(Integer.valueOf(threshold.getText()));
                String[] authorsIdsArr = new String[50];
                ArrayList<Integer> authorsIds = new ArrayList<>();
                if(!authors.getText().equals("")) {
                    authorsIdsArr = authors.getText().split(" ");
                }
                for(int i = 0; i < authorsIdsArr.length; i++) {
                    authorsIds.add(Integer.valueOf(authorsIdsArr[i]));
                }
                book.setAuthorsIds(authorsIds);
                database.addBook(book);
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        vbx.getChildren().addAll(title, bookid, authors, publisherid, price, count, threshold, dateOfPublishing, category, done);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }
    @FXML
    void clickProfile(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 1200, 800);
        System.out.println();
        secondScene.getStylesheets().add("style.css");
        container.setMinWidth(1200);
        container.setMinHeight(800);
        VBox secondaryLayout = new VBox();
        secondaryLayout.setMinWidth(1200);
        secondaryLayout.setMinHeight(800);
        secondaryLayout.setAlignment(Pos.CENTER);
        try {
            Image image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
            ImageView wallpaper = new ImageView(image);
            wallpaper.setFitHeight(800);
            wallpaper.setFitWidth(1200);
            wallpaper.setX(0);
            wallpaper.setY(0);
            container.getChildren().add(wallpaper);

            Image image2 = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/nophoto.gif"));
            ImageView profilepic = new ImageView(image2);
            profilepic.setFitHeight(220);
            profilepic.setFitWidth(200);
            secondaryLayout.getChildren().add(profilepic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Label name = new Label(currentUser.getFirstName() + " " + currentUser.getLastName());
        Label verification;
        if(currentUser.isPromoted()) {
            verification = new Label("Verified");
        } else {
            verification = new Label("Not Verified");
        }
        Label email = new Label(currentUser.getEmailAddress());
        Label phone = new Label(currentUser.getPhoneNumber());
        Label address = new Label(currentUser.getShippingAddress());

        Button button = new Button("Edit my profile info");
        Button buttonBack = new Button("Back");
        buttonBack.setFocusTraversable(true);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent eee) {
                Pane container = new Pane();
                Scene secondScene = new Scene(container, 442.0, 296);
                secondScene.getStylesheets().add("style.css");
                Stage newWindow = new Stage();
                newWindow.setTitle("edit profile");
                newWindow.setScene(secondScene);

                Image image = null;
                try {
                    image = new Image(new FileInputStream(
                            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                                    + "../../../src/view/Resources/images/libraryy.jpg"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                ImageView wallpaper = new ImageView(image);
                wallpaper.setFitHeight(297.5);
                wallpaper.setFitWidth(442.0);
                wallpaper.setX(0);
                wallpaper.setY(0);
                container.getChildren().add(wallpaper);
                VBox vbx = new VBox();
                vbx.setMinWidth(442);
                vbx.setMinHeight(297.5);
                vbx.setAlignment(Pos.CENTER);
                TextField email = new TextField(currentUser.getEmailAddress());
                TextField address = new TextField(currentUser.getEmailAddress());
                TextField phone = new TextField(currentUser.getPhoneNumber());
                TextField name = new TextField(currentUser.getFirstName()+ " " + currentUser.getLastName());
                Button mastercard = new Button("Add MasterCard");
                mastercard.getStyleClass().add("mybutton");
                mastercard.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                        public void handle(ActionEvent e) {
                        Pane container = new Pane();
                        Scene secondScene = new Scene(container, 442.0, 296);
                        secondScene.getStylesheets().add("style.css");
                        Stage newWindow = new Stage();
                        newWindow.setTitle("Master Card");
                        newWindow.setScene(secondScene);

                        Image image = null;
                        try {
                            image = new Image(new FileInputStream(
                                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                                            + "../../../src/view/Resources/images/libraryy.jpg"));
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        ImageView wallpaper = new ImageView(image);
                        wallpaper.setFitHeight(297.5);
                        wallpaper.setFitWidth(442.0);
                        wallpaper.setX(0);
                        wallpaper.setY(0);
                        container.getChildren().add(wallpaper);
                        VBox vbx = new VBox();
                        vbx.setMinWidth(442);
                        vbx.setMinHeight(297.5);
                        vbx.setAlignment(Pos.CENTER);

                        HBox mcard = new HBox();
                        TextField prt1 = new TextField();
                        prt1.setPromptText("XXXX");
                        prt1.setFocusTraversable(false);
                        Label sep1 = new Label("-");
                        Label sep2 = new Label("-");
                        Label sep3 = new Label("-");
                        sep1.setFont(Font.font("Cambria", 30));
                        sep1.setTextFill(Color.WHITE);
                        sep2.setFont(Font.font("Cambria", 30));
                        sep3.setFont(Font.font("Cambria", 30));
                        sep2.setTextFill(Color.WHITE);
                        sep3.setTextFill(Color.WHITE);
                        TextField prt2 = new TextField();
                        prt2.setPromptText("XXXX");
                        prt2.setFocusTraversable(false);
                        TextField prt3 = new TextField();
                        prt3.setPromptText("XXXX");
                        prt3.setFocusTraversable(false);
                        TextField prt4 = new TextField();
                        prt4.setPromptText("XXXX");
                        prt4.setFocusTraversable(false);
                        prt1.setMaxWidth(70);
                        prt2.setMaxWidth(70);
                        prt3.setMaxWidth(70);
                        prt4.setMaxWidth(70);
                        mcard.setAlignment(Pos.CENTER);
                        mcard.setMinWidth(442);
                        vbx.setSpacing(1);

                        mcard.getChildren().addAll(prt1,sep1,prt2,sep2, prt3,sep3,prt4);
                        Button done = new Button("Add Master card");
                        done.setTextFill(Color.WHITE);
                        done.setFocusTraversable(true);
                        done.getStyleClass().add("mybutton");
                        done.setTextFill(Color.WHITE);
                        done.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                String masterCard = prt1.getText() + "-" +
                                        prt2.getText() + "-" +
                                        prt3.getText() + "-" +
                                        prt4.getText();

                                    if(database.addCreditCard(currentUser.getUserId(), masterCard)) {
                                        ((Node) event.getSource()).getScene().getWindow().hide();
                                    }

                            }
                        });
                        vbx.getChildren().addAll(mcard, done);
                        container.getChildren().add(vbx);
                        newWindow.setX(582);
                        newWindow.setY(259);
                        newWindow.show();
                        }
                    });
                Button chngpwd = new Button("Change Password");
                chngpwd.setFocusTraversable(true);
                chngpwd.getStyleClass().add("mybutton");
                chngpwd.setTextFill(Color.WHITE);
                chngpwd.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Pane container = new Pane();
                        Scene secondScene = new Scene(container, 442.0, 296);
                        secondScene.getStylesheets().add("style.css");
                        Stage newWindow = new Stage();
                        newWindow.setTitle("edit password");
                        newWindow.setScene(secondScene);

                        Image image = null;
                        try {
                            image = new Image(new FileInputStream(
                                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                                            + "../../../src/view/Resources/images/libraryy.jpg"));
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        ImageView wallpaper = new ImageView(image);
                        wallpaper.setFitHeight(297.5);
                        wallpaper.setFitWidth(442.0);
                        wallpaper.setX(0);
                        wallpaper.setY(0);
                        container.getChildren().add(wallpaper);
                        VBox vbx = new VBox();
                        vbx.setMinWidth(442);
                        vbx.setMinHeight(297.5);
                        vbx.setAlignment(Pos.CENTER);
                        TextField oldpassword = new TextField();
                        TextField newpassword = new TextField();
                        TextField confirmpasswrd = new TextField();
                        oldpassword.setFocusTraversable(false);
                        newpassword.setFocusTraversable(false);
                        confirmpasswrd.setFocusTraversable(false);
                        oldpassword.setPromptText("Enter your old password");
                        newpassword.setPromptText("Enter your new password");
                        confirmpasswrd.setPromptText("Confirm your new password");
                        Button chngpwd = new Button("Done");
                        chngpwd.setFocusTraversable(true);
                        chngpwd.getStyleClass().add("mybutton");
                        chngpwd.setTextFill(Color.WHITE);
                        chngpwd.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                User temp = new User();
                                temp.setFirstName(currentUser.getFirstName());
                                temp.setPassword(currentUser.getPassword());
                                temp.setPromoted(currentUser.isPromoted());
                                temp.setPhoneNumber(currentUser.getPhoneNumber());
                                temp.setShippingAddress(currentUser.getShippingAddress());
                                temp.setEmailAddress(currentUser.getEmailAddress());
                                temp.setLastName(currentUser.getLastName());
                                temp.setUserId(currentUser.getUserId());
                                if((newpassword.getText() != confirmpasswrd.getText()) || !oldpassword.equals(currentUser.getPassword())) {
                                    chngpwd.setText("Password Doesn't match");
                                } else {
                                    currentUser.setPassword(newpassword.getText());
                                    if(!database.updateUser(currentUser)) {
                                        currentUser.setPassword(temp.getPassword());
                                        chngpwd.setText("Error updating (DB error)");
                                    }
                                }

                                ((Node)e.getSource()).getScene().getWindow().hide();
                            }
                        });
                        vbx.getChildren().addAll(oldpassword, newpassword, confirmpasswrd, chngpwd);
                        container.getChildren().add(vbx);
                        newWindow.setX(582);
                        newWindow.setY(259);
                        newWindow.show();
                    }
                });
                Button done = new Button("Done");
                done.getStyleClass().add("mybutton");
                done.setTextFill(Color.WHITE);
                done.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ((Node) eee.getSource()).getScene().getWindow().hide();
                        User temp = new User();
                        temp.setFirstName(currentUser.getFirstName());
                        temp.setPassword(currentUser.getPassword());
                        temp.setPromoted(currentUser.isPromoted());
                        temp.setPhoneNumber(currentUser.getPhoneNumber());
                        temp.setShippingAddress(currentUser.getShippingAddress());
                        temp.setEmailAddress(currentUser.getEmailAddress());
                        temp.setLastName(currentUser.getLastName());
                        temp.setUserId(currentUser.getUserId());

                        List<String> names;
                        names = Arrays.asList(name.getText().split(" "));
                        currentUser.setFirstName(names.get(0));
                        currentUser.setLastName(names.get(names.size()-1));
                        currentUser.setEmailAddress(email.getText());
                        currentUser.setPhoneNumber(phone.getText());
                        currentUser.setShippingAddress(address.getText());
                        if(!database.updateUser(currentUser)) {
                            currentUser = temp;
                            //todo show error
                        } else {
                            ((Node) event.getSource()).getScene().getWindow().hide();
                            clickProfileButtonToolbar.fire();
                        }
                    }
                });
                vbx.getChildren().addAll(name, email, phone, address, mastercard, chngpwd, done);
                container.getChildren().add(vbx);
                newWindow.setX(582);
                newWindow.setY(259);
                newWindow.show();
            }
        });
        buttonBack.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        }));
        buttonBack.getStyleClass().add("mybutton");
        buttonBack.setTextFill(Color.WHITE);
        button.getStyleClass().add("mybutton");
        button.setTextFill(Color.WHITE);

        Label empty = new Label("");
        Label empty1 = new Label("");
        Label empty2 = new Label("");

        name.setFont(new Font("Cambria", 45));
        name.setTextFill(Color.DARKRED);
        verification.setFont(Font.font("Cambria",25));
        verification.setTextFill(Color.GREEN);
        email.setFont(new Font("Cambria", 27));
        email.setTextFill(Color.BLACK);
        phone.setFont(new Font("Cambria", 27));
        phone.setTextFill(Color.BLACK);
        address.setFont(new Font("Cambria", 27));
        address.setTextFill(Color.BLACK);

        secondaryLayout.getChildren().addAll(empty, empty1, button, empty2, name, verification, email, phone, address, buttonBack);
        container.getChildren().add(secondaryLayout);
        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Profile");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(382);
        newWindow.setY(109);

        newWindow.show();
    }
    @FXML
    void clickViewOrders(ActionEvent event) {

        ScrollPane c = new ScrollPane();
        AnchorPane container = new AnchorPane();
        Scene secondScene = new Scene(c, 1200, 800);
        c.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        System.out.println();
        secondScene.getStylesheets().add("style.css");
        container.setMinWidth(1200);
        container.setMinHeight(800);
        VBox secondaryLayout = new VBox();
        secondaryLayout.setMinWidth(1200);
        secondaryLayout.setMinHeight(800);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setSpacing(3);
        try {
            Image image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
            ImageView wallpaper = new ImageView(image);
            wallpaper.setFitHeight(800);
            wallpaper.setFitWidth(1200);
            wallpaper.setX(0);
            wallpaper.setY(0);
            //       container.getChildren().add(wallpaper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        orders = new ArrayList<>();
        pageNumUsers = 0;
        orders = database.getAllOrders();
        userButtons = new ArrayList<>();

        prepareOrders(secondaryLayout);
        c.setContent(container);
        container.getChildren().add(secondaryLayout);
        Stage newWindow = new Stage();
        newWindow.setTitle("Orders");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(382);
        newWindow.setY(109);

        newWindow.show();
    }

    private void prepareOrders(VBox secondaryLayout) {
        Button search = new Button("Add new Order");
        search.getStyleClass().add("mybutton");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane container = new Pane();
                Scene secondScene = new Scene(container, 442.0, 296);
                secondScene.getStylesheets().add("style.css");
                Stage newWindow = new Stage();
                newWindow.setTitle("Add Order");
                newWindow.setScene(secondScene);

                Image image = null;
                try {
                    image = new Image(new FileInputStream(
                            this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                                    + "../../../src/view/Resources/images/libraryy.jpg"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                ImageView wallpaper = new ImageView(image);
                wallpaper.setFitHeight(297.5);
                wallpaper.setFitWidth(442.0);
                wallpaper.setX(0);
                wallpaper.setY(0);
                container.getChildren().add(wallpaper);
                VBox vbx = new VBox();
                vbx.setMinWidth(442);
                vbx.setMinHeight(297.5);
                vbx.setAlignment(Pos.CENTER);

                TextField bookid = new TextField();
                bookid.setPromptText("Enter book id");
                bookid.setFocusTraversable(false);

                TextField quantity = new TextField();
                quantity.setPromptText("Enter Quantity wanted");
                quantity.setFocusTraversable(false);

                Button done = new Button("place order");
                done.setFocusTraversable(true);
                done.getStyleClass().add("mybutton");
                done.setTextFill(Color.WHITE);
                done.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        database.placeOrder(Integer.valueOf(bookid.getText()), Integer.valueOf(quantity.getText()));
                        ((Node)event.getSource()).getScene().getWindow().hide();
                        orders = database.getAllOrders();
                        prepareOrders(secondaryLayout);
                    }
                });
                vbx.getChildren().addAll(bookid, quantity, done);
                container.getChildren().add(vbx);
                newWindow.setX(582);
                newWindow.setY(259);
                newWindow.show();
            }
        });
        secondaryLayout.getChildren().clear();
        secondaryLayout.getChildren().add(search);
        if(orders == null) {
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            Button userbutton = new Button();
            userbutton.setMinHeight(60);
            userbutton.setMinWidth(1200);
            userButtons.add(userbutton);
            String userOnButton = "Book Id: " + orders.get(i).getBookId() + "";
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + "Quantity: " + orders.get(i).getQuantity();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + "Date: " + orders.get(i).getDate();
            userOnButton = userOnButton + "\t\t";
            userbutton.setTextFill(Color.WHITE);
            userbutton.getStyleClass().add("mybutton");
            userbutton.setTextAlignment(TextAlignment.LEFT);
            userbutton.setId(String.valueOf(i));
            userbutton.setFont(Font.font("Cambria", 23));
            userbutton.setText(userOnButton);
            userbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = Integer.valueOf(((Node)event.getSource()).getId());
                    ((Node)event.getSource()).getStyleClass().add("mybuttonpromoted");
                    database.confirmOrder(orders.get(index).getOrderId());
                    orders = database.getAllOrders();
                    prepareOrders(secondaryLayout);
                    allBooks = database.getBooksByPage(pageNum, LIMIT, new HashMap<>());
                    prepareBooks(allBooks);
                    //prepareUsers(secondaryLayout);
                }
            });
            secondaryLayout.getChildren().add(userbutton);
        }
    }

    @FXML
    void clickViewUsers(ActionEvent event) {
        ScrollPane c = new ScrollPane();
        AnchorPane container = new AnchorPane();
        Scene secondScene = new Scene(c, 1200, 800);
        c.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        System.out.println();
        secondScene.getStylesheets().add("style.css");
        container.setMinWidth(1200);
        container.setMinHeight(800);
        VBox secondaryLayout = new VBox();
        secondaryLayout.setMinWidth(1200);
        secondaryLayout.setMinHeight(800);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setSpacing(3);
        try {
            Image image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
            ImageView wallpaper = new ImageView(image);
            wallpaper.setFitHeight(800);
            wallpaper.setFitWidth(1200);
            wallpaper.setX(0);
            wallpaper.setY(0);
     //       container.getChildren().add(wallpaper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        users = new ArrayList<>();
        pageNumUsers = 0;
        users = database.getUsersByPage(pageNumUsers, LIMIT);
        userButtons = new ArrayList<>();

        prepareUsers(secondaryLayout);
        c.setContent(container);
        container.getChildren().add(secondaryLayout);
        Stage newWindow = new Stage();
        newWindow.setTitle("Users");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(382);
        newWindow.setY(109);

        newWindow.show();
    }

    private void prepareUsers(VBox secondaryLayout) {
        secondaryLayout.getChildren().clear();
        Button search = new Button("Search users by ID");
        search.getStyleClass().add("mybutton");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //todo database.getUserById()
                User user = new User();
                user = users.get(0);
                secondaryLayout.getChildren().clear();

                Button userbutton = new Button();
                userbutton.setMinHeight(60);
                userbutton.setMinWidth(1150);
                userButtons.add(userbutton);
                String userOnButton = user.getFirstName() +" " +user.getLastName();
                userOnButton = userOnButton + "\t\t";
                userOnButton = userOnButton + user.getUserId();
                userOnButton = userOnButton + "\t\t";
                userOnButton = userOnButton + user.getPhoneNumber();
                userOnButton = userOnButton + "\t\t";
                userOnButton = userOnButton + user.getEmailAddress();
                userbutton.setTextFill(Color.WHITE);
                if(user.isPromoted()) {
                    userbutton.getStyleClass().add("mybuttonpromoted");
                } else {
                    userbutton.getStyleClass().add("mybutton");
                }
                userbutton.setTextAlignment(TextAlignment.LEFT);
                userbutton.setId("0");
                userbutton.setFont(Font.font("Cambria", 23));
                userbutton.setText(userOnButton);
                userbutton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int index = Integer.valueOf(((Node)event.getSource()).getId());
                        userButtons.get(index).getStyleClass().add("mybuttonpromoted");
                        database.promote(users.get(index).getUserId());
                    }
                });
                secondaryLayout.getChildren().add(userbutton);
            }
        });
        secondaryLayout.getChildren().add(search);
        for (int i = 0; i < users.size(); i++) {
            Button userbutton = new Button();
            userbutton.setMinHeight(60);
            userbutton.setMinWidth(1200);
            userButtons.add(userbutton);
            String userOnButton = users.get(i).getFirstName() +" " +users.get(i).getLastName();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + users.get(i).getUserId();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + users.get(i).getPhoneNumber();
            userOnButton = userOnButton + "\t\t";
            userOnButton = userOnButton + users.get(i).getEmailAddress();
            userbutton.setTextFill(Color.WHITE);
            if(users.get(i).isPromoted()) {
                userbutton.getStyleClass().add("mybuttonpromoted");
            } else {
                userbutton.getStyleClass().add("mybutton");
            }
            userbutton.setTextAlignment(TextAlignment.LEFT);
            userbutton.setId(String.valueOf(i));
            userbutton.setFont(Font.font("Cambria", 23));
            userbutton.setText(userOnButton);
            userbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = Integer.valueOf(((Node)event.getSource()).getId());
                    ((Node)event.getSource()).getStyleClass().add("mybuttonpromoted");
                    database.promote(users.get(index).getUserId());
                    users = database.getUsersByPage(pageNumUsers, LIMIT);
                    //prepareUsers(secondaryLayout);
                }
            });
            secondaryLayout.getChildren().add(userbutton);

        }
        Button bnext = new Button(">>");
        bnext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                users = new ArrayList<>();
                users = database.getUsersByPage(++pageNumUsers, LIMIT);
                prepareUsers(secondaryLayout);
            }
        });
        Button bprev = new Button("<<");
        bprev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pageNumUsers != 0) {
                    users = new ArrayList<>();
                    users = database.getUsersByPage(--pageNumUsers, LIMIT);
                    prepareUsers(secondaryLayout);
                }
            }
        });
        secondaryLayout.getChildren().addAll(bnext, bprev);
    }


    @FXML
    void checkedManagerBox(ActionEvent event) {
        if(isManagerCheckBox.isSelected()) {
            secretCodeTextField.setVisible(true);
        } else {
            secretCodeTextField.setVisible(false);
        }
    }
    @FXML
    void viewAuthors(ActionEvent event) {
        ScrollPane c = new ScrollPane();
        AnchorPane container = new AnchorPane();
        Scene secondScene = new Scene(c, 1200, 800);
        c.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        System.out.println();
        secondScene.getStylesheets().add("style.css");
        container.setMinWidth(1200);
        container.setMinHeight(800);
        VBox secondaryLayout = new VBox();
        secondaryLayout.setMinWidth(1200);
        secondaryLayout.setMinHeight(800);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setSpacing(3);
        try {
            Image image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
            ImageView wallpaper = new ImageView(image);
            wallpaper.setFitHeight(800);
            wallpaper.setFitWidth(1200);
            wallpaper.setX(0);
            wallpaper.setY(0);
            //       container.getChildren().add(wallpaper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pageNumUsers = 0;
        authors = new ArrayList<>();
        authors = database.getAuthorsByPage(pageNumUsers, LIMIT);
        userButtons = new ArrayList<>();

        prepareAuthors(secondaryLayout);
        c.setContent(container);
        container.getChildren().add(secondaryLayout);
        Stage newWindow = new Stage();
        newWindow.setTitle("Authors");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(382);
        newWindow.setY(109);

        newWindow.show();
    }
    @FXML
    void addAuthor(ActionEvent event) {
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 442.0, 296);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Author");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(297.5);
        wallpaper.setFitWidth(442.0);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        vbx.setMinWidth(442);
        vbx.setMinHeight(297.5);
        vbx.setAlignment(Pos.CENTER);

        TextField authorName = new TextField();
        authorName.setPromptText("Enter the name of the author");
        authorName.setFocusTraversable(false);

        Button done = new Button("Add Author");
        done.setFocusTraversable(true);
        done.getStyleClass().add("mybutton");
        done.setTextFill(Color.WHITE);
        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Author author = new Author();
                author.setName(authorName.getText());
                database.addAuthor(author);
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
        });
        vbx.getChildren().addAll(authorName, done);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }
    @FXML
    void clickSearch(ActionEvent event) {
        Book book = new Book();
        searchElements = new HashMap<>();
        if(!searchAuthorTextbox.getText().equals("")) {
            searchElements.put(book.AUTHOR_NAME, searchAuthorTextbox.getText());
        }
        if(searchCategoryTextbox.getValue() != null) {
            searchElements.put(book.CATEGORY, (String)searchCategoryTextbox.getValue());
        }
        if(!searchTitleTextbox.getText().equals("")) {
            searchElements.put(book.PUBLISHER_NAME, searchTitleTextbox.getText());
        }
        if(searchElements.size() > 0) {
            pageNum = 0;
            allBooks = database.getBooksByPage(pageNum, LIMIT, searchElements);
            showMarket();
            if(allBooks!=null) {
                prepareBooks(allBooks);
            }
        }
    }

    private void prepareBooks(ArrayList<Book> allLibraryBooks) {
        ArrayList<Integer> quantities = new ArrayList();
        ArrayList<CartElement> cartElem = new ArrayList<>();

        addedToCart = false;
        int numberOfBooks = allLibraryBooks.size();
        Pane root = new Pane();
        booksPane.setContent(root);
        String cssLayout =
                "-fx-background-color : brown;\n" +
                        "-fx-border-color: brown;\n" +
                        "-fx-border-insets: 5;\n" +
                        "-fx-border-width: 2;\n" +
                        "-fx-border-style: solid;\n";
        booksPane.setStyle(cssLayout);
        int xValue = 0;
        int yValue = 0;
        currentCount = new TextField[numberOfBooks];
        addToCartButton = new Button[numberOfBooks];
        for(int i = 0; i < numberOfBooks; i++) {
            VBox container = new VBox();
            container.setId(String.valueOf(allLibraryBooks.get(i).getBookId()));
            container.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    showCertainBook(database.getBookById(Integer.valueOf(((VBox)(event.getSource())).getId())));
                }
            });
            if(i%BOOKS_PER_ROW != 0 || i == 0) {
                container.setLayoutX(xValue * HORIZONTAL_SHIFT + MARGIN);
                container.setLayoutY(yValue * VERTICAL_SHIFT + MARGIN);
                xValue++;
            } else {
                xValue = 0;
                yValue++;
                container.setLayoutY(yValue * VERTICAL_SHIFT + MARGIN);
                container.setLayoutX(xValue * HORIZONTAL_SHIFT + MARGIN);
                xValue++;
            }
            /*==============adjusting container style==================================*/
            String cssLayout2 =
                    "-fx-background-color : #fec377;\n" +
                            "-fx-border-color: brown;\n" +
                            "-fx-border-insets: 5;\n" +
                            "-fx-border-width: 2;\n" +
                            "-fx-border-style: solid;\n";
            container.setMaxWidth(HORIZONTAL_SHIFT - 15);
            container.setMinWidth(HORIZONTAL_SHIFT - 15);
            container.setStyle(cssLayout2);
            container.setSpacing(5);
            container.setAlignment(Pos.CENTER);
            //==========================================================================

            String bookName = allLibraryBooks.get(i).getTitle();
            Label bookLabel = new Label(bookName);
            bookLabel.setFont(Font.font("Cambria", 23));

            String Author = allLibraryBooks.get(i).getPubYear();
            HBox bookAuthor = new HBox();
            bookAuthor.setAlignment(Pos.CENTER);
            Label bookAuthor1 = new Label("pub year: ");
            Label bookAuthor2 = new Label(Author);
            bookAuthor1.setStyle("-fx-text-fill: grey ;");
            bookAuthor1.setFont(Font.font("Cambria", 14));
            bookAuthor2.setStyle("-fx-text-fill: brown ;") ;
            bookAuthor2.setFont(Font.font("Cambria", 16));
            bookAuthor.getChildren().addAll(bookAuthor1, bookAuthor2);

            int quantity = allLibraryBooks.get(i).getQuantity();
            HBox count = new HBox();
            count.setAlignment(Pos.CENTER);
            Label count1 = new Label("Available in stock: ");
            Label count2 = new Label(String.valueOf(quantity));
            count1.setFont(Font.font("Cambria", 14));
            count1.setStyle("-fx-text-fill: grey ;");
            count2.setFont(Font.font("Cambria", 18));
            count.getChildren().addAll(count1, count2);

            HBox counter = new HBox();
            counter.setMaxWidth(104);
            Button decrement = new Button("-");
            decrement.setId("d" + String.valueOf(i));
            currentCount[i] = new TextField("1");
            currentCount[i].setMaxWidth(50);
            currentCount[i].setEditable(false);
            currentCount[i].setStyle("-fx-alignment: center ;");
            Button increment = new Button("+");
            increment.setId("i"+String.valueOf(i));


            decrement.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int i = Integer.valueOf(((Node)e.getSource()).getId().substring(1,((Node)e.getSource()).getId().length()));
                    int cc = Integer.valueOf(currentCount[i].getText());
                    if(cc != 0) {
                        currentCount[i].setText(String.valueOf(--cc));
                    }
                }
            });
            increment.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int i = Integer.valueOf(((Node)e.getSource()).getId().substring(1,((Node)e.getSource()).getId().length()));
                    int cc = Integer.valueOf(currentCount[i].getText());
                    currentCount[i].setText(String.valueOf(++cc));
                }
            });

            counter.getChildren().addAll(decrement,currentCount[i], increment);
            addToCartButton[i] = new Button("Add to cart");
            addToCartButton[i].setId(String.valueOf(i));
            addToCartButton[i].setStyle("-fx-background-color: GREEN;\n" +
                    "-fx-text-fill: WHITE ;");
            if(isNowCart) {
                addToCartButton[i].setText("Remove from cart");
                addToCartButton[i].setStyle("-fx-background-color: RED;\n" +
                        "-fx-text-fill: WHITE ;");

            }
            addToCartButton[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(((Button) (event.getSource())).getText().equals("Add to cart")) {
                        Button cartButton = ((Button) (event.getSource()));
                        cartButton.setText("Remove from cart");
                        cartButton.setStyle("-fx-background-color: RED;\n" +
                                "-fx-text-fill: WHITE ;");
                        int index = Integer.valueOf(((Button)(event.getSource())).getId());
                        int quantity = Integer.valueOf(currentCount[index].getText());
                        database.addToCart(allLibraryBooks.get(index).getBookId(),quantity,currentUser.getUserId());
                        boolean found = false;
                        for(int m = 0; m < cartBooks.size(); m++) {
                            if(cartBooks.get(m).getBookId() == allLibraryBooks.get(index).getBookId()) {
                                found = true;
                            }
                        }
                        if(!found) {
                            cartBooks.add(allLibraryBooks.get(index));
                        }
                    } else {
                        Button cartButton = ((Button) (event.getSource()));
                        cartButton.setText("Add to cart");
                        cartButton.setStyle("-fx-background-color: GREEN;\n" +
                                "-fx-text-fill: WHITE ;");
                        int index = Integer.valueOf(((Button)(event.getSource())).getId());
                        int quantity = Integer.valueOf(currentCount[index].getText());
                        database.removeFromCart(allLibraryBooks.get(index).getBookId(),currentUser.getUserId());
                        cartBooks.remove(allLibraryBooks.get(index));
                        if(isNowCart) {
                            prepareBooks(cartBooks);
                        }
                    }
                }
            });

            float bookPrice = allLibraryBooks.get(i).getSellingPrice();
            HBox price = new HBox();
            price.setAlignment(Pos.CENTER);
            Label p1 = new Label("Price/book: LE ");
            Label p2 = new Label(String.valueOf(bookPrice));
            p1.setFont(Font.font("Cambria", 14));
            p1.setStyle("-fx-text-fill: grey ;");
            p2.setFont(Font.font("Cambria", 18));
            p2.setStyle("-fx-text-fill: GREEN ;");
            price.getChildren().addAll(p1, p2);
            Label newLine = new Label("");
            container.getChildren().addAll(bookLabel, bookAuthor, count, counter, price, addToCartButton[i], newLine);
            root.getChildren().add(container);
        }
        double totalPrice = 0.0;
        if(cartBooks.size() != 0) {
            cartElem = database.getCart(currentUser.getUserId());
            for(int k = 0; k < cartElem.size(); k++) {
                for(int l = 0; l < cartBooks.size(); l++) {
                    if(cartBooks.get(l).getBookId() == cartElem.get(k).getBookId()) {
                        currentCount[l].setText(cartElem.get(k).getQuantity()+"");
                        totalPrice+= cartElem.get(k).getQuantity()* cartBooks.get(l).getSellingPrice();
                    }
                }
            }
        }
        checkoutButton.setText("Checkout. LE " + totalPrice);

    }
    private void showErrorLogIn() {
        errorLoginLabel.setVisible(true);
    }
    private void showLogin() {
        toolBar.setVisible(false);
        pageNumLabel.setVisible(false);
        isManagerCheckBox.setVisible(false);
        nextPageButton.setVisible(false);
        prevPageButton.setVisible(false);
        firstNameTextbox.setVisible(false);
        lastNameTextbox.setVisible(false);
        rEmailTextbox.setVisible(false);
        rPasswordTextbox.setVisible(false);
        confirmPasswordTextbox.setVisible(false);
        telefonTextbox.setVisible(false);
        addressTextbox.setVisible(false);
        validIcon.setVisible(false);
        notValidIcon.setVisible(false);
        booksPane.setVisible(false);
        searchTitleTextbox.setVisible(false);
        searchCategoryTextbox.setVisible(false);
        searchAuthorTextbox.setVisible(false);
        searchButton.setVisible(false);
        findCertainBook.setVisible(false);
        buttonIncreaseBPP.setVisible(false);
        buttondecreaseBPP.setVisible(false);
        labelInfo.setVisible(false);
        toolBar.setVisible(false);
        checkoutButton.setVisible(false);

        slogan.setVisible(false);
        loginButton.setLayoutX(loginButton.getLayoutX());
        loginButton.setLayoutY(437 + 160);
        loginButton.setVisible(true);
        registerButton.setVisible(true);
        registerButton.setLayoutX(registerButton.getLayoutX());
        registerButton.setLayoutY(437 + 160);
        emailTextbox.setVisible(true);
        passwordTextbox.setVisible(true);
    }
    private void showMarket() {
        errorLoginLabel.setVisible(false);
        isManagerCheckBox.setVisible(false);
        validIconManager.setVisible(false);
        secretCodeTextField.setVisible(false);
        checkoutButton.setVisible(false);
        firstNameTextbox.setVisible(false);
        lastNameTextbox.setVisible(false);
        rEmailTextbox.setVisible(false);
        rPasswordTextbox.setVisible(false);
        confirmPasswordTextbox.setVisible(false);
        telefonTextbox.setVisible(false);
        addressTextbox.setVisible(false);
        validIcon.setVisible(false);
        notValidIcon.setVisible(false);
        emailTextbox.setVisible(false);
        passwordTextbox.setVisible(false);
        loginButton.setVisible(false);
        registerButton.setVisible(false);

        labelInfo.setVisible(true);
        pageNumLabel.setVisible(true);
        nextPageButton.setVisible(true);
        prevPageButton.setVisible(true);
        findCertainBook.setVisible(true);
        buttondecreaseBPP.setVisible(true);
        buttonIncreaseBPP.setVisible(true);
        toolBar.setVisible(true);
        if(currentUser!= null && currentUser.isPromoted()) {
            addBookButtonToolbar.setVisible(true);
            viewOrdersButtonToolbar.setVisible(true);
            viewReportButtonToolbar.setVisible(true);
            viewUsersButtonToolbar.setVisible(true);
            getAuthorsButtonToolbar.setVisible(true);
            addAuthorButtonToolbar.setVisible(true);
            addPublisherButtonToolbar.setVisible(true);
            publishersButtonToolbar.setVisible(true);
        } else {
            addPublisherButtonToolbar.setVisible(false);
            publishersButtonToolbar.setVisible(true);
            addBookButtonToolbar.setVisible(false);
            viewOrdersButtonToolbar.setVisible(false);
            viewReportButtonToolbar.setVisible(false);
            getAuthorsButtonToolbar.setVisible(true);
            addAuthorButtonToolbar.setVisible(false);
            viewUsersButtonToolbar.setVisible(false);
        }
        booksPane.setVisible(true);
        searchAuthorTextbox.setVisible(true);
        searchButton.setVisible(true);
        searchCategoryTextbox.setVisible(true);
        searchTitleTextbox.setVisible(true);
    }
    private void showRegister() {
        firstNameTextbox.setVisible(true);
        lastNameTextbox.setVisible(true);
        rEmailTextbox.setVisible(true);
        rPasswordTextbox.setVisible(true);
        confirmPasswordTextbox.setVisible(true);
        telefonTextbox.setVisible(true);
        addressTextbox.setVisible(true);
        loginButton.setLayoutX(loginButton.getLayoutX());
        loginButton.setLayoutY(437 + 160);
        registerButton.setLayoutX(registerButton.getLayoutX());
        registerButton.setLayoutY(437 + 160);
        isManagerCheckBox.setVisible(true);

        nextPageButton.setVisible(false);
        findCertainBook.setVisible(false);
        prevPageButton.setVisible(false);
        slogan.setVisible(false);
        booksPane.setVisible(false);
        searchTitleTextbox.setVisible(false);
        searchCategoryTextbox.setVisible(false);
        searchAuthorTextbox.setVisible(false);
        searchButton.setVisible(false);
        buttonIncreaseBPP.setVisible(false);
        buttondecreaseBPP.setVisible(false);
        labelInfo.setVisible(false);
        toolBar.setVisible(false);
        emailTextbox.setVisible(false);
        passwordTextbox.setVisible(false);
    }
    private void showCertainBook(Book book) {
        searchElements = new HashMap<>();
        Pane container = new Pane();
        Scene secondScene = new Scene(container, 600, 300);
        secondScene.getStylesheets().add("style.css");
        Stage newWindow = new Stage();
        newWindow.setTitle("Book info");
        newWindow.setScene(secondScene);

        Image image = null;
        try {
            image = new Image(new FileInputStream(
                    this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
                            + "../../../src/view/Resources/images/libraryy.jpg"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ImageView wallpaper = new ImageView(image);
        wallpaper.setFitHeight(300);
        wallpaper.setFitWidth(600);
        wallpaper.setX(0);
        wallpaper.setY(0);
        container.getChildren().add(wallpaper);
        VBox vbx = new VBox();
        String cssLayout2 =
                "-fx-background-color : lightgrey;\n" +
                        "-fx-border-color: brown;\n" +
                        "-fx-border-insets: 5;\n" +
                        "-fx-border-width: 2;\n" +
                        "-fx-border-style: solid;\n";
        vbx.setMaxWidth(500);
        vbx.setMinWidth(600);
        vbx.setMinHeight(300);
        vbx.setMaxHeight(300);
        vbx.setStyle(cssLayout2);
        vbx.setSpacing(5);
        vbx.setAlignment(Pos.CENTER);
        //==========================================================================

        String bookName = book.getTitle();
        Label bookLabel = new Label(bookName);
        bookLabel.setFont(Font.font("Cambria", 26));

        Label dashes = new Label("------------------------------");
        dashes.setFont(Font.font("Cambria", 23));

        String pubYear = book.getPubYear();
        HBox bookAuthor = new HBox();
        bookAuthor.setAlignment(Pos.CENTER);
        Label pubYear1 = new Label("pub year: ");
        Label pubYear2 = new Label(pubYear);
        pubYear1.setStyle("-fx-text-fill: grey ;");
        pubYear1.setFont(Font.font("Cambria", 17));
        pubYear2.setStyle("-fx-text-fill: brown ;") ;
        pubYear2.setFont(Font.font("Cambria", 19));
        bookAuthor.getChildren().addAll(pubYear1, pubYear2);

        String Pub = book.getPublisherName();
        HBox publisher = new HBox();
        publisher.setAlignment(Pos.CENTER);
        Label pub1 = new Label("Published by: ");
        Label pub2 = new Label(Pub);
        pub1.setStyle("-fx-text-fill: grey ;");
        pub1.setFont(Font.font("Cambria", 17));
        pub2.setStyle("-fx-text-fill: brown ;") ;
        pub2.setFont(Font.font("Cambria", 19));
        publisher.getChildren().addAll(pub1, pub2);

        String auth = "";
        ArrayList<String> l = book.getAuthorsNames();
        for(int n = 0; n < l.size(); n++) {
            auth = auth + l.get(n) + ", ";
        }
        auth = auth.substring(0,auth.length()-1);
        HBox authors = new HBox();
        authors.setAlignment(Pos.CENTER);
        Label au1 = new Label("Author(s): ");
        Label au2 = new Label(auth);
        pub1.setStyle("-fx-text-fill: grey ;");
        pub1.setFont(Font.font("Cambria", 17));
        pub2.setStyle("-fx-text-fill: brown ;") ;
        pub2.setFont(Font.font("Cambria", 19));
        authors.getChildren().addAll(au1, au2);

        String categ = book.getCategory();
        HBox category = new HBox();
        category.setAlignment(Pos.CENTER);
        Label cat1 = new Label("Category: ");
        Label cat2 = new Label(categ);
        cat1.setStyle("-fx-text-fill: grey ;");
        cat1.setFont(Font.font("Cambria", 17));
        cat2.setStyle("-fx-text-fill: brown ;") ;
        cat2.setFont(Font.font("Cambria", 19));
        category.getChildren().addAll(cat1, cat2);

        String id = book.getBookId() + "";
        HBox bookId = new HBox();
        bookId.setAlignment(Pos.CENTER);
        Label b1 = new Label("Book id: ");
        Label b2 = new Label(id);
        b1.setStyle("-fx-text-fill: grey ;");
        b1.setFont(Font.font("Cambria", 17));
        b2.setStyle("-fx-text-fill: brown ;") ;
        b2.setFont(Font.font("Cambria", 19));
        bookId.getChildren().addAll(b1, b2);

        int quantity = book.getQuantity();
        HBox count = new HBox();
        count.setAlignment(Pos.CENTER);
        Label count1 = new Label("Available in stock: ");
        Label count2 = new Label(String.valueOf(quantity));
        count1.setFont(Font.font("Cambria", 17));
        count1.setStyle("-fx-text-fill: grey ;");
        count2.setFont(Font.font("Cambria", 21));
        count.getChildren().addAll(count1, count2);

        float bookPrice = book.getSellingPrice();
        HBox price = new HBox();
        price.setAlignment(Pos.CENTER);
        Label p1 = new Label("Price/book: LE ");
        Label p2 = new Label(String.valueOf(bookPrice));
        p1.setFont(Font.font("Cambria", 14));
        p1.setStyle("-fx-text-fill: grey ;");
        p2.setFont(Font.font("Cambria", 18));
        p2.setStyle("-fx-text-fill: GREEN ;");
        price.getChildren().addAll(p1, p2);
        Label newLine = new Label("");
        vbx.getChildren().addAll(bookLabel,dashes, bookId, category, publisher, authors, count, price, bookAuthor, newLine);
        container.getChildren().add(vbx);
        newWindow.setX(582);
        newWindow.setY(259);
        newWindow.show();
    }
    private void establishNewConncetion() throws SQLException, ClassNotFoundException {
        pageNum = 0;
        searchElements = new HashMap<>();
        allBooks = new ArrayList<Book>();
        currentUser = new User();
        cartBooks = new ArrayList<Book>();

        database = new Model();
        allBooks = database.getBooksByPage(pageNum, LIMIT, new HashMap<>());
//        currentUser.setFirstName("Islam");
//        currentUser.setLastName("Gamal");
//        currentUser.setEmailAddress("islamgamal77@gmail.com");
//        currentUser.setPromoted(true);
//        currentUser.setShippingAddress("Alzorkani ST, Miami, Alexandria");
//        currentUser.setPhoneNumber("(+20) 109-144-8249");
//        currentUser.setPassword("mypassword");
//
//        for(int i = 0; i < 20; i++) {
//            Book book = new Book();
//            book.setBookId(i);
//            book.setCategory("Science");
//            book.setPublisherId(5);
//            book.setPubYear("23 July 1997");
//            book.setSellingPrice((int)(Math. random() * 2000 + 1));
//            book.setTitle("Book Number " + i);
//            book.setQuantity((int)(Math. random() * 50 + 1));
//            allBooks.add(book);
//        }
    }
}
