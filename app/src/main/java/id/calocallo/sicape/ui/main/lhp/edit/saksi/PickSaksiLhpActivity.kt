package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListSaksi
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.saksi.AddSingleSaksiLhpActivity.Companion.ADD_SAKSI_LHP
import id.calocallo.sicape.ui.main.lhp.edit.saksi.EditSaksiLhpActivity.Companion.EDIT_SAKSI_LHP
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterSaksiLhp: ReusableAdapter<ListSaksi>
    private lateinit var callbackSaksiLhp: AdapterCallback<ListSaksi>
    private var listSaksi = arrayListOf<ListSaksi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi_lhp)
        sessionManager = SessionManager(this)
        adapterSaksiLhp = ReusableAdapter(this)

        val detailLhp = intent.extras?.getParcelable<LhpModel>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Saksi"

        btn_add_single_saksi_lhp.setOnClickListener {
            val intent = Intent(this, AddSingleSaksiLhpActivity::class.java)
            intent.putExtra(ADD_SAKSI_LHP, detailLhp)
            startActivity(intent)

        }

        getListSaksiLhp()
    }

    private fun getListSaksiLhp() {
        listSaksi.add(ListSaksi("Ujang", "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor "))
        listSaksi.add(ListSaksi("Sari", "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor "))
        listSaksi.add(ListSaksi("Ahmad", "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor "))
        listSaksi.add(ListSaksi("Rojak", "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor "))
        listSaksi.add(ListSaksi("Utuh", "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor "))

        callbackSaksiLhp = object : AdapterCallback<ListSaksi>{
            override fun initComponent(itemView: View, data: ListSaksi, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama_saksi
            }

            override fun onItemClicked(itemView: View, data: ListSaksi, itemIndex: Int) {
                val intent = Intent(this@PickSaksiLhpActivity, EditSaksiLhpActivity::class.java)
                intent.putExtra(EDIT_SAKSI_LHP, data)
                startActivity(intent)
            }
        }
        adapterSaksiLhp.adapterCallback(callbackSaksiLhp)
            .isVerticalView().addData(listSaksi).setLayout(R.layout.layout_edit_1_text).build(rv_list_saksi_lhp)
    }
}