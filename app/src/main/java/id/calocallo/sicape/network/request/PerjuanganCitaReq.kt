package id.calocallo.sicape.network.request

data class PerjuanganCitaReq(
    var peristiwa: String?,
    var lokasi : String?,
    var tahun_awal:String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan:String?
){
    constructor():this("","","","","","")
}