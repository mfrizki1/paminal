package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.zanyastudios.test.PasalItem
import com.zanyastudios.test.PasalTesDetailsLookup
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.request.SipilPelaporReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesItemKeyProvider
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity.Companion.ID_PELAPOR_SAKSI
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickPasalActivity : BaseActivity() {
    private  lateinit var sessionManager: SessionManager
    private val listPasal = mutableListOf<PasalItem>()
    private lateinit var adapterPasalTes: PasalTesAdapter
    private var selectedIdPasal: MutableList<PasalItem> = mutableListOf()
    private var tracker: SelectionTracker<PasalItem>? = null

    //req
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var lpDisiplinReq = LpDisiplinReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pasal)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)

        when (sessionManager.getJenisLP()) {
            "pidana" -> {
                supportActionBar?.title = "Tambah Data Laporan Pidana"
            }
            "disiplin" -> {
                supportActionBar?.title = "Tambah Data Laporan Disiplin"
            }
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        listPasal.add(PasalItem(1, "Pasal 1","xxxx"))
        listPasal.add(PasalItem(2, "Pasal 2","xxxx"))
        listPasal.add(PasalItem(3, "Pasal 3","xxxx"))
        listPasal.add(PasalItem(4, "Pasal 4","xxxx"))
        getListPasal(listPasal)

        //getSipil if not empty
        val sipil = intent?.extras?.getParcelable<SipilPelaporReq>(SIPIL)

        val idPelapor = intent.extras?.getInt(ID_PELAPOR)
        btn_save_lp_all.setOnClickListener {
            if(sessionManager.getJenisLP() != "kode_etik"){
                addAllLp(sessionManager.getJenisLP(),idPelapor, sipil)
            }else{
                sessionManager.setListPasalLP(selectedIdPasal as ArrayList<LpPasalResp>)
                val intent = Intent(this, PickSaksiActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra(ID_PELAPOR_SAKSI,idPelapor)
                startActivity(intent)
            }
        }

        btn_add_single_pasal.setOnClickListener{
            val intent = Intent(this, AddPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        //apakah kke / tidak


    }

    private fun getListPasal(listPasal: MutableList<PasalItem>) {
        rv_list_pasal_2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPasalTes = PasalTesAdapter(this, listPasal as ArrayList<PasalItem>)
        rv_list_pasal_2.adapter = adapterPasalTes

        tracker = SelectionTracker.Builder<PasalItem>(
            "pasalSelection",
            rv_list_pasal_2,
            PasalTesItemKeyProvider(adapterPasalTes),
            PasalTesDetailsLookup(rv_list_pasal_2),
            StorageStrategy.createParcelableStorage(PasalItem::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterPasalTes.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedIdPasal = it.selection.toMutableList()
                        Log.e("idPasal", "$selectedIdPasal")
                    }
                }
            }
        )
    }
    private fun addAllLp(jenisLP: String?, idPelapor: Int?, sipil: SipilPelaporReq?) {
        when (jenisLP) {
            "pidana" -> {
                lpPidanaReq.no_lp = sessionManager.getNoLP()
                lpPidanaReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpPidanaReq.id_personel_pelapor = idPelapor
                lpPidanaReq.nama_yang_mengetahui = sessionManager.getNamaPimpBidLp()
                lpPidanaReq.pangkat_yang_mengetahui = sessionManager.getPangkatPimpBidLp()
                lpPidanaReq.nrp_yang_mengetahui = sessionManager.getNrpPimpBidLp()
                lpPidanaReq.jabatan_yang_mengetahui = sessionManager.getJabatanPimpBidLp()
                lpPidanaReq.status_pelapor = sessionManager.getPelapor()
                lpPidanaReq.pembukaan_laporan = sessionManager.getPembukaanLpLP()
                lpPidanaReq.isi_laporan = sessionManager.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
                lpPidanaReq.kota_buat_laporan = sessionManager.getKotaBuatLp()
                lpPidanaReq.tanggal_buat_laporan = sessionManager.getTglBuatLp()
                lpPidanaReq.nama_pelapor = sipil?.nama_sipil
                lpPidanaReq.agama_pelapor = sipil?.agama_sipil
                lpPidanaReq.pekerjaan_pelapor = sipil?.pekerjaan_sipil
                lpPidanaReq.kewarganegaraan_pelapor = sipil?.kewarganegaraan_sipil
                lpPidanaReq.alamat_pelapor = sipil?.alamat_sipil
                lpPidanaReq.nik_ktp_pelapor = sipil?.nik_sipil
                lpPidanaReq.no_telp_pelapor = sipil?.no_telp_sipil
                lpPidanaReq.uraian_pelanggaran = sessionManager.getUraianPelanggaranLP()
                Log.e("pidanaAll", "${lpPidanaReq}")
            }
            "disiplin" -> {
                lpDisiplinReq.no_lp = sessionManager.getNoLP()
                lpDisiplinReq.uraian_pelanggaran = jenisLP
                lpDisiplinReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpDisiplinReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpDisiplinReq.kota_buat_laporan = sessionManager.getKotaBuatLp()
                lpDisiplinReq.tanggal_buat_laporan = sessionManager.getTglBuatLp()
                lpDisiplinReq.nama_yang_mengetahui = sessionManager.getNamaPimpBidLp()
                lpDisiplinReq.pangkat_yang_mengetahui = sessionManager.getPangkatPimpBidLp()
                lpDisiplinReq.nrp_yang_mengetahui = sessionManager.getNrpPimpBidLp()
                lpDisiplinReq.jabatan_yang_mengetahui = sessionManager.getJabatanPimpBidLp()
                lpDisiplinReq.id_personel_pelapor = idPelapor
                lpDisiplinReq.macam_pelanggaran = sessionManager.getMacamPelanggaranLP()
                lpDisiplinReq.keterangan_pelapor = sessionManager.getKetPelaporLP()
                lpDisiplinReq.kronologis_dari_pelapor= sessionManager.getKronologisPelapor()
                lpDisiplinReq.rincian_pelanggaran_disiplin =sessionManager.getRincianDisiplin()
                lpDisiplinReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>

                Log.e("add_disiplin", "$lpDisiplinReq")

            }
        }

    }
    companion object{
        const val ID_PELAPOR ="ID_PELAPOR"
        const val SIPIL ="SIPIL"
    }
}