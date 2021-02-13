package id.calocallo.sicape.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FotoResp(

    var id: Int?,
    var nama_file: String?,
    var nama_file_asli: String?,
    var direktori: String?,
    var penggunaan: String?,
    var jenis: String?,
    var url: String?,
    var created_at: String?,
    var updated_at: String?

) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null, null)
}

@Parcelize
data class AllFotoResp(
    var foto_muka: FotoResp?,
    var foto_kanan: FotoResp?,
    var foto_kiri: FotoResp?
) : Parcelable

