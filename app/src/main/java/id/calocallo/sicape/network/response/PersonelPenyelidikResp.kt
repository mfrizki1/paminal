package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelPenyelidikResp(
    var id: Int?,
    var id_lhp: Int?,
    var personel: PersonelMinResp?,
    var is_ketua: Int?
) : Parcelable {
    constructor() : this(null,null,null,null)
}