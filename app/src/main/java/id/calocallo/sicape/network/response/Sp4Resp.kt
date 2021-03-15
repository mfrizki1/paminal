package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sp4Resp(
    var id: Int?,
    var no_sp4: String?,
    var lp: LpResp?,
    var no_surat_perintah_kapolri: String?,
    var tanggal_surat_perintah_kapolri: String?,
    var no_hasil_audit: String?,
    var tanggal_hasil_audit: String?,
    var tanggal_gelar_audit: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_akreditor: String?,
    var pangkat_akreditor: String?,
    var nrp_akreditor: String?,
    var is_ada_dokumen: Int?,
    var dokumen: DokResp?,
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?

    /*var sktb: SktbOnSp3?,
    var sktt: SkttOnSp3?,
    var mengingat_p4: String?,
    var mengingat_p5: String?,
    var menetapkan_p1: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var tembusan: String?,*/

) : Parcelable

@Parcelize
data class AddSp4Resp(
    var sp4: Sp4Resp?
) : Parcelable
    