package com.ecampus.bem.data.api

import com.ecampus.bem.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.POST
import okhttp3.ResponseBody

import retrofit2.http.Multipart








interface Api {
    /* POST Method */

    //login
    @FormUrlEncoded
    @POST("UserApi/login_api")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<BaseRespon<UserRespon>>

    //Pemilihan
    @FormUrlEncoded
    @POST("UserApi/pemilihan")
    fun pemilihan(
        @Field("user_id") user_id: String,
        @Field("nomor_calon") nomor_calon: String,
    ): Call<BaseResponData>

    //getStatus user
    @FormUrlEncoded
    @POST("UserApi/get_stts")
    fun getStts(
        @Field("nim") nim: String
    ): Call<BaseResponData>

    //getStatus user
    @FormUrlEncoded
    @POST("UserApi/get_data_paslon")
    fun getDataPaslon(
        @Field("nim") nim: String
    ): Call<BaseRespon<PaslonRespon>>

    //Regiter
    @FormUrlEncoded
    @POST("UserApi/regiter_api")
    fun register(
        @Field("name") name: String,
        @Field("nim") nim: String,
        @Field("email") email: String,
        @Field("no_phone") no_phone: String,
        @Field("prodi") prodi: String,
        @Field("password") password: String,
    ): Call<BaseResponData>

    //Regiter
    @FormUrlEncoded
    @POST("UserApi/daftar_paslon")
    fun daftarPaslon(
        @Field("nim_presiden") nim_presiden: String,
        @Field("nama_presiden") nama_presiden: String,
        @Field("nim_wakil") nim_wakil: String,
        @Field("nama_wakil") nama_wakil: String,
        @Field("visi") visi: String,
        @Field("misi") misi: String,
    ): Call<BaseResponData>

    //getStatus user
    @FormUrlEncoded
    @POST("UserApi/get_ques")
    fun getQues(
        @Field("id_paslon") id_paslon: String
    ): Call<BaseRespon<List<Ques>>>

    @FormUrlEncoded
    @POST("UserApi/set_ques")
    fun setQues(
        @Field("id_paslon") id_paslon: String,
        @Field("id_user") id_user: String,
        @Field("soal") soal: String
    ): Call<BaseResponData>


    @FormUrlEncoded
    @POST("UserApi/image_paslon")
    fun getImagePaslon(
        @Field("id") id_paslon: String
    ): Call<BaseRespon<List<GetImagePaslon>>>

    /* End POST Method */


    /* Multi Upload File */

    @Multipart
    @POST("UserApi/file_paslon")
    fun uploadFile(
        @Part file: MutableList<MultipartBody.Part>,
        @Part("id") id: RequestBody,
    ): Call<BaseResponData>

    /* End Multi Upload File */

    /* GET Method */

    @GET("UserApi/list_paslon")
    fun getPaslon(): Call<BaseRespon<List<PaslonRespon>>>

    @GET("UserApi/list_all_paslon")
    fun getPaslonAll(): Call<BaseRespon<List<PaslonRespon>>>

    @GET("UserApi/get_ques_stts")
    fun getQuesStts(): Call<BaseRespon<List<QuesStts>>>

    @GET("UserApi/get_voting")
    fun getVoting(): Call<BaseRespon<List<Voting>>>

    @GET("UserApi/voting_akses")
    fun votingAkses(): Call<BaseResponData>

    /* End GET Method */


}


