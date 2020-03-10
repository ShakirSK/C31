package main.master.c31.Network;

public class ApiUtils {

    //public static final String BASE_URL = "http://192.168.42.190/creative-web/api/";
    public static final String BASE_URL = "http://creative31minds.com/creative-web/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}