package fhtw.bsa1.projects.bookstore;

import javax.json.JsonObject;

public abstract class  Publication {

    private String id;
    private String title;
    private String publishedDate;
    private int pageCount;
    private String maturityRating;
    private String language;
    private String selfLink;
    private String thumbnailLink;
    private String previewLink;



    public Publication(JsonObject object) {

        id = object.getString("id");
        title = object.asJsonObject().getJsonObject("volumeInfo").getString("title");
        publishedDate = object.asJsonObject().getJsonObject("volumeInfo").getString("publishedDate");
        pageCount = object.asJsonObject().getJsonObject("volumeInfo").getInt("pageCount");
        maturityRating = object.asJsonObject().getJsonObject("volumeInfo").getString("maturityRating");
        language = object.asJsonObject().getJsonObject("volumeInfo").getString("language");
        selfLink = object.getString("selfLink");
        String printType  = object.asJsonObject().getJsonObject("volumeInfo").getString("printType");
        if (printType.equals("BOOK"))
        thumbnailLink = object.asJsonObject().getJsonObject("volumeInfo").getJsonObject("imageLinks").getString("thumbnail");
        previewLink = maturityRating = object.asJsonObject().getJsonObject("volumeInfo").getString("previewLink");
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public String getLanguage() {
        return language;
    }

    public String getLink() {
        return selfLink;
    }

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
