package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.ui.main.lhp.edit.analisa.PickAnalisaLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.barangbukti.PickBarBuktiLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.PickLidikLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.petunjuk.PickPetunjukActivity
import id.calocallo.sicape.ui.main.lhp.edit.saksi.PickSaksiLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.surat.PickSuratLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.PickTerlaporLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.util.ArrayList

class DetailLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLidik: DetailLidikAdapter
    private lateinit var adapterSaksi: DetailSaksiAdapter
    private lateinit var adapterSurat: DetailSuratAdapter
    private lateinit var adapterPetunjuk: DetailPetunjukAdapter
    private lateinit var adapterBukti: DetailBuktiAdapter
    private lateinit var adapterAnalisa: DetailAnalisaAdapter
    private lateinit var adapterTerlapor: DetailTerlaporAdapter
    var terbukti = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Laporan Hasil Penyelidikan"

        val dataLhp = intent.extras?.getParcelable<LhpModel>(DETAIL_LHP)
        txt_no_lhp_detail.text = dataLhp?.no_lhp
        txt_kesimpulan_detail.text = dataLhp?.kesimpulan
        txt_rekomendasi_detail.text = dataLhp?.rekomendasi
//        txt_pelaksanaan_detail.text = dataLhp?.pelaksanan
//        txt_rencana_penyelidikan_detail.text = dataLhp?.rencana_pelaksanaan_penyelidikan
        txt_pengaduan_detail.text = dataLhp?.tentang
        txt_no_sp_detail.text = dataLhp?.no_surat_perintah_penyelidikan
        txt_waktu_penugasan_detail.text = dataLhp?.waktu_penugasan
        txt_tempat_penyelidikan_detail.text = dataLhp?.daerah_penyelidikan
        txt_tugas_pokok_detail.text = dataLhp?.tugas_pokok
        txt_pokok_permasalahan_detail.text = dataLhp?.pokok_permasalahan
        txt_ket_ahli_detail.text = dataLhp?.keterangan_ahli


//        val listLdkDetail = dataLhp?.listLidik
//        val listSaksiDetail = dataLhp?.listSaksi
//        val listSuratDetail = dataLhp?.listSurat
//        val listPetunjukDetail = dataLhp?.listPetunjuk
//        val listBuktiDetail = dataLhp?.listBukti
//        val listAnalisaDetail = dataLhp?.listAnalisa
//        val listTerlaporDetail = dataLhp?.listTerlapor

//        if (listLdkDetail?.isEmpty()!!) rl_no_data_lidik.visible()
//        if (listSaksiDetail?.isEmpty()!!) rl_no_data_saksi.visible()
//        if (listSuratDetail?.isEmpty()!!) rl_no_data_surat.visible()
//        if (listPetunjukDetail?.isEmpty()!!) rl_no_data_petunjuk.visible()
//        if (listBuktiDetail?.isEmpty()!!) rl_no_data_bukti.visible()
//        if (listAnalisaDetail?.isEmpty()!!) rl_no_data_analisa.visible()
//        if (listTerlaporDetail?.isEmpty()!!) rl_no_data_terlapor.visible()

//        initRV(
//            listLdkDetail, listSaksiDetail, listSuratDetail, listPetunjukDetail, listBuktiDetail,
//            listAnalisaDetail, listTerlaporDetail
//        )

        terbukti = if (dataLhp?.isTerbukti == 0) {
            "Terbukti"
        } else {
            "Tidak Terbukti"
        }
        txt_isTerbukti_detail.text = terbukti
//        val ketuaTim = "${dataLhp.nama_ketua_tim}\t ${dataLhp.pangkat_ketua_tim}\t ${dataLhp.nrp_ketua_tim}"
//        val lidik = dataLhp.listLidik?.filter { it.status_penyelidik =="ketua_tim" }
        val lidik = dataLhp?.personel_penyelidik?.find { it.is_ketua == "ketua_tim" }
        txt_ketua_tim_detail.text =
            "Nama : ${lidik?.nama} \t Pangkat : ${lidik?.pangkat} \t ${lidik?.nrp}"

        val hakAkses = sessionManager.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_lhp.toggleVisibility()
            btn_edit_lidik_lhp.toggleVisibility()
            btn_edit_saksi_lhp.toggleVisibility()
            btn_edit_surat_lhp.toggleVisibility()
            btn_edit_petunjuk_lhp.toggleVisibility()
            btn_edit_barang_buki_lhp.toggleVisibility()
            btn_edit_ket_terlapor_lhp.toggleVisibility()
            btn_edit_analisa_lhp.toggleVisibility()
        }
        btn_edit_lhp.setOnClickListener {
            val intent = Intent(this, EditLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }

        btn_edit_lidik_lhp.setOnClickListener {
            val intent = Intent(this, PickLidikLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }
        btn_edit_saksi_lhp.setOnClickListener {
            val intent = Intent(this, PickSaksiLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }
        btn_edit_surat_lhp.setOnClickListener {
            val intent = Intent(this, PickSuratLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }
        btn_edit_petunjuk_lhp.setOnClickListener {
            val intent = Intent(this, PickPetunjukActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }

        btn_edit_barang_buki_lhp.setOnClickListener {
            val intent = Intent(this, PickBarBuktiLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }
        btn_edit_ket_terlapor_lhp.setOnClickListener {
            val intent = Intent(this, PickTerlaporLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }
        btn_edit_analisa_lhp.setOnClickListener {
            val intent = Intent(this, PickAnalisaLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
        }

    }

    private fun initRV(
        lidik: ArrayList<ListLidik>?,
        saksi: ArrayList<ListSaksi>?,
        surat: ArrayList<ListSurat>?,
        petunjuk: ArrayList<ListPetunjuk>?,
        bukti: ArrayList<ListBukti>?,
        analisa: ArrayList<ListAnalisa>?,
        terlapor: ArrayList<ListTerlapor>?
    ) {
        rv_detail_lidik.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLidik =
            DetailLidikAdapter(this, lidik)
        rv_detail_lidik.adapter = adapterLidik

        rv_saksi_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSaksi = DetailSaksiAdapter(this, saksi)
        rv_saksi_detail.adapter = adapterSaksi

        rv_surat_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSurat = DetailSuratAdapter(this, surat)
        rv_surat_detail.adapter = adapterSurat

        rv_petunjuk_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPetunjuk = DetailPetunjukAdapter(this, petunjuk)
        rv_petunjuk_detail.adapter = adapterPetunjuk

        rv_bukti_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterBukti = DetailBuktiAdapter(this, bukti)
        rv_bukti_detail.adapter = adapterBukti

        rv_analisa_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterAnalisa = DetailAnalisaAdapter(this, analisa)
        rv_analisa_detail.adapter = adapterAnalisa

        rv_ket_terlapor_detail.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterTerlapor = DetailTerlaporAdapter(this, terlapor)
        rv_ket_terlapor_detail.adapter = adapterTerlapor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                alertDialogDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    companion object {
        const val DETAIL_LHP = "DETAIL_LHP"
    }
}