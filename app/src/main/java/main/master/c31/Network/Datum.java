package main.master.c31.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("preschool_id")
    @Expose
    private String preschoolId;
    @SerializedName("ps_user_id")
    @Expose
    private String psUserId;
    @SerializedName("ps_name")
    @Expose
    private String psName;
    @SerializedName("ps_password")
    @Expose
    private String psPassword;
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("ps_email")
    @Expose
    private String psEmail;
    @SerializedName("ps_mobile")
    @Expose
    private String psMobile;
    @SerializedName("ps_logo")
    @Expose
    private String psLogo;
    @SerializedName("center_address")
    @Expose
    private String centerAddress;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("facebook_link")
    @Expose
    private String facebookLink;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("ps_activities")
    @Expose
    private String psActivities;
    @SerializedName("ps_social_media_manager")
    @Expose
    private String psSocialMediaManager;
    @SerializedName("ps_graphics_designer")
    @Expose
    private String psGraphicsDesigner;
    @SerializedName("ps_content_writer")
    @Expose
    private String psContentWriter;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;

    public String getPreschoolId() {
        return preschoolId;
    }

    public void setPreschoolId(String preschoolId) {
        this.preschoolId = preschoolId;
    }

    public String getPsUserId() {
        return psUserId;
    }

    public void setPsUserId(String psUserId) {
        this.psUserId = psUserId;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public String getPsPassword() {
        return psPassword;
    }

    public void setPsPassword(String psPassword) {
        this.psPassword = psPassword;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPsEmail() {
        return psEmail;
    }

    public void setPsEmail(String psEmail) {
        this.psEmail = psEmail;
    }

    public String getPsMobile() {
        return psMobile;
    }

    public void setPsMobile(String psMobile) {
        this.psMobile = psMobile;
    }

    public String getPsLogo() {
        return psLogo;
    }

    public void setPsLogo(String psLogo) {
        this.psLogo = psLogo;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPsActivities() {
        return psActivities;
    }

    public void setPsActivities(String psActivities) {
        this.psActivities = psActivities;
    }

    public String getPsSocialMediaManager() {
        return psSocialMediaManager;
    }

    public void setPsSocialMediaManager(String psSocialMediaManager) {
        this.psSocialMediaManager = psSocialMediaManager;
    }

    public String getPsGraphicsDesigner() {
        return psGraphicsDesigner;
    }

    public void setPsGraphicsDesigner(String psGraphicsDesigner) {
        this.psGraphicsDesigner = psGraphicsDesigner;
    }

    public String getPsContentWriter() {
        return psContentWriter;
    }

    public void setPsContentWriter(String psContentWriter) {
        this.psContentWriter = psContentWriter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}