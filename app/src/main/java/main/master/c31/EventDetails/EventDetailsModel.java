package main.master.c31.EventDetails;

public class EventDetailsModel {

    private String preschool_id;

    private String event_name;

    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_from_date() {
        return event_from_date;
    }

    public void setEvent_from_date(String event_from_date) {
        this.event_from_date = event_from_date;
    }

    public String getEvent_to_date() {
        return event_to_date;
    }

    public void setEvent_to_date(String event_to_date) {
        this.event_to_date = event_to_date;
    }

    public String getEvent_from_time() {
        return event_from_time;
    }

    public void setEvent_from_time(String event_from_time) {
        this.event_from_time = event_from_time;
    }

    public String getEvent_to_time() {
        return event_to_time;
    }

    public void setEvent_to_time(String event_to_time) {
        this.event_to_time = event_to_time;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    private String event_from_date;

    private String event_to_date;

    private String event_from_time;

    private String event_to_time;

    private String event_venue;

    private String event_description;

    private String created_by;


    public EventDetailsModel(String preschool_id, String event_name, String event_from_date, String event_to_date, String event_from_time, String event_to_time, String event_venue, String event_description, String created_by) {
        this.preschool_id = preschool_id;
        this.event_name = event_name;
        this.event_from_date = event_from_date;
        this.event_to_date = event_to_date;
        this.event_from_time = event_from_time;
        this.event_to_time = event_to_time;
        this.event_venue = event_venue;
        this.event_description = event_description;
        this.created_by = created_by;
    }

}
