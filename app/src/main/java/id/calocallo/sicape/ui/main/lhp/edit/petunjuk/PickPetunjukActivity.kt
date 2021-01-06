package id.calocallo.sicape.ui.main.lhp.edit.petunjuk

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListPetunjuk
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.petunjuk.AddPetunjukLhpActivity.Companion.ADD_PETUNJUK
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_petunjuk.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickPetunjukActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterPetunjuk: ReusableAdapter<ListPetunjuk>
    private lateinit var callbackPetunjuk: AdapterCallback<ListPetunjuk>
    private var listPetunjuk = arrayListOf<ListPetunjuk>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_petunjuk)
        sessionManager = SessionManager(this)
        adapterPetunjuk = ReusableAdapter(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Petunjuk"
        btn_add_single_petunjuk.setOnClickListener {
            val intent = Intent(this, AddPetunjukLhpActivity::class.java)
            intent.putExtra(ADD_PETUNJUK, detailLhp)
            startActivity(intent)
        }
        getListPetunjuk()
    }

    private fun getListPetunjuk() {
        listPetunjuk.add(ListPetunjuk("abc"))
        listPetunjuk.add(ListPetunjuk("detpvfdnssd"))
        listPetunjuk.add(ListPetunjuk("saduhasnfeslffojfd"))
        callbackPetunjuk = object : AdapterCallback<ListPetunjuk> {
            override fun initComponent(itemView: View, data: ListPetunjuk, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.petunjuk
            }

            override fun onItemClicked(itemView: View, data: ListPetunjuk, itemIndex: Int) {
                val intent = Intent(this@PickPetunjukActivity, EditPetunjukLhpActivity::class.java)
                intent.putExtra(EditPetunjukLhpActivity.EDIT_PETUNJUK, data)
                startActivity(intent)
            }
        }
        adapterPetunjuk.adapterCallback(callbackPetunjuk)
            .isVerticalView()
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_petunjuk)
            .addData(listPetunjuk)
    }
}