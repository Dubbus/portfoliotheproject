import java.awt.print.Book;

import components.map.Map;
import components.map.Map1L;

public class BookTracker {

    public record BookTracker(String title, String author, String genre, int pages){}

    private final Map<String, Book> books;

    //Constructor Method
    public BookTracker(){
            this.books = new Map1L<>();
    }

    //add a book
    public void addBook(Book book){
        this.books.add(book.title(), book);
    }

    //remove a book
    public void removeBook(Book book){
        this.books.remove(this.books.title(), book);
    }
}

// Main method for testing
public static void main(String[] args) {
    BookTracker tracker = new BookTracker();

    // Add a book to the tracker
    tracker.addBook(new Book("Dune", "Frank Herbert", "Sci-Fi", 412, false));
    tracker.addBook(new Book("1984", "George Orwell", "Dystopian", 328, false));

}
}