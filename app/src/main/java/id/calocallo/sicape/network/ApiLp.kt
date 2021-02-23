package id.calocallo.sicape.network

import id.calocallo.sicape.network.response.PasalResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiLp {
    companion object {
        const val ACCEPT = "Accept: application/json"
    }
    /*PASAL*/
    @Headers(ACCEPT)
    @GET("pasal")
    fun getAllPasal(@Header("Authorization") tokenBearer: String): Call<ArrayList<PasalResp>>
}