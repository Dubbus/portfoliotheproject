import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;

/**
 * Test Plan:
 *
 * - Verify getRecommendations returns only direct neighbors at distance 1.
 * - Verify getRecommendations returns empty when there are no neighbors.
 * - Verify getRecommendations returns all equidistant neighbors when multiple at minimal distance.
 * - Verify getRecommendations returns empty if source title does not exist in the system.
 * - Verify getRecommendations fails with assertion when sourceTitle is null.
 *
 * - Test areAdjacent for: same author, same genre, no adjacency.
 * - Verify areAdjacent fails with assertion when either argument is null.
 */
public class BookRecommenderTestRunner {

    private final BookRecommender1 rec;
    private final Book A, B, C, D, E;

    /**
     * JUnit creates a new instance of this class before each @Test.
     * By putting our “setUp” logic here in the constructor, we guarantee
     * rec, A–E are non‐null in every test without using any annotations.
     */
    public BookRecommenderTestRunner() {
        this.rec = new BookRecommender1();
        this.A   = new Book("A", "X", "G1", 100);
        this.B   = new Book("B", "X", "G2", 150);
        this.C   = new Book("C", "Y", "G2", 200);
        this.D   = new Book("D", "Z", "G3", 120);
        this.E   = new Book("E", "Y", "G1",  80);
    }

    @Test
    public void testDirectNeighbors() {
        this.rec.addBook(this.A);
        this.rec.addBook(this.B);
        this.rec.addBook(this.C);
        Map<String, Book> r        = this.rec.getRecommendations("A");
        Map<String, Book> expected = new Map1L<>();
        expected.add("B", this.B);
        assertEquals(expected, r);
    }

    @Test
    public void testNoNeighbors() {
        this.rec.addBook(this.D);
        Map<String, Book> r = this.rec.getRecommendations("D");
        assertEquals(0, r.size());
    }

    @Test
    public void testMultipleEquidistant() {
        this.rec.addBook(this.A);
        this.rec.addBook(this.B);
        this.rec.addBook(this.C);
        this.rec.addBook(this.E);
        Map<String, Book> r        = this.rec.getRecommendations("A");
        Map<String, Book> expected = new Map1L<>();
        expected.add("B", this.B);
        expected.add("E", this.E);
        assertEquals(expected, r);
    }


}
