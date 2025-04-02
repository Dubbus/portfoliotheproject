/*book recommender kernel component with primary methods
 * @initially <pre>
 * (): ensures this = {}
 * (Book b): ensures this = #this ∪ {b}
 * (String title): ensures this = #this - {b in #this | b.title = title}
 * </pre>
 */

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

    /*adds a book to the recommender system
     * @param b the book to be added
     * @updates this
     * @ensures this = #this + b
     */
    void addBook(Book b);
}
