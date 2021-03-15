package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.TindDisiplinReq
import id.calocallo.sicape.network.response.AddTindDisplResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.TindDisplResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditTindDisiplinSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var tindakan: String? = null
    private var tindDisiplinReq = TindDisiplinReq()
    private var idPersonel: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tind_disiplin_skhd)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Tindakan Disiplin"

        val dataTindDisp =
            intent.extras?.getParcelable<TindDisplResp>(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN)
        getDataTindDisiplin(dataTindDisp)

        idPersonel = dataTindDisp?.personel?.id
        /*set */
        val itemTind = listOf(
            "Lari",
            "Push Up",
            "Sit Up",
            "Jumping Jack",
            "Hormat Bendera",
            "Mengguling",
            "Jalan Jongkok"
        )
        val adapter = ArrayAdapter(this, R.layout.item_spinner, itemTind)
        spinner_tind_disiplin_edit.setAdapter(adapter)
        spinner_tind_disiplin_edit.setOnItemClickListener { parent, _, position, _ ->
            tindakan = parent.getItemAtPosition(position).toString()
        }

        btn_choose_personel_tind_disiplin_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL_TIND_DISP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_tind_disiplin_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_tind_disiplin_edit)
        btn_save_tind_disiplin_edit.setOnClickListener {
            btn_save_tind_disiplin_edit.showProgress {
                progressColor = Color.WHITE
            }
            tindDisiplinReq.isi_tindakan_disiplin = tindakan
            tindDisiplinReq.id_personel = idPersonel
            Log.e("edit Tind", "$tindDisiplinReq")
            apiEditTindDispl(dataTindDisp)
        }
    }

    private fun apiEditTindDispl(dataTindDisp: TindDisplResp?) {
        NetworkConfig().getServSkhd().updPersonelTindDispl(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            dataTindDisp?.id,
            tindDisiplinReq
        ).enqueue(
            object :
                Callback<Base1Resp<AddTindDisplResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddTindDisplResp>>,
                    response: Response<Base1Resp<AddTindDisplResp>>
                ) {
                    if (response.body()?.message == "Data tindakan disiplin updated succesfully") {
                        btn_save_tind_disiplin_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)
                    } else {
                        btn_save_tind_disiplin_edit.hideProgress(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddTindDisplResp>>, t: Throwable) {
                    btn_save_tind_disiplin_edit.hideProgress(R.string.not_update)
                    Toast.makeText(this@EditTindDisiplinSkhdActivity, "$t", Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERSONEL_TIND_DISP) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_tind_disiplin_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_tind_disiplin_edit.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_tind_disiplin_edit.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_tind_disiplin_edit.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_tind_disiplin_edit.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
            }
        }
    }

    private fun getDataTindDisiplin(dataTindDisp: TindDisplResp?) {
        spinner_tind_disiplin_edit.setText(dataTindDisp?.isi_tindakan_disiplin)
        tindakan = dataTindDisp?.isi_tindakan_disiplin
        txt_nama_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nama
        txt_pangkat_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.pangkat.toString()
            .toUpperCase(Locale.ROOT)
        txt_nrp_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nrp
        txt_jabatan_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.jabatan
        txt_kesatuan_personel_tind_disiplin_edit.text =
            dataTindDisp?.personel?.satuan_kerja?.kesatuan
    }

    companion object {
        const val REQ_PERSONEL_TIND_DISP = 2
    }
}