package id.calocallo.sicape.ui.main.editpersonel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPersonelActivity : BaseActivity() {
    private var jk: String? = null
    private var agmNow: String? = null
    private var agmBefore = ""
    private var sttsKawin: Int? = null
    private val addPersonelReq = AddPersonelReq()
    private lateinit var sessionManager: SessionManager
    private var idPersonel = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_personel)

        sessionManager = SessionManager(this)

        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        idPersonel = detail?.id.toString()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama.toString()
        getPersonel(detail)

        initSpinner(spinner_jk_edit, spinner_stts_kwn_edit, sp_agm_now_edit, sp_agm_before_edit)

        bindProgressButton(btn_save_personel_edit)
        btn_save_personel_edit.attachTextChangeAnimator()
        btn_save_personel_edit.setOnClickListener {
            doUpdate(sessionManager.fetchHakAkses())

        }
        getSatker()

    }

    private fun getPersonel(detail: PersonelModel?) {
        when (detail?.agama_sekarang) {
            "islam" -> {
                agmNow = "islam"
                sp_agm_now_edit.setText("Islam")
            }
            "katolik" -> {
                agmNow = "katolik"
                sp_agm_now_edit.setText("Katolik")
            }
            "protestan" -> {
                agmNow = "protestan"
                sp_agm_now_edit.setText("Protestan")
            }
            "buddha" -> {
                agmNow = "buddha"
                sp_agm_now_edit.setText("Buddha")
            }
            "hindu" -> {
                agmNow = "hindu"
                sp_agm_now_edit.setText("Hindu")
            }
            "konghuchu" -> {
                agmNow = "konghuchu"
                sp_agm_now_edit.setText("Konghuchu")
            }
            else -> {
                agmNow = ""
            }
        }

        when (detail?.agama_sebelumnya) {
            "islam" -> {
                agmBefore = "islam"
                sp_agm_before_edit.setText("Islam")
            }
            "katolik" -> {
                agmBefore = "katolik"
                sp_agm_before_edit.setText("Katolik")
            }
            "protestan" -> {
                agmBefore = "protestan"
                sp_agm_before_edit.setText("Protestan")
            }
            "buddha" -> {
                agmBefore = "buddha"
                sp_agm_before_edit.setText("Buddha")
            }
            "hindu" -> {
                agmBefore = "hindu"
                sp_agm_before_edit.setText("Hindu")
            }
            "konghuchu" -> {
                agmBefore = "konghuchu"
                sp_agm_before_edit.setText("Konghuchu")
            }
            else -> {
                agmBefore = ""
            }
        }

        when (detail?.jenis_kelamin) {
            "laki_laki" -> {
                jk = "laki_laki"
                spinner_jk_edit.setText("Laki-Laki")
            }
            "perempuan" -> {
                jk = "perempuan"
                spinner_jk_edit.setText("Perempuan")
            }
            else -> {
                jk = ""
            }
        }

        when (detail?.id_satuan_kerja) {
            1 -> spinner_kesatuan_edit.setText("POLRESTA BANJARMASIN")
            2 -> spinner_kesatuan_edit.setText("POLRES BANJARBARU")
            3 -> spinner_kesatuan_edit.setText("POLRES BANJAR")
            4 -> spinner_kesatuan_edit.setText("POLRES TAPIN")
            5 -> spinner_kesatuan_edit.setText("POLRES HULU SUNGAI SELATAN")
            6 -> spinner_kesatuan_edit.setText("POLRES HULU SUNGAI TENGAH")
            7 -> spinner_kesatuan_edit.setText("POLRES HULU SUNGAI UTARA")
            8 -> spinner_kesatuan_edit.setText("POLRES BALANGAN")
            9 -> spinner_kesatuan_edit.setText("POLRES TABALONG")
            10 -> spinner_kesatuan_edit.setText("POLRES TANAH LAUT")
            11 -> spinner_kesatuan_edit.setText("POLRES TANAH BUMBU")
            12 -> spinner_kesatuan_edit.setText("POLRES KOTABARU")
            13 -> spinner_kesatuan_edit.setText("POLRES BATOLA")
            14 -> spinner_kesatuan_edit.setText("SAT BRIMOB")
            15 -> spinner_kesatuan_edit.setText("SAT POLAIR")
            16 -> spinner_kesatuan_edit.setText("SPN BANJARBARU")
            17 -> spinner_kesatuan_edit.setText("POLDA KALSEL")
            18 -> spinner_kesatuan_edit.setText("SARPRAS")
        }
        when (detail?.status_perkawinan) {
            "0" -> {
                spinner_stts_kwn_edit.setText("Tidak")
                sttsKawin = 0
                txt_layout_kawin_berapa_edit.visibility = View.GONE
                cl_ttl_kawin_edit.visibility = View.GONE
            }
            "1" -> {
                spinner_stts_kwn_edit.setText("Ya")
                sttsKawin = 1
                txt_layout_kawin_berapa_edit.visibility = View.VISIBLE
                cl_ttl_kawin_edit.visibility = View.VISIBLE
            }
        }
        addPersonelReq.id_satuan_kerja = detail?.id_satuan_kerja
        addPersonelReq.jenis_kelamin = jk
        addPersonelReq.agama_sekarang = agmNow
        addPersonelReq.agama_sebelumnya = agmBefore
        addPersonelReq.status_perkawinan = sttsKawin

        edt_bahasa_edit.setText(detail?.bahasa)
        edt_almt_ktp_edit.setText(detail?.alamat_sesuai_ktp)
        edt_almt_rmh_edit.setText(detail?.alamat_rumah)
        edt_hobi_edit.setText(detail?.hobi)
        edt_kebiasaan_edit.setText(detail?.kebiasaan)
        edt_kwg_edit.setText(detail?.kewarganegaraan)
        edt_nrp_edit.setText(detail?.nrp)
        edt_pangkat_edit.setText(detail?.pangkat.toString().toUpperCase())
        edt_pekerjaan_edit.setText(detail?.jabatan)
        edt_suku_edit.setText(detail?.ras)
        edt_jmlh_anak_edit.setText(detail?.jumlah_anak)
        edt_kwin_berapa_edit.setText(detail?.perkawinan_keberapa)
        edt_nama_alias_edit.setText(detail?.nama_alias)
        edt_nama_lengkap_edit.setText(detail?.nama)
        edt_no_ktp_edit.setText(detail?.no_ktp)
        edt_no_telp_pribadi_edit.setText(detail?.no_telp)
        edt_no_telp_rmh_edit.setText(detail?.no_telp_rumah)
        edt_tgl_kwn_edit.setText(detail?.tanggal_perkawinan)
        edt_tmpt_kwn_edit.setText(detail?.tempat_perkawinan)
        edt_tgl_ttl_edit.setText(detail?.tanggal_lahir)
        edt_tmpt_ttl_edit.setText(detail?.tempat_lahir)
        edt_how_to_kwg_edit.setText(detail?.cara_peroleh_kewarganegaraan)
    }

    private fun getSatker() {
        NetworkConfig().getService().showSatker(
            "Bearer ${sessionManager.fetchAuthToken()}"
        ).enqueue(object : Callback<ArrayList<SatKerResp>> {
            override fun onFailure(call: Call<ArrayList<SatKerResp>>, t: Throwable) {
                Toast.makeText(this@EditPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<ArrayList<SatKerResp>>,
                response: Response<ArrayList<SatKerResp>>
            ) {
                if (response.isSuccessful) {
                    val listNameSatker = response.body()
                    val listSatker = listOf(
                        "POLRESTA BANJARMASIN",
                        "POLRES BANJARBARU",
                        "POLRES BANJAR",
                        "POLRES TAPIN",
                        "POLRES HULU SUNGAI SELATAN",
                        "POLRES HULU SUNGAI TENGAH",
                        "POLRES HULU SUNGAI UTARA",
                        "POLRES BALANGAN",
                        "POLRES TABALONG",
                        "POLRES TANAH LAUT",
                        "POLRES TANAH BUMBU",
                        "POLRES KOTABARU",
                        "POLRES BATOLA",
                        "SAT BRIMOB",
                        "SAT POLAIR",
                        "SPN BANJARBARU",
                        "POLDA KALSEL",
                        "SARPRAS"
                    )
                    val adapterSatker =
                        ArrayAdapter(this@EditPersonelActivity, R.layout.item_spinner, listSatker)

                    spinner_kesatuan_edit.setAdapter(adapterSatker)
                    spinner_kesatuan_edit.setOnItemClickListener { parent, view, position, id ->
                        for (i in 0..listNameSatker?.size!!) {
                            when (position) {
                                i -> {
                                    addPersonelReq.id_satuan_kerja = listNameSatker[i].id
                                    Log.e("satker", "satker ${listNameSatker[i].id}")
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@EditPersonelActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doUpdate(hakAkses: String?) {
        addPersonelReq.tempat_perkawinan = edt_tmpt_kwn_edit.text.toString()
        addPersonelReq.tanggal_perkawinan = edt_tgl_kwn_edit.text.toString()
        addPersonelReq.tempat_lahir = edt_tmpt_ttl_edit.text.toString()
        addPersonelReq.tanggal_lahir = edt_tgl_ttl_edit.text.toString()
        addPersonelReq.nama = edt_nama_lengkap_edit.text.toString()
        addPersonelReq.nama_alias = edt_nama_alias_edit.text.toString()
        addPersonelReq.no_telp = edt_no_telp_pribadi_edit.text.toString()
        addPersonelReq.ras = edt_suku_edit.text.toString()
        addPersonelReq.jabatan = edt_pekerjaan_edit.text.toString()
        addPersonelReq.pangkat = edt_pangkat_edit.text.toString()
        addPersonelReq.nrp = edt_nrp_edit.text.toString()
        addPersonelReq.alamat_rumah = edt_almt_rmh_edit.text.toString()
        addPersonelReq.no_telp_rumah = edt_no_telp_rmh_edit.text.toString()
        addPersonelReq.kewarganegaraan = edt_kwg_edit.text.toString()
        addPersonelReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_edit.text.toString()
        addPersonelReq.status_perkawinan = sttsKawin
        addPersonelReq.perkawinan_keberapa = edt_kwin_berapa_edit.text.toString()
        addPersonelReq.jumlah_anak = Integer.parseInt(edt_jmlh_anak_edit.text.toString())
        addPersonelReq.alamat_sesuai_ktp = edt_almt_ktp_edit.text.toString()
        addPersonelReq.no_ktp = edt_no_ktp_edit.text.toString()
        addPersonelReq.hobi = edt_hobi_edit.text.toString()
        addPersonelReq.kebiasaan = edt_kebiasaan_edit.text.toString()
        addPersonelReq.bahasa = edt_bahasa_edit.text.toString()

        btn_save_personel_edit.showProgress {
            progressColor = Color.WHITE
        }
        //animatedButton
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
//        Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        //validasi Hak Akses & API
        if (hakAkses != "operator") {
            NetworkConfig().getService().updatePersonel(
                "Bearer ${sessionManager.fetchAuthToken()}",
                idPersonel,
                addPersonelReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@EditPersonelActivity, "Gagal Update", Toast.LENGTH_SHORT)
                        .show()
                    btn_save_personel_edit.hideDrawable(R.string.save)

                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data identitas updated succesfully") {
//                        Toast.makeText(this@EditPersonelActivity, R.string.data_saved, Toast.LENGTH_SHORT).show()
                            btn_save_personel_edit.showDrawable(animatedDrawable) {
                                buttonTextRes = R.string.data_updated
                                textMarginRes = R.dimen.space_10dp
                            }
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 500)

                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                btn_save_personel_edit.hideDrawable(R.string.save)
                            }, 3000)
                            btn_save_personel_edit.hideDrawable(R.string.not_update)
                        }
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_personel_edit.hideDrawable(R.string.save)
                        }, 3000)
                        btn_save_personel_edit.hideDrawable(R.string.not_update)
                    }
                }
            })
        }

    }

    //Data Spinner JK, STTS_KAWIN, AGAMA_SKRG, AGAMA_SBLM
    private fun initSpinner(
        sp_jk: AutoCompleteTextView,
        sp_stts_kawin: AutoCompleteTextView,
        sp_agama_skrg: AutoCompleteTextView,
        sp_agama_sblm: AutoCompleteTextView
    ) {
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        sp_jk.setAdapter(adapterJk)
        sp_jk.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                addPersonelReq.jenis_kelamin = "laki_laki"
                jk = "laki_laki"
            } else {
                jk = "perempuan"
                addPersonelReq.jenis_kelamin = "perempuan"
            }
