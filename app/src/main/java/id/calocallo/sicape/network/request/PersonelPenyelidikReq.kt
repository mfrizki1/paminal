package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelPenyelidikReq (
    var id_personel: Int?,
    var is_ketua: Int?
):Parcelable{
    constructor():this(0,null)
}