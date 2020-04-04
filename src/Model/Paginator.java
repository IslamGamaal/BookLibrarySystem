package Model;

public class Paginator {
    private final int limit = 50;
    private int pageNumber;

    Paginator(){
        pageNumber = 0;
    }

    public int getLimit() {
        return limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getNextPage(){
        return ++pageNumber;
    }

    public int getPreviousPage() {
        if (pageNumber == 0)
            return pageNumber;
        return --pageNumber;
    }
}
