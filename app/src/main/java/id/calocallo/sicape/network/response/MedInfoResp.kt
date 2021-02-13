package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedInfoResp(
    var id: Int?,
    var personel: PersonelMinResp?,
    var sumber: String?,
    var topik: String?,
    var alasan: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable
