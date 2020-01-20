package fhtw.bsa1.projects.bookstore;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * to open a new SupClass(GoogleBooksAPI)
 */
public class GoogleBooksAPI {
    /**
     * define a new final object form string type /API URL
     * static is shared to all objects of the same class
     *
     * @ENDPOINT
     */
    public static final String ENDPOINT = "https://www.googleapis.com/books/v1/volumes?";

    /** define the instance variables */
    private String query;
    private String author;

    /**
     * in the first constructor, make one choice just with query(with 2 parameters)
     * @param query
     */
    public GoogleBooksAPI(String query) {
        this.query = query.replaceAll(" ", "");
    }

    /**
     * in the second constructor,  make two choices with query and author(with 2 parameters)
     * @param query
     * @param author
     */
    public GoogleBooksAPI(String query, String author) {
        this.query = query.replaceAll(" ", "");
        ;
        this.author = author.replaceAll(" ", "");
        ;
    }

    /**
     * to get query value
     * @return
     */
    public String getQuery() {
        return query;
    }

    /**
     * to get author value
     * @return
     */
    public String getAuthor() {
        return author;
    }


    /**
     *
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public Set<Magazine> getMagazines() throws MalformedURLException, IOException {

        /**
         * by hashSet(Used to store consecutive elements, without regard to the arrangement of these elements
         * , allows null storage. a list but without duplication)
         */

        String line = "";
        Set<Magazine> magazines = new HashSet<>();
        URL magazineUrl;

        /**
         *when we have author & query ,then we search in the ENDPOINT URL for them
         * else it's just search for query
         */
        if (author != null && !author.equals("")) {
            magazineUrl = new URL(ENDPOINT + "q=" + query + "+inauthor:" + author + "&printType=magazines");
        } else {
            magazineUrl = new URL(ENDPOINT + "q=" + query + "&printType=magazines");
        }


        HttpURLConnection connection;

        try {
            /**
             * to open the connection for Magazine URL ,and bring the info.
             */
            connection = (HttpURLConnection) magazineUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            /**
             *hasNext to move to another word
             * put the Data in String variable
             */
            Scanner sc = new Scanner(magazineUrl.openStream());
            while (sc.hasNext()) {
                line += sc.nextLine();
            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //convert string variable to json object
        /**
         * convert string variable to json object
         */
        JsonReader reader = Json.createReader(new StringReader(line));

        JsonObject object = reader.readObject();

        JsonArray magazinesArray = object.getJsonArray("items");

        if (magazinesArray != null && magazinesArray.size() != 0) {
            for (JsonValue magazine : magazinesArray) {
                magazines.add(new Magazine(magazine.asJsonObject()));

            }
        }

        return magazines;
    }

    public Set<Book> getBooks() throws MalformedURLException, IOException {

        URL bookUrl = null;

        String line = "";
        Set<Book> books = new HashSet<>();

        try {
            if (author != null && !author.equals("")) {
                bookUrl = new URL(ENDPOINT + "q=" + query + "+inauthor:" + author + "&printType=books");
            } else {
                bookUrl = new URL(ENDPOINT + "q=" + query + "&printType=books");
            }

        } catch (MalformedURLException e) {
            System.out.println("Unable to get data from URL");
        }


        HttpURLConnection connection = (HttpURLConnection) bookUrl.openConnection();
        connection.setRequestMethod("GET");

        connection.connect();
        Scanner sc = new Scanner(bookUrl.openStream());
        line = "";
        while (sc.hasNext()) {
            line += sc.nextLine();
        }
        sc.close();

        JsonReader reader = Json.createReader(new StringReader(line));

        JsonObject object = reader.readObject();

        JsonArray booksArray = object.getJsonArray("items");

        if (booksArray != null && booksArray.size() != 0) {
            for (JsonValue book : booksArray) {
                books.add(new Book(book.asJsonObject()));

            }
        }


        return books;
    }


}
