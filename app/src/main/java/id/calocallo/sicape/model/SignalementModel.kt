package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignalementModel(
    var tinggi: Int?,
    var rambut: String?,
    var muka: String?,
    var mata: String?,
    var sidik_jari: String?,
    var cacat: String?,
    var kesenangan: String?,
    var kelemahan: String?,
    var yang_mempengaruhi: String?,
    var keluarga_dekat: String?,
    var lain_lain: String?
) : Parcelable {
    constructor():this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}