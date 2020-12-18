package id.calocallo.sicape.model

data class PerjuanganCitaReq(
    var peristiwa: String?,
    var lokasi : String?,
    var tahun_awal:String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan:String?
)