//            jk = parent.getItemAtPosition(position).toString()
//            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }

        val sttsKawinItem = listOf("Ya", "Tidak")
        val adapterSttusKawin = ArrayAdapter(this, R.layout.item_spinner, sttsKawinItem)
        sp_stts_kawin.setAdapter(adapterSttusKawin)
        sp_stts_kawin.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {  // YA
                sttsKawin = 1
                txt_layout_kawin_berapa_edit.visibility = View.VISIBLE
                cl_ttl_kawin_edit.visibility = View.VISIBLE
            } else {              //Tidak
                sttsKawin = 0
                txt_layout_kawin_berapa_edit.visibility = View.GONE
                cl_ttl_kawin_edit.visibility = View.GONE
            }
//            sttsKawin = parent.getItemAtPosition(position)
//            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }

        val agamaItem =
            listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu", "Tidak Ada")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        sp_agama_skrg.setAdapter(adapterAgama)
        sp_agama_skrg.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agmNow = "islam"
                addPersonelReq.agama_sekarang = "islam"
            } else if (position == 1) {
                agmNow = "katolik"
                addPersonelReq.agama_sekarang = "katolik"
            } else if (position == 2) {
                agmNow = "protestan"
                addPersonelReq.agama_sekarang = "protestan"
            } else if (position == 3) {
                agmNow = "buddha"
                addPersonelReq.agama_sekarang = "buddha"
            } else if (position == 4) {
                agmNow = "hindu"
                addPersonelReq.agama_sekarang = "hindu"
            } else if (position == 5) {
                agmNow = "konghuchu"
                addPersonelReq.agama_sekarang = "konghuchu"
            } else {
                agmNow = ""
                addPersonelReq.agama_sekarang = ""
            }
        }
        sp_agama_sblm.setAdapter(adapterAgama)
        sp_agama_sblm.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agmBefore = "islam"
                addPersonelReq.agama_sebelumnya = "islam"
            } else if (position == 1) {
                agmBefore = "katolik"
                addPersonelReq.agama_sebelumnya = "v"
            } else if (position == 2) {
                agmBefore = "protestan"
                addPersonelReq.agama_sebelumnya = "protestan"
            } else if (position == 3) {
                agmBefore = "buddha"
                addPersonelReq.agama_sebelumnya = "buddha"
            } else if (position == 4) {
                agmBefore = "hindu"
                addPersonelReq.agama_sebelumnya = "hindu"
            } else if (position == 5) {
                agmBefore = "konghuchu"
                addPersonelReq.agama_sebelumnya = "konghuchu"
            } else {
                agmBefore = ""
                addPersonelReq.agama_sebelumnya = ""
            }
        }
    }

}