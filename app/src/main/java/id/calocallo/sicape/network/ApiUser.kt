package id.calocallo.sicape.network

import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiUser {
    companion object {
        const val LOGIN = "guest/login"
        const val AUTH = "auth"
        const val GET_USER = "${AUTH}/profile"
        const val LOGOUT = "${AUTH}/logout"
    }


    @Headers(ApiPersonel.ACCEPT)
    @POST(LOGIN)
    fun login(@Body loginReq: LoginReq): Call<LoginResp>

    @Headers(ApiPersonel.ACCEPT)
    @GET(GET_USER)
    fun getUser(@Header("Authorization") tokenBearer: String): Call<UserResp>

    @Headers(ApiPersonel.ACCEPT)
    @GET(LOGOUT)
    fun logout(@Header("Authorization") token: String): Call<BaseResp>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("auth/password/update")
    fun changePassword(
        @Header("Authorization") token: String,
        @Body changePassReq: ChangePassReq
    ): Call<BaseResp>


    /*operator Personel*/
    @Headers(ApiPersonel.ACCEPT)
    @GET("user/personel/operator")
    fun getListPersOperator(
        @Header("Authorization") token: String
    ): Call<ArrayList<UserResp>>


    @Headers(ApiPersonel.ACCEPT)
    @POST("user/personel/operator")
    fun addPersOperator(
        @Header("Authorization") token: String,
        @Body personelOperatorReq: PersonelOperatorReq
    ): Call<Base1Resp<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @GET("user/personel/operator/{id_operator}")
    fun getDetailPersOperator(
        @Header("Authorization") token: String,
        @Path("id_operator") id_operator: String
    ): Call<UserResp>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("user/personel/operator/{id_operator}")
    fun updPersOperator(
        @Header("Authorization") token: String,
        @Path("id_operator") id_operator: Int,
        @Body personelOperatorReq: PersonelOperatorReq
    ): Call<Base1Resp<PersonelModelMax2>>

    @Headers(ApiPersonel.ACCEPT)
    @DELETE("user/personel/operator/{id_operator}")
    fun delPersOperator(
        @Header("Authorization") token: String,
        @Path("id_operator") id_operator: String
    ): Call<BaseResp>

    /*SIPIL OPERATOR*/
    @Headers(ApiPersonel.ACCEPT)
    @GET("user/sipil/operator")
    fun getListSipilOperator(
        @Header("Authorization") token: String
    ): Call<ArrayList<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @GET("user/sipil/operator/{id_sipil}")
    fun getDetailSipilOperator(
        @Header("Authorization") token: String,
        @Path("id_sipil") id_sipil: String
    ): Call<UserResp>

    @Headers(ApiPersonel.ACCEPT)
    @POST("user/sipil/operator")
    fun addSipilOperator(
        @Header("Authorization") token: String,
        @Body sipilOperatorReq: SipilOperatorReq
    ): Call<Base1Resp<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("user/sipil/operator/{id_operator}")
    fun updSipilOperator(
        @Header("Authorization") token: String,
        @Path("id_operator") id_operator: String,
        @Body sipilOperatorReq: SipilOperatorReq
    ): Call<Base1Resp<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @DELETE("user/sipil/operator/{id_operator}")
    fun delSipilOperator(
        @Header("Authorization") token: String,
        @Path("id_operator") id_operator: String
    ): Call<BaseResp>

    /*admin*/
    @Headers(ApiPersonel.ACCEPT)
    @GET("user/personel/admin")
    fun getListAdmin(
        @Header("Authorization") token: String
    ): Call<ArrayList<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @GET("user/personel/admin/{id_admin}")
    fun getDetailAdmin(
        @Header("Authorization") token: String,
        @Path("id_admin") id_admin: String
    ): Call<UserResp>

    @Headers(ApiPersonel.ACCEPT)
    @POST("user/personel/admin")
    fun addAdmin(
        @Header("Authorization") token: String,
        @Body adminReq: AdminReq
    ): Call<Base1Resp<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @PATCH("user/personel/admin/{id_admin}")
    fun updAdmin(
        @Header("Authorization") token: String,
        @Path("id_admin") id_admin: String,
        @Body adminReq: AdminReq
    ): Call<Base1Resp<UserResp>>

    @Headers(ApiPersonel.ACCEPT)
    @DELETE("user/personel/admin/{id_admin}")
    fun delAdmin(
        @Header("Authorization") token: String,
        @Path("id_admin") id_admin: String
    ): Call<BaseResp>

}