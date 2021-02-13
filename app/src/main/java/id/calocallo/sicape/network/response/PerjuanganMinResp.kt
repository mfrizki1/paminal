package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PerjuanganMinResp(
    var id: Int?,
    var id_personel: Int?,
    var peristiwa: String?,
    var lokasi: String?
) : Parcelable

@Parcelize
data class DetailPerjuanganResp(
    val id: String?,
    val personel: PersonelMinResp?,
    val peristiwa: String?,
    val lokasi: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val dalam_rangka: String?,
    val keterangan: String?
):Parcelable