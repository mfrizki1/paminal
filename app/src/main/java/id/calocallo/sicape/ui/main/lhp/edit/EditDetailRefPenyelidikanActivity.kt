package id.calocallo.sicape.ui.main.lhp.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_detail_ref_penyelidikan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditDetailRefPenyelidikanActivity : BaseActivity() {
    private var refLpReq = RefPenyelidikanReq()
    private lateinit var sessionManager: SessionManager
    private var detailRef = RefPenyelidikanResp()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        setContentView(R.layout.activity_edit_detail_ref_penyelidikan)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Referensi Penyelidikan"

        detailRef =
            intent.extras?.getParcelable<RefPenyelidikanResp>(ListDetailRefPenyelidikanActivity.REQ_DATA_LHP_FOR_DETAIL)!!

        txt_no_lp_ref_edit.text = detailRef.no_lp

        /*set button for change lp*/
        btn_change_lp_ref_penyelidikan.setOnClickListener {
            val intent = Intent(this, ChooseLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_LP_FOR_EDIT_REF_LP)
        }
        /*set button for update detaul ref penyelidikan*/
        btn_update_ref_penyelidikan.setOnClickListener {
            updateRef(detailRef)
            Log.e("editDetailRef", "$refLpReq")
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

    private fun deleteRefPenyelidikan(detailRef: RefPenyelidikanResp) {

    }

    private fun updateRef(detailRef: RefPenyelidikanResp) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        refLpReq = data?.getParcelableExtra<RefPenyelidikanReq>(AddLhpActivity.DATA_LP)!!
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_LP_FOR_EDIT_REF_LP) {
            txt_no_lp_ref_edit.text = refLpReq.no_lp
        }
    }

    companion object {
        const val REQ_LP_FOR_EDIT_REF_LP = 1
    }
}