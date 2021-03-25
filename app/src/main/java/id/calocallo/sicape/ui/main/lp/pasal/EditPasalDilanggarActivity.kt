package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.ListIdPasalReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_pasal_dilanggar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasalDilanggarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var pasalDilanggarReq = ListIdPasalReq()
    private var idPasal: Int? = null

    companion object {
        const val REQ_EDIT_PASAL_DILANGGAR = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasal_dilanggar)
        sessionManager1 = SessionManager1(this)
        val pasal = intent.extras?.getParcelable<PasalDilanggarResp>("DETAIL_EDIT_PASAL")
//        val namaJenis = intent.extras?.getString("NAMA_JENIS")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Pasal Yang Dilanggar"
        /* when (namaJenis) {
             "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
             "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
             "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
         }
 //        supportActionBar?.title = namaJenis
         edt_pasal_lp_edit.setText(pasal?.pasal?.nama_pasal)
         edt_detail_pasal_lp_edit.setText(pasal?.pasal?.isi_pasal)
 */


        /*view Edit*/
        idPasal = pasal?.pasal?.id
        txt_pasal_on_pasal_dilanggar_edit.text = pasal?.pasal?.nama_pasal
        txt_tentang_on_pasal_dilanggar_edit.text = pasal?.pasal?.tentang_pasal
        txt_isi_on_pasal_dilanggar_edit.text = pasal?.pasal?.isi_pasal

        btn_choose_pasal_edit.setOnClickListener {
            val intent = Intent(this, ListPasalActivity::class.java).apply {
                this.putExtra("PICK_PASAL", true)
            }
            startActivityForResult(intent, REQ_EDIT_PASAL_DILANGGAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_single_pasal_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_pasal_edit)
        btn_save_single_pasal_edit.setOnClickListener {
//            pasalReq.nama_pasal = edt_pasal_lp_edit.text.toString()
//            pasalReq.isi_pasal = edt_detail_pasal_lp_edit.text.toString()
            pasalDilanggarReq.id_pasal = idPasal
            updPasalDilanggar(pasal)
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, size, size)

            btn_save_single_pasal_edit.showProgress {
                progressColor = Color.WHITE
            }


        }

        btn_delete_single_pasal_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deletePasal()
                }
                negativeButton("Tidak")
            }.show()
        }
    }

    private fun updPasalDilanggar(pasal: PasalDilanggarResp?) {
        pasal?.id?.let {
            NetworkConfig().getServLp().updPasalDilanggar(
                "Bearer ${sessionManager1.fetchAuthToken()}", it, pasalDilanggarReq
            ).enqueue(object : Callback<Base1Resp<AddPasalDilanggarResp>> {
                override fun onFailure(call: Call<Base1Resp<AddPasalDilanggarResp>>, t: Throwable) {
                    Toast.makeText(
                        this@EditPasalDilanggarActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_save_single_pasal_edit.hideDrawable(R.string.not_update)
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddPasalDilanggarResp>>,
                    response: Response<Base1Resp<AddPasalDilanggarResp>>
                ) {
                    if (response.body()?.message == "Data pasal dilanggar updated succesfully") {
                        btn_save_single_pasal_edit.hideDrawable(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        toast("${response.body()?.message}")
                        btn_save_single_pasal_edit.hideDrawable(R.string.not_update)
                    }
                }
            })
        }
    }

    private fun deletePasal() {
        val pasal = intent.extras?.getParcelable<PasalDilanggarResp>("DETAIL_EDIT_PASAL")
        pasal?.id?.let {
            NetworkConfig().getServLp().delPasalDilanggar(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object :Callback<BaseResp>{
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@EditPasalDilanggarActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if(response.body()?.message == "Data pasal dilanggar removed succesfully"){
                        finish()
                    }else{
                        toast("${response.body()?.message}")
                        Toast.makeText(this@EditPasalDilanggarActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_EDIT_PASAL_DILANGGAR && resultCode == ListPasalActivity.RES_PASAL_DILANGGAR) {
            val pasal = data?.getParcelableExtra<PasalResp>("DATA_PASAL")
            idPasal = pasal?.id
            txt_pasal_on_pasal_dilanggar_edit.text = pasal?.nama_pasal
            txt_tentang_on_pasal_dilanggar_edit.text = pasal?.tentang_pasal
            txt_isi_on_pasal_dilanggar_edit.text = pasal?.isi_pasal
        }
    }
}