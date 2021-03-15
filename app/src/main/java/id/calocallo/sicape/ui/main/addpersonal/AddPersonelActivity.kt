package id.calocallo.sicape.ui.main.addpersonal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.PendPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.util.*


class AddPersonelActivity : BaseActivity(){
    companion object {
        const val RES_POLRES = 121
        const val RES_POLSEK = 122
        const val RES_POLDA = 123

        const val REQ_POLDA = 111
        const val REQ_POLRES = 112
        const val REQ_POLSEK = 113
    }

    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager1: SessionManager1
//    private lateinit var searchableDialog: SearchableDialog
    private var jk: String? = null
    private var agmNow: String? = null
    private var agmBefore = ""
    private var sttsKawin: Int? = null
    private var idSatker: Int? = null
    private val addPersonelReq = AddPersonelReq()
    private var jenisKesatuan: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personel)

        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Personel"
        sheenValidator = SheenValidator(this)

        btn_next_personel.attachTextChangeAnimator()
        bindProgressButton(btn_next_personel)


        initSpinner()
        sheenValidator.registerAsRequired(edt_jmlh_anak)

        /*
//        sheenValidator.registerAsRequired(edt_nama_alias)
//        sheenValidator.registerAsRequired(edt_tmpt_ttl)
//        sheenValidator.registerAsRequired(edt_tgl_ttl)
//        sheenValidator.registerAsRequired(edt_pekerjaan)
//        sheenValidator.registerAsRequired(edt_pangkat)
//        sheenValidator.registerAsRequired(edt_nrp)
//        sheenValidator.registerAsRequired(edt_kesatuan)
//        sheenValidator.registerAsRequired(edt_almt_kntr)
//        sheenValidator.registerAsPhone(edt_no_telp_kntr)
//        sheenValidator.registerAsRequired(edt_almt_rmh)
//        sheenValidator.registerAsPhone(edt_no_telp_rmh)
//        sheenValidator.registerAsRequired(edt_kwg)
//        sheenValidator.registerAsRequired(edt_how_to_kwg)
//        sheenValidator.registerAsRequired(edt_tmpt_kwn)
//        sheenValidator.registerAsRequired(edt_tgl_kwn)
//        sheenValidator.registerAsRequired(edt_almt_ktp)
//        sheenValidator.registerAsRequired(edt_no_ktp)
//        sheenValidator.registerAsRequired(edt_hobi)
//        sheenValidator.registerAsRequired(edt_kebiasaan)
//        sheenValidator.registerAsRequired(edt_bahasa)

         */

        btn_personel_add_polda.setOnClickListener {
            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(Intent(this, PoldaActivity::class.java), REQ_POLDA)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            Log.e("polda text", "${txt_satker_polda.text}")
            Log.e("polres text", "${txt_satker_polda.text}")
            Log.e("polsek text", "${txt_satker_polda.text}")
            txt_polres_add.gone()
            txt_polres_add.text = ""

            txt_sat_polres_add.gone()
            txt_sat_polres_add.text = ""

            txt_polsek_add.gone()
            txt_polsek_add.text = ""

            txt_sat_polsek_add.gone()
            txt_sat_polsek_add.text = ""

        }
        btn_personel_add_polres.setOnClickListener {
//            spinner_satker_add.hint = "Satker Polres"
//            txt_layout_satker_personel_add.visible()

            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polres")
            startActivityForResult(Intent(this, PolresActivity::class.java), REQ_POLRES)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polda.gone()
            txt_satker_polda.text = ""

            txt_polsek_add.gone()
            txt_polsek_add.text = ""

            txt_sat_polsek_add.gone()
            txt_sat_polsek_add.text = ""
        }
        btn_personel_add_polsek.setOnClickListener {
//            spinner_satker_add.hint = "Satker Polsek"
//            txt_layout_satker_personel_add.visible()

            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polsek")
//            intent.putExtra(PolresActivity.IS_POLSEK, PolresActivity.IS_POLSEK)
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)


            txt_satker_polda.gone()
            txt_satker_polda.text = ""

            txt_polres_add.gone()
            txt_polres_add.text = ""

            txt_sat_polres_add.gone()
            txt_sat_polres_add.text = ""
        }


        btn_next_personel.setOnClickListener {

            sheenValidator.validate()
//            val kesatuan = edt_kesatuan.text.toString()
//            val almtKantor = edt_almt_kntr.text.toString()
//            val noTelpKantor = edt_no_telp_kntr.text.toString()
//            val agama_skrg = sp_agm_now.text.toString()
//            val agama_sblm = sp_agm_before.text.toString()
//            val jmlh_anak = Integer.parseInt(edt_jmlh_anak.text.toString())
//            addPersonelReq.alamat_kantor = almtKantor
            addPersonelReq.agama_sebelumnya = agmBefore
            addPersonelReq.agama_sekarang = agmNow
            addPersonelReq.jenis_kelamin = jk
            addPersonelReq.id_satuan_kerja = idSatker
            addPersonelReq.status_perkawinan = sttsKawin
            addPersonelReq.jenis_kesatuan = jenisKesatuan
            addPersonelReq.alamat_rumah = edt_almt_rmh.text.toString()
            addPersonelReq.alamat_sesuai_ktp = edt_almt_ktp.text.toString()
            addPersonelReq.aliran_kepercayaan = null
            addPersonelReq.bahasa = edt_bahasa.text.toString()
            addPersonelReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg.text.toString()
            addPersonelReq.hobi = edt_hobi.text.toString()
            addPersonelReq.jabatan = edt_pekerjaan.text.toString()
            addPersonelReq.jumlah_anak = edt_jmlh_anak.text.toString().toInt()
            addPersonelReq.kebiasaan = edt_kebiasaan.text.toString()

            addPersonelReq.kewarganegaraan = edt_kwg.text.toString()
            addPersonelReq.nama = edt_nama_lengkap.text.toString()
            addPersonelReq.nama_alias = edt_nama_alias.text.toString()
            addPersonelReq.no_ktp = edt_no_ktp.text.toString()
            addPersonelReq.no_telp = edt_no_telp_pribadi.text.toString()
            addPersonelReq.no_telp_rumah = edt_no_telp_rmh.text.toString()
//            addPersonelReq.no_telp_kantor = noTelpKantor
            addPersonelReq.nrp = edt_nrp.text.toString()
            addPersonelReq.pangkat = edt_pangkat.text.toString().toUpperCase(Locale.ROOT)
            addPersonelReq.ras = edt_suku.text.toString()
            addPersonelReq.perkawinan_keberapa = edt_kwin_berapa.text.toString().toInt()
            addPersonelReq.tanggal_lahir = edt_tgl_ttl.text.toString()
            addPersonelReq.tempat_lahir = edt_tmpt_ttl.text.toString()
            addPersonelReq.tanggal_perkawinan = edt_tgl_kwn.text.toString()
            addPersonelReq.tempat_perkawinan = edt_tmpt_kwn.text.toString()

            sessionManager1.setPersonel(addPersonelReq)
            Log.e("personel", "${sessionManager1.getPersonel()}")
            startActivity(Intent(this@AddPersonelActivity, PendPersonelActivity::class.java))

        }

    }

    @SuppressLint("SetTextI18n")
    private fun test(personel: AddPersonelReq) {
        edt_almt_rmh.setText(personel.alamat_rumah)
        edt_almt_ktp.setText(personel.alamat_sesuai_ktp)
        edt_bahasa.setText(personel.bahasa)
        edt_how_to_kwg.setText(personel.cara_peroleh_kewarganegaraan)
        edt_hobi.setText(personel.hobi)
        edt_pekerjaan.setText(personel.jabatan)
        edt_jmlh_anak.setText(personel.jumlah_anak.toString())
        edt_kebiasaan.setText(personel.kebiasaan)
        edt_kwg.setText(personel.kewarganegaraan)
        edt_nama_lengkap.setText(personel.nama)
        edt_nama_alias.setText(personel.nama_alias)
        edt_no_ktp.setText(personel.no_ktp)
        edt_no_telp_pribadi.setText(personel.no_telp)
        edt_no_telp_rmh.setText(personel.no_telp_rumah)
        edt_nrp.setText(personel.nrp)
        edt_pangkat.setText(personel.pangkat)
        edt_suku.setText(personel.ras)
        edt_kwin_berapa.setText(personel.perkawinan_keberapa.toString())
        edt_tgl_ttl.setText(personel.tanggal_lahir)
        edt_tmpt_ttl.setText(personel.tempat_lahir)
        edt_tgl_kwn.setText(personel.tanggal_perkawinan)
        edt_tmpt_kwn.setText(personel.tempat_perkawinan)
        txt_satker_polda.visible()
        txt_satker_polda.text = personel.id_satuan_kerja.toString()
        idSatker = personel.id_satuan_kerja
        jk = personel.jenis_kelamin
        agmNow = personel.agama_sekarang
        agmBefore = personel.agama_sebelumnya
        sttsKawin= personel.status_perkawinan
        when(personel.agama_sekarang){
            "islam"->sp_agm_now.setText("Islam")
            "katolik"->sp_agm_now.setText("katolik")
            "protestan"->sp_agm_now.setText("protestan")
            "buddha"->sp_agm_now.setText("buddha")
            "hindu"->sp_agm_now.setText("hindu")
            "konghuchu"->sp_agm_now.setText("konghuchu")
        }

        when(personel.agama_sebelumnya){
            "islam"->sp_agm_before.setText("Islam")
            "katolik"->sp_agm_before.setText("katolik")
            "protestan"->sp_agm_before.setText("protestan")
            "buddha"->sp_agm_before.setText("buddha")
            "hindu"->sp_agm_before.setText("hindu")
            "konghuchu"->sp_agm_before.setText("konghuchu")
            else->sp_agm_before.setText("Tidak ada")
        }
        when(personel.jenis_kelamin){
            "laki_laki"->spinner_jk.setText("Laki")
            "perempuan"->spinner_jk.setText("perempuan")

        }
        when(personel.status_perkawinan){
            0->{
                spinner_stts_kwn.setText("Tidak")
                txt_layout_kawin_berapa.visibility = View.GONE
                cl_ttl_kawin.visibility = View.GONE
            }
            1->{
                spinner_stts_kwn.setText("Ya")
                txt_layout_kawin_berapa.visibility = View.VISIBLE
                cl_ttl_kawin.visibility = View.VISIBLE
            }

        }

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_POLDA -> {
                if (resultCode == RES_POLDA) {
                    val polda = data?.getParcelableExtra<SatKerResp>(PoldaActivity.DATA_POLDA)
                    Log.e("polda","$polda")
                    txt_satker_polda.visible()
                    txt_satker_polda.text = "Satuan Kerja : ${polda?.kesatuan}"
                    jenisKesatuan = "polda"
                    idSatker = polda?.id
                }else{
                    txt_satker_polda.gone()
                }
            }
            REQ_POLRES -> {
                if (resultCode == RES_POLRES) {
                    val polres = data?.getParcelableExtra<SatKerResp>(SatPolresActivity.DATA_POLRES_SAT)
                    Log.e("polres","$polres")

                    txt_satker_polda.visible()
                    txt_satker_polda.text = "Kepolisian Resort : ${polres?.kesatuan}"
                    idSatker = polres?.id
                }else{
                    txt_sat_polres_add.gone()
                    txt_polres_add.gone()
                }
            }
            REQ_POLSEK -> {
                if (resultCode == RES_POLSEK) {
                    val polsek = data?.getParcelableExtra<SatKerResp>(SatPolsekActivity.UNIT_POLSEK)
                    Log.e("polsek","$polsek")
                    txt_satker_polda.visible()
                    txt_satker_polda.text = "Kepolisian Sektor : ${polsek?.kesatuan}"
                    idSatker = polsek?.id
                }else{
                    txt_sat_polsek_add.gone()
                    txt_polsek_add.gone()
                }
            }
        }
    }

    /*private fun satker(jenis: String) {
        val list = arrayListOf<SearchListItem>()
        when (jenis) {
            "polda" -> {
                list.add(SearchListItem(1, "Abc"))
                list.add(SearchListItem(2, "Dce"))
                list.add(SearchListItem(3, "Fgh"))
                list.add(SearchListItem(4, "Fgh"))
                list.add(SearchListItem(5, "Xxx"))
                searchableDialog = SearchableDialog(this, list, getString(R.string.polda))
                searchableDialog.setOnItemSelected(this)
                spinner_satker_add.setOnClickListener { searchableDialog.show() }
            }
            "polres" -> {
                for (i in 0..9) {
                    val tempListRes = SearchListItem(i, "Res $i")
                    list.add(tempListRes)
                }
                searchableDialog = SearchableDialog(this, list, getString(R.string.polres))
                searchableDialog.setOnItemSelected(this)
                spinner_satker_add.setOnClickListener { searchableDialog.show() }
                if (spinner_satker_add.text!!.isNotEmpty()) {
                    txt_layout_unit_sat_personel_add.visible()

                    for (i in 0..9) {
                        val tempListResSat = SearchListItem(i, "Sat $i")
                        list.add(tempListResSat)
                    }
                    searchableDialog = SearchableDialog(this, list, getString(R.string.polres))
                    searchableDialog.setOnItemSelected(this)
                    spinner_unit_sat_add.setOnClickListener { searchableDialog.show() }
                }
            }
            "polsek" -> {
                for (i in 0..16) {
                    val tempListSek = SearchListItem(i, "Sek $i")
                    list.add(tempListSek)
                }
                searchableDialog = SearchableDialog(this, list, getString(R.string.polres))
                searchableDialog.setOnItemSelected(this)
                spinner_satker_add.setOnClickListener { searchableDialog.show() }
                if (spinner_satker_add.text!!.isNotEmpty()) {
                    txt_layout_unit_sat_personel_add.visible()

                    for (i in 0..9) {
                        val tempListSekSat = SearchListItem(i, "Unit $i")
                        list.add(tempListSekSat)
                    }
                    searchableDialog = SearchableDialog(this, list, getString(R.string.polres))
                    searchableDialog.setOnItemSelected(this)
                    spinner_unit_sat_add.setOnClickListener { searchableDialog.show() }
                }
            }


        }
    }*/

    private fun initSpinner() {
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        spinner_jk.setAdapter(adapterJk)
        spinner_jk.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                jk = "laki_laki"
            } else {
                jk = "perempuan"
            }
        }

        val sttsKawinItem = listOf("Ya", "Tidak")
        val adapterSttusKawin = ArrayAdapter(this, R.layout.item_spinner, sttsKawinItem)
        spinner_stts_kwn.setAdapter(adapterSttusKawin)
        spinner_stts_kwn.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {  // YA
                sttsKawin = 1
                txt_layout_kawin_berapa.visibility = View.VISIBLE
                cl_ttl_kawin.visibility = View.VISIBLE
            } else {              //Tidak
                sttsKawin = 0
                txt_layout_kawin_berapa.visibility = View.GONE
                cl_ttl_kawin.visibility = View.GONE
            }
        }

        val agamaItem = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        sp_agm_now.setAdapter(adapterAgama)
        sp_agm_now.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agmNow = "islam"
            } else if (position == 1) {
                agmNow = "katolik"
            } else if (position == 2) {
                agmNow = "protestan"
            } else if (position == 3) {
                agmNow = "budha"
            } else if (position == 4) {
                agmNow = "hindu"
            } else {
                agmNow = "konghuchu"
            }
        }
        sp_agm_before.setAdapter(adapterAgama)
        sp_agm_before.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agmBefore = "islam"
            } else if (position == 1) {
                agmBefore = "katolik"
            } else if (position == 2) {
                agmBefore = "protestan"
            } else if (position == 3) {
                agmBefore = "buddha"
            } else if (position == 4) {
                agmBefore = "hindu"
            } else {
                agmBefore = "konghuchu"
            }
        }

