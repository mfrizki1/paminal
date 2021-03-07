package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelPenyelidikReq (
    var id_personel: Int?,
    var nama_personel: String?,
    var is_ketua: Int?
):Parcelable{
    constructor():this(null,null,null)
}