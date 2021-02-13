package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PekerjaanModel(
    var id:String?,
    var personel: PersonelMinResp?,
    var pekerjaan:String?,
    var golongan:String?,
    var instansi:String?,
    var berapa_tahun:String?,
    var keterangan:String?
): Parcelable