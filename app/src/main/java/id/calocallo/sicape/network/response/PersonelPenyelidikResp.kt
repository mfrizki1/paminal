package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelPenyelidikResp(
    var id: Int?,
    var id_lhp: Int?,
    var personel: PersonelMinResp?,
    var is_ketua: Int?,
    var detail_keterangan: String?
) : Parcelable {
    constructor() : this(null,null,null,null,null)
}