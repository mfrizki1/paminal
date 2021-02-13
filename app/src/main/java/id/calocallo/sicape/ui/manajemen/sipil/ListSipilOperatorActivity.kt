package id.calocallo.sicape.ui.manajemen.sipil

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.HakAksesSipil
import id.calocallo.sicape.ui.manajemen.EditOperatorActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_sipil_operator.*
import kotlinx.android.synthetic.main.activity_manajemen_operator.*
import kotlinx.android.synthetic.main.item_account.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSipilOperatorActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterSipil = ReusableAdapter<HakAksesSipil>(this)
    private lateinit var callbackSipil: AdapterCallback<HakAksesSipil>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sipil_operator)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Operator Sipil"
        sessionManager1 = SessionManager1(this)
        listOperatorSipil()


        btn_add_single_sipil_operator.setOnClickListener {
            startActivity(Intent(this, AddSipilOperatorActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    override fun onResume() {
        super.onResume()
        listOperatorSipil()

    }

    private fun listOperatorSipil() {
        rl_pb.visible()
        NetworkConfig().getService()
            .getListSipilOperator("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<HakAksesSipil>> {
                override fun onFailure(call: Call<ArrayList<HakAksesSipil>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_sipil_operator.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<HakAksesSipil>>,
                    response: Response<ArrayList<HakAksesSipil>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        callbackSipil = object : AdapterCallback<HakAksesSipil> {
                            @SuppressLint("SetTextI18n")
                            override fun initComponent(
                                itemView: View,
                                data: HakAksesSipil,
                                itemIndex: Int
                            ) {
                                itemView.txt_judul_acc.text = "Sipil"
                                itemView.txt_nama_acc_add.text =
                                    "Nama : ${data.operator_sipil?.nama}"
                                itemView.txt_pangkat_acc_add.gone()
                                itemView.txt_nrp_acc_add.gone()
                                itemView.txt_jabatan_acc_add.gone()
                                itemView.txt_kesatuan_acc_add.gone()
                                if (data.is_aktif == 1) {
                                    itemView.txt_aktif_acc_add.text = "Aktif"
                                } else {
                                    itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                                }
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: HakAksesSipil,
                                itemIndex: Int
                            ) {
                                val status = intent.extras?.getString("manajemen")
                                val intent = Intent(
                                    this@ListSipilOperatorActivity,
                                    DetailSipilOperatorActivity::class.java
                                )
                                intent.putExtra("manajemen", status)
                                intent.putExtra("acc", data)
                                startActivity(intent)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                            }
                        }
                        response.body()?.let {
                            adapterSipil.adapterCallback(callbackSipil)
                                .isVerticalView().filterable()
                                .addData(it)
                                .build(rv_list_sipil_operator)
                                .setLayout(R.layout.item_account)
                        }
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_sipil_operator.gone()
                    }
                }
            })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Nama"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSipil.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}