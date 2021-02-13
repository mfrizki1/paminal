package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdminResp (
    var id: Int?,
    var personel: PersonelLapor?,
    var is_aktif : Int?
) : Parcelable{
    constructor():this(null,null,null)
}
