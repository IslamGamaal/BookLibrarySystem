package Model;

import Entites.Book;
import Entites.Order;
import Entites.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ModelTest {
    Model model;

    @Before
    public void before() {
        try {
            model = new Model();
        } catch (SQLException e) {
            Assert.assertTrue(false);
        } catch (ClassNotFoundException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void register() {
        User dummyUser = new User();
        dummyUser.setEmailAddress("test@test.com");
        dummyUser.setPassword("1234");
        dummyUser.setFirstName("Tester");
        dummyUser.setLastName("Islam");
        dummyUser.setPhoneNumber("010-10101001");
        dummyUser.setShippingAddress("Any address");

        Assert.assertTrue(model.register(dummyUser));
        model.deleteUser(dummyUser);
    }

    @Test
    public void registerNoEmail() {
        User dummyUser = new User();
        dummyUser.setEmailAddress("");
        dummyUser.setPassword("1234");
        dummyUser.setFirstName("Tester");
        dummyUser.setLastName("Islam");
        dummyUser.setPhoneNumber("010-10101001");
        dummyUser.setShippingAddress("Any address");

        Assert.assertFalse(model.register(dummyUser));
    }

    @Test
    public void registerNoPassword() {
        User dummyUser = new User();
        dummyUser.setEmailAddress("tester@tester.com");
        dummyUser.setPassword("");
        dummyUser.setFirstName("Tester");
        dummyUser.setLastName("Islam");
        dummyUser.setPhoneNumber("010-10101001");
        dummyUser.setShippingAddress("Any address");

        Assert.assertFalse(model.register(dummyUser));
    }

    @Test
    public void registerNoPersonalData() {
        User dummyUser = new User();
        dummyUser.setEmailAddress("tester@tester.com");
        dummyUser.setPassword("1234");

        Assert.assertTrue(model.register(dummyUser));
        model.deleteUser(dummyUser);
    }

    @Test
    public void registerDuplicateEmail() {
        User dummyUser1 = new User();
        dummyUser1.setEmailAddress("tester@tester.com");
        dummyUser1.setPassword("1234");
        model.register(dummyUser1);

        User dummyUser2 = new User();
        dummyUser2.setEmailAddress("tester@tester.com");
        dummyUser2.setPassword("123456");

        Assert.assertFalse(model.register(dummyUser2));
        model.deleteUser(dummyUser1);
    }


    @Test
    public void logIn() {
        //I manually Added islam@testing with Password: 1234 into the database
        User actualUser = model.logIn("islam@testing", "1234");

        Assert.assertEquals("islam@testing", actualUser.getEmailAddress());
        Assert.assertEquals("1234", actualUser.getPassword());
        Assert.assertEquals("Islam", actualUser.getFirstName());
        Assert.assertEquals("Gamal", actualUser.getLastName());
        Assert.assertEquals("Miami", actualUser.getShippingAddress());
        Assert.assertEquals("010-010-010", actualUser.getPhoneNumber());
    }

    @Test
    public void logInWrongPassword() {
        //I manually Added islam@testing with Password: 1234 into the database
        User actualUser = model.logIn("islam@testing", "XXXXXX");

        Assert.assertNull(actualUser);
    }

    @Test
    public void logInWrongEmail() {
        //I manually Added islam@testing with Password: 1234 into the database
        User actualUser = model.logIn("XXXXXXXX", "1234");

        Assert.assertNull(actualUser);
    }

    @Test
    public void updateUser() {
        //I manually Added islam@testing with Password: 1234 into the database
        User updatedUser = new User();
        updatedUser.setEmailAddress("islam@testing");
        updatedUser.setPassword("1234");
        updatedUser.setFirstName("Islam");
        updatedUser.setLastName("Gamal");
        updatedUser.setPhoneNumber("010-010-010");
        updatedUser.setShippingAddress("Miami");

        Assert.assertTrue(model.updateUser(updatedUser));
    }

    @Test
    public void updateUserNoMatchesFound() {
        //I manually Added islam@testing with Password: 1234 into the database
        User updatedUser = new User();
        updatedUser.setEmailAddress("xxxxxxxxxxxx");
        updatedUser.setPassword("1234");
        updatedUser.setFirstName("New Islam");
        updatedUser.setLastName("New Gamal");
        updatedUser.setPhoneNumber("010-010-010");
        updatedUser.setShippingAddress("Miami");

        Assert.assertFalse(model.updateUser(updatedUser));
    }

    @Test
    public void getStartBooks() {
        Assert.assertNotNull(model.getStartBooks());
    }

    @Test
    public void getBookById() {
        Book book = model.getBookById(13);

        Assert.assertEquals("La casa de papel", book.getTitle());
    }

    @Test
    public void getBookByIdNotFound() {
        Book book = model.getBookById(13000);

        Assert.assertNull(book);
    }

    @Test
    public void getBookByTitle() {
        Book book = model.getBookByTitle("La casa de papel");

        Assert.assertEquals(13, book.getBookId());
    }

    @Test
    public void getBookByTitleNotFound() {
        Book book = model.getBookByTitle("XXXX");

        Assert.assertNull(book);
    }

    @Test
    public void addBook() {
        Book book = new Book();
        book.setBookId(10021);
        book.setCategory("Art");
        book.setPublisherId(1);
        book.setPubYear("2020-7-23");
        book.setQuantity(14);
        book.setThreshold(10);
        book.setTitle("How to test perfectly");
        book.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));
        book.setSellingPrice(1000);

        Assert.assertTrue(model.addBook(book));
        model.deleteBook(book);
    }

    @Test
    public void addBookNoID() {
        Book book = new Book();
        book.setCategory("Art");
        book.setPublisherId(1);
        book.setPubYear("2020-7-23");
        book.setQuantity(14);
        book.setThreshold(10);
        book.setTitle("How to test perfectly");
        book.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));

        Assert.assertFalse(model.addBook(book));
    }

    @Test
    public void addBookNoAuthors() {
        Book book = new Book();
        book.setBookId(10021);
        book.setCategory("Art");
        book.setPublisherId(1);
        book.setPubYear("2020-7-23");
        book.setQuantity(14);
        book.setThreshold(10);
        book.setTitle("How to test perfectly");

        Assert.assertFalse(model.addBook(book));
    }

    @Test
    public void addBookNoQuantityNoPriceNoThreshold() {
        Book book = new Book();
        book.setBookId(10021);
        book.setCategory("Art");
        book.setPublisherId(1);
        book.setPubYear("2020-7-23");
        book.setThreshold(10);
        book.setTitle("How to test perfectly");
        book.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));
        //default quantity, Price, threshold are zeros
        Assert.assertTrue(model.addBook(book));
        model.deleteBook(book);
    }

    @Test
    public void addBookDuplicate() {
        Book book1 = new Book();
        book1.setBookId(10021);
        book1.setCategory("Art");
        book1.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));
        model.addBook(book1);

        Book book2 = new Book();
        book2.setBookId(10021);
        book2.setCategory("Art");
        book2.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));

        Assert.assertFalse(model.addBook(book2));
        model.deleteBook(book1);
    }

    @Test
    public void modifyBook() {
        Book book = new Book();
        book.setBookId(100212);
        book.setCategory("Art");
        book.setPublisherId(1);
        book.setPubYear("2020-7-23");
        book.setQuantity(14);
        book.setThreshold(10);
        book.setTitle("How to test");
        book.setAuthorsIds(new ArrayList<>(Arrays.asList(1,2,3)));
        book.setSellingPrice(1000);
        Assert.assertTrue(model.addBook(book));
        model.deleteBook(book);
    }

    @Test
    public void getAllOrders() {
        Assert.assertNotNull(model.getAllOrders());
    }

    @Test
    public void placeOrder() {
        Assert.assertTrue(model.placeOrder(13, 5));
        model.confirmOrder(8);
    }

    @Test
    public void confirmOrder() {
    }
}
