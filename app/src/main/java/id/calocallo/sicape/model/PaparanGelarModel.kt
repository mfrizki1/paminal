package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaparanGelarModel(
    var dasar_paparan: String?,
    var kronologis_paparan: String?
) : Parcelable {
    constructor() : this(null, null)
}