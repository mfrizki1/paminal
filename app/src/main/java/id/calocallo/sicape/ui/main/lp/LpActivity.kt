package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp.*
import kotlinx.android.synthetic.main.item_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LpActivity : BaseActivity() {
    private lateinit var list: ArrayList<LpDisiplinModel>
//    private lateinit var adapterLp: LpAdapter
    private lateinit var adapterLpDisiplin: ReusableAdapter<LpDisiplinModel>
    private lateinit var adapterCallbackLpDisiplin: AdapterCallback<LpDisiplinModel>

    companion object {
        const val JENIS = "JENIS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp)
        setupActionBarWithBackButton(toolbar)
        val jenis = intent.extras?.getString(JENIS)
        supportActionBar?.title = "Laporan Polisi $jenis"

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
                "Faisal Rizki", "Briptu", "987123456", "Polda Banjarmasin","POLARI",
                "", "pidana"
            )
        )

        list.add(
            LpDisiplinModel(
                "LP 987/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Ahmad Julian", "Bripda", "12341234", "Polair","POLARI",
                "", "disiplin"
            )
        )
        list.add(
            LpDisiplinModel(
                "LP 987/2020/BIDPROPAM", "Hukuman", "Pasal 12 Ayat 1",
                "Ahmad Julian", "Bripda", "12341234", "Polres BJM","POLARI",
                "", "kode_etik"
            )
        )

        //TODO FILTERING LIST
        val abc = list.filter { it.jenis_pelanggaran == tempJenis }
        Log.e("abc", "${abc.size}")
        Log.e("tempJenis", tempJenis)

        adapterLpDisiplin = ReusableAdapter(this)
        adapterCallbackLpDisiplin = object : AdapterCallback<LpDisiplinModel>{
            override fun initComponent(itemView: View, data: LpDisiplinModel, itemIndex: Int) {
                itemView.txt_no_lp.text = data.no_lp
                itemView.txt_nama_lp.text = data.nama_personel
                itemView.txt_nrp_pangkat_lp.text = "Pangkat: ${data.pangkat_personel}    NRP: ${data.nrp_personel}"
                itemView.txt_hukuman_lp.text = data.hukuman
                itemView.txt_pasal_lp.text= data.pasal
                itemView.txt_kesatuan_lp.text = data.kesatuan
                itemView.txt_jabatan_lp.text = data.jabatan
            }

            override fun onItemClicked(itemView: View, data: LpDisiplinModel, itemIndex: Int) {
                val intent = Intent(this@LpActivity, DetailLpActivity::class.java)
                intent.putExtra(DetailLpActivity.DETAIL_LP, data)
                startActivity(intent)
            }
        }
        adapterLpDisiplin.filterable()
            .adapterCallback(adapterCallbackLpDisiplin)
            .setLayout(R.layout.item_lp)
            .isVerticalView()
            .addData(abc)
            .build(rv_lp)

        /*
        rv_lp.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLp = LpAdapter(this, abc, object : LpAdapter.OnCLickLp {
            override fun onClick(position: Int) {
                val intent = Intent(this@LpActivity, DetailLpActivity::class.java)
                intent.putExtra(DetailLpActivity.DETAIL_LP, list[position])
                startActivity(intent)
            }
        })
        rv_lp.adapter = adapterLp

         */

        btn_add_lp.setOnClickListener {
            startActivity(Intent(this, AddLpActivity::class.java))
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