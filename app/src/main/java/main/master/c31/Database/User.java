package main.master.c31.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "preschool_id")
    private String preschool_id;

    @ColumnInfo(name = "ps_user_id")
    private String ps_user_id;

    @ColumnInfo(name = "ps_name")
    private String ps_name;


    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebooklink() {
        return facebooklink;
    }

    public void setFacebooklink(String facebooklink) {
        this.facebooklink = facebooklink;
    }

    @ColumnInfo(name = "owner_name")
    private String ownername;

    @ColumnInfo(name = "website")
    private String website;

    @ColumnInfo(name = "facebook_link")
    private String facebooklink;


    public String getPs_activities() {
        return ps_activities;
    }

    public void setPs_activities(String ps_activities) {
        this.ps_activities = ps_activities;
    }

    @ColumnInfo(name = "ps_activities")
    private String ps_activities;

    public String getCenter_address() {
        return center_address;
    }

    public void setCenter_address(String center_address) {
        this.center_address = center_address;
    }

    @ColumnInfo(name = "center_address")
    private String center_address;

    public String getPs_logo() {
        return ps_logo;
    }

    public void setPs_logo(String ps_logo) {
        this.ps_logo = ps_logo;
    }

    @ColumnInfo(name = "ps_logo")
    private String ps_logo;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getPs_user_id() {
        return ps_user_id;
    }

    public void setPs_user_id(String ps_user_id) {
        this.ps_user_id = ps_user_id;
    }

    public String getPs_name() {
        return ps_name;
    }

    public void setPs_name(String ps_name) {
        this.ps_name = ps_name;
    }


    public String getPs_email() {
        return ps_email;
    }

    public void setPs_email(String ps_email) {
        this.ps_email = ps_email;
    }

    @ColumnInfo(name = "ps_email")
    private String ps_email;

    public String getPs_mobile() {
        return ps_mobile;
    }

    public void setPs_mobile(String ps_mobile) {
        this.ps_mobile = ps_mobile;
    }

    @ColumnInfo(name = "ps_mobile")
    private String ps_mobile;

    public String getPs_social_media_manager() {
        return ps_social_media_manager;
    }

    public void setPs_social_media_manager(String ps_social_media_manager) {
        this.ps_social_media_manager = ps_social_media_manager;
    }

    public String getPs_graphics_designer() {
        return ps_graphics_designer;
    }

    public void setPs_graphics_designer(String ps_graphics_designer) {
        this.ps_graphics_designer = ps_graphics_designer;
    }

    public String getPs_content_writer() {
        return ps_content_writer;
    }

    public void setPs_content_writer(String ps_content_writer) {
        this.ps_content_writer = ps_content_writer;
    }

    @ColumnInfo(name = "ps_social_media_manager")
    private String ps_social_media_manager;

    @ColumnInfo(name = "ps_graphics_designer")
    private String ps_graphics_designer;

    @ColumnInfo(name = "ps_content_writer")
    private String ps_content_writer;


}
