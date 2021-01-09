package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity.Companion.GET_TERLAPOR
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var ketTerlaporReq = KetTerlaporReq()
    private var idTerlapor: Int? = null
    private var namaTerlapor: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_terlapor_lhp)
        sessionManager = SessionManager(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Terlapor"

        /*SET INTENT VALUE*/
        val addKetTerlaporFromAllLHP = intent.extras?.getString(ListKetTerlaporLhpActivity.LIST_KET_TERLAPOR)
        btn_add_terlapor_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_terlapor_single)
        btn_add_terlapor_single.setOnClickListener {
            addTerlapor(addKetTerlaporFromAllLHP)
        }

        /*set button for get personel*/
        bt_choose_personel_terlapor.setOnClickListener {
            //TODO CHANGE FILTERING PERSONEL(BERDASARKAN LP YG ADA DI LHP TRS PILIH PERSONELNYA)
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, ADD_TERLAPOR_PERSONEL)
        }
    }


    private fun addTerlapor(ketTerlapor: String?) {
        if (ketTerlapor == null) {
//        terlaporReq.nama_terlapor = edt_nama_terlapor_single.text.toString()
            ketTerlaporReq.isi_keterangan_terlapor = edt_ket_terlapor_add.text.toString()
            ketTerlaporReq.id_personel = idTerlapor
            ketTerlaporReq.nama_pesonel = namaTerlapor
            Log.e("addSingleTErapor","$ketTerlaporReq")

            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            btn_add_terlapor_single.showProgress {
                progressColor = Color.WHITE
            }

            btn_add_terlapor_single.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_add_terlapor_single.hideDrawable(R.string.save)
            }, 3000)
        } else {
            ketTerlaporReq.isi_keterangan_terlapor = edt_ket_terlapor_add.text.toString()
            ketTerlaporReq.id_personel = idTerlapor
            ketTerlaporReq.nama_pesonel = namaTerlapor
            val intent = Intent()
            intent.putExtra(GET_TERLAPOR, ketTerlaporReq)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_TERLAPOR_PERSONEL) {
            idTerlapor = personel?.id
            namaTerlapor = personel?.nama
            txt_nama_terlapor_add.text = "Nama : ${personel?.nama}"
            txt_pangkat_terlapor_add.text = "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_terlapor_add.text = "NRP : ${personel?.nrp}"
            txt_jabatan_terlapor_add.text = "Jabatan : ${personel?.jabatan}"
            txt_kesatuan_terlapor_add.text = "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    companion object {
        const val ADD_TERLAPOR = "ADD_TERLAPOR"
        const val ADD_TERLAPOR_PERSONEL = 121
    }
}