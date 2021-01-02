package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListTerlapor
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.AddTerlaporLhpActivity.Companion.ADD_TERLAPOR
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.EditTerlaporLhpActivity.Companion.EDIT_TERLAPOR
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterTerlapor: ReusableAdapter<ListTerlapor>
    private lateinit var callbackTerlapor: AdapterCallback<ListTerlapor>
    private var listTerlapor = arrayListOf<ListTerlapor>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_terlapor_lhp)
        sessionManager = SessionManager(this)
        adapterTerlapor = ReusableAdapter(this)
        val detailLhp = intent.extras?.getParcelable<LhpModel>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Terlapor"

        getListTerlapor()
        btn_add_single_terlapor.setOnClickListener {
            val intent = Intent(this, AddTerlaporLhpActivity::class.java)
            intent.putExtra(ADD_TERLAPOR, detailLhp)
            startActivity(intent)
        }
    }

    private fun getListTerlapor() {
        listTerlapor.add(ListTerlapor("Hariyanto", "Melakukan Samting samting"))
        listTerlapor.add(ListTerlapor("Sri Wahyuni", "Sedang tidur pada posko"))
        listTerlapor.add(ListTerlapor("Faisal", "XXXXXXXXXXXXXX"))
        callbackTerlapor = object : AdapterCallback<ListTerlapor> {
            override fun initComponent(itemView: View, data: ListTerlapor, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama_terlapor
            }

            override fun onItemClicked(itemView: View, data: ListTerlapor, itemIndex: Int) {
                val intent =
                    Intent(this@PickTerlaporLhpActivity, EditTerlaporLhpActivity::class.java)
                intent.putExtra(EDIT_TERLAPOR, data)
                startActivity(intent)
            }
        }
        adapterTerlapor.adapterCallback(callbackTerlapor)
            .isVerticalView().addData(listTerlapor).setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_terlapor)
    }
}