package id.calocallo.sicape.ui.gelar

import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.GelarReq
import id.calocallo.sicape.utils.GelarDataManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_gelar4.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddGelar4Activity : BaseActivity() {
    private lateinit var gelarDataManager: GelarDataManager
    private var gelarReq = GelarReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gelar4)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Gelar Perkara"
        gelarDataManager = GelarDataManager(this)

        btn_save_all_gelar_add.setOnClickListener {
            saveAllGelar()
        }
    }

    private fun saveAllGelar() {
        /*gelar1*/
        val gelar1 = gelarDataManager.getGelar1()
        gelarReq.tentang = gelar1.tentang
        gelarReq.dasar_dasar = gelar1.dasar_dasar
        gelarReq.tanggal = gelar1.tanggal
        gelarReq.pukul = gelar1.pukul
        gelarReq.tempat = gelar1.tempat
        gelarReq.uraian = gelar1.uraian
        gelarReq.hari = gelar1.hari
        gelarReq.id_pemapar = gelar1.id_pemapar
        gelarReq.id_peserta = gelar1.id_peserta
        gelarReq.id_pimpinan = gelar1.id_pimpinan

        /*paparan*/
        val paparan = gelarDataManager.getPaparanGelar()
        gelarReq.dasar_paparan = paparan.dasar_paparan
        gelarReq.kronologis_paparan = paparan.kronologis_paparan

        /*tanggapan*/
        val tanggapanPeserta = gelarDataManager.getTanggPesertaGelar()
        gelarReq.list_tanggapan = tanggapanPeserta

        gelarReq.kesimpulan = edt_kesimpulan_gelar_add.editText?.text.toString()
        gelarReq.rekomendasi = edt_rekomendasi_gelar_add.editText?.text.toString()
        gelarReq.kota_dibuat = edt_kota_gelar_add.editText?.text.toString()
        gelarReq.tanggal_dibuat = edt_tanggal_gelar_add.editText?.text.toString()

        gelarReq.nama_pimpinan = edt_nama_pimpinan_gelar_add.editText?.text.toString()
        gelarReq.pangkat_pimpinan =
            edt_pangkat_pimpinan_gelar_add.editText?.text.toString().toUpperCase()
        gelarReq.nrp_pimpinan = edt_nrp_pimpinan_gelar_add.editText?.text.toString()

        gelarReq.nama_notulen = edt_nama_notulen_gelar_add.editText?.text.toString()
        gelarReq.pangkat_notulen =
            edt_pangkat_notulen_gelar_add.editText?.text.toString().toUpperCase()
        gelarReq.nrp_notulen = edt_nrp_notulen_gelar_add.editText?.text.toString()


    }
}