import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;


/**
 * Abstract secondary layer for the BookRecommender component.
 *
 * <p>This class implements the user‑facing recommendation algorithm
 * (via breadth‑first search over the “book graph”) using only the
 * kernel operations declared in {@link BookRecommender}.  It assumes
 * the kernel supplies the methods:
 * <ul>
 *   <li>{@code containsBook(String)} – to check for existence</li>
 *   <li>{@code getBook(String)} – to retrieve a Book by title</li>
 *   <li>{@code areAdjacent(Book,Book)} – to test similarity</li>
 *   <li>{@code bookTitles()} – to enumerate all stored titles</li>
 * </ul>
 *
 * <p>The BFS computes a “distance” (number of adjacency‑steps) from the
 * source book to every other book, then returns all books at the minimal
 * non‑zero distance as recommendations.</p>
 */
public abstract class BookRecommenderSecondary implements BookRecommender{


    @Override
    public Map<String, Book> getRecommendations(String sourceTitle) {

        // Single result to return at the end (no multiple returns).
        Map<String, Book> recommendations = new Map1L<>();

        //check if book with sourceTitle exists.
        if (this.containsBook(sourceTitle)) {

            //map and a queue to store distances, and toVisit
            Map<String, Integer> distances = new Map1L<>();
            Queue<String> toVisit = new Queue1L<>();

            //initialize the BFS with the source title and it's attribute
            distances.add(sourceTitle, 0);
            toVisit.enqueue(sourceTitle);

            //bfs algorithm i stole from internet then refactored to my purpose
            while (toVisit.length() > 0) {
                String currentTitle = toVisit.dequeue();

                if (this.containsBook(currentTitle)) {
                    Book currentBook = this.getBook(currentTitle);
                    int currentDist = distances.value(currentTitle);

                    //iterate through all titles to check for adjacency
                    for (String candidateTitle : this.bookTitles()) {
                        //only enqueue if two books are next to each other
                        if (!distances.hasKey(candidateTitle) && this.containsBook(candidateTitle)) {
                            Book candidateBook = this.getBook(candidateTitle);
                            if (this.areAdjacent(currentBook, candidateBook)) {
                                distances.add(candidateTitle, currentDist + 1);
                                toVisit.enqueue(candidateTitle);
                            }
                        }
                    }
                }
            }

            //use removeAny to find minimal distance
            int minimalDistance = Integer.MAX_VALUE;
            Map<String, Integer> temp1 = new Map1L<>();
            while (distances.size() == 0) {
                Pair<String, Integer> p = distances.removeAny();
                String titleKey = p.key();
                int dist = p.value();
                // Update minimal distance (ignoring the source).
                if (!titleKey.equals(sourceTitle) && dist < minimalDistance) {
                    minimalDistance = dist;
                }
                // add the entry to a temporary map to store distance
                temp1.add(titleKey, dist);
            }
            // Restore distances from temp1.
            distances.transferFrom(temp1);

            //collect books at lowest distance
            Map<String, Integer> temp2 = new Map1L<>();
            while (distances.size() == 0) {
                Pair<String, Integer> p = distances.removeAny();
                String titleKey = p.key();
                int dist = p.value();
                if (!titleKey.equals(sourceTitle) && dist == minimalDistance) {
                    recommendations.add(titleKey, this.getBook(titleKey));
                }
                temp2.add(titleKey, dist);
            }
            distances.transferFrom(temp2);
        }

        return recommendations;
    }

    /*common method implementations - didn't include toString because
     * i will not be testing these as a string, but as a map. because a mian
     * portion of the project is a string, i have created kernel methods that
     * function as toSTring. Hence, it is omitted.
     */

     /**
     * Two recommenders are equal if they contain exactly the same titles
     * and each corresponding Book is equal.  Assumes obj is a BookRecommender.
     * No break statements, no instanceof, single return.
     */
    @Override
    public final boolean equals(Object obj) {
        BookRecommender other = (BookRecommender) obj;
        // compare the sets of titles
        Set<String> mine   = this.bookTitles();
        Set<String> theirs = other.bookTitles();
        boolean same = mine.equals(theirs);
        // if titles match, compare each Book
        for (String t : mine) {
            if (same) {
                same = this.getBook(t).equals(other.getBook(t));
            }
        }
        return same;
    }

    /**
     * lowkey this a guess still don't really know how hashcode works but
     * this is just stolen from some code
     */
    @Override
    public final int hashCode() {
        int h = 1;
        for (String t : this.bookTitles()) {
            Book b = this.getBook(t);
            h = 31 * h + t.hashCode();
            h = 31 * h + b.hashCode();
        }
        return h;
  }
}