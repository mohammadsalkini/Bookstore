package fhtw.bsa1.projects.bookstore;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

/**
 * to open a new SupClass(Book) ,that extends from the SuperClass(Publication)
 */
public class Book extends Publication {

    /**
     * to define the instances Variables(Attributes) in BookClass
     */
    private List<String> authors;
    private String publisher;
    private String isbn10;
    private String isbn13;

    /**
     * to define Book Constructor and send the Object to the superClass Publication
     * Book Con. is protected (It can only be accessed from the classes in the same package or from the classes it inherits from)
     *
     * @param object
     */
    protected Book(JsonObject object) {

        super(object);
        if (authors == null) {
            authors = new ArrayList<>();
        }
        JsonArray authorsArray = object.getJsonObject("volumeInfo").getJsonArray("authors");


        if (authorsArray != null && authorsArray.size() != 0) {
            for (JsonValue author : authorsArray) {
                authors.add(author.toString());

            }
        }
        try {
            publisher = object.getJsonObject("volumeInfo").getString("publisher");
        } catch (Exception e) {
            publisher = "";
        }

        JsonArray industryIdentifiers = object.getJsonObject("volumeInfo").getJsonArray("industryIdentifiers");

        if (industryIdentifiers.size() == 2) {
            String type1 = industryIdentifiers.get(0).asJsonObject().getString("type");

            if (type1.equals("ISBN_10")) {
                isbn10 = industryIdentifiers.get(0).asJsonObject().getString("identifier");
            } else {
                isbn13 = industryIdentifiers.get(0).asJsonObject().getString("identifier");
            }

            String type2 = industryIdentifiers.get(1).asJsonObject().getString("type");

            if (type2.equals("ISBN_10")) {
                isbn10 = industryIdentifiers.get(1).asJsonObject().getString("identifier");
            } else {
                isbn13 = industryIdentifiers.get(1).asJsonObject().getString("identifier");
            }

        } else {
            String type1 = industryIdentifiers.get(0).asJsonObject().getString("type");
            if (type1.equals("ISBN_10")) {
                isbn10 = industryIdentifiers.get(0).asJsonObject().getString("identifier");
            } else {
                isbn13 = industryIdentifiers.get(0).asJsonObject().getString("identifier");
            }
        }

    }

    /**
     * to get the author value
     *
     * @return
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * to get the publisher value
     *
     * @return
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * to get the isbn10 value
     *
     * @return
     */
    public String getIsbn10() {
        return isbn10;
    }

    /**
     * to get the isbn13 value
     *
     * @return
     */
    public String getIsbn13() {
        return isbn13;
    }

    /**
     * A subclass that inherits from a class of its abstract type,
     * is forced to do Override for functions defined as abstract
     *
     * functions/method,that Inherits from the Publication class because it's abstract.
     *
     * toString: String returns all of the Scanner object information.
     * @return
     */
    @Override
    public String toString() {
        return "Book: " + super.toString() +
                "authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'';
    }

}
