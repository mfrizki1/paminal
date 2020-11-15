package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPersonelReq
import id.calocallo.sicape.network.response.AddPersonelResp
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.PendPersonelActivity
import id.calocallo.sicape.utils.Constants
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddPersonelActivity : BaseActivity() {
    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager: SessionManager
    private var jk: String? = null
    private var agmNow: String? = null
    private var agmBefore: String? = null
    private var sttsKawin: Int? = null
    private val addPersonelReq = AddPersonelReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personel)

        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Personel"
        sheenValidator = SheenValidator(this)

        btn_next_personel.attachTextChangeAnimator()
        bindProgressButton(btn_next_personel)


        initSpinner(spinner_jk, spinner_stts_kwn, sp_agm_now, sp_agm_before)
//        sheenValidator.registerAsRequired(edt_nama_lengkap)
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
        btn_next_personel.setOnClickListener {

            sheenValidator.validate()
            val namaLengkap = edt_nama_lengkap.text.toString()
            val namaAlias = edt_nama_alias.text.toString()
            val tmptLahir = edt_tmpt_ttl.text.toString()
            val tglLahir = edt_tgl_ttl.text.toString()
            val pekerjaann = edt_pekerjaan.text.toString()
            val pangkat = edt_pangkat.text.toString()
            val nrp = edt_nrp.text.toString()
            val kesatuan = edt_kesatuan.text.toString()
            val almtKantor = edt_almt_kntr.text.toString()
            val noTelpKantor = edt_no_telp_kntr.text.toString()
            val almtRumah = edt_almt_rmh.text.toString()
            val noTelpRumah = edt_no_telp_rmh.text.toString()
            val kwg = edt_kwg.text.toString()
            val howToKWG = edt_how_to_kwg.text.toString()
            val tmptKawin = edt_tmpt_kwn.text.toString()
            val tglKawin = edt_tgl_kwn.text.toString()
            val almtKtp = edt_almt_ktp.text.toString()
            val noKtp = edt_no_ktp.text.toString()
            val hobi = edt_hobi.text.toString()
            val kebiasaan = edt_kebiasaan.text.toString()
            val bahasa = edt_bahasa.text.toString()
            val suku = edt_suku.text.toString()
//            val agama_skrg = sp_agm_now.text.toString()
//            val agama_sblm = sp_agm_before.text.toString()
            val jmlh_anak = Integer.parseInt(edt_jmlh_anak.text.toString())
            val kawin_keberapa = edt_kwin_berapa.text.toString()
            val no_telp_pribadi = edt_no_telp_pribadi.text.toString()

            addPersonelReq.agama_sebelumnya = agmBefore
            addPersonelReq.agama_sekarang = agmNow
            addPersonelReq.alamat_kantor = almtKantor
            addPersonelReq.alamat_rumah = almtRumah
            addPersonelReq.alamat_sesuai_ktp = almtKtp
            addPersonelReq.aliran_kepercayaan = null
            addPersonelReq.bahasa = bahasa
            addPersonelReq.cara_peroleh_kewarganegaraan = howToKWG
            addPersonelReq.hobi = hobi
            addPersonelReq.jabatan = pekerjaann
            addPersonelReq.jenis_kelamin = jk
            addPersonelReq.jumlah_anak = jmlh_anak
            addPersonelReq.kebiasaan = kebiasaan
            addPersonelReq.kesatuan = kesatuan
            addPersonelReq.kewarganegaraan = kwg
            addPersonelReq.nama = namaLengkap
            addPersonelReq.nama_alias = namaAlias
            addPersonelReq.no_ktp = noKtp
            addPersonelReq.no_telp = no_telp_pribadi
            addPersonelReq.no_telp_rumah = noTelpRumah
            addPersonelReq.no_telp_kantor = noTelpKantor
            addPersonelReq.nrp = nrp
            addPersonelReq.pangkat = pangkat
            addPersonelReq.ras = suku
            addPersonelReq.perkawinan_keberapa = kawin_keberapa
            addPersonelReq.tanggal_lahir = tglLahir
            addPersonelReq.tempat_lahir = tmptLahir
            addPersonelReq.tanggal_perkawinan = tglKawin
            addPersonelReq.tempat_perkawinan = tmptKawin
            addPersonelReq.status_perkawinan = sttsKawin


            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)


            NetworkConfig().getService()
                .addPersonel(
                    tokenBearer = "Bearer ${sessionManager.fetchAuthToken()}",
                    addPersonelRequest = addPersonelReq
                )
                .enqueue(object : Callback<AddPersonelResp> {
                    override fun onFailure(call: Call<AddPersonelResp>, t: Throwable) {
                        Toast.makeText(
                            this@AddPersonelActivity,
                            "Gagal Koneksi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<AddPersonelResp>, response: Response<AddPersonelResp>) {
                        if (response.isSuccessful) {
                            if (response.body()?.message == "Data personel saved succesfully") {
                                btn_next_personel.showDrawable(animatedDrawable) {
                                    buttonTextRes = R.string.save
                                    textMarginRes = R.dimen.space_10dp
                                }

                                btn_next_personel.hideDrawable(R.string.save)
                                val intent = Intent(
                                    this@AddPersonelActivity,
                                    PendPersonelActivity::class.java
                                )
                               Constants.ID_PERSONEL = response.body()!!.id
                                startActivity(intent)

                            } else {
                                btn_next_personel.showDrawable(animatedDrawable) {
                                    buttonTextRes = R.string.not_save
                                    textMarginRes = R.dimen.space_10dp
                                }
                                btn_next_personel.hideDrawable(R.string.not_save)
                                Toast.makeText(
                                    this@AddPersonelActivity,
                                    "Gagal Menyimpan",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        } else {
                            btn_next_personel.showDrawable(animatedDrawable) {
                                buttonTextRes = R.string.not_save
                                textMarginRes = R.dimen.space_10dp
                            }
                            btn_next_personel.hideDrawable(R.string.not_save)
                            Toast.makeText(this@AddPersonelActivity, "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                })


            Log.e(
                "IDEN", "$namaLengkap, $namaAlias, $tmptLahir, $tglLahir," +
                        "$pekerjaann, $pangkat / $nrp, $kesatuan, $almtKantor / $noTelpKantor," +
                        "$almtRumah / $noTelpRumah, $kwg, $howToKWG, $tmptKawin / $tglKawin," +
                        "$almtKtp, $noKtp, $hobi, $kebiasaan, $bahasa, $jk, $sttsKawin, $suku, $jmlh_anak"
            )

//            startActivity(Intent(this@AddPersonelActivity, PendPersonelActivity::class.java))

        }

    }

    private fun initSpinner(
        spinner_jk: AutoCompleteTextView,
        stts_kawin: AutoCompleteTextView,
        spAgmNow: AutoCompleteTextView,
        spAgmBefore: AutoCompleteTextView
    ) {
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        spinner_jk.setAdapter(adapterJk)
        spinner_jk.setOnItemClickListener { parent, view, position, id ->
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
        stts_kawin.setAdapter(adapterSttusKawin)
        stts_kawin.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {  // YA
                sttsKawin = 1
                txt_layout_kawin_berapa.visibility = View.VISIBLE
                cl_ttl_kawin.visibility = View.VISIBLE
            } else {              //Tidak
                sttsKawin = 0
                txt_layout_kawin_berapa.visibility = View.GONE
                cl_ttl_kawin.visibility = View.GONE
            }
//            sttsKawin = parent.getItemAtPosition(position)
//            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }

        val agamaItem = listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        spAgmNow.setAdapter(adapterAgama)
        spAgmNow.setOnItemClickListener { parent, view, position, id ->
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
            }else{
                agmNow = "konghuchu"
            }
        }
        spAgmBefore.setAdapter(adapterAgama)
        spAgmBefore.setOnItemClickListener { parent, view, position, id ->
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
            }else{
                agmBefore = "konghuchu"
            }
        }
    }
}