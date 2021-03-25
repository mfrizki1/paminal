package id.calocallo.sicape.ui.main.rehab.rpph

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.response.AddRpphResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditRpphActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var rpphReq = RpphReq()
    private var idPutKke: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data RPPH"
        sessionManager1 = SessionManager1(this)

        val dataRpph = intent.extras?.getParcelable<RpphResp>(EDIT_RPPH_EDIT)
        getDataRpph(dataRpph)

        btn_save_rpph_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_rpph_edit)
        btn_save_rpph_edit.setOnClickListener {
            updateRpph(dataRpph)
        }

        /*    btn_pick_put_kke_rps_edit.setOnClickListener {
                val intent = Intent(this, ChooseSkhdActivity::class.java)
                intent.putExtra(ChooseSkhdActivity.PUT_KKE, ChooseSkhdActivity.PUT_KKE)
                startActivityForResult(intent, REQ_PUT_KKE_ON_RPPH_EDIT)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }*/
    }

    private fun updateRpph(dataRpph: RpphResp?) {
        rpphReq.no_rpph = edt_no_rpph_edit.text.toString()
        rpphReq.nama_dinas = edt_nama_dinas_rpph_edit.text.toString()
        rpphReq.no_nota_dinas = edt_no_nota_dinas_rpph_edit.text.toString()
        rpphReq.tanggal_nota_dinas = edt_tgl_nota_dinas_rpph_edit.text.toString()
        rpphReq.penerima_salinan_keputusan =
            edt_penerima_salinan_keputusan_rpph_edit.text.toString()
        rpphReq.kota_penetapan = edt_kota_penetapan_rpph_edit.text.toString()
        rpphReq.tanggal_penetapan = edt_tanggal_penetapan_rpph_edit.text.toString()
        rpphReq.nama_kabid_propam = edt_nama_pimpinan_rpph_edit.text.toString()
        rpphReq.pangkat_kabid_propam = edt_pangkat_pimpinan_rpph_edit.text.toString().toUpperCase()
        rpphReq.nrp_kabid_propam = edt_nrp_pimpinan_rpph_edit.text.toString()
        Log.e("edit RPPH", "$rpphReq")


        btn_save_rpph_edit.showProgress {
            progressColor = Color.WHITE
        }

        btn_save_rpph_edit.hideDrawable(R.string.save)
        apiUpdRpph(dataRpph)
    }

    private fun apiUpdRpph(dataRpph: RpphResp?) {
        NetworkConfig().getServRpph().updRpph("Bearer ${sessionManager1.fetchAuthToken()}", dataRpph?.id, rpphReq).enqueue(
            object :
                Callback<Base1Resp<AddRpphResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddRpphResp>>,
                    response: Response<Base1Resp<AddRpphResp>>
                ) {
                    if (response.body()?.message == "Data rpph updated succesfully") {
                        btn_save_rpph_edit.hideDrawable(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)

                    } else {
                        toast("${response.body()?.message}")
                        btn_save_rpph_edit.hideDrawable(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddRpphResp>>, t: Throwable) {
                    Toast.makeText(this@EditRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_rpph_edit.hideDrawable(R.string.not_update)
                }
            })
    }

    private fun getDataRpph(dataRpph: RpphResp?) {
        txt_lp_rpph_edit.text = dataRpph?.lp?.no_lp
        edt_no_rpph_edit.setText(dataRpph?.no_rpph)
        edt_nama_dinas_rpph_edit.setText(dataRpph?.nama_dinas)
        edt_no_nota_dinas_rpph_edit.setText(dataRpph?.no_nota_dinas)
        edt_tgl_nota_dinas_rpph_edit.setText(dataRpph?.tanggal_nota_dinas)
        edt_penerima_salinan_keputusan_rpph_edit.setText(dataRpph?.penerima_salinan_keputusan)
        edt_kota_penetapan_rpph_edit.setText(dataRpph?.kota_penetapan)
        edt_tanggal_penetapan_rpph_edit.setText(dataRpph?.tanggal_penetapan)
        edt_nama_pimpinan_rpph_edit.setText(dataRpph?.nama_kabid_propam)
        edt_pangkat_pimpinan_rpph_edit.setText(
            dataRpph?.pangkat_kabid_propam.toString()
                .toUpperCase(Locale.ROOT)
        )
        edt_nrp_pimpinan_rpph_edit.setText(dataRpph?.nrp_kabid_propam)
        /* txt_put_kke_rps_edit.text = dataRpph?.putkke?.no_putkke
         idPutKke = dataRpph?.putkke?.id
         edt_no_rpph_edit.setText(dataRpph?.no_rpph)
         edt_dasar_rpph_edit.setText(dataRpph?.dasar_ph)
         edt_isi_rekomendasi_rpph_edit.setText(dataRpph?.isi_rekomendasi)
         edt_kota_penetapan_rpph_edit.setText(dataRpph?.kota_penetapan)
         edt_tanggal_penetapan_rpph_edit.setText(dataRpph?.tanggal_penetapan)
         edt_nama_pimpinan_rpph_edit.setText(dataRpph?.nama_yang_menetapkan)
         edt_pangkat_pimpinan_rpph_edit.setText(
             dataRpph?.pangkat_yang_menetapkan.toString().toUpperCase()
         )
         edt_nrp_pimpinan_rpph_edit.setText(dataRpph?.nrp_yang_menetapkan)
         edt_jabatan_pimpinan_rpph_edit.setText(dataRpph?.jabatan_yang_menetapkan)
         edt_tembusan_rpph_edit.setText(dataRpph?.tembusan)*/
    }

    companion object {
        const val EDIT_RPPH_EDIT = "EDIT_RPPH"
        const val GET_RPPH_EDIT = "GET_RPPH"
        const val REQ_PUT_KKE_ON_RPPH_EDIT = 1
        const val RES_PUT_KKE_ON_RPPH_EDIT = 2
    }
}