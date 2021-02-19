package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TanggPesertaModel(
    var nama_peserta: String?,
    var pendapat_peserta: String?
) : Parcelable {
    constructor() : this(null, null)
}