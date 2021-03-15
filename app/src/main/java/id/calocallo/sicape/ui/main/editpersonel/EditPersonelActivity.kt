package id.calocallo.sicape.ui.main.editpersonel

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
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
    private lateinit var sessionManager1: SessionManager1
    private var idPersonel = ""
    private var idSatker: Int? = null
    private var jenisKesatuan: String? = null

    companion object {
        const val RES_POLRES_EDIT = 121
        const val RES_POLSEK_EDIT = 122
        const val RES_POLDA_EDIT = 123

        const val REQ_POLDA_EDIT = 111
        const val REQ_POLRES_EDIT = 112
        const val REQ_POLSEK_EDIT = 113
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_personel)

        sessionManager1 = SessionManager1(this)

        val bundle = intent.extras
        val detail = bundle?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        idPersonel = detail?.id.toString()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama.toString()
        getPersonel(detail)

        initSpinner()

        bindProgressButton(btn_save_personel_edit)
        btn_save_personel_edit.attachTextChangeAnimator()
        btn_save_personel_edit.setOnClickListener {
            doUpdate(sessionManager1.fetchHakAkses())

        }
//        getSatker()

        btn_personel_edit_polda.setOnClickListener {

            btn_personel_edit_polda.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polda.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polres.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PoldaActivity::class.java),
                AddPersonelActivity.REQ_POLDA
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            Log.e("polda text", "${txt_satker_polda_edit.text}")
            Log.e("polres text", "${txt_satker_polda_edit.text}")
            Log.e("polsek text", "${txt_satker_polda_edit.text}")
            txt_polres_edit.gone()
            txt_polres_edit.text = ""

            txt_sat_polres_edit.gone()
            txt_sat_polres_edit.text = ""

            txt_polsek_edit.gone()
            txt_polsek_edit.text = ""

            txt_sat_polsek_edit.gone()
            txt_sat_polsek_edit.text = ""

        }
        btn_personel_edit_polres.setOnClickListener {
//            spinner_satker_edit.hint = "Satker Polres"
//            txt_layout_satker_personel_edit.visible()

            btn_personel_edit_polres.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polres.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polres")
            startActivityForResult(
                Intent(this, PolresActivity::class.java),
                AddPersonelActivity.REQ_POLRES
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polda_edit.gone()
            txt_satker_polda_edit.text = ""

            txt_polsek_edit.gone()
            txt_polsek_edit.text = ""

            txt_sat_polsek_edit.gone()
            txt_sat_polsek_edit.text = ""
        }
        btn_personel_edit_polsek.setOnClickListener {
//            spinner_satker_edit.hint = "Satker Polsek"
//            txt_layout_satker_personel_edit.visible()

            btn_personel_edit_polsek.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polsek.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polres.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polsek")
            val intent = Intent(this, PolsekActivity::class.java)
//            intent.putExtra(PolresActivity.IS_POLSEK, PolresActivity.IS_POLSEK)
            startActivityForResult(intent, AddPersonelActivity.REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)


            txt_satker_polda_edit.gone()
            txt_satker_polda_edit.text = ""

            txt_polres_edit.gone()
            txt_polres_edit.text = ""

            txt_sat_polres_edit.gone()
            txt_sat_polres_edit.text = ""
        }

    }

    private fun getPersonel(detail: AllPersonelModel1?) {
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

        /*
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

         */
        when (detail?.status_perkawinan) {
            0 -> {
                spinner_stts_kwn_edit.setText("Tidak")
                sttsKawin = 0
                txt_layout_kawin_berapa_edit.visibility = View.GONE
                cl_ttl_kawin_edit.visibility = View.GONE
            }
            1 -> {
                spinner_stts_kwn_edit.setText("Ya")
                sttsKawin = 1
                txt_layout_kawin_berapa_edit.visibility = View.VISIBLE
                cl_ttl_kawin_edit.visibility = View.VISIBLE
            }
        }
        addPersonelReq.id_satuan_kerja = detail?.satuan_kerja?.id
        addPersonelReq.jenis_kelamin = jk
        addPersonelReq.agama_sekarang = agmNow
        addPersonelReq.agama_sebelumnya = agmBefore
        addPersonelReq.status_perkawinan = sttsKawin

        edt_aliran_kepercayaan_personel_edit.setText(detail?.aliran_kepercayaan)
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
        edt_jmlh_anak_edit.setText(detail?.jumlah_anak.toString())
        edt_kwin_berapa_edit.setText(detail?.perkawinan_keberapa.toString())
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

        txt_satker_polda_edit.visible()
        txt_satker_polda_edit.text = "${detail?.satuan_kerja?.kesatuan}"
        idSatker = detail?.satuan_kerja?.id
        /*when(detail?.id_satuan_kerja){
            "polda"->{
                txt_satker_polda_edit.visible()
                btn_personel_edit_polda.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                btn_personel_edit_polda.setTextColor(resources.getColor(R.color.white))
            }
            "polres"->{
                txt_polres_edit.visible()
                txt_sat_polres_edit.visible()
                btn_personel_edit_polres.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                btn_personel_edit_polres.setTextColor(resources.getColor(R.color.white))
            }
            "polsek"->{
                txt_polsek_edit.visible()
                txt_sat_polsek_edit.visible()
                btn_personel_edit_polsek.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                btn_personel_edit_polsek.setTextColor(resources.getColor(R.color.white))
            }
            else->{
                txt_satker_polda_edit.visible()
                txt_satker_polda_edit.text = "Masih Kosong"
            }
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            AddPersonelActivity.REQ_POLDA -> {
                if (resultCode == AddPersonelActivity.RES_POLDA) {
                    val polda = data?.getParcelableExtra<SatKerResp>(PoldaActivity.DATA_POLDA)
                    txt_satker_polda_edit.visible()
                    txt_satker_polda_edit.text = "Satuan Kerja : ${polda?.kesatuan}"
                    jenisKesatuan = "polda"
                    idSatker = polda?.id
                } else {
                    txt_satker_polda_edit.gone()
                }
            }
            AddPersonelActivity.REQ_POLRES -> {
                if (resultCode == AddPersonelActivity.RES_POLRES) {
                    val polres = data?.extras
                    val dataPolres =
                        polres?.getParcelable<SatKerResp>(SatPolresActivity.DATA_POLRES_SAT)
                    txt_sat_polres_edit.visible()
                    txt_sat_polres_edit.text = "Satuan Kerja  : ${dataPolres?.kesatuan}"
                    jenisKesatuan = "polres"
                    idSatker = dataPolres?.id
                } else {
                    txt_sat_polres_edit.gone()
                    txt_polres_edit.gone()
                }
            }
            AddPersonelActivity.REQ_POLSEK -> {
                if (resultCode == AddPersonelActivity.RES_POLSEK) {
                    val polsek = data?.extras
                    val dataPolsek = polsek?.getParcelable<SatKerResp>(SatPolsekActivity.UNIT_POLSEK)
                    txt_sat_polsek_edit.visible()
                    txt_sat_polsek_edit.text = "Satuan Kerja : ${dataPolsek?.kesatuan}"
                    jenisKesatuan = "polsek"
                    idSatker = dataPolsek?.id
                } else {
                    txt_sat_polsek_edit.gone()
                    txt_polsek_edit.gone()
                }
            }
        }
    }

    private fun getSatker() {
        NetworkConfig().getServPers().showSatker(
            "Bearer ${sessionManager1.fetchAuthToken()}"
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
        addPersonelReq.aliran_kepercayaan = edt_aliran_kepercayaan_personel_edit.text.toString()
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
        addPersonelReq.perkawinan_keberapa = edt_kwin_berapa_edit.text.toString().toInt()
        addPersonelReq.jumlah_anak = Integer.parseInt(edt_jmlh_anak_edit.text.toString())
        addPersonelReq.alamat_sesuai_ktp = edt_almt_ktp_edit.text.toString()
        addPersonelReq.no_ktp = edt_no_ktp_edit.text.toString()
        addPersonelReq.hobi = edt_hobi_edit.text.toString()
        addPersonelReq.kebiasaan = edt_kebiasaan_edit.text.toString()
        addPersonelReq.bahasa = edt_bahasa_edit.text.toString()
        addPersonelReq.jenis_kesatuan = jenisKesatuan
        addPersonelReq.id_satuan_kerja = idSatker
        Log.e("editPesonel", "$addPersonelReq")

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
            NetworkConfig().getServPers().updatePersonel(
                "Bearer ${sessionManager1.fetchAuthToken()}",
                idPersonel,
                addPersonelReq
            ).enqueue(object : Callback<Base1Resp<PersonelModelMax1>> {
                override fun onFailure(call: Call<Base1Resp<PersonelModelMax1>>, t: Throwable) {
                    Toast.makeText(this@EditPersonelActivity, "Gagal Update", Toast.LENGTH_SHORT)
                        .show()
                    btn_save_personel_edit.hideDrawable(R.string.save)

                }

                override fun onResponse(
                    call: Call<Base1Resp<PersonelModelMax1>>,
                    response: Response<Base1Resp<PersonelModelMax1>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data identitas personel updated succesfully") {
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
    private fun initSpinner() {
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        spinner_jk_edit.setAdapter(adapterJk)
        spinner_jk_edit.setOnItemClickListener { parent, view, position, id ->
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
        spinner_stts_kwn_edit.setAdapter(adapterSttusKawin)
        spinner_stts_kwn_edit.setOnItemClickListener { parent, view, position, id ->
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
        sp_agm_now_edit.setAdapter(adapterAgama)
        sp_agm_now_edit.setOnItemClickListener { parent, view, position, id ->
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
        sp_agm_before_edit.setAdapter(adapterAgama)
        sp_agm_before_edit.setOnItemClickListener { parent, view, position, id ->
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