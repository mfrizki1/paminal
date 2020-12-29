package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class PelanggaranResp(
    var id: Int?,
    var nama_pelanggaran: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor():this(0,"","","","")
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(nama_pelanggaran)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PelanggaranResp> =
            object : Parcelable.Creator<PelanggaranResp> {
                override fun createFromParcel(source: Parcel): PelanggaranResp =
                    PelanggaranResp(source)

                override fun newArray(size: Int): Array<PelanggaranResp?> = arrayOfNulls(size)
            }
    }
}