package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhpResp(
    var id: Int?,
    var no_skhp: String?,
    var personel: AllPersonelModel?,
    var is_memenuhi_syarat: Int?,
    var tanggal_kenaikan_pangkat: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var kepada_penerima: String?,
    var kota_penerima: String?,
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
data class AddSkhpResp(
    var skhp: SkhpResp?
) : Parcelable