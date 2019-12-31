package fhtw.bsa1.projects.bookstore;

import javax.json.JsonObject;

public class Magazine extends Publication{

    private String description;

    public Magazine(JsonObject object) {
        super(object);

        description = object.asJsonObject().getJsonObject("volumeInfo").getString("description");

    }

    public String getDescription () {
        return description;
    }

    @Override
    public String toString() {
        return "Magazine: " + super.toString() +
                "description='" + description + '\'';
    }


}
