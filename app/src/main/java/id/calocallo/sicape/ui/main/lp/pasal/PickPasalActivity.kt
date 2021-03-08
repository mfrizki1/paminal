package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.*
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesDetailsLookup
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.*
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.DokLpResp
import id.calocallo.sicape.ui.main.lp.disiplin.ListLpDisiplinActivity
import id.calocallo.sicape.ui.main.lp.kke.ListLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesItemKeyProvider
import id.calocallo.sicape.ui.main.lp.pidana.ListLpPidanaActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal.*
import kotlinx.android.synthetic.main.activity_pick_saksi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickPasalActivity : BaseActivity(), ActionMode.Callback {
    private lateinit var sessionManager1: SessionManager1
    private val listPasal = mutableListOf<PasalResp>()
    private lateinit var adapterPasalTes: PasalTesAdapter
    private var selectedIdPasal: MutableList<PasalResp> = mutableListOf()
    private var tracker: SelectionTracker<PasalResp>? = null
    private val listIdPasal = ArrayList<ListIdPasalReq>()

    //req
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var lpDisiplinReq = LpDisiplinReq()

    /*action mode*/
    private var actionMode: ActionMode? = null

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
        apiListPasal()
//        getListPasal(listPasal)

        //getSipil if not empty
        val sipil = intent?.extras?.getParcelable<SipilPelaporReq>(SIPIL)

        val idPelapor = intent.extras?.getInt(ID_PELAPOR)
        bindProgressButton(btn_save_lp_all)
        btn_save_lp_all.attachTextChangeAnimator()
        btn_save_lp_all.setOnClickListener {

            for (i in 0 until selectedIdPasal.size) {
                listIdPasal.add(ListIdPasalReq(selectedIdPasal[i].id))
            }

            addAllLp(sessionManager1.getJenisLP(), idPelapor, sipil)

            /*if (sessionManager1.getJenisLP() != "kode_etik") {
                addAllLp(sessionManager1.getJenisLP(), idPelapor, sipil)
            } else {
                sessionManager1.setListPasalLP(listIdPasal)
                val intent = Intent(this, PickSaksiActivity::class.java)
                intent.putExtra(ID_PELAPOR_SAKSI, idPelapor)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }*/
        }

        btn_add_single_pasal.setOnClickListener {
            val intent = Intent(this, AddPasalActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        searchView()

    }

    private fun apiListPasal() {
        NetworkConfig().getServLp().getAllPasal(
            "Bearer ${sessionManager1.fetchAuthToken()}"
        ).enqueue(object : Callback<ArrayList<PasalResp>> {
            override fun onResponse(
                call: Call<ArrayList<PasalResp>>,
                response: Response<ArrayList<PasalResp>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { getListPasal(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<PasalResp>>, t: Throwable) {
                Toast.makeText(this@PickPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()

            }
        })
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
            object : SelectionTracker.SelectionObserver<PasalResp>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedIdPasal = it.selection.toMutableList()
                        Log.e("idPasal", "$selectedIdPasal")
                        if (selectedIdPasal.isEmpty()) {
                            actionMode?.finish()
                        } else {
                            if (actionMode == null) actionMode =
                                startSupportActionMode(this@PickPasalActivity)
                            actionMode?.title = " ${selectedIdPasal.size}"
                        }
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


        when (jenisLP) {
            "pidana" -> {
//                lpPidanaReq.no_lp = sessionManager1.getNoLP()
                lpPidanaReq.id_satuan_kerja = sessionManager1.getIDPersonelTerlapor()
                lpPidanaReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
//                lpPidanaReq.id_personel_pelapor = idPelapor
                lpPidanaReq.nama_kep_spkt = sessionManager1.getNamaPimpBidLp()
                lpPidanaReq.pangkat_kep_spkt = sessionManager1.getPangkatPimpBidLp()
                lpPidanaReq.nrp_kep_spkt = sessionManager1.getNrpPimpBidLp()
                lpPidanaReq.jabatan_kep_spkt = sessionManager1.getJabatanPimpBidLp()
//                lpPidanaReq.status_pelapor = sessionManager1.getPelapor()
//                lpPidanaReq.pembukaan_laporan = sessionManager1.getPembukaanLpLP()
                lpPidanaReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = listIdPasal
                lpPidanaReq.waktu_buat_laporan = sessionManager1.getWaktuBuatLaporan()
//                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
                lpPidanaReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpPidanaReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpPidanaReq.nama_pelapor = sipil?.nama_sipil
                lpPidanaReq.agama_pelapor = sipil?.agama_sipil
                lpPidanaReq.pekerjaan_pelapor = sipil?.pekerjaan_sipil
                lpPidanaReq.kewarganegaraan_pelapor = sipil?.kewarganegaraan_sipil
                lpPidanaReq.alamat_pelapor = sipil?.alamat_sipil
                lpPidanaReq.nik_ktp_pelapor = sipil?.nik_sipil
                lpPidanaReq.no_telp_pelapor = sipil?.no_telp_sipil
                lpPidanaReq.jenis_kelamin_pelapor = sipil?.jenis_kelamin
                lpPidanaReq.tempat_lahir_pelapor = sipil?.tempat_lahir_pelapor
                lpPidanaReq.tanggal_lahir_pelapor = sipil?.tanggal_lahir_pelapor
                lpPidanaReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
//                lpPidanaReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("addLp", "$lpPidanaReq")
                apiAddLpPidana()
            }
            "disiplin" -> {
                lpDisiplinReq.id_satuan_kerja = sessionManager1.getIDPersonelTerlapor()
                lpDisiplinReq.waktu_buat_laporan = sessionManager1.getWaktuBuatLaporan()
                lpDisiplinReq.no_lp = sessionManager1.getNoLP()
                lpDisiplinReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
//                lpDisiplinReq.id_personel_operator = sessionManager1.fetchUserPersonel()?.id
                lpDisiplinReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpDisiplinReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpDisiplinReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpDisiplinReq.nama_kabid_propam = sessionManager1.getNamaPimpBidLp()
                lpDisiplinReq.pangkat_kabid_propam = sessionManager1.getPangkatPimpBidLp()
                lpDisiplinReq.nrp_kabid_propam = sessionManager1.getNrpPimpBidLp()
                lpDisiplinReq.jabatan_kabid_propam = sessionManager1.getJabatanPimpBidLp()
                lpDisiplinReq.id_personel_pelapor = idPelapor
                lpDisiplinReq.macam_pelanggaran = sessionManager1.getMacamPelanggaranLP()
                lpDisiplinReq.keterangan_pelapor = sessionManager1.getKetPelaporLP()
                lpDisiplinReq.kronologis_dari_pelapor = sessionManager1.getKronologisPelapor()
                lpDisiplinReq.rincian_pelanggaran_disiplin = sessionManager1.getRincianDisiplin()
                lpDisiplinReq.pasal_dilanggar = listIdPasal
//                lpDisiplinReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("addDisipin", "$lpDisiplinReq")
                apiAddLpDisiplin()
            }
            "kode_etik" -> {
                //                lpKKeReq.no_lp = sessionManager1.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.id_satuan_kerja = 123
                lpKKeReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
                lpKKeReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpKKeReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpKKeReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = idPelapor
//                lpKKeReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpKKeReq.nama_kabid_propam = sessionManager1.getNamaPimpBidLp()
                lpKKeReq.pangkat_kabid_propam = sessionManager1.getPangkatPimpBidLp()
                lpKKeReq.nrp_kabid_propam = sessionManager1.getNrpPimpBidLp()
                lpKKeReq.jabatan_kabid_propam = sessionManager1.getJabatanPimpBidLp()
                lpKKeReq.alat_bukti = sessionManager1.getAlatBukiLP()
                lpKKeReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpKKeReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
                lpKKeReq.pasal_dilanggar = sessionManager1.getListPasalLP()
//                lpKKeReq.saksi_kode_etik = listSaksi as ArrayList<SaksiReq>
//                lpKKeReq.saksi_kode_etik = selectedSaksi as ArrayList<LpSaksiResp>
//                lpKKeReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("KKE", "$lpKKeReq")
                apiAddKke()
            }
        }

    }

    private fun apiAddKke() {
        NetworkConfig().getServLp().addLpKke("Bearer ${sessionManager1.fetchAuthToken()}", lpKKeReq)
            .enqueue(object :
                Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(this@PickPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_lp_all.hideDrawable(R.string.not_save)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp kode etik saved succesfully") {
                        val animatedDrawable =
                            ContextCompat.getDrawable(
                                this@PickPasalActivity,
                                R.drawable.animated_check
                            )!!
                        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
                        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
                        btn_save_lp_all.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent =
                                Intent(this@PickPasalActivity, ListLpKodeEtikActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }, 500)
                    } else {
                        Toast.makeText(
                            this@PickPasalActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        btn_save_lp_all.hideDrawable(R.string.not_save)
                    }
                }
            })
    }

    private fun apiAddLpDisiplin() {
        NetworkConfig().getServLp()
            .addLpDisiplin("Bearer ${sessionManager1.fetchAuthToken()}", lpDisiplinReq)
            .enqueue(object : Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(this@PickPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_lp_all.hideDrawable(R.string.not_save)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp disiplin saved succesfully") {
                        btn_save_lp_all.hideDrawable(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent =
                                Intent(this@PickPasalActivity, ListLpDisiplinActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }, 500)
                    } else {
                        Toast.makeText(
                            this@PickPasalActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        ).show()
                        btn_save_lp_all.hideDrawable(R.string.not_save)
                    }
                }
            })
    }

    private fun apiAddLpPidana() {
        NetworkConfig().getServLp()
            .addLpPidana("Bearer ${sessionManager1.fetchAuthToken()}", lpPidanaReq)
            .enqueue(object : Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(this@PickPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_lp_all.hideDrawable(R.string.not_save)
                    Log.e("t", "$t")
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp pidana saved succesfully") {
                        val animatedDrawable =
                            ContextCompat.getDrawable(
                                this@PickPasalActivity,
                                R.drawable.animated_check
                            )!!
                        //Defined bounds are required for your drawable
                        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
                        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
                        btn_save_lp_all.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent =
                                Intent(this@PickPasalActivity, ListLpPidanaActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }, 500)
                    } else {
                        btn_save_lp_all.hideDrawable(R.string.not_save)
                    }
                }
            })
    }


    companion object {
        const val ID_PELAPOR = "ID_PELAPOR"
        const val SIPIL = "SIPIL"
    }


    private fun searchView() {
        pasal_searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPasalTes.filter.filter(newText)
                return true
            }

        })
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.let {
            val inflater: MenuInflater = it.menuInflater
            inflater.inflate(R.menu.action_mode_menu, menu)
            return true
        }
        return false
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_view_list -> {
                val listNamePasal = ArrayList<String>()
                for (i in 0 until selectedIdPasal.size) {
                    selectedIdPasal[i].nama_pasal?.let { listNamePasal.add(it) }
                }
                Toast.makeText(this, "$listNamePasal", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        adapterPasalTes.tracker?.clearSelection()
        adapterPasalTes.notifyDataSetChanged()
        actionMode = null
    }
}