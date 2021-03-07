package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPersLidikResp(
    var id: Int?,
    var id_lhp: LhpMinResp?,
    var personel: PersonelMinResp?,
    var is_ketua: Int?
):Parcelable

@Parcelize
data class AddPersLidikResp(
    var personel_penyelidik: DetailPersLidikResp?
):Parcelable
