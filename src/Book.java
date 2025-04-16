/**
     * Represents a tracker for a book, containing details such as title,
     * author, genre, and the number of pages.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param genre  the genre of the book
     * @param pages  the number of pages in the book
     */
public record Book(String title, String author, String genre, int pages) {


}
