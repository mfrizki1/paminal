package id.calocallo.sicape.network.request

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefPenyelidikanReq (
    var id_lp: Int?,
    var no_lp: String?,
    var isi_keterangan_terlapor: String?
):Parcelable{
    constructor():this(null,null,null)
}