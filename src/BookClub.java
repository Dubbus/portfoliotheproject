import components.map.Map;
import components.map.Map.Pair;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Represents a simple BookClub that tracks a shared recommender and can suggest
 * “next reads” to members, with a small interactive demo.
 */
public class BookClub {

    String name;
    BookRecommender1 recommender;

    /**
     * Create a BookClub with the given name.
     *
     * @param name
     *            the name of the club; must not be null or empty
     */
    public BookClub(String name) {
        assert name != null
                && !name.isEmpty() : "Violation of: name is not null/empty";
        this.name = name;
        this.recommender = new BookRecommender1();
    }

    /**
     * Adds a book to the club’s shared collection.
     *
     * @param b
     *            the book to add; must not be null
     */
    public void addToCollection(Book b) {
        assert b != null : "Violation of: b is not null";
        this.recommender.addBook(b);
    }

    /**
     * Suggests the next read for a member based on the last book they read.
     *
     * @param lastReadTitle
     *            title of the last book; must not be null
     * @return a set of recommended Book objects (possibly empty)
     */
    public Set<Book> suggestNextBooks(String lastReadTitle) {
        assert lastReadTitle != null : "Violation of: lastReadTitle is not null";
        Map<String, Book> recMap = this.recommender
                .getRecommendations(lastReadTitle);
        Set<Book> recs = new Set1L<>();
        // Iterate over Map.Pair<String,Book> entries
        for (Pair<String, Book> entry : recMap) {
            recs.add(entry.value());
        }
        return recs;
    }

    /**
     * Interactive main showing how a BookClub might be used.
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        BookClub club = new BookClub("Biography Bungalow");
        club.addToCollection(
                new Book("Into The Wild", "Jon Krakauer", "Biography", 271));
        club.addToCollection(new Book("The Years of Lyndon Johnson",
                "Robert Caro", "Biography", 480));
        club.addToCollection(
                new Book("Ulysses", "James Joyce", "Biography", 375));

        String last = "Into The Wild";
        System.out.println("Club \"" + club.name + "\" suggestions after \""
                + last + "\":");
        Set<Book> picks = club.suggestNextBooks(last);
        for (Book b : picks) {
            System.out.println("  -> " + b);
        }
    }

}
