package Model;

import Entites.*;

import java.sql.SQLData;
import java.util.HashMap;

public class SQLCommands {

    public String selectFromBooks(int limit , int pageNumber){
        String query = "SELECT * FROM BOOK LIMIT " + limit*pageNumber + "," + limit;
        return query;
    }
    public String logInUser(String email, String password){
        String query = "SELECT * FROM USER WHERE USER.email_address = \""
                + email + "\"  AND  USER.password = \"" + password + "\"";
        return query;
    }

    public String insertBook(Book book){
        String query = "INSERT INTO BOOK VALUES ("
                +book.getBookId() + ","
                + "\"" + book.getTitle() + "\","
                +"\"" + book.getPubYear() + "\","
                +book.getSellingPrice() + ","
                + "\"" + book.getCategory() + "\","
                + book.getQuantity() + ","
                +book.getPublisherId() + ","
                +book.getThreshold() + ")";
        return query;
    }

    public String updateBook(Book book){
        String query = "UPDATE BOOK SET " + book.TITLE + " = \"" + book.getTitle() + "\","
                + book.PUBYEAR + " = \"" + book.getPubYear() + "\","
                + book.SELLINGPRICE + " = " + book.getSellingPrice() + ","
                + book.CATEGORY + " = " + "\"" +  book.getCategory() + "\","
                + book.QUANTITY + " = " + book.getQuantity() + ","
                + book.THRESHOLD + " = " + book.getThreshold()
                + " WHERE BOOK." + book.BOOKID + " = " +""  + book.getBookId();
        return query;
    }

    public String deleteBook(Book book) {
        String query = "DELETE FROM BOOK WHERE BOOK.book_id = "
                + book.getBookId();
        return query;
    }

    public String updateUser(User user) {
        String query = "UPDATE USER SET "
                + user.FIRSTNAME + " = \"" + user.getFirstName() + "\","
                + user.LASTNAME + " = " + "\"" +  user.getLastName() +  "\","
                + user.EMAILADDRESS + " = " + "\"" +  user.getEmailAddress() + "\","
                + user.PHONENUMBER + " = " + "\"" + user.getPhoneNumber() + "\","
                + user.SHIPPINGADDRESS + " = " + "\"" + user.getShippingAddress() + "\","
                + user.PASSWORD + " = " + "\"" + user.getPassword() + "\","
                + user.PROMOTED + " = " + user.isPromoted()
                + " WHERE USER." + user.EMAILADDRESS + " = "  + "\"" + user.getEmailAddress() +"\"";
        return query;
    }

    public String deleteUser(User user) {
        String query = "DELETE FROM USER WHERE USER.email_address = \""
                + user.getEmailAddress() +"\"";
        return query;
    }

//    public String addToCart(CartElement cartElement) {
//        String query = "INSERT INTO CART VALUES ("
//                +cartElement.getBookId() + ","
//                +cartElement.getUserId() + ","
//                +cartElement.getQuantity() + ","
//                +cartElement.isInCart() + ")";
//        return query;
//    }

    public String getCart(int userId) {
        String query = "SELECT * FROM CART WHERE CART.user_id = " + userId;
        return query;
    }

    public String getAllUsers() {
        String query = "SELECT * FROM USER";
        return query;
    }

    public String promoteUser(int userId) {
        String query = "UPDATE USER SET promoted = 1 WHERE USER.user_id = " + userId;

        return query;
    }

    public String getAllOrders() {
        String query = "SELECT * FROM `ORDER`";
        System.out.println(query);
        return query;
    }

    public String placeOrder(Order order) {
        String query = "INSERT INTO `ORDER` VALUES ("
                +order.getOrderId() + ","
                +order.getBookId() + ","
                +order.getQuantity() + ","
                +"current_date" + ")";
        return query;
    }

    public String deleteOrder(int orderId) {
        String query = "DELETE FROM `ORDER` WHERE ORDER.order_id = "
                + orderId ;
        return query;
    }

