import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;


/**
 * Abstract secondary layer for the BookRecommender component.
 *
 * This class implements the user‑facing recommendation algorithm
 * (via breadth‑first search over the “book graph”) using only the
 * kernel operations declared in {@link BookRecommender}.  It assumes
 * the kernel supplies the methods:
 *
 *   {@code containsBook(String)} – to check for existence<
 *   {@code getBook(String)} – to retrieve a Book by title
 *   {@code areAdjacent(Book,Book)} – to test similarity
 *   {@code bookTitles()} – to enumerate all stored titles
 *
 *
 * <p>The BFS computes a “distance” (number of adjacency‑steps) from the
 * source book to every other book, then returns all books at the minimal
 * non‑zero distance as recommendations.
 */
public abstract class BookRecommenderSecondary implements BookRecommender {


   /**
 * Provides recommendations for the given book title by performing
 * a breadth‑first search over the “adjacency graph” of books.
 */
@Override
public Map<String, Book> getRecommendations(String sourceTitle) {
    assert sourceTitle != null : "Violation of: sourceTitle is not null";
    Map<String, Book> recommendations = new Map1L<>();
    if (!this.containsBook(sourceTitle)) {
        return recommendations;
    }
    // BFS setup
    Map<String, Integer> distance = new Map1L<>();
    Queue<String> toVisit = new Queue1L<>();
    distance.add(sourceTitle, 0);
    toVisit.enqueue(sourceTitle);

    // 1) BFS: label distances
    while (toVisit.length() > 0) {
        String current = toVisit.dequeue();
        int currDist = distance.value(current);
        Book currentBook = this.getBook(current);
        // iterate over all titles
        for (String candidate : this.bookTitles()) {
            if (!distance.hasKey(candidate)) {
                Book candidateBook = this.getBook(candidate);
                if (this.areAdjacent(currentBook, candidateBook)) {
                    distance.add(candidate, currDist + 1);
                    toVisit.enqueue(candidate);
                }
            }
        }
    }

    // 2) find minimal non‑zero distance
    int minDist = Integer.MAX_VALUE;
    for (Pair<String, Integer> p : distance) {
        String title = p.key();
        int d        = p.value();
        if (!title.equals(sourceTitle) && d < minDist) {
            minDist = d;
        }
    }

    // 3) collect all at that distance
    if (minDist < Integer.MAX_VALUE) {
        for (Pair<String, Integer> p : distance) {
            if (!p.key().equals(sourceTitle) && p.value() == minDist) {
                recommendations.add(p.key(), this.getBook(p.key()));
            }
        }
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


     /**
   * Determines whether two books are adjacent in the recommendation graph.
   *
   * Two books are considered adjacent if they share the same author or
   * the same genre.
   *
   * @param firstBook  the first book; must not be null
   * @param secondBook the second book; must not be null
   * @return true if the books are adjacent, false otherwise
   */
  @Override
public final boolean areAdjacent(Book firstBook, Book secondBook) {
    assert firstBook != null : "firstBook is not null";
    assert secondBook != null : "secondBook is not null";
    return firstBook.author().equals(secondBook.author())   // same author
        || firstBook.genre().equals(secondBook.genre());    // or same genre
}


}