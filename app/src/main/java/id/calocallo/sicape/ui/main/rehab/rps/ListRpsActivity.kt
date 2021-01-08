package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.response.RpsResp
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_rps.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListRpsActivity : BaseActivity() {
    private var listRps = arrayListOf<RpsResp>()
    private var adapterRps = ReusableAdapter<RpsResp>(this)
    private lateinit var callbackRps: AdapterCallback<RpsResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data RPS"

        btn_add_single_rps.setOnClickListener {
            startActivity(Intent(this, AddRpsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListRps()
    }

    private fun getListRps() {
        listRps.add(
            RpsResp(
                1,
                SkhdOnRpsModel(1, "SKHD/1/2020/BIDDOKES"),
                "RPS1/2020/xxx",
                "dasarPe",
                resources.getString(R.string.paragraf),
                "01-01-2000",
                "Banjarmasin",
                "Jabatan1",
                "Rizki",
                "bripda",
                "123123",
                "tembuasan\nsadnsadsadd\nasdisdas",
                null,
                null
            )
        )

        listRps.add(
            RpsResp(
                2,
                SkhdOnRpsModel(2, "SKHD/2/2020/BIDDOKES"),
                "RPS2/2020/xxx",
                "dasarPe2",
                resources.getString(R.string.paragraf),
                "01-01-2020",
                "Banjarmasin",
                "Jabatan1",
                "Rizki",
                "bripda",
                "123123",
                "tembuasan\nsadnsadsadd\nasdisdas",
                null,
                null
            )
        )
        callbackRps = object : AdapterCallback<RpsResp> {
            override fun initComponent(itemView: View, data: RpsResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_rps
            }

            override fun onItemClicked(itemView: View, data: RpsResp, itemIndex: Int) {
                val intent = Intent(this@ListRpsActivity, DetailRpsActivity::class.java)
                intent.putExtra(DetailRpsActivity.DETAIL_RPS, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        adapterRps.adapterCallback(callbackRps)
            .isVerticalView().filterable()
            .addData(listRps)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_list_rps)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari RPS"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterRps.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
