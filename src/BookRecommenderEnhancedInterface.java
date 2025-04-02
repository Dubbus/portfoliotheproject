

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
     * assigns a weight to the edge between two books in the recommendation graph.
     * lower weights indicate a stronger relationship. (no clue if this is how i'm supposed to do this)
     * @param title1 the title of the first book
     * @param title2 the title of the second book
     * @param weight the weight to assign (must be non-negative)
     * @updates the recommendation graph
     * @ensures the weight is correct
     */
    void assignEdgeWeight(String title1, String title2, double weight);

    /**
     * gets the weight assigned to the edge between two books.
     * @param title1 the title of the first book
     * @param title2 the title of the second book
     * @return the weight of the edge, or a default value if no weight is assigned
     */
    double getEdgeWeight(String title1, String title2);

    /**
     * computes the shortest path between two books using dijkstra's algorithm.
     * @param startTitle the title of the starting book
     * @param endTitle the title of the destination book
     * @return a map representing the shortest path, where keys denote the order in the path
     */
    Map<Integer, Book> getShortestPath(String startTitle, String endTitle);
}
