package id.calocallo.sicape.ui.main.putkke

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.network.response.PutKkeResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_put_kke.*
import kotlinx.android.synthetic.main.item_skhd.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListPutKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var listPutKke = arrayListOf<PutKkeResp>()
    private var adapterPutKke = ReusableAdapter<PutKkeResp>(this)
    private lateinit var callbackPutKke: AdapterCallback<PutKkeResp>
    private var listLhpOnSkhd = LhpOnSkhd()
    private var listLpOnSkhd = LpOnSkhd()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Putusan Kode Etik"

        btn_add_single_put_kke.setOnClickListener {
            startActivity(Intent(this, AddPutKkeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
        getListPutKke()
    }

    private fun getListPutKke() {
        listLhpOnSkhd = LhpOnSkhd(1, "LHP/1/2020/BIDPROPAM")
        listLpOnSkhd = LpOnSkhd(1, "LP/1/2020/BIDPROPAM", "kode_etik")
        listPutKke.add(
            PutKkeResp(
                1,listLhpOnSkhd, listLpOnSkhd,
                "PUTKKE/1/2020/BIDPROPAM", resources.getString(R.string.paragraf),"Berkas Pemeriksaan",
                "Keputusan Kapolda","surat persangkaan pelanggaran","pembacaan atas persangkaan",
                "kemudian akreditor selaku penuntut","rekomendasi sanksi\nsanksi1\nsanksi2",
                "Terbukti secara sah dan meyakinkan","hasil sanksi\nsanksi1",
                "Galuh","BRIPDA","0987","Wakil Utuh","KOMBES","9876",
                "Anggota Dulak","ipda","8765","Banjarmasin","12-12-2000",null,null
            )
        )

        listPutKke.add(
            PutKkeResp(
                2,listLhpOnSkhd, listLpOnSkhd,
                "PUTKKE/2/2020/BIDPROPAM", resources.getString(R.string.paragraf),"Berkas Pemeriksaan",
                "Keputusan Kapolda","surat persangkaan pelanggaran","pembacaan atas persangkaan",
                "kemudian akreditor selaku penuntut","rekomendasi sanksi\nsanksi1\nsanksi2",
                "Terbukti secara sah dan meyakinkan","hasil sanksi\nsanksi1",
                "Galuh","BRIPDA","0987","Wakil Utuh","KOMBES","9876",
                "Anggota Dulak","ipda","8765","Banjarmasin","12-12-2000",null,null
            )
        )

        listPutKke.add(
            PutKkeResp(
                2,listLhpOnSkhd, listLpOnSkhd,
                "PUTKKE/3/2020/BIDPROPAM", resources.getString(R.string.paragraf),"Berkas Pemeriksaan",
                "Keputusan Kapolda","surat persangkaan pelanggaran","pembacaan atas persangkaan",
                "kemudian akreditor selaku penuntut","rekomendasi sanksi\nsanksi1\nsanksi2",
                "Terbukti secara sah dan meyakinkan","hasil sanksi\nsanksi1",
                "Galuh","BRIPDA","0987","Wakil Utuh","KOMBES","9876",
                "Anggota Dulak","ipda","8765","Banjarmasin","12-12-2000",null,null
            )
        )
        callbackPutKke = object :AdapterCallback<PutKkeResp>{
            override fun initComponent(itemView: View, data: PutKkeResp, itemIndex: Int) {
                itemView.txt_no_skhd.text = data.no_putkke
                itemView.txt_no_lhp_skhd.text = data.lhp?.no_lhp
                itemView.txt_no_lp_skhd.text = data.lp?.no_lp
            }

            override fun onItemClicked(itemView: View, data: PutKkeResp, itemIndex: Int) {
                val intent =Intent(this@ListPutKkeActivity, DetailPutKkeActivity::class.java)
                intent.putExtra(DETAIL_PUTKKE, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        adapterPutKke.adapterCallback(callbackPutKke)
            .isVerticalView().addData(listPutKke)
            .setLayout(R.layout.item_skhd)
            .build(rv_put_kke)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No PUT KKE"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPutKke.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object{
        const val DETAIL_PUTKKE ="DETAIL_PUTKKE"
    }
}