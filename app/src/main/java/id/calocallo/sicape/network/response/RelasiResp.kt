package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RelasiResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var lokasi: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable