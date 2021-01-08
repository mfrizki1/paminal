package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.TindDisiplinResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_skhd_tind_disiplin.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class SkhdTindDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var listTindDisiplin = arrayListOf<TindDisiplinResp>()
    private var personelTerlapor = PersonelLapor()
    private var adapterTindDisiplin = ReusableAdapter<TindDisiplinResp>(this)
    private lateinit var callbackTindDisiplin: AdapterCallback<TindDisiplinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skhd_tind_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Tindakan Disiplin"
        sessionManager = SessionManager(this)

        getListTindDisiplin()
        btn_add_single_tind_disiplin.setOnClickListener {
            startActivity(Intent(this, AddTindDisiplinSkhdActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getListTindDisiplin() {
        personelTerlapor =
            PersonelLapor(1, "Gusti", "bripda", "jabtan 1", "123456", "Polda Kalimantasn Selatan")

        listTindDisiplin.add(
            TindDisiplinResp(1, personelTerlapor, "Lari", null, null)
        )
        listTindDisiplin.add(
            TindDisiplinResp(
                2,
                PersonelLapor(
                    2,
                    "Wahyu",
                    "bripda",
                    "jabtan 1",
                    "123456",
                    "Polda Kalimantasn Selatan"
                ),
                "Lari",
                null,
                null
            )
        )
        callbackTindDisiplin = object : AdapterCallback<TindDisiplinResp> {
            override fun initComponent(itemView: View, data: TindDisiplinResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.personel?.nama
                itemView.txt_detail_2.text = data.isi_tindakan_disiplin
            }

            override fun onItemClicked(itemView: View, data: TindDisiplinResp, itemIndex: Int) {
                val intent = Intent(this@SkhdTindDisiplinActivity, DetailTindDisiplinActivity::class.java)
                intent.putExtra(EDIT_TIND_DISIPLIN, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        adapterTindDisiplin.adapterCallback(callbackTindDisiplin)
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
    companion object{
        const val EDIT_TIND_DISIPLIN = "EDIT_TIND_DISIPLIN"
    }
}