package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhgMinResp(
    var id: Int?,
    var lp: LpMinResp?,
    var dugaan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable

@Parcelize
data class PesertaLhgResp(
    var id: Int?,
    var id_lhg: Int?,
    var nama_peserta: String?,
    var pendapat: String?
) : Parcelable{
    constructor():this(null,null,null,null)
}

@Parcelize
data class AddPesertaLhgResp(
    var peserta_gelar: PesertaLhgResp?
) : Parcelable

