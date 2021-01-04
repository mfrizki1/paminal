package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaksiLhpResp (
    var id: Int?,
    var isi_keterangan_saksi: String?,
    var status_saksi: String?,
    var nama: String?,
    //polisi
    var id_personel: Int?,
    var pangkat: String?,
    var jabatan: String?,
    var nrp: String?,
    var id_satuan_kerja: Int?,
    var kesatuan: String?,
    //sipil
    var tempat_lahir: String?,
    var tanggal_lahir:String?,
    var pekerjaan: String?,
    var alamat: String?,
    var created_at: String?,
    var updated_at: String?
):Parcelable