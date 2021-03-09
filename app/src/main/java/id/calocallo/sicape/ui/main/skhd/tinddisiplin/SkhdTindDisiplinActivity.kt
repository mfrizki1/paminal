package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.TindDisplMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_skhd_tind_disiplin.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class SkhdTindDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var listTindDisiplin = arrayListOf<TindDisplMinResp>()
    private var personelTerlapor = PersonelLapor()
    private var adapterTindDisiplin = ReusableAdapter<TindDisplMinResp>(this)
    private lateinit var callbackTindDisplMin: AdapterCallback<TindDisplMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skhd_tind_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Tindakan Disiplin"
        sessionManager1 = SessionManager1(this)

        getListTindDisiplin()
        btn_add_single_tind_disiplin.setOnClickListener {
            startActivity(Intent(this, AddTindDisiplinSkhdActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getListTindDisiplin() {
        /*personelTerlapor =
            PersonelLapor(
                1,
                "Gusti",
                "bripda",
                "jabtan 1",
                "123456",
                1,
                "Polda Kalimantasn Selatan",
                "Jl Banjarmasin",
                "islam",
                "laki_laki",
                "Batola",
                "12-01-2000", "081212"
            )

        listTindDisiplin.add(
            TindDisplMinResp(1, personelTerlapor, "Lari", null, null)
        )
        listTindDisiplin.add(
            TindDisplMinResp(
                2,
                PersonelLapor(
                    2,
                    "Wahyu",
                    "bripda",
                    "jabtan 1",
                    "123456",
                    2,
                    "Polda Kalimantasn Selatan", "Jl Banjarmasin",
                    "islam",
                    "laki_laki",
                    "Batola", "12-01-2000", "081212"
                ),
                "Lari",
                null,
                null
            )
        )*/
        callbackTindDisplMin = object : AdapterCallback<TindDisplMinResp> {
            override fun initComponent(itemView: View, data: TindDisplMinResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.id_personel
//                itemView.txt_detail_2.text = data.isi_tindakan_disiplin
            }

            override fun onItemClicked(itemView: View, data: TindDisplMinResp, itemIndex: Int) {
                val intent =
                    Intent(this@SkhdTindDisiplinActivity, DetailTindDisiplinActivity::class.java)
                intent.putExtra(EDIT_TIND_DISIPLIN, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        adapterTindDisiplin.adapterCallback(callbackTindDisplMin)
            .isVerticalView()
            .addData(listTindDisiplin)
            .setLayout(R.layout.item_2_text)
            .build(rv_list_tind_disipin)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No Nama"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterTindDisiplin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val EDIT_TIND_DISIPLIN = "EDIT_TIND_DISIPLIN"
    }
}