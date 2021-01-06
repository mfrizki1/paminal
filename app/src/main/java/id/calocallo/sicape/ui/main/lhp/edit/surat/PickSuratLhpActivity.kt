package id.calocallo.sicape.ui.main.lhp.edit.surat

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListSurat
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.surat.AddSingleSuratLhpActivity.Companion.ADD_SURAT
import id.calocallo.sicape.ui.main.lhp.edit.surat.EditSuratLhpActivity.Companion.EDIT_SURAT
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_surat_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickSuratLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterSurat: ReusableAdapter<ListSurat>
    private lateinit var callbackSurat: AdapterCallback<ListSurat>
    private var listSurat = arrayListOf<ListSurat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_surat_lhp)
        sessionManager = SessionManager(this)
        adapterSurat = ReusableAdapter(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Surat"

        btn_add_single_surat.setOnClickListener {
            val intent = Intent(this@PickSuratLhpActivity, AddSingleSuratLhpActivity::class.java)
            intent.putExtra(ADD_SURAT, detailLhp)
            startActivity(intent)
        }

        getListSurat()

    }

    private fun getListSurat() {
        listSurat.add(ListSurat("Surat a"))
        listSurat.add(ListSurat("Surat B"))
        listSurat.add(ListSurat("Surat cdr"))
        callbackSurat = object : AdapterCallback<ListSurat> {
            override fun initComponent(itemView: View, data: ListSurat, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.surat
            }

            override fun onItemClicked(itemView: View, data: ListSurat, itemIndex: Int) {
                val intent = Intent(this@PickSuratLhpActivity, EditSuratLhpActivity::class.java)
                intent.putExtra(EDIT_SURAT, data)
                startActivity(intent)
            }
        }
        adapterSurat.adapterCallback(callbackSurat)
            .isVerticalView()
            .build(rv_list_surat)
            .setLayout(R.layout.layout_edit_1_text)
            .addData(listSurat)
    }
}