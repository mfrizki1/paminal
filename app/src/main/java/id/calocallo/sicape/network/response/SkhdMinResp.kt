package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhdMinResp(
    val id: Int?,
    val no_skhd: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
):Parcelable
