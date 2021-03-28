package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailSaksiLhpResp(
    var id: Int?,
    var lhp : LhpMinResp?,
    var lp : LpMinResp?,
    var status_saksi: String?,
    var detail_keterangan: String?,
    var kesimpulan_keterangan: String?,
    var personel: PersonelMinResp?,
    var nama: String?,
    var tempat_lahir : String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?,
    var isi_keterangan_saksi: String?
):Parcelable

@Parcelize
data class AddSaksiLhpResp(
    var saksi: DetailSaksiLhpResp?
):Parcelable
