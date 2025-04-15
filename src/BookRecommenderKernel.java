/*book recommender kernel component with primary methods
 * @initially <pre>
 * (): ensures this = {}
 * (Book b): ensures this = #this ∪ {b}
 * (String title): ensures this = #this - {b in #this | b.title = title}
 * </pre>
 */

import components.set.Set;
import components.standard.Standard;


/**
 * book recommender kernel component with primary methods.
 *
 * @initially <pre>
 *  ():
 *   ensures this = {}
 *  (book b):
 *   ensures this = #this ∪ {b}
 *  (string title):
 *   ensures this = #this - {b in #this | b.title = title}
 * </pre>
 */
public interface BookRecommenderKernel extends Standard<BookRecommender> {

   /**
     * Adds a book to the recommender system.
     *
     * @param b the book to be added
     * @requires (The parameter b must not be null.)
     * @updates this
     * @ensures (After this method call, the system's state includes all
     *  previously added books plus the new book b.)
     */
    void addBook(Book b);

    /**
     * Removes a book from the recommender system.
     *
     * @param title the title of the book user wants to remove
     * @requires (The parameter title must not be null.)
     * @updates this
     * @ensures (After this method call, the system's state contains all the
     * previous books except those whose title equals the provided title.)
     */
    void removeBook(String title);

    /**
     * Retrieves the book with the given title.
     *
     * @param title the title of the book to retrieve
     * @requires (The parameter title must not be null.)
     * @return the book with the given title, or null if not found
     * @ensures (Returns a book whose title matches the given title if it
     * exists; otherwise, returns null. The system's state remains unchanged.)
     */
    Book getBook(String title);

    /**
     * Checks whether a book with the given title exists.
     *
     * @param title the title to check
     * @requires (The parameter title must not be null.)
     * @return true if a book with the title exists, false otherwise
     * @ensures (Returns true if there is at least one book in the system with
     * the given title; returns false otherwise. The system's state remains
     *  unchanged.)
     */
    boolean containsBook(String title);

    /**
     * Gives the amount of books in the system.
     *
     * @return the number of books
     * @requires (There are no special preconditions; the system should have
     *  been initialized.)
     * @ensures (Returns the total count of books in the system. The system's
     * state remains unchanged.)
     */
    int size();


     /**
     * Returns a set of all book titles in the system.
     *
     * @return a List containing all book titles
     */
    Set<String> bookTitles();

}
