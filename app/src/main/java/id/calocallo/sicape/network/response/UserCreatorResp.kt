package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserCreatorResp(
    var personel: PersonelMinResp?,
    var id: Int?,
    var hak_akses: String?,
    var satuan_kerja: SatKerResp?,
    var is_aktif: Int?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable{
    constructor():this(null,null,null,null,
        null,null,null)
}