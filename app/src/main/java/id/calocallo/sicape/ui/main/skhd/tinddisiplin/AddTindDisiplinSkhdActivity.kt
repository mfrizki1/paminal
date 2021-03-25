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
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTindDisiplinSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var tindDisiplinReq = TindDisiplinReq()
    private var idPersonel: Int? = null
    private var tindakan: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tind_disiplin_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Tindakan Disiplin"

        sessionManager1 = SessionManager1(this)
        btn_save_tind_disiplin_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_tind_disiplin_add)
        btn_save_tind_disiplin_add.setOnClickListener {
            btn_save_tind_disiplin_add.showProgress {
                progressColor = Color.WHITE
            }
            tindDisiplinReq.id_personel = idPersonel
            tindDisiplinReq.isi_tindakan_disiplin = tindakan
            Log.e("add Tind", "$tindDisiplinReq")
            addTindDispl()
        }

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
        spinner_tind_disiplin.setAdapter(adapter)
        spinner_tind_disiplin.setOnItemClickListener { parent, _, position, _ ->
            tindakan = parent.getItemAtPosition(position).toString()
//            Log.e("spinner", parent.getItemAtPosition(position).toString())
        }

        btn_choose_personel_tind_disiplin.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addTindDispl() {
        NetworkConfig().getServSkhd()
            .addPersonelTindDispl("Bearer ${sessionManager1.fetchAuthToken()}", tindDisiplinReq)
            .enqueue(
                object :
                    Callback<Base1Resp<AddTindDisplResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddTindDisplResp>>,
                        response: Response<Base1Resp<AddTindDisplResp>>
                    ) {
                        if (response.body()?.message == "Data tindakan disiplin saved succesfully") {
                            btn_save_tind_disiplin_add.hideProgress(R.string.data_saved)
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            btn_save_tind_disiplin_add.hideProgress(R.string.not_save)
                            toast("${response.body()?.message}")
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddTindDisplResp>>, t: Throwable) {
                        btn_save_tind_disiplin_add.hideProgress(R.string.not_save)
                        Toast.makeText(this@AddTindDisiplinSkhdActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_tind_disiplin_add.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_tind_disiplin_add.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_tind_disiplin_add.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_tind_disiplin_add.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_tind_disiplin_add.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
            }
        }
    }

    companion object {
        const val REQ_PERSONEL_TIND = 1
    }
}