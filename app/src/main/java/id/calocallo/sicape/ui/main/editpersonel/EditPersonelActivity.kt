package id.calocallo.sicape.ui.main.editpersonel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.activity_edit_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPersonelActivity : BaseActivity() {
    private var jk: String? = null
    private var agmNow: String? = null
    private var agmBefore: String? = null
    private var sttsKawin: Int? = null
    private val addPersonelReq = AddPersonelReq()
    private lateinit var sessionManager: SessionManager
    private var idPersonel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_personel)

        sessionManager = SessionManager(this)

        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        idPersonel = detail?.id.toString()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama.toString()

        Log.e("id_personel", idPersonel)
        if (detail != null) {
            val nama = detail.nama
            val alias = detail.nama_alias
            val jabatan = detail.jabatan
            val kesatuan = detail.kesatuan
            val pangkat = detail.pangkat
            val nrp = detail.nrp
            val jk = detail.jenis_kelamin
//            val agama = detail.agama_sekarang
            val almt_kntr = detail.alamat_kantor
            val almt_ktp = detail.alamat_sesuai_ktp
            val almt_rmh = detail.alamat_rumah
            val bahasa = detail.bahasa
            val hobi = detail.hobi
            val kebiasaan = detail.kebiasaan
            val kwg = detail.kewarganegaraan
            val how_to_kwg = detail.cara_peroleh_kewarganegaraan
            val suku = detail.ras
            val jmlh_anak = detail.jumlah_anak
            val kawin_berapa = detail.perkawinan_keberapa
            val no_ktp = detail.no_ktp
            val no_telp_kntr = detail.no_telp_kantor
            val no_telp_rmh = detail.no_telp_rumah
            val no_telp_pribadi = detail.no_telp
            val tgl_kawin = detail.tanggal_perkawinan
            val tempat_kawin = detail.tempat_perkawinan
            val tgl_lahir = detail.tanggal_lahir
            val tmpt_lahir = detail.tempat_lahir
            val agama_sblm = detail.agama_sebelumnya
            val agama_skrg = detail.agama_sekarang


            edt_bahasa_edit.setText(bahasa)
            edt_almt_kntr_edit.setText(almt_kntr)
            edt_almt_ktp_edit.setText(almt_ktp)
            edt_almt_rmh_edit.setText(almt_rmh)
            edt_hobi_edit.setText(hobi)
//            edt_agama
            edt_kebiasaan_edit.setText(kebiasaan)
            edt_kesatuan_edit.setText(kesatuan)
            edt_kwg_edit.setText(kwg)
            edt_nrp_edit.setText(nrp)
            edt_pangkat_edit.setText(pangkat)
            edt_pekerjaan_edit.setText(jabatan)
            edt_suku_edit.setText(suku)
            edt_jmlh_anak_edit.setText(jmlh_anak)
            edt_kwin_berapa_edit.setText(kawin_berapa)
            edt_nama_alias_edit.setText(alias)
            edt_nama_lengkap_edit.setText(nama)
            edt_no_ktp_edit.setText(no_ktp)
            edt_no_telp_kntr_edit.setText(no_telp_kntr)
            edt_no_telp_pribadi_edit.setText(no_telp_pribadi)
            edt_no_telp_rmh_edit.setText(no_telp_rmh)
            edt_tgl_kwn_edit.setText(tgl_kawin)
            edt_tmpt_kwn_edit.setText(tempat_kawin)
            edt_tgl_ttl_edit.setText(tgl_lahir)
            edt_tmpt_ttl_edit.setText(tmpt_lahir)
            spinner_jk_edit.setText(jk)
            sp_agm_before_edit.setText(agama_sblm)
            sp_agm_now_edit.setText(agama_skrg)
            edt_how_to_kwg_edit.setText(how_to_kwg)
        } else {
            Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }

        initSpinner(spinner_jk_edit, spinner_stts_kwn_edit, sp_agm_now_edit, sp_agm_before_edit)

        btn_save_personel_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_personel_edit)
        btn_save_personel_edit.setOnClickListener {
            doUpdate(sessionManager.fetchHakAkses())
        }

    }

    private fun doUpdate(hakAkses: String?) {
        addPersonelReq.tempat_perkawinan = edt_tmpt_kwn_edit.text.toString()
        addPersonelReq.tanggal_perkawinan = edt_tgl_kwn_edit.text.toString()
        addPersonelReq.tempat_lahir = edt_tmpt_ttl_edit.text.toString()
        addPersonelReq.tanggal_lahir = edt_tgl_ttl_edit.text.toString()
        addPersonelReq.nama = edt_nama_lengkap_edit.text.toString()
        addPersonelReq.nama_alias = edt_nama_alias_edit.text.toString()
        addPersonelReq.jenis_kelamin = jk
        addPersonelReq.no_telp = edt_no_telp_pribadi_edit.text.toString()
        addPersonelReq.ras = edt_suku_edit.text.toString()
        addPersonelReq.jabatan = edt_pekerjaan_edit.text.toString()
        addPersonelReq.pangkat = edt_pangkat_edit.text.toString()
        addPersonelReq.nrp = edt_nrp_edit.text.toString()
        addPersonelReq.kesatuan = edt_kesatuan_edit.text.toString()
        addPersonelReq.alamat_kantor = edt_almt_kntr_edit.text.toString()
        addPersonelReq.no_telp_kantor = edt_no_telp_kntr_edit.text.toString()
        addPersonelReq.alamat_rumah = edt_almt_rmh_edit.text.toString()
        addPersonelReq.no_telp_rumah = edt_no_telp_rmh_edit.text.toString()
        addPersonelReq.kewarganegaraan = edt_kwg_edit.text.toString()
        addPersonelReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_edit.text.toString()
        addPersonelReq.agama_sekarang = agmNow
        addPersonelReq.agama_sebelumnya = agmBefore
        addPersonelReq.status_perkawinan = sttsKawin
        addPersonelReq.perkawinan_keberapa = edt_kwin_berapa_edit.text.toString()
        addPersonelReq.jumlah_anak = Integer.parseInt(edt_jmlh_anak_edit.text.toString())
        addPersonelReq.alamat_sesuai_ktp = edt_almt_ktp_edit.text.toString()
        addPersonelReq.no_ktp = edt_no_ktp_edit.text.toString()
        addPersonelReq.hobi = edt_hobi_edit.text.toString()
        addPersonelReq.kebiasaan = edt_kebiasaan_edit.text.toString()
        addPersonelReq.bahasa = edt_bahasa_edit.text.toString()

        //animatedButton
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
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
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data personel updated succesfully") {
                            btn_save_personel_edit.showDrawable(animatedDrawable) {
                                buttonTextRes = R.string.data_Updated
                                textMarginRes = R.dimen.space_10dp
                                finish()
                            }
                        } else {
                            Toast.makeText(this@EditPersonelActivity, "Error2", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this@EditPersonelActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
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
                jk = "laki_laki"
            } else {
                jk = "perempuan"
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

        val agamaItem = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        sp_agama_skrg.setAdapter(adapterAgama)
        sp_agama_skrg.setOnItemClickListener { parent, view, position, id ->
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
        sp_agama_sblm.setAdapter(adapterAgama)
        sp_agama_sblm.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agmBefore = "islam"
            } else if (position == 1) {
                agmBefore = "katolik"
            } else if (position == 2) {
                agmBefore = "protestan"
            } else if (position == 3) {
                agmBefore = "budha"
            } else if (position == 4) {
                agmBefore = "hindu"
            } else {
                agmBefore = "konghuchu"
            }
        }
    }
}