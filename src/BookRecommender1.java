/*book recommender kernel component with primary methods
 * @initially <pre>
 * (): ensures this = {}
 * (Book b): ensures this = #this ∪ {b}
 * (String title): ensures this = #this - {b in #this | b.title = title}
 * </pre>
 */

 import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;


/**
 * {@code BookRecommender1} implements the BookRecommender kernel
 * using a Map from titles to Book objects.
 *
 * @convention
 *   [for every pair (k, v) in rep: k = v.getTitle()
 *    and all keys k are distinct, non‑null, non‑empty strings,
 *    and all values v are non‑null Book objects]
 * @correspondence
 *   this = { v in Book | there exists k such that (k ↦ v) in rep }
 */
public class BookRecommender1 extends BookRecommenderSecondary {




    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of this: a map from each book’s title to the Book object.
     */
    private Map<String, Book> rep;

    /**
     * Helper to create a fresh, empty representation.
     */
    private void createNewRep() {
        this.rep = new Map1L<>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No‑argument constructor.
     */
    public BookRecommender1() {
        this.createNewRep();
    }

    /**
     * Adds a book to the recommender system.
     *
     * @param b the book to be added
     * @requires (The parameter b must not be null.)
     * @updates this
     * @ensures (After this method call, the system's state includes all
     *  previously added books plus the new book b.)
     */
    @Override
    public final void addBook(Book b) {
        this.rep.add(b.title(), b);
    }


    /**
     * Removes a book from the recommender system.
     *
     * @param title the title of the book user wants to remove
     * @requires (The parameter title must not be null.)
     * @updates this
     * @ensures (After this method call, the system's state contains all the
     * previous books except those whose title equals the provided title.)
     */
    @Override
    public final void removeBook(String title) {
        if (this.rep.hasKey(title)) {
            this.rep.remove(title);
        }
    }

    /**
     * Retrieves the book with the given title.
     *
     * @param title the title of the book to retrieve
     * @requires (The parameter title must not be null.)
     * @return the book with the given title, or null if not found
     * @ensures (Returns a book whose title matches the given title if it
     * exists; otherwise, returns null. The system's state remains unchanged.)
     */
    @Override
    public Book getBook(String title){
        return this.rep.value(title);
    }

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
    @Override
    public boolean containsBook(String title){
        return this.rep.hasKey(title);
    }

    /**
     * Gives the amount of books in the system.
     *
     * @return the number of books
     * @requires (There are no special preconditions; the system should have
     *  been initialized.)
     * @ensures (Returns the total count of books in the system. The system's
     * state remains unchanged.)
     */
    @Override
    public int size() {
        return this.rep.size();
    }


     /**
     * Returns a set of all book titles in the system.
     *
     * @return a List containing all book titles
     */
    @Override
    public Set<String> bookTitles(){
        Set<String> titles = new Set1L<>();
        for (Map.Pair<String, Book> p : this.rep) {
            titles.add(p.key());
        }
        return titles;
    }


    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final BookRecommender newInstance() {
        return new BookRecommender1();
    }

    @Override
    public final void transferFrom(BookRecommender source) {
    BookRecommender1 local = (BookRecommender1) source;

    // O(1) swap of the internal map references
    Map<String,Book> temp = this.rep;
    this.rep       = local.rep;

    // leave source empty
    local.rep = temp;
    local.createNewRep();


}

