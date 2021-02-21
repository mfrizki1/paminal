package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.PasalDilanggarResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpPasalModel(
    var isChecked: Boolean,
    val pasalDilanggarModel: ArrayList<PasalDilanggarResp>
//    val selected
):Parcelable {
    val checked: Boolean = false
}