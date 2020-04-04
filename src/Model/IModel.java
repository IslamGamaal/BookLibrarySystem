package Model;

import Entites.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface IModel {

    boolean register(User user);
    User logIn(String name, String password);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    boolean deleteBook(Book book);
    ArrayList<Book> getStartBooks();
    int getNumberOfPagesOfBooks(HashMap<String, String> searchMap);
    ArrayList<Book> getBooksByPage(int pageNumber, int limit, HashMap<String, String> searchMap);
    boolean addToCart(int bookId, int quantity, int userId);
    boolean removeFromCart(int bookId, int userId);
    boolean emptyCart(int userId);
    ArrayList<CartElement> getCart(int userId);
    boolean checkout(int userId);
    Book getBookById(int bookId);
    Book getBookByTitle(String title);
    int getNumberOfPagesOfUsers();
    ArrayList<User> getUsersByPage(int pageNumber, int limit);
    boolean addCreditCard(int userId, String creditNumber);
    boolean checkCreditCard(int userId, String creditNumber);

    //manager functions
    ArrayList<User> getAllUsers();
    boolean promote(int userId);
    boolean addBook(Book book);
    boolean modifyBook(Book newBook);
    ArrayList<Order> getAllOrders();
    boolean placeOrder(int bookId, int quantity);
    boolean confirmOrder(int OrderId);
    int getNumberOfPagesOfPublisher();
    ArrayList<Publisher> getPublishersByPage(int pageNumber, int limit);
    boolean addPublisher(Publisher publisher);
    int getNumberOfPagesOfAuthors();
    ArrayList<Author> getAuthorsByPage(int pageNumber, int limit);
    boolean addAuthor(Author author);

    // functions that return reports


}
