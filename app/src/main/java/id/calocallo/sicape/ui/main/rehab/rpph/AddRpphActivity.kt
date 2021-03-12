package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.response.AddRpphResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.ui.main.choose.lp.LpChooseActivity
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRpphActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var rpphReq = RpphReq()
    private var idLp: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data RPPH"
        sessionManager1 = SessionManager1(this)

        btn_pick_lp_rpph_add.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(IS_RPPH, true)
            startActivityForResult(intent, REQ_LP_RPPH)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_rpph_add.setOnClickListener {
            rpphReq.id_lp = idLp
            rpphReq.no_rpph = edt_no_rpph_add.text.toString()
            rpphReq.nama_dinas = edt_nama_dinas_rpph_add.text.toString()
            rpphReq.no_nota_dinas = edt_no_nota_dinas_rpph_add.text.toString()
            rpphReq.tanggal_nota_dinas = edt_tgl_nota_dinas_rpph_add.text.toString()
            rpphReq.penerima_salinan_keputusan =
                edt_penerima_salinan_keputusan_rpph_add.text.toString()
            rpphReq.kota_penetapan = edt_kota_penetapan_rpph_add.text.toString()
            rpphReq.tanggal_penetapan = edt_tanggal_penetapan_rpph_add.text.toString()
            rpphReq.nama_kabid_propam = edt_nama_pimpinan_rpph_add.text.toString()
            rpphReq.pangkat_kabid_propam =
                edt_pangkat_pimpinan_rpph_add.text.toString().toUpperCase()
            rpphReq.nrp_kabid_propam = edt_nrp_pimpinan_rpph_add.text.toString()
            btn_save_rpph_add.showProgress {
                progressColor = Color.WHITE
            }
            apiAddRpph()
            Log.e("add Data RPPH", "$rpphReq")
        }

    }

    private fun apiAddRpph() {
        NetworkConfig().getServRpph().addRpph("Bearer ${sessionManager1.fetchAuthToken()}", rpphReq)
            .enqueue(
                object :
                    Callback<Base1Resp<AddRpphResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddRpphResp>>,
                        response: Response<Base1Resp<AddRpphResp>>
                    ) {
                        if (response?.body()?.message == "Data rpph saved succesfully") {
                            btn_save_rpph_add.hideProgress(R.string.data_saved)
                            btn_save_rpph_add.showSnackbar(R.string.data_saved) {
                                action(R.string.next) {
                                    finish()
                                }
                            }
                        } else {
                            btn_save_rpph_add.hideProgress(R.string.not_save)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddRpphResp>>, t: Throwable) {
                        Toast.makeText(this@AddRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_rpph_add.hideProgress(R.string.not_save)
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_RPPH) {
            if (resultCode == PickJenisLpActivity.RES_LP_CHOOSE) {
                val dataLp = data?.getParcelableExtra<LpMinResp>(AddLhpActivity.DATA_LP)
//                rpphReq.id_putkke = putKkeModel?.id
                txt_lp_rpph_add.text = dataLp?.no_lp
                idLp = dataLp?.id
            }
        }
    }

    companion object {
        const val GET_RPPH = "GET_RPPH"
        const val IS_RPPH = "IS_RPPH"
        const val REQ_LP_RPPH = 1
        const val RES_PUT_KKE_ON_RPPH = 2
    }
}