package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PekerjaanModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PekerjaanMinResp(
    var id:Int?,
    var id_personel:Int?,
    var pekerjaan:String?,
    var berapa_tahun:String?
) : Parcelable


@Parcelize
data class AddPekerjaanResp(
  var riwayat_pekerjaan: PekerjaanModel?
) : Parcelable
