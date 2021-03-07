package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_skhd.*
import kotlinx.android.synthetic.main.activity_lp_choose.*
import kotlinx.android.synthetic.main.activity_lp_choose.rv_list_lp_choose
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListLpSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    private var listPidanaLP = ArrayList<LpPidanaResp>()
    private var listDisiplinLP = ArrayList<LpDisiplinResp>()
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var listPasal = arrayListOf<PasalDilanggarResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    private var adapterLpChoose = ReusableAdapter<LpMinResp>(this)
    private lateinit var callbackLpChoose: AdapterCallback<LpMinResp>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_skhd)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Data Laporan Polisi"
        /*set idLHP for getlist lp*/
//        val idLhp = intent.extras?.getInt(ChooseLpSkhdActivity.ID_LHP_FOR_LP)
//        Log.e("ListLpSkhd", "$idLhp")

        val dataLhp = intent.getParcelableExtra<LhpMinResp>(ChooseLhpActivity.DATA_LHP)
        Log.e(TAG, "$dataLhp")

        apiListLpByLhp(dataLhp)

        /* val jenisLPFromSkhd = intent.extras?.getString(ChooseLpSkhdActivity.CHOOSE_LP_SKHD)
        when (jenisLPFromSkhd) {
            "pidana" -> {
//                getPidana(idLhp)
            }
            "disiplin" -> {
                supportActionBar?.title = "Data Laporan Polisi Disiplin"
//                getDisiplin(idLhp)
            }
        }

        satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "")
        personelTerLapor =
            PersonelLapor(
                1, "faisal", "bripda", "jabatan", "1234", 1, "polda kalsel", "Jl Banjarmasin",
                "islam",
                "laki_laki",
                "Batola", "12-01-2000", "081212"
            )
        personelPeLapor =
            PersonelLapor(
                2, "utuh", "ipda", "jabatan", "0987", 2, "polresta banjarmasin", "Jl Banjarmasin",
                "islam",
                "laki_laki",
                "Batola", "12-01-2000", "081212"
            )
               listPasal.add(PasalDilanggarResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
               listPasal.add(PasalDilanggarResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
               listPasal.add(PasalDilanggarResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
               listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "", "", "", 1, "", "", ""))
               listSaksi.add(LpSaksiResp(2, "Akbar", "saksi", "", "", "", 0, "", "", ""))
               listSaksi.add(LpSaksiResp(3, "Wahyu", "saksi", "", "", "", 0, "", "", ""))

    }

    private fun getPidana(idLhp: Int?) {
        listPidanaLP.add(
            LpPidanaResp(
                1,
                "LP/PIDANA/2020/BIDPROPAM",
                satKerResp,
                personelTerLapor,
                "Uraian Pelanggaran",
                "Banjarmasin",
                "12-12-2000",
                "Rojak Ahmad",
                "Kombes",
                "12345678",
                "Polair",
                "polda Kalsel",
                "polisi",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                personelPeLapor,
                resources.getString(R.string.paragraf),
                resources.getString(R.string.paragraf),
                listPasal,
                "",
                "",
                ""
            )
        )

        listPidanaLP.add(
            LpPidanaResp(
                2,
                "LP/PIDANA2/2020/BIDPROPAM",
                satKerResp,
                personelTerLapor,
                "Uraian Pelanggaran",
                "Banjarmasin",
                "12-12-2000",
                "Rojak Ahmad",
                "Kombes",
                "12345678",
                "Polair",
                "polda Kalsel",
                "sipil",
                "sipil",
                "islam",
                "pekerjaan",
                "Indonesia",
                "jl xxx",
                "081212",
                "123456",
                null,
                resources.getString(R.string.paragraf),
                resources.getString(R.string.paragraf),
                listPasal,
                "",
                "",
                ""
            )
        )
        callbackLpPidanaChoose = object : AdapterCallback<LpPidanaResp> {
            override fun initComponent(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra(PIDANA, data)
                setResult(999, intent)
                finish()
            }
        }
        adapterLpPidanaChoose.adapterCallback(callbackLpPidanaChoose)
            .isVerticalView().addData(listPidanaLP)
            .setLayout(R.layout.layout_1_text_clickable).build(rv_choose_lp_skhd)
    }

     private fun getDisiplin(idLhp: Int?) {
         listDisiplinLP.add(
             LpDisiplinResp(
                 1, "LP/DISIPLIN1",
                 "disiplin", personelTerLapor, personelPeLapor,
                 "Banjarmasin", "12-01-20", "Budi",
                 "IPDA", "87654321", "KOMBES", "polda kALSEEL",
                 "macam_pelanggaran", "keterangan terlapor",
                 "kronologis", "rincian", listPasal, satKerResp, "", ""
             )
         )
         callbackLpDisiplinChoose = object : AdapterCallback<LpDisiplinResp> {
             override fun initComponent(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                 itemView.txt_1_clickable.text = data.no_lp

             }

             override fun onItemClicked(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                 itemView.img_clickable.toggleVisibility()
                 val intent = Intent()
                 intent.putExtra(DISIPLIN, data)
                 setResult(888, intent)
                 finish()
             }
         }
         adapterLpDisiplinChoose.adapterCallback(callbackLpDisiplinChoose)
             .isVerticalView().addData(listDisiplinLP)
             .setLayout(R.layout.layout_1_text_clickable).build(rv_choose_lp_skhd)
     }*/


    }

    private fun apiListLpByLhp(dataLhp: LhpMinResp?) {
        rl_pb.visible()
        NetworkConfig().getServLp()
            .getLpByJenis("Bearer ${sessionManager1.fetchAuthToken()}", "pidana").enqueue(
                object : Callback<ArrayList<LpMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<LpMinResp>>,
                        response: Response<ArrayList<LpMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListLpByLhp(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_choose_lp_skhd.gone()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LpMinResp>>, t: Throwable) {
                        Toast.makeText(this@ListLpSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_choose_lp_skhd.gone()
                    }
                })

//        getListLpByLhp(response.body())
    }

    private fun getListLpByLhp(list: ArrayList<LpMinResp>?) {
        callbackLpChoose = object : AdapterCallback<LpMinResp> {
            override fun initComponent(itemView: View, data: LpMinResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpMinResp, itemIndex: Int) {
                /**
                 * get ID And go to previous activity
                 */
                itemView.img_clickable.visible()
                val intent = Intent().apply {
                    this.putExtra(DATA_LP, data)
                }
                setResult(RESULT_LP, intent)
                finish()
            }

        }
        list?.let {
            adapterLpChoose.adapterCallback(callbackLpChoose)
                .isVerticalView()
                .filterable().addData(it).setLayout(R.layout.layout_1_text_clickable)
                .build(rv_choose_lp_skhd)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Lp"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpChoose.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val PIDANA = "PIDANA"
        const val DISIPLIN = "DISIPLIN"
        const val TAG = "--ListLpSkhdActivity"
        const val RESULT_LP = 23
        const val DATA_LP = "DATA_LP"

    }

}