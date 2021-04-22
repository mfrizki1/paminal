package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkttResp(
    var id: Int?,
    var no_sktt: String?,
    var lp: LpResp?,
    var no_laporan_hasil_audit_investigasi: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var tembusan: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable

@Parcelize
data class AddSkttResp(
  var sktt: SkttResp?
) : Parcelable