    public String registerUser(User user) {
        String query = "INSERT INTO USER VALUES "
                +"(user_id,"
                +"\"" + user.getFirstName() + "\","
                + "\"" +  user.getLastName() +  "\","
                + "\"" +  user.getEmailAddress() + "\","
                + "\"" + user.getPhoneNumber() + "\","
                + "\"" + user.getShippingAddress() + "\","
                + "\"" + user.getPassword() + "\","
                + user.isPromoted() + ")";
        return query;
    }

    public String getWhereClauseBooks(HashMap<String, String> searchMap) {
        if(searchMap.isEmpty()) {
            return "FROM BOOK ";
        }
        String from = " FROM BOOK ";
        String where = " WHERE ";
        Boolean flag = false;
                if(searchMap.containsKey("publisher_name")) {
                    from+= " , PUBLISHER";
                    where+= " BOOK.publisher_id = PUBLISHER.publisher_id AND  PUBLISHER.name = \"" + searchMap.get("publisher_name") + "\" ";
                    flag = true;
                }
                if(searchMap.containsKey("author_name")) {
                    if(flag) {
                        where += " AND ";
                    }
                    from+= ", AUTHORED_BY, AUTHOR ";
                    where+= " BOOK.book_id = AUTHORED_BY.book_id  AND  AUTHORED_BY.author_id = AUTHOR.author_id AND "
                +" AUTHOR.name = \"" + searchMap.get("author_name") +"\" ";
                    flag = true;
                }
                if (searchMap.containsKey("category")) {
                    if(flag) {
                        where += " AND ";
                    }
                    where+= " BOOK.category = \""+ searchMap.get("category") + "\" ";
                    flag = true;
                }
                String query = from + where;
        return query;
    }

    public String getBookById(int bookId) {
     String query = "SELECT BOOK.book_id , BOOK.title , BOOK.pub_year,BOOK.selling_price ,BOOK.category , BOOK.quantity , BOOK.publisher_id , BOOK.threshold ,\n" +
                " \"\" As author_name , PUBLISHER.name AS publisher_name\n" +
                " FROM BOOK, PUBLISHER\n" +
                " WHERE BOOK.book_id = " + bookId + " AND  BOOK.publisher_id = PUBLISHER.publisher_id AND Not exists (select * From AUTHORED_BY where AUTHORED_BY.book_id = " + bookId + " )\n" +
                " Union\n" +
                " SELECT BOOK.book_id , BOOK.title , BOOK.pub_year,BOOK.selling_price ,BOOK.category , BOOK.quantity , BOOK.publisher_id , BOOK.threshold ,\n" +
                "       AUTHOR.name As author_name , PUBLISHER.name AS publisher_name\n" +
                "    FROM BOOK,AUTHOR,AUTHORED_BY,PUBLISHER\n" +
                " WHERE BOOK.book_id = "+ bookId +" AND  BOOK.publisher_id = PUBLISHER.publisher_id AND BOOK.book_id = AUTHORED_BY.book_id  AND  AUTHORED_BY.author_id = AUTHOR.author_id";
        return query;
    }

    public String getBookByTitle(String title) {
        String query = "SELECT BOOK.book_id , BOOK.title , BOOK.pub_year,BOOK.selling_price ,BOOK.category , BOOK.quantity , BOOK.publisher_id , BOOK.threshold ,\n" +
                " \"\" As author_name , PUBLISHER.name AS publisher_name\n" +
                " FROM BOOK, PUBLISHER\n" +
                " WHERE BOOK.title = \'" + title + "\' AND  BOOK.publisher_id = PUBLISHER.publisher_id\n" +
                " AND \'" + title + "\' Not in (select BOOK.title From BOOK, AUTHORED_BY where AUTHORED_BY.book_id = BOOK.book_id)\n" +
                " Union\n" +
                " SELECT BOOK.book_id , BOOK.title , BOOK.pub_year,BOOK.selling_price ,BOOK.category , BOOK.quantity , BOOK.publisher_id , BOOK.threshold ,\n" +
                " AUTHOR.name As author_name , PUBLISHER.name AS publisher_name\n" +
                " FROM BOOK,AUTHOR,AUTHORED_BY,PUBLISHER\n" +
                " WHERE BOOK.title = \'" + title + "\' AND  BOOK.publisher_id = PUBLISHER.publisher_id AND BOOK.book_id = AUTHORED_BY.book_id  AND  AUTHORED_BY.author_id = AUTHOR.author_id\n";
        return query;
    }

