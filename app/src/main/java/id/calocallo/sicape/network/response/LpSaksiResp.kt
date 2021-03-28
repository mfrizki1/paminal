package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpSaksiResp(
    var id: Int?,
    var id_lp: Int?,
    var status_saksi:String?,
    var kesimpulan_keterangan:String?,
    var personel: PersonelMinResp?,
    var nama:String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at: String?

):Parcelable

data class SaksiPersonelResp(
    var id: Int?,
    var id_lp: Int?,
    var status_saksi:String?,
    var detail_keterangan: String?,
    var kesimpulan_keterangan: String?,
    var personel: PersonelMinResp?
)
data class SaksiSipilResp(
    var id: Int?,
    var id_lp: Int?,
    var status_saksi:String?,
    var detail_keterangan: String?,
    var kesimpulan_keterangan: String?,
    var nama:String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?
)
data class AddSaksiPersonelResp(var saksi: SaksiPersonelResp?)
data class AddSaksiSipilResp(var saksi: SaksiSipilResp?)