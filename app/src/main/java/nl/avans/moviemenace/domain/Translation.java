package nl.avans.moviemenace.domain;

public class Translation {
    private int movieID;
    private String title;
    private String description;
    private String url;
    private String language;

    public Translation(int movieID, String title, String description, String url, String language) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
        this.url = url;
        this.language = language;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
