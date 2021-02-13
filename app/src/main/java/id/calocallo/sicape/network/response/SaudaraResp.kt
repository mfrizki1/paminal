package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaudaraResp(
    var id: Int?,
    var personel: PersonelMinResp?,
    var status_ikatan: String?,
    var nama: String?,
    var jenis_kelamin: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan_atau_sekolah: String?,
    var organisasi_yang_diikuti: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable