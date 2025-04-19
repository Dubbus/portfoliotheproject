import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;

/**
 * Test Plan:
 *
 *   Verify initial state: size() and bookTitles() on an empty recommender.
 *   Test addBook, containsBook, getBook, size, and bookTitles for correct
 * additions.
 *   Test removeBook for removing existing titles and no-ops on non-existent
 * titles.
 *  Test clear(), newInstance(), and transferFrom() for proper behavior
 * and make sure they're O(1)
 *
 */
public class BookRecommenderTest {

    private BookRecommender1 recommender;
    private Book bookA, bookB, bookC, bookD;

    /*
     * I created a instance method to set up the books in the system.
     * This way, we can safely test books in the graph.
     */
    public final void setUp() {
        this.recommender = new BookRecommender1();
        this.bookA = new Book("A", "Alice", "Fiction", 100);
        this.bookB = new Book("B", "Alice", "SciFi", 200);
        this.bookC = new Book("C", "Bob",   "Fiction", 150);
        this.bookD = new Book("D", "Carol", "Poetry", 120);
    }

    @Test
    public void testAddContainsGetSizeAndTitles() {
        assertEquals(0, this.recommender.size());
        this.recommender.addBook(this.bookA);
        assertTrue(this.recommender.containsBook("A"));
        assertEquals(this.bookA, this.recommender.getBook("A"));
        assertEquals(1, this.recommender.size());

        this.recommender.addBook(this.bookB);
        Set<String> expectedTitles = new Set1L<>();
        expectedTitles.add("A");
        expectedTitles.add("B");
        assertEquals(expectedTitles, this.recommender.bookTitles());
    }

    @Test
    public void testRemoveBook() {
        this.recommender.addBook(this.bookA);
        this.recommender.addBook(this.bookB);
        this.recommender.removeBook("A");
        assertFalse(this.recommender.containsBook("A"));
        assertTrue(this.recommender.containsBook("B"));
        // Removing a non-existing title should have no effect
        this.recommender.removeBook("X");
        assertEquals(1, this.recommender.size());
    }

    @Test
    public void testClearAndNewInstanceAndTransferFrom() {
        BookRecommender other = this.recommender.newInstance();
        this.recommender.clear();
        assertEquals(0, this.recommender.size());

        this.recommender.transferFrom(other);
        assertEquals(0, this.recommender.size());

        other.addBook(this.bookC);
        this.recommender.transferFrom(other);
        assertEquals(1, this.recommender.size());
        assertTrue(this.recommender.containsBook("C"));

        this.recommender.clear();
        assertEquals(0, this.recommender.size());
    }
}

