import components.map.Map;
import components.map.Map.Pair;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Simple console demo showing how to use BookRecommender1 to store a catalog of
 * books and get recommendations based on user input.
 */
public class LibraryDemo {

    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        BookRecommender1 recommender = new BookRecommender1();

        // Build our catalog
        recommender.addBook(new Book("Fellowship Of The Ring", "JRR Tolkien",
                "Fantasy", 412));
        recommender.addBook(new Book("Hobbit", "JRR Tolkien", "Fantasy", 256));
        recommender.addBook(new Book("Hyperion", "Dan Simmons", "SciFi", 482));
        recommender.addBook(new Book("Iliad", "Homer", "Epic", 704));
        recommender.addBook(new Book("Odyssey", "Homer", "Epic", 560));

        // Prompt the user for a book title
        System.out.print("Enter a book title for recommendations: ");
        String query = in.nextLine();

        System.out.println("\nRecommendations for \"" + query + "\":");
        Map<String, Book> recs = recommender.getRecommendations(query);
        if (recs.size() == 0) {
            System.out.println("  (no recommendations found)");
        } else {
            // Iterate over Map.Pair<String,Book>
            for (Pair<String, Book> entry : recs) {
                Book book = entry.value();
                System.out.println("  • " + book);
            }
        }

        in.close();
    }

}
