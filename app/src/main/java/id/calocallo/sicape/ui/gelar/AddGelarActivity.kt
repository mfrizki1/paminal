package id.calocallo.sicape.ui.gelar

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.network.request.LhgReq
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.GelarDataManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.text.SimpleDateFormat
import java.util.*

class AddGelarActivity : BaseActivity() {
    private lateinit var gelarDataManager: GelarDataManager
    private var lhgReq = LhgReq()
    private var idLp: Int? = null

    companion object {
        const val REQ_PESERTA = 1
        const val RES_PESERTA = 4
        const val REQ_PEMAPAR = 2
        const val REQ_PIMPINAN = 3
        const val REQ_NOTULEN = 5
        const val REQ_LP_GELAR = 6
        const val IS_LHG = "IS_LHG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gelar)
        gelarDataManager = GelarDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Gelar Perkara"
        Logger.addLogAdapter(AndroidLogAdapter())

        btn_peserta_lhg_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            intent.putExtra(ChoosePersonelActivity.MULTIPLE, true)
            startActivityForResult(intent, REQ_PESERTA)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_choose_personel_pemapar_lhg_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PEMAPAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_pimpinan_lhg_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PIMPINAN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_notulen_lhg_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_NOTULEN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_next_lhg_add.setOnClickListener {
            saveGelar()
            val intent = Intent(this, AddTanggPesertaGelarActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_lp_lhg_add.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java).apply {
                this.putExtra(IS_LHG, true)
            }
            startActivityForResult(intent, REQ_LP_GELAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    private fun saveGelar() {
        lhgReq.pasal_kuh_pidana = edt_pasal_kuh_pidana_gelar_add.editText?.text.toString()
        lhgReq.dugaan = edt_dugaan_tindak_lhg_add.editText?.text.toString()
        lhgReq.dasar = edt_dasar_lhg_add.editText?.text.toString()
        lhgReq.tanggal = edt_tanggal_lhg_add.editText?.text.toString()
        lhgReq.waktu_mulai = edt_waktu_mulai_lhg_add.editText?.text.toString()
        lhgReq.waktu_selesai = edt_waktu_selesai_lhg_add.editText?.text.toString()
        lhgReq.tempat = edt_tempat_lhg_add.editText?.text.toString()
        lhgReq.kronologis_kasus = edt_kronologis_kasus_gelar_add.editText?.text.toString()
        lhgReq.no_surat_perintah_penyidikan =
            edt_surat_penyidikan_gelar_add.editText?.text.toString()
        lhgReq.id_lp = idLp
        lhgReq.nama_yang_menangani = edt_nama_menangani_lhg_add.editText?.text.toString()
        lhgReq.pangkat_yang_menangani = edt_pangkat_menangani_lhg_add.editText?.text.toString()
        gelarDataManager.setGelar1(lhgReq)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_PIMPINAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val pimpinan = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_pimpinan_lhg_add.text = "Nama: ${pimpinan?.nama}"
                    txt_pangkat_pimpinan_lhg_add.text = "Pangkat: ${
                        pimpinan?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    txt_nrp_pimpinan_lhg_add.text = "NRP: ${pimpinan?.nrp}"
                    txt_jabatan_pimpinan_lhg_add.text = "Jabatan: ${pimpinan?.jabatan}"
                    txt_kesatuan_pimpinan_lhg_add.text =
                        "Kesatuan:e ${pimpinan?.satuan_kerja?.kesatuan}"
                    lhgReq.nama_pimpinan = pimpinan?.nama
                    lhgReq.nrp_pimpinan = pimpinan?.nrp
                    lhgReq.pangkat_pimpinan = pimpinan?.pangkat.toString().toUpperCase()
                }
            }
            REQ_PEMAPAR -> {
                if (resultCode == Activity.RESULT_OK) {
                    val pemapar = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_pemapar_lhg_add.text = "Nama: ${pemapar?.nama}"
                    txt_pangkat_pemapar_lhg_add.text = "Pangkat: ${
                        pemapar?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    txt_nrp_pemapar_lhg_add.text = "NRP: ${pemapar?.nrp}"
                    txt_jabatan_pemapar_lhg_add.text = "Jabatan: ${pemapar?.jabatan}"
                    txt_kesatuan_pemapar_lhg_add.text =
                        "Kesatuan: ${pemapar?.satuan_kerja?.kesatuan}"

                    lhgReq.nama_pemapar = pemapar?.nama
                    lhgReq.pangkat_pemapar = pemapar?.pangkat.toString().toUpperCase()
                }
            }
            REQ_NOTULEN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val notulen = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_notulen_lhg_add.text = "Nama: ${notulen?.nama}"
                    txt_pangkat_notulen_lhg_add.text = "Pangkat: ${
                        notulen?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    txt_nrp_notulen_lhg_add.text = "NRP: ${notulen?.nrp}"
                    txt_jabatan_notulen_lhg_add.text = "Jabatan: ${notulen?.jabatan}"
                    txt_kesatuan_notulen_lhg_add.text =
                        "Kesatuan: ${notulen?.satuan_kerja?.kesatuan}"
                    lhgReq.nama_notulen = notulen?.nama
                    lhgReq.nrp_notulen = notulen?.nrp
                    lhgReq.pangkat_notulen = notulen?.pangkat.toString().toUpperCase()
                }
            }
            REQ_LP_GELAR -> {
                if (resultCode == ChooseLhpActivity.RES_LP_CHOSE_LHP) {
                    val dataLpGelar = data?.getParcelableExtra<LpOnSkhd>(ChooseLhpActivity.DATA_LP)
                    txt_no_lp_choose_lhg_add.text = dataLpGelar?.lp?.no_lp
                    idLp = dataLpGelar?.lp?.id

                }
            }
        }
    }

    private fun datePick() {
        val c = Calendar.getInstance(Locale("id", "ID"))
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, mYear, monthOfYear, dayOfMonth ->

                val simpleDateFormat = SimpleDateFormat("EEEE", Locale("id", "ID"))
                val date = Date(mYear, month, dayOfMonth - 1)
                val dayString =
                    simpleDateFormat.format(date) //returns true day name for current month only
                edt_tanggal_lhg_add.editText?.setText("$dayString, $dayOfMonth/${monthOfYear + 1}/$mYear")

            }, year, month, day
        )
        dpd.show()
    }
}