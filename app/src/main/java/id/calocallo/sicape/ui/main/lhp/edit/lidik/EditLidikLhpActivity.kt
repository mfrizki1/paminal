package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lidik_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var lidikReq = PersonelPenyelidikReq()
    private var statusLidik: String? = null
    private var idPersonelLidik: Int? = null
    private var currIsKetua: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lidik_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Personel Penyelidik"
        val lhp = intent.extras?.getParcelable<PersonelPenyelidikResp>(EDIT_LIDIK)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_lidik_edit.gone()
            btn_save_lidik_edit.gone()
        }
        btn_save_lidik_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_lidik_edit)
        btn_save_lidik_edit.setOnClickListener {
            lidikReq.id_personel = idPersonelLidik
            lidikReq.is_ketua = currIsKetua
Log.e("lidikReq", "$lidikReq")
            btn_save_lidik_edit.showProgress {
                progressColor = Color.WHITE
            }
            updateLidik(lhp)
        }
        btn_delete_lidik_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteLidik(lhp)
                }
                negativeButton("Tidak")
            }.show()
        }
        getViewLidik(lhp)
        btn_choose_personel_lidik_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL_LIDIK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rg_lidik_edit.setOnCheckedChangeListener { group, checkedId ->
            var radio :RadioButton = findViewById(checkedId)
            if(radio.isChecked && radio.text == "Ketua Tim"){
                currIsKetua = 1
            }else{
                currIsKetua = 0
            }
        }
    }

    companion object {

        const val EDIT_LIDIK = "EDIT_LIDIK"
        const val REQ_PERSONEL_LIDIK = 123
    }

    @SuppressLint("SetTextI18n")
    private fun getViewLidik(lhp: PersonelPenyelidikResp?) {
        when (lhp?.is_ketua) {
            1 -> {
                rb_ketua_lidik_edit.isChecked = true
                currIsKetua = 1
            }
            0 -> {
                rb_anggota_lidik_edit.isChecked = true
                currIsKetua = 0
            }
        }
        idPersonelLidik = lhp?.personel?.id
        txt_nama_lidik_edit.text = "Nama : ${lhp?.personel?.nama}"
        txt_pangkat_lidik_edit.text = "Pangkat : ${lhp?.personel?.pangkat.toString().toUpperCase()}"
        txt_nrp_lidik_edit.text = "NRP : ${lhp?.personel?.nrp}"
        txt_jabatan_lidik_edit.text = "Jabatan : ${lhp?.personel?.jabatan}"
        txt_kesatuan_lidik_edit.text =
            "Kesatuan : ${lhp?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

    }

    private fun updateLidik(lhp: PersonelPenyelidikResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)

        NetworkConfig().getServLhp().updPersLidik("Bearer ${sessionManager1.fetchAuthToken()}", lhp?.id, lidikReq).enqueue(
            object :
                Callback<Base1Resp<AddPersLidikResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddPersLidikResp>>,
                    response: Response<Base1Resp<AddPersLidikResp>>
                ) {
                    if (response.body()?.message == "Data personel penyelidik updated succesfully") {
                        btn_save_lidik_edit.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_updated
                            textMarginRes = R.dimen.space_10dp
                        }

                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },1000)
                    } else {
                        Toast.makeText(this@EditLidikLhpActivity, R.string.error, Toast.LENGTH_SHORT).show()
                        btn_save_lidik_edit.hideDrawable(R.string.not_update)
                    }

                }

                override fun onFailure(call: Call<Base1Resp<AddPersLidikResp>>, t: Throwable) {
                    Toast.makeText(this@EditLidikLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_lidik_edit.hideDrawable(R.string.not_update)
                }
            })



    }

    private fun deleteLidik(lhp: PersonelPenyelidikResp?) {
        NetworkConfig().getServLhp().delPersLidik("Bearer ${sessionManager1.fetchAuthToken()}", lhp?.id).enqueue(
            object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data personel penyelidik removed succesfully") {
                        Toast.makeText(
                            this@EditLidikLhpActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1000)
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@EditLidikLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_PERSONEL_LIDIK) {
            idPersonelLidik = personel?.id
            txt_nama_lidik_edit.text ="Nama ${personel?.nama}"
            txt_pangkat_lidik_edit.text ="Pangkat ${personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_lidik_edit.text ="NRP ${personel?.nrp}"
            txt_jabatan_lidik_edit.text ="Jabatan ${personel?.jabatan}"
            txt_kesatuan_lidik_edit.text ="Kesatuan ${personel?.satuan_kerja?.kesatuan}"
        }
    }


}
