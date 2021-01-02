package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp.*
import kotlinx.android.synthetic.main.item_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LpActivity : BaseActivity() {
    private lateinit var list: ArrayList<LpDisiplinModel>
    private var listLp: MutableList<LpResp> = mutableListOf()

    //    private lateinit var adapterLp: LpAdapter
    private lateinit var adapterLpDisiplin: ReusableAdapter<LpDisiplinModel>
    private lateinit var adapterCallbackLpDisiplin: AdapterCallback<LpDisiplinModel>
    private lateinit var adapterLp: LpAdapter

    //    private lateinit var adapterLp: ReusableAdapter<LpResp>
    private lateinit var callbackLp: AdapterCallback<LpResp>
    private lateinit var sessionManager: SessionManager

    companion object {
        const val JENIS = "JENIS"
        const val TYPEVIEW = "TYPEVIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_lp)
         setupActionBarWithBackButton(toolbar)
        val jenis = intent.extras?.getString(JENIS)
        supportActionBar?.title = "Laporan Polisi $jenis"
        sessionManager = SessionManager(this)
//        adapterLp = ReusableAdapter(this)
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_add_lp.gone()
        }

        //TODO API JENIS
        var tempJenis = ""
        if (jenis == "Pidana") {
            tempJenis = "pidana"
            doAPI(tempJenis)
        } else if (jenis == "Kode Etik") {
            tempJenis = "kode_etik"
            doAPI(tempJenis)
        } else {
            tempJenis = "disiplin"
            doAPI(tempJenis)
        }

        //TODO STATIC LIST
        list = ArrayList()
        list.add(
            LpDisiplinModel(
                "LP 123/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Faisal Rizki", "Briptu", "987123456", "Polda Banjarmasin", "POLARI",
                "", "pidana"
            )
        )
        list.add(
            LpDisiplinModel(
                "LP 123/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Faisal Rizki", "Briptu", "987123456", "Polda Banjarmasin", "POLARI",
                "", "pidana"
            )
        )

        list.add(
            LpDisiplinModel(
                "LP 987/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Ahmad Julian", "Bripda", "12341234", "Polair", "POLARI",
                "", "disiplin"
            )
        )
        list.add(
            LpDisiplinModel(
                "LP 987/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Ahmad Julian", "Bripda", "12341234", "Polres BJM", "POLARI",
                "", "kode_etik"
            )
        )
        listLp.add(
            LpResp(
                1, "pidana", "LP/PIDANA/2020/BIDPROPAM", 4,
                4, 1, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""
            )
        )
        listLp.add(
            LpResp(
                2, "pidana", "LP/PIDANA2/2020/BIDPROPAM", 4,
                5, 2, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""

            )
        )
        listLp.add(
            LpResp(
                3, "kode_etik", "LP/KODE_ETIK/2020/BIDPROPAM", 4,
                5, 2, sessionManager.getAlatBukiLP(), sessionManager.getListPasalLP(),
                sessionManager.getListSaksi(), "KET", "", "", ""

            )
        )

        //TODO FILTERING LIST
//        val abc = list.filter { it.jenis_pelanggaran == tempJenis }
        val abc = listLp.filter { it.jenis == tempJenis }
        Log.e("abc", "${abc.size}")
        Log.e("tempJenis", tempJenis)

        callbackLp = object : AdapterCallback<LpResp> {
            override fun initComponent(itemView: View, data: LpResp, itemIndex: Int) {
                itemView.txt_no_lp.text = data.no_lp
                itemView.txt_jabatan_lp_dilapor.text = "JABATAN 1"
                itemView.txt_nama_lp_dilapor.text = "NAMA"
                itemView.txt_nrp_pangkat_lp_dilapor.text = "PANGKAT BRIPTU, NRP: 90909090"
                itemView.txt_kesatuan_lp_dilapor.text = "POLRESTA BJM"

                itemView.txt_jabatan_lp_terlapor.text = "JABATAN 1"
                itemView.txt_nama_lp_terlapor.text = "NAMA"
                itemView.txt_nrp_pangkat_lp_terlapor.text = "PANGKAT BRIPTU, NRP: 90909090"
                itemView.txt_kesatuan_lp_terlapor.text = "POLRESTA BJM"

            }

            override fun onItemClicked(itemView: View, data: LpResp, itemIndex: Int) {
                val intent = Intent(this@LpActivity, DetailLpActivity::class.java)
                intent.putExtra(DetailLpActivity.DETAIL_LP, data)
                startActivity(intent)
            }

        }

        rv_lp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLp = LpAdapter(this, abc, object : LpAdapter.OnCLickLp {
            override fun onClick(position: Int) {
                val intent = Intent(this@LpActivity, DetailLpActivity::class.java)
                intent.putExtra(DetailLpActivity.DETAIL_LP, abc[position])
                startActivity(intent)
            }
        })
        rv_lp.adapter = adapterLp


        btn_add_lp.setOnClickListener {
            val intent = Intent(this, AddLpActivity::class.java)
            intent.putExtra("JENIS_LP", tempJenis)
            startActivity(intent)
        }
    }

    private fun doAPI(tempJenis: String) {
        Log.e("jenis", tempJenis)
        //TODO API SESUAI JENIS
//        NetworkConfig().getService().

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No LP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpDisiplin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}