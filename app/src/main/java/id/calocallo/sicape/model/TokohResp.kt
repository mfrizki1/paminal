package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable

data class TokohResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var asal_negara: String?,
    var alasan: String?,
    var keterangan: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(nama)
        writeString(asal_negara)
        writeString(alasan)
        writeString(keterangan)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TokohResp> = object : Parcelable.Creator<TokohResp> {
            override fun createFromParcel(source: Parcel): TokohResp = TokohResp(source)
            override fun newArray(size: Int): Array<TokohResp?> = arrayOfNulls(size)
        }
    }
}