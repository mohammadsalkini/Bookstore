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

public class GoogleBooksAPI {

    public static final String ENDPOINT = "https://www.googleapis.com/books/v1/volumes?";

    private String query;
    private String author;

    public GoogleBooksAPI(String query) {
        this.query = query.replaceAll(" ", "");
    }

    public GoogleBooksAPI(String query, String author) {
        this.query = query.replaceAll(" ", "");;
        this.author = author.replaceAll(" ", "");;
    }

    public String getQuery() {
        return query;
    }

    public String getAuthor() {
        return author;
    }


    public Set<Magazine> getMagazines() throws MalformedURLException, IOException {

        String line = "";
        Set<Magazine> magazines = new HashSet<>();
        URL magazineUrl;
        if (author != null) {
            magazineUrl = new URL(ENDPOINT + "q=" + query + "+inauthor:" + author + "&printType=magazines");
        } else {
            magazineUrl = new URL(ENDPOINT + "q=" + query + "&printType=magazines");
        }
        HttpURLConnection connection = (HttpURLConnection) magazineUrl.openConnection();
        connection.setRequestMethod("GET");

        connection.connect();
        Scanner sc;

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            //put the Data in String variable
            sc = new Scanner(magazineUrl.openStream());
            while (sc.hasNext()) {
                line += sc.nextLine();
            }
            sc.close();

            //convert string variable to json object
            JsonReader reader = Json.createReader(new StringReader(line));

            JsonObject object = reader.readObject();


            JsonArray magazinesArray = object.getJsonArray("items");

            if (magazinesArray != null) {
                for (JsonValue magazine : magazinesArray) {
                    magazines.add(new Magazine(magazine.asJsonObject()));

                }
            }
        }
        return magazines;
    }

    public Set<Book> getBooks() throws MalformedURLException, IOException {

        URL bookUrl;
        if (author != null) {
            bookUrl = new URL(ENDPOINT + "q=" + query + "+inauthor:" + author + "&printType=books");
        } else {
            bookUrl = new URL(ENDPOINT + "q=" + query + "&printType=books");
        }


        Set<Book> books = new HashSet<>();

        HttpURLConnection connection = (HttpURLConnection) bookUrl.openConnection();
        connection.setRequestMethod("GET");

        connection.connect();
        Scanner sc;

        int responseCode = connection.getResponseCode();
        String line = "";
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            sc = new Scanner(bookUrl.openStream());
            line = "";
            while (sc.hasNext()) {
                line += sc.nextLine();
            }
            sc.close();

            JsonReader reader = Json.createReader(new StringReader(line));

            JsonObject object = reader.readObject();

            JsonArray booksArray = object.getJsonArray("items");

            if (booksArray != null) {
                for (JsonValue book : booksArray) {
                    books.add(new Book(book.asJsonObject()));

                }
            }
        }

        return books;
    }


}
