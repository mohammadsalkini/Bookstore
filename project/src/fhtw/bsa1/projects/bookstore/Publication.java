package fhtw.bsa1.projects.bookstore;

import javax.json.JsonObject;

/**
 * to open a new SuperClass Publication ,that defined as abstract Class
 */

public abstract class Publication {
    /**
     * to define the instances Variables(Attributes) in PublicationClass
     */
    private String id;
    private String title;
    private String publishedDate;
    private int pageCount;
    private String maturityRating;
    private String language;
    private String selfLink;
    private String thumbnailLink;
    private String previewLink;


    /**
     * to define Book Constructor Publication
     * to bring the data from JasonObject and put them in a object
     *
     * @param object
     */

    public Publication(JsonObject object) {
/**
 *to use all the information ,it's important to write a link for the place for each one.
 */

        id = object.getString("id");
        title = object.asJsonObject().getJsonObject("volumeInfo").getString("title");
        publishedDate = object.asJsonObject().getJsonObject("volumeInfo").getString("publishedDate");
        pageCount = object.asJsonObject().getJsonObject("volumeInfo").getInt("pageCount");
        maturityRating = object.asJsonObject().getJsonObject("volumeInfo").getString("maturityRating");
        language = object.asJsonObject().getJsonObject("volumeInfo").getString("language");
        selfLink = object.getString("selfLink");
        String printType = object.asJsonObject().getJsonObject("volumeInfo").getString("printType");
        if (printType.equals("BOOK"))
            thumbnailLink = object.asJsonObject().getJsonObject("volumeInfo").getJsonObject("imageLinks").getString("thumbnail");
        previewLink = maturityRating = object.asJsonObject().getJsonObject("volumeInfo").getString("previewLink");
    }

    /**
     * to get id value
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * to ge Title value
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * to get PublishedData value
     *
     * @return
     */
    public String getPublishedDate() {
        return publishedDate;
    }

    /**
     * to get PageCount value
     *
     * @return
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * to get MaturityRating
     *
     * @return
     */
    public String getMaturityRating() {
        return maturityRating;
    }

    /**
     * to get Language value
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    /**
     * to get Link value
     *
     * @return
     */
    public String getLink() {
        return thumbnailLink;
    }

    /**
     * A subclass that inherits from a class of its abstract type, is forced to do Override
     * for functions defined as abstract.
     *
     * the use of override is to make the other SupClasses see this instances variables
     * @return
     */
    //n
    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", pageCount=" + pageCount +
                ", maturityRating='" + maturityRating + '\'' +
                ", language='" + language + '\'' +
                ", selfLink='" + selfLink + '\'' +
                ", thumbnailLink='" + thumbnailLink + '\'' +
                ", previewLink='" + previewLink + '\'';
    }
}
