package Entites;

public class CartElement {
    public final String BOOKID = "book_id";
    public final String USERID = "user_id";
    public final String QUANTITY = "quantity";
    int bookId;
    int userId;
    int quantity;
    boolean inCart;

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }



    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
