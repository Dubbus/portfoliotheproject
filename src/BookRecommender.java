

import components.map.Map;
import components.set.Set;

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
     * Returns a set of all book titles in the system.
     *
     * @return a List containing all book titles
     */
    Set<String> bookTitles();


}
