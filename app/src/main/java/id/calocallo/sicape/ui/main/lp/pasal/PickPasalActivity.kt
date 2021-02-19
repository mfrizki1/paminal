package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.*
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesDetailsLookup
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.request.SipilPelaporReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesItemKeyProvider
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity.Companion.ID_PELAPOR_SAKSI
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickPasalActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private val listPasal = mutableListOf<PasalResp>()
    private lateinit var adapterPasalTes: PasalTesAdapter
    private var selectedIdPasal: MutableList<PasalResp> = mutableListOf()
    private var tracker: SelectionTracker<PasalResp>? = null

    //req
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var lpDisiplinReq = LpDisiplinReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_pasal)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)

        when (sessionManager1.getJenisLP()) {
            "pidana" -> {
                supportActionBar?.title = "Tambah Data Laporan Pidana"
            }
            "disiplin" -> {
                supportActionBar?.title = "Tambah Data Laporan Disiplin"
            }
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        listPasal.add(
            PasalResp(
                1,
                5,
                "Pasal 1",
                "xxxx"
            )
        )
        listPasal.add(
            PasalResp(
                2,
                4,
                "Pasal 2",
                "xxxx"
            )
        )
        listPasal.add(
            PasalResp(
                3,
                17,
                "Pasal 3",
                "xxxx"
            )
        )
        listPasal.add(
            PasalResp(
                4,
                12,
                "Pasal 4",
                "xxxx"
            )
        )
        getListPasal(listPasal)

        //getSipil if not empty
        val sipil = intent?.extras?.getParcelable<SipilPelaporReq>(SIPIL)

        val idPelapor = intent.extras?.getInt(ID_PELAPOR)
        bindProgressButton(btn_save_lp_all)
        btn_save_lp_all.attachTextChangeAnimator()
        btn_save_lp_all.setOnClickListener {
            if (sessionManager1.getJenisLP() != "kode_etik") {
                addAllLp(sessionManager1.getJenisLP(), idPelapor, sipil)
            } else {
                sessionManager1.setListPasalLP(selectedIdPasal as ArrayList<LpPasalResp>)
                val intent = Intent(this, PickSaksiActivity::class.java)
                intent.putExtra(ID_PELAPOR_SAKSI, idPelapor)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        btn_add_single_pasal.setOnClickListener {
            val intent = Intent(this, AddPasalLpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //apakah kke / tidak


    }

    private fun getListPasal(listPasal: MutableList<PasalResp>) {
        rv_list_pasal_2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPasalTes = PasalTesAdapter(this, listPasal as ArrayList<PasalResp>)
        rv_list_pasal_2.adapter = adapterPasalTes

        tracker = SelectionTracker.Builder<PasalResp>(
            "pasalSelection",
            rv_list_pasal_2,
            PasalTesItemKeyProvider(adapterPasalTes),
            PasalTesDetailsLookup(rv_list_pasal_2),
            StorageStrategy.createParcelableStorage(PasalResp::class.java)
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
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_lp_all.showProgress {
            progressColor = Color.WHITE
        }


        btn_save_lp_all.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }

        btn_save_lp_all.hideDrawable(R.string.save)

        when (jenisLP) {
            "pidana" -> {
                lpPidanaReq.no_lp = sessionManager1.getNoLP()
                lpPidanaReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpPidanaReq.id_personel_pelapor = idPelapor
                lpPidanaReq.nama_yang_mengetahui = sessionManager1.getNamaPimpBidLp()
                lpPidanaReq.pangkat_yang_mengetahui = sessionManager1.getPangkatPimpBidLp()
                lpPidanaReq.nrp_yang_mengetahui = sessionManager1.getNrpPimpBidLp()
                lpPidanaReq.jabatan_yang_mengetahui = sessionManager1.getJabatanPimpBidLp()
                lpPidanaReq.status_pelapor = sessionManager1.getPelapor()
                lpPidanaReq.pembukaan_laporan = sessionManager1.getPembukaanLpLP()
                lpPidanaReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
                lpPidanaReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpPidanaReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpPidanaReq.nama_pelapor = sipil?.nama_sipil
                lpPidanaReq.agama_pelapor = sipil?.agama_sipil
                lpPidanaReq.pekerjaan_pelapor = sipil?.pekerjaan_sipil
                lpPidanaReq.kewarganegaraan_pelapor = sipil?.kewarganegaraan_sipil
                lpPidanaReq.alamat_pelapor = sipil?.alamat_sipil
                lpPidanaReq.nik_ktp_pelapor = sipil?.nik_sipil
                lpPidanaReq.no_telp_pelapor = sipil?.no_telp_sipil
                lpPidanaReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
                lpPidanaReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("pidanaAll", "$lpPidanaReq")
            }
            "disiplin" -> {
                lpDisiplinReq.no_lp = sessionManager1.getNoLP()
                lpDisiplinReq.uraian_pelanggaran = jenisLP
                lpDisiplinReq.id_personel_operator = sessionManager1.fetchUserPersonel()?.id
                lpDisiplinReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpDisiplinReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpDisiplinReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpDisiplinReq.nama_yang_mengetahui = sessionManager1.getNamaPimpBidLp()
                lpDisiplinReq.pangkat_yang_mengetahui = sessionManager1.getPangkatPimpBidLp()
                lpDisiplinReq.nrp_yang_mengetahui = sessionManager1.getNrpPimpBidLp()
                lpDisiplinReq.jabatan_yang_mengetahui = sessionManager1.getJabatanPimpBidLp()
                lpDisiplinReq.id_personel_pelapor = idPelapor
                lpDisiplinReq.macam_pelanggaran = sessionManager1.getMacamPelanggaranLP()
                lpDisiplinReq.keterangan_pelapor = sessionManager1.getKetPelaporLP()
                lpDisiplinReq.kronologis_dari_pelapor = sessionManager1.getKronologisPelapor()
                lpDisiplinReq.rincian_pelanggaran_disiplin = sessionManager1.getRincianDisiplin()
                lpDisiplinReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
                lpDisiplinReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("add_disiplin", "$lpDisiplinReq")

            }
        }

    }

    companion object {
        const val ID_PELAPOR = "ID_PELAPOR"
        const val SIPIL = "SIPIL"
    }
}