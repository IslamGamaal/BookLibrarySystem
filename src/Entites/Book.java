package Entites;

import java.util.ArrayList;

public class Book {
    public final String BOOKID = "book_id";
    public final String TITLE = "title";
    public final String PUBYEAR = "pub_year";
    public final String SELLINGPRICE = "selling_price";
    public final String CATEGORY = "category";
    public final String QUANTITY = "quantity";
    public final String PUBLISHERID = "publisher_id";
    public final String THRESHOLD = "threshold";

    public final String PUBLISHER_NAME = "publisher_name";
    public final String AUTHOR_NAME = "author_name";

    int bookId;
    String title;
    String pubYear;
    float sellingPrice;
    String category;
    int quantity;
    int publisherId;
    int threshold;
    String PublisherName;
    ArrayList<Integer> authorsIds = null;
    ArrayList<String> authorsNames = null;

    public ArrayList<String> getAuthorsNames() {
        return authorsNames;
    }

    public String getPublisherName() {
        return PublisherName;
    }

    public ArrayList<Integer> getAuthorsIds() {
        return authorsIds;
    }

    public void setAuthorsIds(ArrayList<Integer> authorsIds) {
        this.authorsIds = authorsIds;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }



    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubYear() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear = pubYear;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public void setPublisherName(String publisher_name) {
        this.PublisherName = publisher_name;
    }

    public void addAuthor(String author_name) {
        if (authorsNames == null) {
            authorsNames = new ArrayList<>();
        }
        authorsNames.add(author_name);
    }
}
