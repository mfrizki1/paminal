package id.calocallo.sicape.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class LhpReq(
    var no_lhp: String?,
    var referensi_penyelidikan: ArrayList<RefPenyelidikanReq>?,
    var personel_penyelidik: ArrayList<PersonelPenyelidikReq>?,
    var saksi: ArrayList<SaksiLhpReq>?,
//    var keterangan_terlapor: ArrayList<KetTerlaporReq>?,
    var tentang: String?,
    var no_surat_perintah_penyelidikan: String?,

    var tugas_pokok: String?,
    var pokok_permasalahan: String?,
    var keterangan_ahli: String?,
    var kesimpulan: String?,
    var rekomendasi: String?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,

    //textmultiline
    var surat: String?,
    var petunjuk: String?,
    var barang_bukti: String?,
    var analisa: String?,
    var is_terbukti: Int?,
    var tanggal_mulai_penyelidikan: String?
//    var wilayah_hukum_penyelidikan: String?

) : Parcelable {
    constructor() : this(
      null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null
    )
}