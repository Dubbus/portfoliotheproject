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