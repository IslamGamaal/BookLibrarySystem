package Model;

import Entites.CartElement;

import java.sql.*;
import com.mysql.jdbc.Driver;

public class ConnectionHandler {

    Connection con = null;
    private static ConnectionHandler instance = null;
    private ConnectionHandler(){};

    public static ConnectionHandler getInstance() {
        if (instance == null) {
            instance = new ConnectionHandler();
        }
        return instance;
    }

    public void startConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/BOOK_STORE","root", "");
        return;
    }
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(con == null) {
            startConnection();
        }
        return con;
    }
    public void closeConnection() throws SQLException {
        con.close();
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }

    public int executeInsert(String query) throws SQLException{
        Statement stmt = con.createStatement();
        return stmt.executeUpdate(query);
    }

    public boolean prepareCall(int userId , int bookId) throws SQLException {
        CallableStatement statement;
        if (bookId < 0){
            String query = "{call purchase(?)}";
            statement = con.prepareCall(query);
            statement.setInt(1, userId);

        }
        else{
            String query = "{call remove_from_cart(?,?)}";
            statement = con.prepareCall(query);
            statement.setInt(1 , userId);
            statement.setInt(2, bookId);
        }
        statement.execute();
        return true;
    }

    public boolean prepareCallForAddToCart(int bookId, int userId, int quantity) throws SQLException{
        CallableStatement statement;
        String query ="{call add_to_cart(?,?,?)}";
        statement = con.prepareCall(query);
        statement.setInt(1,bookId);
        statement.setInt(2,userId);
        statement.setInt(3,quantity);
        statement.execute();
        return true;

    }
}
