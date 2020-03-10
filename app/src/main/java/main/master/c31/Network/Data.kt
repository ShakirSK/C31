package main.master.c31.Network


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("center_address")
    val centerAddress: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("facebook_link")
    val facebookLink: String,
    @SerializedName("modified_by")
    val modifiedBy: String,
    @SerializedName("modified_date")
    val modifiedDate: String,
    @SerializedName("owner_name")
    val ownerName: String,
    @SerializedName("preschool_id")
    val preschoolId: String,
    @SerializedName("ps_activities")
    val psActivities: String,
    @SerializedName("ps_content_writer")
    val psContentWriter: String,
    @SerializedName("ps_email")
    val psEmail: String,
    @SerializedName("ps_graphics_designer")
    val psGraphicsDesigner: String,
    @SerializedName("ps_logo")
    val psLogo: String,
    @SerializedName("ps_mobile")
    val psMobile: String,
    @SerializedName("ps_name")
    val psName: String,
    @SerializedName("ps_password")
    val psPassword: String,
    @SerializedName("ps_social_media_manager")
    val psSocialMediaManager: String,
    @SerializedName("ps_user_id")
    val psUserId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("website")
    val website: String
)