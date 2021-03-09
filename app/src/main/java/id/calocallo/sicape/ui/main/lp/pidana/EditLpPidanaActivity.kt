package id.calocallo.sicape.ui.main.lp.pidana

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    private var editLpPidana = LpPidanaReq()

    companion object {
        const val EDIT_PIDANA = "EDIT_PIDANA"
        const val REQ_PELAPOR = 202
        const val REQ_TERLAPOR = 101

    }

    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    private var namaSatker: String? = null
    private var namaSipil: String? = null
    private var jkSipil: String? = null
    private var agamaSipil: String? = null
    private var noTelpSipil: String? = null
    private var nikSipil: String? = null
    private var alamatSipil: String? = null
    private var kwgSipil: String? = null
    private var pekerjaanSipil: String? = null
    private var tmptSipil: String? = null
    private var tglSipil: String? = null

    private var namaPimpinan: String? = null
    private var pangkatPimpinan: String? = null
    private var nrpPimpinan: String? = null
    private var jabatanPimpinan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_pidana)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Pidana"
        val pidana = intent.extras?.getParcelable<LpResp>(EDIT_PIDANA)
        getViewEditPidana(pidana)

        btn_choose_personel_pelapor_pidana_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_choose_personel_terlapor_pidana_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_edit_lp_pidana.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_lp_pidana)
        btn_save_edit_lp_pidana.setOnClickListener {
            btn_save_edit_lp_pidana.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_lp_pidana.hideDrawable(R.string.save)
            }, 3000)
            updateLpPidana(pidana)
        }

//        btn_sipil_edit.setOnClickListener {
        materialAlertDialogBuilder =
            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_edit.setOnClickListener {
            sipilAlertDialog = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_pidana, null, false)
            launchSipilView()
