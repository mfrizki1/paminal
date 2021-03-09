package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefPenyelidikanResp(
    var id: Int?,
    var id_lp: Int?,
    var lhp: LhpMinResp?,
    var lp: LpResp?,
    var isi_keterangan_terlapor: String?
):Parcelable {
    constructor() : this(null,null,null,null,null)
}

@Parcelize
data class AddRefPenyelidikanResp(
    var referensi_penyelidikan: RefPenyelidikanResp?
):Parcelable