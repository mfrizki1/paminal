package id.calocallo.sicape.ui.main.lhp.edit.ref_penyelidikan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_detail_ref_penyelidikan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDetailRefPenyelidikanActivity : BaseActivity() {
    private var refLpReq = RefPenyelidikanReq()
    private var idLp: Int? = null
    private lateinit var sessionManager1: SessionManager1
    private var detailRef = RefPenyelidikanResp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager1 = SessionManager1(this)
        setContentView(R.layout.activity_edit_detail_ref_penyelidikan)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Referensi Penyelidikan"

        detailRef =
            intent.extras?.getParcelable<RefPenyelidikanResp>(ListDetailRefPenyelidikanActivity.REQ_DATA_LHP_FOR_DETAIL)!!
        getDetailRef(detailRef)

        /*set button for change lp*/
        btn_change_lp_ref_penyelidikan.setOnClickListener {
            val intent = Intent(this, PickJenisLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_LP_FOR_EDIT_REF_LP)
        }
        /*set button for update detaul ref penyelidikan*/
        btn_update_ref_penyelidikan.attachTextChangeAnimator()
        bindProgressButton(btn_update_ref_penyelidikan)
        btn_update_ref_penyelidikan.setOnClickListener {
            refLpReq.id_lp = idLp
            refLpReq.detail_keterangan_terlapor = edt_ket_terlapor_ref_edit.text.toString()
            updateRef(detailRef)
            Log.e("editDetailRef", "$refLpReq")
            btn_update_ref_penyelidikan.showProgress {
                progressColor = Color.WHITE
            }
        }

        /*set button for delete detail ref penyelidikan*/
        btn_delete_ref_penyelidikan.setOnClickListener {
            alert("Yakin Hapus Data") {
                positiveButton("Iya") {
                    deleteRefPenyelidikan(detailRef)
                }
                negativeButton("Tidak") {}
            }.show()
        }
    }

    private fun getDetailRef(detailRef: RefPenyelidikanResp) {
        NetworkConfig().getServLhp()
            .detailRefPenyelidikan("Bearer ${sessionManager1.fetchAuthToken()}", detailRef.id)
            .enqueue(
                object : Callback<RefPenyelidikanResp> {
                    override fun onResponse(
                        call: Call<RefPenyelidikanResp>,
                        response: Response<RefPenyelidikanResp>
                    ) {
                        if (response.isSuccessful) {
                            viewDetailRef(response.body())
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<RefPenyelidikanResp>, t: Throwable) {
                        Toast.makeText(
                            this@EditDetailRefPenyelidikanActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    private fun viewDetailRef(dataRef: RefPenyelidikanResp?) {
        /*viewDetail*/
        txt_no_lp_ref_edit.text = dataRef?.lp?.no_lp
        edt_ket_terlapor_ref_edit.setText(dataRef?.lp?.detail_keterangan_terlapor)
    }

    private fun deleteRefPenyelidikan(detailRef: RefPenyelidikanResp) {
        NetworkConfig().getServLhp()
            .delRefPenyelidikan("Bearer ${sessionManager1.fetchAuthToken()}", detailRef.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data referensi penyelidikan removed succesfully") {
                            Toast.makeText(
                                this@EditDetailRefPenyelidikanActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 1000)
                        } else {
                            Toast.makeText(
                                this@EditDetailRefPenyelidikanActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(
                            this@EditDetailRefPenyelidikanActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    private fun updateRef(detailRef: RefPenyelidikanResp) {
        NetworkConfig().getServLhp().updRefPenyelidikan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailRef.lp?.id,
            refLpReq
        ).enqueue(object : Callback<Base1Resp<DokLpResp>> {
            override fun onResponse(
                call: Call<Base1Resp<DokLpResp>>,
                response: Response<Base1Resp<DokLpResp>>
            ) {
                if (response.body()?.message == "Data keterangan terlapor updated succesfully") {
                    btn_update_ref_penyelidikan.hideProgress(R.string.data_updated)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 750)
                } else {
                    btn_update_ref_penyelidikan.hideProgress(R.string.not_update)
                }
            }

            override fun onFailure(
                call: Call<Base1Resp<DokLpResp>>, t: Throwable
            ) {
                Toast.makeText(this@EditDetailRefPenyelidikanActivity, "$t", Toast.LENGTH_SHORT)
                    .show()
                btn_update_ref_penyelidikan.hideProgress(R.string.not_update)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val dataRefLp =
            data?.getParcelableExtra<LpMinResp>(AddRefPenyelidikActivity.GET_LP_FROM_CHOOSE_LP)
        if (resultCode == AddRefPenyelidikActivity.RES_LP_ON_REF && requestCode == REQ_LP_FOR_EDIT_REF_LP) {
            txt_no_lp_ref_edit.text = dataRefLp?.no_lp
            idLp = dataRefLp?.id
        }
    }

    companion object {
        const val REQ_LP_FOR_EDIT_REF_LP = 1
    }
}