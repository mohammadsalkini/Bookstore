package fhtw.bsa1.projects.bookstore;

import javax.json.JsonObject;
/**
 * to open a new SupClass(Magazine) ,that extends from the SuperClass(Publication)
 */
public class Magazine extends Publication{
    /**
     * to define the instances Variables(Attributes) in MagazineClass
     */
    private String description;
    /**
     * to define Magazine Constructor, and send the Object to the superClass Publication
     * @param object
     */
    public Magazine(JsonObject object) {
        super(object);

        description = object.asJsonObject().getJsonObject("volumeInfo").getString("description");

    }

    /**
     * to get the description value
     * @return
     */
    public String getDescription () {
        return description;
    }
    /**
     *the MagazineClass must do Override for all functions/method,that Inherits
     *from the Publication class because it's abstract.
     * @return
     */
    @Override
    public String toString() {
        return "Magazine: " + super.toString() + '\'' +
                "description='" + description + '\'';
    }


}
