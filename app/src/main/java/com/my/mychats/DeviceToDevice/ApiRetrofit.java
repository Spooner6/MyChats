package com.my.mychats.DeviceToDevice;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiRetrofit {

    @Headers(
            {
                    "Content-type:application/json",
                    "Authorization:key=AAAArDJLHWo:APA91bErA81o5sYCfCWDUd8ukKMN-Fyk9VkwSEKxPXWXzUx5zWaI6cxdy9lMf1ZGv-COki6ykV8Y5upMdJOegV0v_HJ1gaA0N_iNSDlnt4fnzLNgqskXFEiodCnIEIt9oInJFJsnwMBj"
            }
    )

    @FormUrlEncoded
    @POST("send")
    Call<ResponseBody> sendNotification(
            @Field("sender") String sender,
            @Field("receiver") String receiver,
            @Field("body") String body);
           // @Body Sender body);
}


