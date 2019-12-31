package fhtw.bsa1.projects.bookstore;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class Book extends Publication{

    private List<String> authors;
    private String publisher;
    private String isbn10;
    private String isbn13;

    public Book(JsonObject object) {
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

            publisher = object.getJsonObject("volumeInfo").getString("publisher");
        }

    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    @Override
    public String toString() {
        return "Book: " + super.toString() +
                "authors=" + authors +
                ", published='" + publisher + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'';
    }

}
