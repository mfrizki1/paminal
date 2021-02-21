package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasalDilanggarResp(
    var id: Int?,
    val id_lp: String?,
    val pasal: PasalResp?,
    val user_creator: UserCreatorResp?,
    val user_updater: UserCreatorResp?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at: String?
):Parcelable

@Parcelize
data class AddPasalDilanggarResp(
    var pasal_dilanggar: PasalDilanggarResp?
) : Parcelable