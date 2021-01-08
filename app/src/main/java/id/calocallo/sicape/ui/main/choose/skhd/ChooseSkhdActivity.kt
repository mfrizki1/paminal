package id.calocallo.sicape.ui.main.choose.skhd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.ui.main.rehab.rps.AddRpsActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_skhd.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ChooseSkhdActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var skhdOnRpsModel = ArrayList<SkhdOnRpsModel>()
    private var adapterSkhdOnRps = ReusableAdapter<SkhdOnRpsModel>(this)
    private lateinit var callbackSkhdOnRps: AdapterCallback<SkhdOnRpsModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data SKHD"
        sessionManager = SessionManager(this)
        getListChooseSkhd()
    }

    private fun getListChooseSkhd() {
        skhdOnRpsModel.add(SkhdOnRpsModel(1, "SKHD1/2020?xxx"))
        skhdOnRpsModel.add(SkhdOnRpsModel(2, "SKHD2/2020?xxx"))
        skhdOnRpsModel.add(SkhdOnRpsModel(3, "SKHD3/2020?xxx"))
        callbackSkhdOnRps = object : AdapterCallback<SkhdOnRpsModel> {
            override fun initComponent(itemView: View, data: SkhdOnRpsModel, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_skhd
            }

            override fun onItemClicked(itemView: View, data: SkhdOnRpsModel, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra(AddRpsActivity.GET_SKHD, data)
                setResult(AddRpsActivity.RES_ID_SKHD, intent)
                finish()
            }
        }
        adapterSkhdOnRps.adapterCallback(callbackSkhdOnRps)
            .isVerticalView()
            .addData(skhdOnRpsModel)
            .filterable()
            .setLayout(R.layout.layout_1_text_clickable)
            .build(rv_choose_skhd)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP Disiplin"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSkhdOnRps.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}