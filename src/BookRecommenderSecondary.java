import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;

public abstract class BookRecommenderSecondary implements BookRecommender{

     /**
     * Performs a breadth-first search (BFS) from the source book, identifying
     * all reachable books and computing their “distance” in the recommendation
     * graph (where adjacency is defined by the kernel's {@code areAdjacent}
     * method). It then returns a {@code Map<String, Book>} of all books
     * at the minimal nonzero distance from the source.
     *
     * @param sourceTitle the title of the source book; must not be {@code null}
     * @return a map of recommended books keyed by their title; returns an empty map
     *         if the source does not exist in the system
     * @throws IllegalArgumentException if {@code sourceTitle} is {@code null}
     */
    public Map<String, Book> getRecommendations(String sourceTitle) {

        // Single result to return at the end (no multiple returns).
        Map<String, Book> recommendations = new Map1L<>();

        //check if book with sourceTitle exists.
        if (containsBook(sourceTitle)) {

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
                    Book currentBook = getBook(currentTitle);
                    int currentDist = distances.value(currentTitle);

                    //iterate through all titles to check for adjacency
                    for (String candidateTitle : allTitles()) {
                        //only enqueue if two books are next to each other
                        if (!distances.hasKey(candidateTitle) && containsBook(candidateTitle)) {
                            Book candidateBook = getBook(candidateTitle);
                            if (areAdjacent(currentBook, candidateBook)) {
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
                    recommendations.add(titleKey, getBook(titleKey));
                }
                temp2.add(titleKey, dist);
            }
            distances.transferFrom(temp2);
        }

        return recommendations;
    }
}