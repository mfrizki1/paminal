package id.calocallo.sicape.ui.main.skhd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListHukumanSkhd
import id.calocallo.sicape.model.SkhdModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_skhd.*
import kotlinx.android.synthetic.main.item_1_text.view.*
import kotlinx.android.synthetic.main.item_skhd.*
import kotlinx.android.synthetic.main.item_skhd.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class SkhdActivity : BaseActivity() {
    companion object {
        const val SKHD = "SKDH"
    }

    private lateinit var adapter: ReusableAdapter<SkhdModel>

    private lateinit var adapterSkhd: SkhdAdapter

    //    private lateinit var adapterSkhd: AdapterCallback<SkhdModel>
    private lateinit var adapterhkm: ReusableAdapter<ListHukumanSkhd>
    private lateinit var adapterHkmSkhd: AdapterCallback<ListHukumanSkhd>
    private lateinit var listHukumanSkhd: ArrayList<ListHukumanSkhd>
    private lateinit var listSkhd: ArrayList<SkhdModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skhd)
        setupActionBarWithBackButton(toolbar)
        val jenis = intent.extras?.getString(SKHD)
        supportActionBar?.title = "Surat Keterangan Hasil Disiplin ${jenis}"
//        val rvHukum = findViewById<RecyclerView>(R.id.rv_hukuman_skhd)

        //TODO API JENIS SKHD
        var tempJenis = ""
        if (jenis == "Pidana") {
            tempJenis = "pidana"
            doAPISKHD(tempJenis)
        } else if (jenis == "Disiplin") {
            tempJenis = "disiplin"
            doAPISKHD(tempJenis)

        } else {
            tempJenis = "kode_etik"
            doAPISKHD(tempJenis)
        }

        adapter = ReusableAdapter(this)
        adapterhkm = ReusableAdapter(this)

        listHukumanSkhd = ArrayList()
        listSkhd = ArrayList()

        listHukumanSkhd.add(ListHukumanSkhd("HUKUM 1"))
        listHukumanSkhd.add(ListHukumanSkhd("HUKUM 2"))

        listSkhd.add(
            SkhdModel(
                "NO SKHD 123/xx/2020/BIDPROPAM",
                "LP 123/xx/2020/BIDPROPAM",
                "Faisal",
                "Briptu",
                "987654",
                "Jabatan 1",
                "Polda BJM",
                listHukumanSkhd,
                "RES BJM",
                "INI DETAIL SKHD",
                "12 Agustus 2020",
                "14.30 WITA",
                "13-12-2019",
                "KEPALA BIDANG PROPAM",
                "Faisal Rizki",
                "Briptu",
                "Briptu",
                "7123021742"
            )
        )
        listSkhd.add(
            SkhdModel(
                "NO SKHD 123/xx/2020/BIDPROPAM",
                "LP 123/xx/2020/BIDPROPAM",
                "Faisal",
                "Briptu",
                "987654",
                "Jabatan 1",
                "Polda BJM",
                listHukumanSkhd,
                "RES BJM",
                "INI DETAIL SKHD",
                "12 Agustus 2020",
                "14.30 WITA",
                "13-12-2019",
                "KEPALA BIDANG PROPAM",
                "Faisal Rizki",
                "Briptu",
                "7123021742",
                "7123021742"
            )
        )
        listSkhd.add(
            SkhdModel(
                "NO SKHD 123/xx/2020/BIDPROPAM",
                "LP 123/xx/2020/BIDPROPAM",
                "Faisal",
                "Briptu",
                "987654",
                "Jabatan 1",
                "Polda BJM",
                listHukumanSkhd,
                "RES BJM",
                "INI DETAIL SKHD",
                "12 Agustus 2020",
                "14.30 WITA",
                "13-12-2019",
                "KEPALA BIDANG PROPAM",
                "Faisal Rizki",
                "Briptu",
                "7123021742",
                "7123021742"
            )
        )

        /*
        adapterSkhd = object : AdapterCallback<SkhdModel> {
            override fun initComponent(itemView: View, data: SkhdModel, itemIndex: Int) {
                itemView.txt_no_skhd.text = data.no_skhd
                itemView.txt_nama_skhd.text = data.nama_personel
                itemView.txt_nrp_pangkat_skhd.text =
                    "Pangkat : ${data.pangkat_personel}      NRP: ${data.nrp_personel}"
                itemView.txt_jabatan_skhd.text = data.jabatan
                itemView.txt_kesatuan_skhd.text = data.kesatuan
                itemView.txt_nama_bidang_skhd.text = data.nama_bidang
                itemView.txt_pangkat_nrp_bidang.text =
                    "Pangkat : ${data.pangkat_bidang}      NRP: ${data.nrp_bidang}"

                adapterHkmSkhd = object : AdapterCallback<ListHukumanSkhd> {
                    override fun initComponent(itemView: View, data: ListHukumanSkhd, itemIndex: Int
                    ) {
                        itemView.txt_item_detail_lhp.text = data.hukuman
                    }

                    override fun onItemClicked(itemView: View, data: ListHukumanSkhd, itemIndex: Int
                    ) {

                    }
                }
                adapterhkm.adapterCallback(adapterHkmSkhd)
                    .setLayout(R.layout.item_1_text)
                    .isVerticalView()
                    .addData(listHukumanSkhd)
//                    .build(rvHukum)
//                itemView.rv_hukuman_skhd.adapter =
                rvHukum.adapter = adapterhkm


            }

            override fun onItemClicked(itemView: View, data: SkhdModel, itemIndex: Int) {
                Toast.makeText(this@SkhdActivity, "${data}", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.filterable()
            .adapterCallback(adapterSkhd)
            .setLayout(R.layout.item_skhd)
            .isVerticalView()
            .addData(listSkhd)
            .build(rv_skhd)


         */
        btn_add_skhd.setOnClickListener {
            startActivity(Intent(this, AddSkhdActivity::class.java))
        }

        rv_skhd.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSkhd = SkhdAdapter(this, listSkhd, object : SkhdAdapter.OnClickSkhd{
            override fun onCLick(position: Int) {
                val intent = Intent(this@SkhdActivity, DetailSkhdActivity::class.java)
                intent.putExtra(DetailSkhdActivity.DETAIL_SKHD, listSkhd[position])
                startActivity(intent)
            }
        })
        rv_skhd.adapter = adapterSkhd

    }

    private fun doAPISKHD(tempJenis: String) {
        Log.e("jenis", tempJenis)
        //TODO API SESUAI JENIS
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No SKHD"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}