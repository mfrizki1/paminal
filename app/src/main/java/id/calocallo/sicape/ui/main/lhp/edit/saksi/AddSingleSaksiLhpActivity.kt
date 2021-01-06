package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SaksiLhpReq
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lhp.add.PickSaksiAddLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSingleSaksiLhpActivity : BaseActivity() {
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertD: View
    private lateinit var sessionManager: SessionManager
    private var saksiReqLhp = SaksiLhpReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_saksi_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(ADD_SAKSI_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi LHP"
        val addSaksi = intent.extras?.getString(PickSaksiAddLhpActivity.ADD_SAKSI)

        btn_add_saksi_lhp_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_saksi_lhp_single)
        btn_add_saksi_lhp_single.setOnClickListener {
            //for add single saksi from edit lhp
            if(addSaksi == null){
                saksiReqLhp.isi_keterangan_saksi = edt_ket_saksi.text.toString()
                Log.e("addSaksi", "$saksiReqLhp")
            //for add on lhp add(all)
            }else{
                saksiReqLhp.isi_keterangan_saksi = edt_ket_saksi.text.toString()
                val intent = Intent()
                intent.putExtra(SET_DATA_SAKSI, saksiReqLhp)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }

        rg_add_saksi.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Sipil") {
                saksiReqLhp.status_saksi = "sipil"
                ll_personel_saksi.gone()
                ll_sipil_saksi.visible()
                saksiReqLhp.id_personel = null

                //sipil view
                mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                btn_sipil_add_saksi.setOnClickListener {
                    sipilAlertD = LayoutInflater.from(this)
                        .inflate(R.layout.item_sipil_saksi, null, false)
                    launchSipilView()
                }


            } else {
                saksiReqLhp.status_saksi = "polisi"
                ll_personel_saksi.visible()
                ll_sipil_saksi.gone()
                saksiReqLhp.tempat_lahir = null
                saksiReqLhp.tanggal_lahir = null
                saksiReqLhp.alamat = null
                saksiReqLhp.pekerjaan = null

                //set button for add personel saksi
                btn_choose_personel_saksi.setOnClickListener {
                    val intent = Intent(this, ChoosePersonelActivity::class.java)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    startActivityForResult(intent, REQ_POLISI)
                }

            }

        }
    }

    private fun launchSipilView() {
        val namaSipilView = sipilAlertD.findViewById<TextInputEditText>(R.id.edt_nama_sipil_saksi)
        val pekerjaanSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil_saksi)
        val tempatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil_saksi)
        val tanggalSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil_saksi)
        val alamatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_alamat_sipil_saksi)

        mAlerDBuilder.setView(sipilAlertD)
            .setTitle("Tambah Data Sipil Saksi")
            .setPositiveButton("Tambah") { dialog, _ ->
                saksiReqLhp.alamat = alamatSipilView.text.toString()
                saksiReqLhp.tanggal_lahir = tanggalSipilView.text.toString()
                saksiReqLhp.tempat_lahir = tempatSipilView.text.toString()
                saksiReqLhp.pekerjaan = pekerjaanSipilView.text.toString()
                saksiReqLhp.nama = namaSipilView.text.toString()
                txt_nama_sipil_saksi.text = "Nama : ${saksiReqLhp.nama}"
                txt_alamat_sipil_saksi.text = "Alamat : ${saksiReqLhp.alamat}"
                txt_pekerjaan_sipil_saksi.text = "Pekerjaan : ${saksiReqLhp.pekerjaan}"
                txt_tanggal_lahir_sipil_saksi.text = "Tanggal Lahir : ${saksiReqLhp.tanggal_lahir}"
                txt_tempat_lahir_sipil_saksi.text = "Tempat Lahir : ${saksiReqLhp.tempat_lahir}"
            }
            .setNegativeButton("Batal") { dialog, _ -> }
            .show()
    }

    private fun addSaksiLhpSingle(detailLhp: LhpResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_add_saksi_lhp_single.showProgress {
            progressColor = Color.WHITE
        }
//        saksiReqLhp.nama = edt_nama_saksi_lhp_single.text.toString()
//        saksiReqLhp.uraian_saksi = edt_uraian_saksi_lhp_single.text.toString()

        btn_add_saksi_lhp_single.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_saksi_lhp_single.hideDrawable(R.string.save)
        }, 3000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelSaksi = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_POLISI) {
            saksiReqLhp.nama = personelSaksi?.nama
            saksiReqLhp.id_personel = personelSaksi?.id
            txt_nama_saksi_edit.text = "Nama : ${personelSaksi?.nama}"
            txt_pangkat_saksi_edit.text = "Pangkat : ${personelSaksi?.pangkat}"
            txt_nrp_saksi_edit.text = "NRP : ${personelSaksi?.nrp}"
            txt_jabatan_saksi_edit.text = "Jabatan : ${personelSaksi?.jabatan}"
            txt_kesatuan_saksi_edit.text = "Kesatuan : ${personelSaksi?.satuan_kerja?.kesatuan}"
        }
    }

    companion object {
        const val ADD_SAKSI_LHP = "ADD_SAKSI_LHP"
        const val REQ_POLISI = 123
        const val SET_DATA_SAKSI = "SET_DATA_SAKSI"
    }
}