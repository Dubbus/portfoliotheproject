import components.map.Map;
import components.map.Map1L;

public class proofofconcept {

    // Inner class for BookTracker
    public static class BookTracker {

        public static record Book(String title, String author, String genre,
                int pages) {
        }

        private final Map<String, Book> books;

        // Constructor
        public BookTracker() {
            this.books = new Map1L<>();
        }

        // Add a book
        public final void addBook(Book book) {
            this.books.add(book.title(), book);
        }

        // Remove a book by title
        public final void removeBook(String title) {
            this.books.remove(title);
        }

        // Print all books
        public final void printBooks() {
            for (String title : this.books.keySet()) {
                System.out.println(this.books.value(title));
            }
        }
    }

    public static void main(String[] args) {
        BookTracker tracker = new BookTracker();

        tracker.addBook(
                new BookTracker.Book("Dune", "Frank Herbert", "Sci-Fi", 412));
        tracker.addBook(new BookTracker.Book("1984", "George Orwell",
                "Dystopian", 328));

        // Print all books
        System.out.println("All Books:");
        tracker.printBooks();
    }
}
