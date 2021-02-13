package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PenghargaanMinResp (
    var id: Int?,
    var id_personel: Int?,
    var penghargaan: String?,
    var diterima_dari: String?
) : Parcelable

@Parcelize
data class DetailPenghargaanResp(
    val id: String?,
    val personel: PersonelMinResp?,
    val penghargaan: String?,
    val diterima_dari: String?,
    val dalam_rangka: String?,
    val tahun: String?,
    val keterangan: String?
) : Parcelable