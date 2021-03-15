package id.calocallo.sicape.ui.manajemen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.UserResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
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

class ManajemenOperatorActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var callbackOperator: AdapterCallback<UserResp>
    private var adapterOperator = ReusableAdapter<UserResp>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manajemen_operator)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Manajemen Operator"

        btn_add_single_operator.setOnClickListener {
            val intent = Intent(this, AddOperatorActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        listOperatorPolisi()

    }
    private fun listOperatorPolisi() {
        rl_pb.visible()
        NetworkConfig().getServUser()
            .getListPersOperator("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<UserResp>> {
                override fun onFailure(call: Call<ArrayList<UserResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_operator.gone()
                    Log.e("t","$t")
                }

                override fun onResponse(
                    call: Call<ArrayList<UserResp>>,
                    response: Response<ArrayList<UserResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        listOperator(response.body())
                       /* callbackOperator = object : AdapterCallback<UserResp> {
                            override fun initComponent(
                                itemView: View, data: UserResp, itemIndex: Int
                            ) {
                                itemView.txt_nama_acc_add.text = "Nama : ${data.nama}"
                                itemView.txt_username_acc_add.text =
                                    "Pangkat: ${data.username}"
                                *//*   itemView.txt_nrp_acc_add.text = "NRP : ${data.personel?.nrp}"
                                   itemView.txt_jabatan_acc_add.text =
                                       "Jabatan : ${data.personel?.jabatan}"
                                   itemView.txt_kesatuan_acc_add.text =
                                       "Kesatuan : ${data.satuan_kerja?.kesatuan.toString()
                                           .toUpperCase()}"*//*
                                if (data.is_aktif == 1) {
                                    itemView.txt_aktif_acc_add.text = "Aktif"
                                } else {
                                    itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                                }
                            }

                            override fun onItemClicked(
                                itemView: View, data: UserResp, itemIndex: Int
                            ) {
                                val status = intent.extras?.getString("manajemen")
                                val intent = Intent(
                                    this@ManajemenOperatorActivity,
                                    DetailOperatorActivity::class.java
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
                            adapterOperator.adapterCallback(callbackOperator)
                                .isVerticalView().filterable()
                                .addData(it)
                                .build(rv_list_operator)
                                .setLayout(R.layout.item_account)
                        }*/
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_operator.gone()
                    }
                }
            })
    }

    private fun listOperator(list: ArrayList<UserResp>?) {
        callbackOperator = object : AdapterCallback<UserResp> {
            override fun initComponent(itemView: View, data: UserResp, itemIndex: Int) {
                itemView.txt_nama_acc_add.text = data.nama
                itemView.txt_username_acc_add.text = data.username
                if (data.is_aktif == 0) {
                    itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                } else {
                    itemView.txt_aktif_acc_add.text = "Aktif"
                }
            }

            override fun onItemClicked(itemView: View, data: UserResp, itemIndex: Int) {
                val intent = Intent(
                    this@ManajemenOperatorActivity,
                    DetailOperatorActivity::class.java
                )
                intent.putExtra("acc", data)
                startActivity(intent)
                overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left
                )
            }
        }
        list?.let {
            adapterOperator.adapterCallback(callbackOperator)
                .isVerticalView()
                .filterable()
                .addData(it)
                .setLayout(R.layout.item_account)
                .build(rv_list_operator)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Operator"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterOperator.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        listOperatorPolisi()
    }
}