/*        val adapterSatker =
            ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.satker))
        spinnerSatkerAdd.setAdapter(adapterSatker)
        spinnerSatkerAdd.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> idSatker = 1
                1 -> idSatker = 2
                2 -> idSatker = 3
                3 -> idSatker = 4
                4 -> idSatker = 5
                5 -> idSatker = 6
                6 -> idSatker = 7
                7 -> idSatker = 8
                8 -> idSatker = 9
                9 -> idSatker = 10
                10 -> idSatker = 11
                11 -> idSatker = 12
                12 -> idSatker = 13
                13 -> idSatker = 14
                14 -> idSatker = 15
                15 -> idSatker = 16
                16 -> idSatker = 17
                17 -> idSatker = 18
            }
        }*/
    }

    override fun onResume() {
        super.onResume()

        btn_personel_add_polda.setOnClickListener {
            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(Intent(this, PoldaActivity::class.java), REQ_POLDA)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            Log.e("polda text", "${txt_satker_polda.text}")
            Log.e("polres text", "${txt_satker_polda.text}")
            Log.e("polsek text", "${txt_satker_polda.text}")
            txt_polres_add.gone()
            txt_polres_add.text = ""

            txt_sat_polres_add.gone()
            txt_sat_polres_add.text = ""

            txt_polsek_add.gone()
            txt_polsek_add.text = ""

            txt_sat_polsek_add.gone()
            txt_sat_polsek_add.text = ""

        }
        btn_personel_add_polres.setOnClickListener {
//            spinner_satker_add.hint = "Satker Polres"
//            txt_layout_satker_personel_add.visible()

            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polres")
            startActivityForResult(Intent(this, PolresActivity::class.java), REQ_POLRES)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polda.gone()
            txt_satker_polda.text = ""

            txt_polsek_add.gone()
            txt_polsek_add.text = ""

            txt_sat_polsek_add.gone()
            txt_sat_polsek_add.text = ""
        }
        btn_personel_add_polsek.setOnClickListener {
//            spinner_satker_add.hint = "Satker Polsek"
//            txt_layout_satker_personel_add.visible()

            btn_personel_add_polsek.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda.setTextColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polres.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres.setTextColor(resources.getColor(R.color.colorPrimary))
//            satker("polsek")
//            intent.putExtra(PolresActivity.IS_POLSEK, PolresActivity.IS_POLSEK)
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)


            txt_satker_polda.gone()
            txt_satker_polda.text = ""

            txt_polres_add.gone()
            txt_polres_add.text = ""

            txt_sat_polres_add.gone()
            txt_sat_polres_add.text = ""
        }

        val personel = sessionManager1.getPersonel()
        Log.e("getPersonel", "${sessionManager1.getPersonel()}")

        if(personel !=null) {
            Log.e("getPersonel", "${sessionManager1.getPersonel()}")
//            test(sessionManager1.getPersonel())
            initSpinner()
        }
    }

  /*  override fun onClick(position: Int, searchListItem: SearchListItem) {
        searchableDialog.dismiss()
        spinner_satker_add.setText(searchListItem.title)
        addPersonelReq.id_satuan_kerja = searchListItem.id
    }*/

}