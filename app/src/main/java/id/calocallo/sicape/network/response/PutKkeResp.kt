package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PutKkeResp(
    var id: Int?,/**/
    var lp: LpResp?,/**/
    var no_putkke: String?,/**/
    var no_berkas_pemeriksaan_pendahuluan: String?,/*!*/
    var tanggal_berkas_pemeriksaan_pendahuluan: String?,/*!*/
    var no_keputusan_kapolda_kalsel: String?,/*!*/
    var tanggal_keputusan_kapolda_kalsel: String?,/*!*/
    var no_surat_persangkaan_pelanggaran_kode_etik: String?,/*!*/
    var tanggal_surat_persangkaan_pelanggaran_kode_etik: String?,/*!*/
    var no_tuntutan_pelanggaran_kode_etik: String?,/*!*/
    var tanggal_tuntutan_pelanggaran_kode_etik: String?,/*!*/
    var sanksi_hasil_keputusan: String?,/**/
    var lokasi_sidang: String?,/*!*/
    var tanggal_putusan: String?,/**/

    var pangkat_ketua_komisi: String?,/**/
    var nama_ketua_komisi: String?,/**/
    var nrp_ketua_komisi: String?,/**/
    var jabatan_ketua_komisi: String?,/**/
    var kesatuan_ketua_komisi: String?,/**/

    var nama_wakil_ketua_komisi: String?,
    var pangkat_wakil_ketua_komisi: String?,
    var nrp_wakil_ketua_komisi: String?,
    var jabatan_wakil_ketua_komisi: String?,/**/
    var kesatuan_wakil_ketua_komisi: String?,/**/

    var nama_anggota_komisi: String?,
    var pangkat_anggota_komisi: String?,
    var nrp_anggota_komisi: String?,
    var jabatan_anggota_komisi: String?,/**/
    var kesatuan_anggota_komisi: String?,/**/
    var is_ada_dokumen: Int?,/**/
    var dokumen: DokResp?,/**/
    var user_creator: UserResp?,/**/
    var user_updater: UserResp?,/**/
    var user_deleter: UserResp?,/**/
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?

    /*var lhp: LhpOnSkhd?,
    var pembukaan_putusan: String?,
    var menimbang_p2: String?,
    var mengingat_p4: String?,
    var memperhatikan_p1: String?,
    var memperhatikan_p3: String?,
    var memperhatikan_p5: String?,
    var sanksi_rekomendasi: String?,
    var memutuskan_p1: String?,

    var kota_putusan: String?,*/
) : Parcelable {
    constructor() : this(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null,null, null, null, null
    )
}

@Parcelize
data class AddPutKkeResp(
    var putkke: PutKkeResp?
) : Parcelable