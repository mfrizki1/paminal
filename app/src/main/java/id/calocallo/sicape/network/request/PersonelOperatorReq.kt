package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelOperatorReq(
    var id_satuan_kerja: Int?,
    var nama: String?,
    var username: String?,
    var id_personel: Int?,
    var password: String?,
    var password_confirmation: String?,
    var is_aktif: Int?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null)
}