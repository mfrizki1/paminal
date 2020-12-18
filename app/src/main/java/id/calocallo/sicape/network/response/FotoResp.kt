package id.calocallo.sicape.network.response

import com.google.gson.annotations.SerializedName

data class FotoResp(

	@field:SerializedName("id_stored_file")
	val idStoredFile: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)
