package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrangsResp(
    var id: Int?,
    var personel: PersonelMinResp?,
    var nama: String?,
    var jenis_kelamin: String?,
    var umur: String?,
    var alamat: String?,
    var pekerjaan: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable