

import components.map.Map;

/**
 * book recommender enhanced interface that provides secondary methods.
 * extends the book recommender kernel with some enhanced methods that introduce
 * the graphs that i'll be using to recommend books.
 */
public interface BookRecommender extends BookRecommenderKernel {

    /**
     * gets recommendations for a given book title.
     * @param title the title of the book to base recommendations on
     * @return a map containing recommended books keyed by their title
     */
    Map<String, Book> getRecommendations(String title);

    /**
    * Determines whether two books are adjacent in the recommendation graph.
    *
    * Two books are considered adjacent if they share the same author or
    * the same genre.
    *
    *
    * @param firstBook  the first book; assumed to be non-null
    * @param secondBook the second book; assumed to be non-null
    * @return {@code true} if the books are adjacent, {@code false} otherwise
    */
    boolean areAdjacent(Book firstBook, Book secondBook);

}




