package main.master.c31.LauncherMainActivity.UpdateProfile;

public class UpdatePofileModel {
    public UpdatePofileModel(String preschool_id, String owner_name, String ps_email, String center_address, String ps_mobile, String website, String facebook_link) {
        this.preschool_id = preschool_id;
        this.owner_name = owner_name;
        this.ps_email = ps_email;
        this.center_address = center_address;
        this.ps_mobile = ps_mobile;
        this.website = website;
        this.facebook_link = facebook_link;
    }

    private String preschool_id;

     private String owner_name;

     private String ps_email;

     private String center_address;

    public String getPreschool_id() {
        return preschool_id;
    }

    public void setPreschool_id(String preschool_id) {
        this.preschool_id = preschool_id;
    }

    public String getOwnername() {
        return owner_name;
    }

    public void setOwnername(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPs_email() {
        return ps_email;
    }

    public void setPs_email(String ps_email) {
        this.ps_email = ps_email;
    }

    public String getCenter_address() {
        return center_address;
    }

    public void setCenter_address(String center_address) {
        this.center_address = center_address;
    }

    public String getPs_mobile() {
        return ps_mobile;
    }

    public void setPs_mobile(String ps_mobile) {
        this.ps_mobile = ps_mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebooklink() {
        return facebook_link;
    }

    public void setFacebooklink(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    private String ps_mobile;

     private String website;

    private String facebook_link;




}
