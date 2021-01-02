package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.LpPasalResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpPasalModel(
    var isChecked: Boolean,
    val pasalModel: ArrayList<LpPasalResp>
//    val selected
):Parcelable {
    val checked: Boolean = false
}