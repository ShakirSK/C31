package main.master.c31.Network;

import com.google.gson.JsonObject;
import main.master.c31.ArtWork.ArtworkModel;
import main.master.c31.ArtWork.ArtworkResponse;
import main.master.c31.Birthday.BirthdayModel;
import main.master.c31.EventDetails.EventDetailsModel;
import main.master.c31.LauncherMainActivity.HOME.ActiveStatus.ActiveStatusModel;
import main.master.c31.LauncherMainActivity.UpdateProfile.UpdatePofileModel;
import main.master.c31.LauncherMainActivity.UpdateProfile.UpdateProfileResponse;
import main.master.c31.LauncherMainActivity.userdatamodel.PreSchoolUserModel;
import main.master.c31.UploadActivity.UploadActivityList.ActivityModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {



    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })
    @POST("login")
    Call<Example> login(@Body loginmodel login);


    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })
 /*   @POST("ArtworksAPI/artwork")
    Call<ArtworkResponse> Artwork(@Body ArtworkModel artworkModel);
  */
    @Multipart
    @POST("ArtworksAPI/artwork")
    Call<ResponseBody> Artwork(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("artwork_name") RequestBody artwork_name,
            @Part("size") RequestBody size,
            @Part("date") RequestBody date,
            @Part("venue") RequestBody venue,
            @Part("content") RequestBody content,
            @Part("description") RequestBody description,
            @Part("created_by") RequestBody created_by,
            @Part MultipartBody.Part file
    );

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })
    @POST("EventAPI/event")
    Call<ArtworkResponse> EventDetails(@Body EventDetailsModel eventDetailsModel);


/*    @POST("BirthdayAPI/birthday")
    Call<ArtworkResponse> Birthday(
            @Body RequestBody file
    );*/

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })


    @GET("TestAPI/test/{id}")
    Call<List<ActiveStatusModel>> look_status_of_app(
            @Path("id") String id
    );


    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })


    @GET("{ActivityAPI}/{activity}/{id}")
    Call<List<ActivityModel>> posturdata(
            @Path("ActivityAPI") String ActivityAPI,
            @Path("activity") String activity,
            @Path("id") String id
    );

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @Multipart
    @POST("BirthdayAPI/birthday")
    Call<ResponseBody> Birthday(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("student_name") RequestBody student_name,
            @Part("dob") RequestBody datesubmit,
            @Part MultipartBody.Part file,
            @Part("created_by") RequestBody created_by
    );

   /* @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @Multipart
    @POST("ActivityAPI/activity")
    Call<ResponseBody> Activity(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("activity_name") RequestBody activity_name,
            @Part("activity_date") RequestBody activity_date,
            @Part("description") RequestBody description,
            @Part("created_by") RequestBody created_by,
            @Part MultipartBody.Part[] files

    );*/

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @Multipart
    @POST("ActivityAPI/activity")
    Call<ResponseBody> Activity(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("activity_name") RequestBody activity_name,
            @Part("activity_date") RequestBody activity_date,
            @Part("description") RequestBody description,
            @Part("created_by") RequestBody created_by,
            @Part MultipartBody.Part[] files,
            @Part("picture_count") RequestBody picture_count,
            @Part MultipartBody.Part files_zip,
             @Part("is_video") RequestBody is_video

    );

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @Multipart
    @POST("FbcreativeAPI/fbcreative")
        Call<ResponseBody> FBRequirement(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("modified_by") RequestBody modified_by,
            @Part MultipartBody.Part[] files

    );

    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @Multipart
    @POST("FbpostAPI/fbpost")
    Call<ResponseBody> FBPostRequest(
            @Part("preschool_id") RequestBody preschool_id,
            @Part("fb_post_name") RequestBody fb_post_name,
            @Part("ischange") RequestBody ischange,
            @Part("created_by") RequestBody created_by,
            @Part MultipartBody.Part[] files,
            @Part("changes") RequestBody changes,
            @Part("link") RequestBody link,
             @Part("description") RequestBody description
    );


    //user data
    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })

    @GET("UserAPI/user")
    Call<List<PreSchoolUserModel>> userdata_for_preschool(
    );


    //update preschool profile
    @Headers({
            "x-api-key: Creativeminds@31",
            "Authorization: Basic YWRtaW46MTIzNA=="
    })
    @POST("TestAPI/test")
    Call<UpdateProfileResponse> UpdateProfile(@Body UpdatePofileModel updatePofileModel);

/*
    @POST("BirthdayAPI/birthday")
    Call<ArtworkResponse> Birthday(@Body BirthdayModel birthdayModel);*/

  /*  @GET()
    Call getactivity();*/


  //delete activty
  @Headers({
          "x-api-key: Creativeminds@31",
          "Authorization: Basic YWRtaW46MTIzNA=="
  })

  @Multipart
  @POST("ActivityAPI/activity")
  Call<ResponseBody> DeleteActivity(
          @Part("activity_id") RequestBody activity_id
  );

}
