package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PekerjaanLuarDinasMinResp(
    val id: String?,
    val id_personel: String?,
    val pekerjaan: String?,
    val instansi: String?
) : Parcelable

@Parcelize
data class DetailPekerjaanLuar(
    val id: Int?,
    val personel: PersonelMinResp?,
    val pekerjaan: String?,
    val instansi: String?,
    val tahun_awal: String?,
   val  tahun_akhir: String?,
   val  dalam_rangka: String?,
    val keterangan: String?
) : Parcelable