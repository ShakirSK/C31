package main.master.c31.ArtWork;

public class ArtworkModel {

    private String preschool_id;

    private String artwork_name;

    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getArtwork_name() {
        return artwork_name;
    }

    public void setArtwork_name(String artwork_name) {
        this.artwork_name = artwork_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    private String size;

    private String date;

    private String venue;


    private String content;

    private String description;

    private String created_by;

    public ArtworkModel(String preschool_id, String artwork_name, String size, String date, String venue, String content, String description, String created_by) {
        this.preschool_id = preschool_id;
        this.artwork_name = artwork_name;
        this.size = size;
        this.date = date;
        this.venue = venue;
        this.content = content;
        this.description = description;
        this.created_by = created_by;
    }

}
