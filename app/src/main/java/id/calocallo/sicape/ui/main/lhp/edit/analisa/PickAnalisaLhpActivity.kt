package id.calocallo.sicape.ui.main.lhp.edit.analisa

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListAnalisa
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.analisa.AddAnalisaLhpActivity.Companion.ADD_ANALISA
import id.calocallo.sicape.ui.main.lhp.edit.analisa.EditAnalisaLhpActivity.Companion.EDIT_ANALISA
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_analisa_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickAnalisaLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterAnalisa: ReusableAdapter<ListAnalisa>
    private lateinit var callbackAnalisa: AdapterCallback<ListAnalisa>
    private var listAnalisa = arrayListOf<ListAnalisa>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_analisa_lhp)
        sessionManager1 = SessionManager1(this)
        adapterAnalisa = ReusableAdapter(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Analisa"

        btn_add_single_analisa.setOnClickListener {
            val intent = Intent(this, AddAnalisaLhpActivity::class.java)
            intent.putExtra(ADD_ANALISA, detailLhp)
            startActivity(intent)

        }
        getListAnalisa()
    }

    private fun getListAnalisa() {
        listAnalisa.add(ListAnalisa("Analisa 1 dibuktikan bahwa pelaku melakukan abc dan meninggalkan tempat pada xxx "))
        listAnalisa.add(ListAnalisa("Analisa 2 dibuktikan bahwa pelaku melakukan abc dan meninggalkan tempat pada xxx "))
        listAnalisa.add(ListAnalisa("Analisa 3 dibuktikan bahwa pelaku melakukan abc dan meninggalkan tempat pada xxx "))
        callbackAnalisa = object : AdapterCallback<ListAnalisa> {
            override fun initComponent(itemView: View, data: ListAnalisa, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.analisa
            }

            override fun onItemClicked(itemView: View, data: ListAnalisa, itemIndex: Int) {
                val intent = Intent(this@PickAnalisaLhpActivity, EditAnalisaLhpActivity::class.java)
                intent.putExtra(EDIT_ANALISA, data)
                startActivity(intent)
            }
        }
        adapterAnalisa.adapterCallback(callbackAnalisa)
            .isVerticalView()
            .addData(listAnalisa)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_analisa)
    }
}