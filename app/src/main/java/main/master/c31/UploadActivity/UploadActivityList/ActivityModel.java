package main.master.c31.UploadActivity.UploadActivityList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityModel {

    @SerializedName("activity_id")
    @Expose
    private String activityId;
    @SerializedName("preschool_id")
    @Expose
    private String preschoolId;
    @SerializedName("activity_name")
    @Expose
    private String activityName;

    public String getEventfromdate() {
        return eventfromdate;
    }

    public void setEventfromdate(String eventfromdate) {
        this.eventfromdate = eventfromdate;
    }

    @SerializedName("event_from_date")
    @Expose
    private String eventfromdate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @SerializedName("date")
    @Expose
    private String date;


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @SerializedName("dob")
    @Expose
    private String dob;


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @SerializedName("student_name")
    @Expose
    private String studentName;

    @SerializedName("artwork_name")
    @Expose
    private String artworkname;


    @SerializedName("fb_post_name")
    @Expose
    private String fbpostname;

    public String getArtworkname() {
        return artworkname;
    }

    public void setArtworkname(String artworkname) {
        this.artworkname = artworkname;
    }

    public String getFbpostname() {
        return fbpostname;
    }

    public void setFbpostname(String fbpostname) {
        this.fbpostname = fbpostname;
    }

    public String getFbrequirements() {
        return fbrequirements;
    }

    public void setFbrequirements(String fbrequirements) {
        this.fbrequirements = fbrequirements;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    @SerializedName("fb_requirements")
    @Expose
    private String fbrequirements;

    @SerializedName("event_name")
    @Expose
    private String eventname;






    @SerializedName("activity_date")
    @Expose
    private String activityDate;
    @SerializedName("pictures")
    @Expose
    private String pictures;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("content_writer_status")
    @Expose
    private String contentWriterStatus;
    @SerializedName("social_media_manager_status")
    @Expose
    private String socialMediaManagerStatus;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modified_by")
    @Expose
    private Object modifiedBy;
    @SerializedName("modified_date")
    @Expose
    private Object modifiedDate;

    public String getIs_video() {
        return is_video;
    }

    public void setIs_video(String is_video) {
        this.is_video = is_video;
    }

    @SerializedName("is_video")
    @Expose
    private String is_video;


    public String getPicturecount() {
        return picturecount;
    }

    public void setPicturecount(String picturecount) {
        this.picturecount = picturecount;
    }

    @SerializedName("picture_count")
    @Expose
    private String picturecount;



    public Object getGraphicsdesignerstatus() {
        return graphicsdesignerstatus;
    }

    public void setGraphicsdesignerstatus(Object graphicsdesignerstatus) {
        this.graphicsdesignerstatus = graphicsdesignerstatus;
    }

    @SerializedName("graphics_designer_status")
    @Expose
    private Object graphicsdesignerstatus;



    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPreschoolId() {
        return preschoolId;
    }

    public void setPreschoolId(String preschoolId) {
        this.preschoolId = preschoolId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentWriterStatus() {
        return contentWriterStatus;
    }

    public void setContentWriterStatus(String contentWriterStatus) {
        this.contentWriterStatus = contentWriterStatus;
    }

    public String getSocialMediaManagerStatus() {
        return socialMediaManagerStatus;
    }

    public void setSocialMediaManagerStatus(String socialMediaManagerStatus) {
        this.socialMediaManagerStatus = socialMediaManagerStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}