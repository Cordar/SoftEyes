package com.hsd.upc.hack.softeyes2

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @FormUrlEncoded
    @POST("multipredict")
    suspend fun caption(
        @Field("image_base64") image_base64: String?,
        @Field("model_id") model_id: String = "caption",
        @Query("client_key") client_key: String = "6c7525013b2bc86831354dc19c8d0c2fd73d9da1a4d707c48fa1c55a00ab8577"
    ): MultipredictCaptionModel
}