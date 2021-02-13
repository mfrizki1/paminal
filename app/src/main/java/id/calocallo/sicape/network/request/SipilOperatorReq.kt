package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SipilOperatorReq(
    var nama: String?,
    var alamat: String?,
    var password: String?,
    var password_confirmation: String?,
    var id_satuan_kerja: Int?,
    var is_aktif: Int?
) : Parcelable{
    constructor():this(null,null,null,
        null,null,null)
}