//            }
        }
        val adapterSatker =
            ArrayAdapter(this, R.layout.item_spinner, resources.getStringArray(R.array.satker))
        spinner_kesatuan_pimpinan_bidang_edit.setAdapter(adapterSatker)
        spinner_kesatuan_pimpinan_bidang_edit.setOnItemClickListener { parent, view, position, id ->
            namaSatker = parent.getItemAtPosition(position) as String?

        }


    }

    private fun updateLpPidana(pidana: LpResp?) {
        changedIdTerlapor = pidana?.personel_terlapor?.id
        editLpPidana.nama_kep_spkt = edt_nama_pimpinan_bidang_edit.text.toString()
        editLpPidana.pangkat_kep_spkt = edt_pangkat_pimpinan_bidang_edit.text.toString()
        editLpPidana.nrp_kep_spkt = edt_nrp_pimpinan_bidang_edit.text.toString()
        editLpPidana.jabatan_kep_spkt = edt_jabatan_pimpinan_bidang_edit.text.toString()
//        editLpPidana.no_lp = edt_no_lp_pidana_edit.text.toString()
//        editLpPidana.pembukaan_laporan = edt_pembukaan_laporan_pidana_edit.text.toString()
        editLpPidana.isi_laporan = edt_isi_laporan_pidana_edit.text.toString()
        editLpPidana.kota_buat_laporan = edt_kota_buat_edit_lp.text.toString()
        editLpPidana.tanggal_buat_laporan = edt_tgl_buat_edit.text.toString()
        editLpPidana.id_satuan_kerja = 123
        editLpPidana.uraian_pelanggaran = edt_uraian_pelanggaran_pidana_edit.text.toString()
//        lpPidanaReq.id_personel_operator = sessionManager.fetchUser()?.id
//        lpPidanaReq.id_satuan_kerja = sessionManager.getJenisLP().toString().toLowerCase()

        /* if (changedIdPelapor == null) {
 //            editLpPidana.id_personel_pelapor = pidana?.personel_pelapor?.id
         } else {
             editLpPidana.id_personel_pelapor = changedIdPelapor
         }*/

        /*if (changedIdTerlapor == null) {
//            editLpPidana.id_personel_terlapor = pidana?.personel_pelapor?.id
        } else {
        }*/
        editLpPidana.id_personel_terlapor = changedIdTerlapor

        editLpPidana.nama_pelapor = namaSipil
        editLpPidana.agama_pelapor = agamaSipil
        editLpPidana.pekerjaan_pelapor = pekerjaanSipil
        editLpPidana.kewarganegaraan_pelapor = kwgSipil
        editLpPidana.jenis_kelamin_pelapor = jkSipil
        editLpPidana.alamat_pelapor = alamatSipil
        editLpPidana.no_telp_pelapor = noTelpSipil
        editLpPidana.nik_ktp_pelapor = nikSipil
        editLpPidana.tempat_lahir_pelapor = tmptSipil
        editLpPidana.tanggal_lahir_pelapor = tglSipil
        editLpPidana.waktu_buat_laporan = edt_pukul_buat_edit.text.toString()
        apiUpdPidana(pidana)

        Log.e("update", "$editLpPidana")
    }

    private fun apiUpdPidana(pidana: LpResp?) {
        NetworkConfig().getServLp()
            .updLpPidana("Bearer ${sessionManager1.fetchAuthToken()}", pidana?.id, editLpPidana)
            .enqueue(object :
                Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(
                        this@EditLpPidanaActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_save_edit_lp_pidana.hideDrawable(R.string.not_update)
                    Log.e("t", "$t")
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp pidana updated succesfully") {
                        val animatedDrawable = ContextCompat.getDrawable(
                            this@EditLpPidanaActivity,
                            R.drawable.animated_check
                        )!!
                        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
                        animatedDrawable.setBounds(0, 0, size, size)
                        btn_save_edit_lp_pidana.showDrawable(animatedDrawable) {
                            textMarginRes = R.dimen.space_10dp
                            buttonTextRes = R.string.data_updated
                        }
                        finish()
                    } else {
                        btn_save_edit_lp_pidana.hideDrawable(R.string.not_update)

                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewEditPidana(pidana: LpResp?) {
        //general
        edt_no_lp_pidana_edit.setText(pidana?.no_lp)
//        edt_pembukaan_laporan_pidana_edit.setText(pidana?.pembukaan_laporan)
        edt_isi_laporan_pidana_edit.setText(pidana?.detail_laporan?.isi_laporan)

        //dibuat
        edt_kota_buat_edit_lp.setText(pidana?.kota_buat_laporan)
        edt_tgl_buat_edit.setText(pidana?.tanggal_buat_laporan)
        edt_pukul_buat_edit.setText(pidana?.detail_laporan?.waktu_buat_laporan)

        /*sipil*/
        namaSipil = pidana?.detail_laporan?.nama_pelapor
        jkSipil = pidana?.detail_laporan?.jenis_kelamin_pelapor
        agamaSipil = pidana?.detail_laporan?.agama_pelapor
        noTelpSipil = pidana?.detail_laporan?.no_telp_pelapor
        nikSipil = pidana?.detail_laporan?.nik_ktp_pelapor
        alamatSipil = pidana?.detail_laporan?.alamat_pelapor
        kwgSipil = pidana?.detail_laporan?.kewarganegaraan_pelapor
        pekerjaanSipil = pidana?.detail_laporan?.pekerjaan_pelapor
        tglSipil = pidana?.detail_laporan?.tanggal_lahir_pelapor
        tmptSipil = pidana?.detail_laporan?.tempat_lahir_pelapor

        txt_nama_sipil_pidana_lp_edit.text = "Nama :  ${pidana?.detail_laporan?.nama_pelapor}"
        txt_ttl_sipil_pidana_lp_edit.text =
            "TTL : ${pidana?.detail_laporan?.tempat_lahir_pelapor}, ${formatterTanggal(pidana?.detail_laporan?.tanggal_lahir_pelapor)}"
        txt_agama_sipil_pidana_lp_edit.text = "Agama : ${pidana?.detail_laporan?.agama_pelapor}"
        txt_pekerjaan_sipil_pidana_lp_edit.text =
            "Pekerjaan : ${pidana?.detail_laporan?.pekerjaan_pelapor}"
        txt_kwg_sipil_pidana_lp_edit.text =
            "Kewarganegaraan : ${pidana?.detail_laporan?.kewarganegaraan_pelapor}"
        txt_alamat_sipil_pidana_lp_edit.text = "Alamat : ${pidana?.detail_laporan?.alamat_pelapor}"
        txt_no_telp_sipil_pidana_lp_edit.text =
            "No Telepon : ${pidana?.detail_laporan?.no_telp_pelapor}"
        txt_nik_ktp_sipil_pidana_lp_edit.text =
            "NIK KTP : ${pidana?.detail_laporan?.nik_ktp_pelapor}"
        txt_jk_sipil_pidana_lp_edit.text =
            "Jenis Kelamin : ${pidana?.detail_laporan?.jenis_kelamin_pelapor}"

        //pimpinan
        edt_nama_pimpinan_bidang_edit.setText(pidana?.detail_laporan?.nama_kep_spkt)
        edt_pangkat_pimpinan_bidang_edit.setText(pidana?.detail_laporan?.pangkat_kep_spkt)
        edt_nrp_pimpinan_bidang_edit.setText(pidana?.detail_laporan?.nrp_kep_spkt)
        edt_jabatan_pimpinan_bidang_edit.setText(
            pidana?.detail_laporan?.jabatan_kep_spkt.toString().toUpperCase()
        )

        /*  spinner_kesatuan_pimpinan_bidang_edit.setText(
             pidana?.kesatuan_yang_mengetahui.toString().toUpperCase()
         )
         namaSatker = pidana?.kesatuan_yang_mengetahui.toString().toUpperCase()*/
        //set radiobutton apakah dia pelapor sipil atau bukan
        /*  if (pidana?.status_pelapor == "sipil") {
              ll_sipil_edit.visible()
              ll_personel_edit.gone()
              rb_sipil_pidana_edit.isChecked = true
              editLpPidana.status_pelapor = "sipil"
              txt_nama_sipil_pidana_lp_edit.text = "Nama :  ${pidana?.nama_pelapor}"
              txt_agama_sipil_pidana_lp_edit.text = "Agama : ${pidana?.agama_pelapor}"
              txt_pekerjaan_sipil_pidana_lp_edit.text = "Pekerjaan : ${pidana?.pekerjaan_pelapor}"
              txt_kwg_sipil_pidana_lp_edit.text =
                  "Kewarganegaraan : ${pidana?.kewarganegaraan_pelapor}"
              txt_alamat_sipil_pidana_lp_edit.text = "Alamat : ${pidana?.alamat_pelapor}"
              txt_no_telp_sipil_pidana_lp_edit.text = "No Telepon : ${pidana?.no_telp_pelapor}"
              txt_nik_ktp_sipil_pidana_lp_edit.text = "NIK KTP : ${pidana?.nik_ktp_pelapor}"
          } else {
              ll_sipil_edit.gone()
              ll_personel_edit.visible()
              rb_polisi_pidana_edit.isChecked = true
              editLpPidana.status_pelapor = "polisi"
    //            lpPidanaReq.id_personel_pelapor = pidana?.personel_pelapor
              txt_nama_pelapor_pidana_lp_edit.text =
                  "Nama : ${pidana?.personel_pelapor?.nama}"
              txt_pangkat_pelapor_pidana_lp_edit.text =
                  "Pangkat : ${pidana?.personel_pelapor?.pangkat.toString().toUpperCase()}"
              txt_nrp_pelapor_pidana_lp_edit.text = "NRP :  ${pidana?.personel_pelapor?.nrp}"
              txt_jabatan_pelapor_pidana_lp_edit.text =
                  "Jabatan :  ${pidana?.personel_pelapor?.jabatan}"
              txt_kesatuan_pelapor_pidana_lp_edit.text =
                  "Kesatuan :  ${pidana?.personel_pelapor?.kesatuan.toString().toUpperCase()}"
          }
          rg_pelapor_edit.setOnCheckedChangeListener { group, checkedId ->
              val radio: RadioButton = findViewById(checkedId)
              if (radio.isChecked) {
                  editLpPidana.status_pelapor = radio.text.toString().toLowerCase()
                  Log.e("radio detail", "${radio.text}")
                  if (radio.text.toString().toLowerCase() == "sipil") {
                      ll_sipil_edit.visible()
                      ll_personel_edit.gone()
                  } else {
                      ll_sipil_edit.gone()
                      ll_personel_edit.visible()
                  }
              }
          }
    */

        //personel terlapor
//                lpPidanaReq.id_personel_terlapor = pidana?.personel_terlapor
        editLpPidana.id_personel_terlapor = pidana?.personel_terlapor?.id
        txt_nama_terlapor_lp_edit.text = "Nama : ${pidana?.personel_terlapor?.nama}"
        txt_pangkat_terlapor_lp_edit.text =
            "Pangkat : ${pidana?.personel_terlapor?.pangkat.toString().toUpperCase()}"
        txt_nrp_terlapor_lp_edit.text = "NRP : ${pidana?.personel_terlapor?.nrp}"
        txt_jabatan_terlapor_lp_edit.text =
            "Jabatan : ${pidana?.personel_terlapor?.jabatan}"
        txt_kesatuan_terlapor_lp_edit.text =
            "Kesatuan : ${
                pidana?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                    .toUpperCase()
            }"
        //uraiain
        edt_uraian_pelanggaran_pidana_edit.setText(pidana?.uraian_pelanggaran)
    }

    @SuppressLint("SetTextI18n")
    private fun launchSipilView() {
        val spAgama =
            sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_agama_sipil)
        val jkSipilView =
            sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_jk_sipil)
        val namaSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nama_sipil)
        val pekerjaanSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil)
        val kwgSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_kwg_sipil)
        val alamatSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
        val noTelpSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
        val nikSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)
        val tmptSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil)
        val tglSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil)

        val ll = sipilAlertDialog.findViewById<LinearLayout>(R.id.ll_add_sipil)
        val pb = sipilAlertDialog.findViewById<RelativeLayout>(R.id.rl_pb)

        //NetworkConfig().getService().
        //add Sipil
        //muncul pb
        //jika sudah berhasil menambahkan maka muncul id sipil_terlapor dan pb hilang
        //jika gagal maka logcat error ada error
        val jkItem = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItem)
        jkSipilView.setAdapter(adapterJk)
        jkSipilView.setOnItemClickListener { parent, view, position, id ->
            txt_jk_sipil_pidana_lp_edit.text =
                "Jenis Kelamin : ${parent.getItemAtPosition(position)}"
            when (position) {
                0 -> jkSipil == "laki_laki"
                1 -> jkSipil == "perempuan"
            }
        }

        val agamaItem =
            listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        spAgama.setAdapter(adapterAgama)
        spAgama.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    agamaSipil = "islam"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Katolik"
                }
                2 -> {
                    agamaSipil = "protestan"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Protestan"
                }
                3 -> {
                    agamaSipil = "buddha"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Buddha"
                }
                4 -> {
                    agamaSipil = "hindu"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Hindu"
                }
                5 -> {
                    agamaSipil = "konghuchu"
                    txt_agama_sipil_pidana_lp_edit.text = "Agama : Konghuchu"
                }
            }
        }
        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { dialog, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_pidana_lp_edit.text = "Nama : ${namaSipilView.text.toString()}"

                tmptSipil = tmptSipilView.text.toString()
                tglSipil = tglSipilView.text.toString()
                txt_ttl_sipil_pidana_lp_edit.text =
                    "TTL : ${tmptSipil}, ${formatterTanggal(tglSipil)}"
                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_pidana_lp_edit.text =
                    "Pekerjaan : ${pekerjaanSipilView.text.toString()}"

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_pidana_lp_edit.text =
                    "Kewarganegaraan : ${kwgSipilView.text.toString()}"

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_pidana_lp_edit.text = "Alamat : ${alamatSipilView.text.toString()}"

                noTelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_pidana_lp_edit.text =
                    "No Telepon : ${noTelpSipilView.text.toString()}"

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_pidana_lp_edit.text = "NIK KTP : ${nikSipilView.text.toString()}"

//                dialog.dismiss()

            }
            .setNegativeButton("Batal") { dialog, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
            }
            .show()

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_TERLAPOR -> {
//                            personel?.id?.let { sessionManager.setIDPersonelTerlapor(it) }
                        changedIdTerlapor = personel?.id
                        txt_nama_terlapor_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_terlapor_lp_edit.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_terlapor_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_terlapor_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_edit.text =
                            "Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                    }
                    REQ_PELAPOR -> {
//                            personel?.id?.let { sessionManager.setIDPersonelPelapor(it) }
                        changedIdPelapor = personel?.id
                        txt_nama_pelapor_pidana_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_pidana_lp_edit.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_pidana_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_pidana_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_pidana_lp_edit.text =
                            "Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                    }
                }
            }
        }
    }
}