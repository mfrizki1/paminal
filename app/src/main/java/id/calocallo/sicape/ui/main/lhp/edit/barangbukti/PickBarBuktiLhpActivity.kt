package id.calocallo.sicape.ui.main.lhp.edit.barangbukti

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListBukti
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.barangbukti.AddBarBuktiLhpActivity.Companion.ADD_BARBUKTI
import id.calocallo.sicape.ui.main.lhp.edit.barangbukti.EditBarBuktiLhpActivity.Companion.EDIT_BARBUKTI
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_bar_bukti_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickBarBuktiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterBarBukti: ReusableAdapter<ListBukti>
    private lateinit var callbackBarBukti: AdapterCallback<ListBukti>
    private var listBukti = arrayListOf<ListBukti>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_bar_bukti_lhp)
        sessionManager = SessionManager(this)
        adapterBarBukti = ReusableAdapter(this)

        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Petunjuk"
        btn_add_single_bar_bukti.setOnClickListener {
            val intent = Intent(this, AddBarBuktiLhpActivity::class.java)
            intent.putExtra(ADD_BARBUKTI, detailLhp)
            startActivity(intent)
        }
        getListBarBukti()
    }

    private fun getListBarBukti() {
        listBukti.add(ListBukti("asdv"))
        listBukti.add(ListBukti("acdftre"))

        callbackBarBukti = object : AdapterCallback<ListBukti> {
            override fun initComponent(itemView: View, data: ListBukti, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.bukti
            }

            override fun onItemClicked(itemView: View, data: ListBukti, itemIndex: Int) {
                val intent =
                    Intent(this@PickBarBuktiLhpActivity, EditBarBuktiLhpActivity::class.java)
                intent.putExtra(EDIT_BARBUKTI, data)
                startActivity(intent)
            }
        }
        adapterBarBukti.adapterCallback(callbackBarBukti)
            .isVerticalView().addData(listBukti).setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_bar_bukti)
    }
}