    public String getBooksByPage(int pageNumber, int limit, HashMap<String , String> searchMap) {
        String whereClause = getWhereClauseBooks(searchMap);
        String query = "SELECT BOOK.book_id , BOOK.title , BOOK.pub_year," +
                " BOOK.selling_price ,BOOK.category , BOOK.quantity , BOOK.publisher_id , BOOK.threshold "
                +whereClause+" LIMIT " + pageNumber*limit+ " , " +limit+"";
        System.out.println(query);
        return query;
    }

    public String getNumberOfPagesOfBooks(String key, HashMap<String , String> searchMap) {
        String whereClause = getWhereClauseBooks(searchMap);
        String query = "SELECT COUNT AS \"" + key + "\" " + whereClause + " GROUP BY book_id";
        return query;
    }

    public String getNumberOfPagesOfUsers(String key) {
        String query = "SELECT COUNT AS \"" + key + "\" FROM USER GROUP BY user_id";
        return query;
    }

    public String getUsersByPage(int pageNumber, int limit) {
        String query = "SELECT * FROM USER LIMIT " + pageNumber*limit+ " , " +limit+"";
        return query;
    }

    public String emptyCart(int userId) {
        String query = "DELETE FROM CART WHERE user_id = " + userId;
        return query;
    }

    public String getAllPublishers() {
        String query = "SELECT * FROM PUBLISHER";
        return query;
    }

    public String insertPublisher(Publisher publisher) {
        String query = "INSERT INTO PUBLISHER VALUES ("
                + "publisher_id,"
                + "\"" + publisher.getName() + "\","
                +"\"" + publisher.getPhone() + "\","
                + "\"" + publisher.getAddress() + "\")";
        return query;
    }

    public String getNumberOfPagesOfPublishers(String key) {
        String query = "SELECT COUNT AS \"" + key + "\" FROM PUBLISHER GROUP BY publisher_id";
        return query;
    }

    public String getPublishersByPage(int pageNumber, int limit) {
        String query = "SELECT * FROM PUBLISHER LIMIT " + pageNumber*limit+ " , " +limit+"";
        return query;
    }

    public String getNumberOfPagesOfAuthors(String key) {
        String query = "SELECT COUNT AS \"" + key + "\" FROM AUTHOR GROUP BY author_id";
        return query;
    }

    public String getAuthorsByPage(int pageNumber, int limit) {
        String query = "SELECT * FROM AUTHOR LIMIT " + pageNumber*limit+ " , " +limit+"";
        return query;
    }

    public String insertAuthor(Author author) {
        String query = "INSERT INTO AUTHOR VALUES ("
                + "author_id,"
                + "\"" + author.getName() + "\")";
        return query;
    }

    public String setAuthoredBy(int authorId, int bookId) {
        String query = "INSERT INTO AUTHORED_BY VALUES ("
                + authorId + ","
                + bookId +")";
        return query;
    }

    public String addToCreditCard(int userId, String creditNumber) {
        String query = "INSERT INTO CREDIT_CARD VALUES ("
                +userId + ","
                +"\"" + creditNumber +"\")";
        return query;
    }

    public String checkCreditNumber(int userId, String creditNumber) {
        String query = "SELECT * FROM CREDIT_CARD WHERE CREDIT_CARD.user_id = "
                + userId +" AND CREDIT_CARD.credit_number = \"" + creditNumber + "\"";
        return query;
    }

    public String getTotalPriceInCart(int user_id) {
        String query = "select sum(CART.quantity*BOOK.selling_price) total " +
                "from CART, BOOK where CART.book_id = BOOK.book_id AND CART.user_id = " + user_id;
        return query;
    }

}
