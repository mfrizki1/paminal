package id.calocallo.sicape.ui.manajemen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.AdminResp
import id.calocallo.sicape.network.response.HakAksesSipil
import id.calocallo.sicape.network.response.OperatorResp
import id.calocallo.sicape.network.response.UserCreatorResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
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
    private lateinit var callbackOperator: AdapterCallback<UserCreatorResp>
    private var adapterOperator = ReusableAdapter<UserCreatorResp>(this)

    private var adapterSipil = ReusableAdapter<HakAksesSipil>(this)
    private lateinit var callbackSipil: AdapterCallback<HakAksesSipil>

    private lateinit var callbackAdmin: AdapterCallback<AdminResp>
    private var adapterAdmin = ReusableAdapter<AdminResp>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manajemen_operator)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        val status = intent.extras?.getString("manajemen")
        Log.e("stts", "$status")
        when (status) {
            "admin" -> {
                listAdmin()
                supportActionBar?.title = "Manajemen Admin"
                btn_add_single_operator.setOnClickListener {
                    val intent = Intent(this, AddOperatorActivity::class.java)
                    intent.putExtra("manajemen", status)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }
            else->{
                supportActionBar?.title = "Manajemen Operator"
                btn_add_single_operator.setOnClickListener {
                    val intent = Intent(this, KatOperatorActivity::class.java)
                    intent.putExtra("manajemen", status)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
                val operator = intent.extras?.getString("list")

                if (operator == "polisi") {
                    listOperatorPolisi()
                } else {
                    listOperatorSipil()
                }
            }
        }



    }

    private fun listOperatorSipil() {
        rl_pb.visible()
        NetworkConfig().getService().getListSipilOperator("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<HakAksesSipil>> {
                override fun onFailure(call: Call<ArrayList<HakAksesSipil>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_operator.gone()
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
                                itemView.txt_nama_acc_add.text = "Nama : ${data.operator_sipil?.nama}"
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
                                    this@ManajemenOperatorActivity,
                                    EditOperatorActivity::class.java
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
                                .build(rv_list_operator)
                                .setLayout(R.layout.item_account)
                        }
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_operator.gone()
                    }
                }
            })

    }

    private fun listOperatorPolisi() {
        rl_pb.visible()
        NetworkConfig().getService().getListPersOperator("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<UserCreatorResp>> {
                override fun onFailure(call: Call<ArrayList<UserCreatorResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_operator.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<UserCreatorResp>>,
                    response: Response<ArrayList<UserCreatorResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        callbackOperator = object : AdapterCallback<UserCreatorResp> {
                            @SuppressLint("SetTextI18n")
                            override fun initComponent(
                                itemView: View,
                                data: UserCreatorResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_nama_acc_add.text = "Nama : ${data.personel?.nama}"
                                itemView.txt_pangkat_acc_add.text =
                                    "Pangkat: ${data.personel?.pangkat.toString().toUpperCase()}"
                                itemView.txt_nrp_acc_add.text = "NRP : ${data.personel?.nrp}"
                                itemView.txt_jabatan_acc_add.text =
                                    "Jabatan : ${data.personel?.jabatan}"
                                itemView.txt_kesatuan_acc_add.text =
                                    "Kesatuan : ${data.satuan_kerja?.kesatuan.toString()
                                        .toUpperCase()}"
                                if (data.is_aktif == 1) {
                                    itemView.txt_aktif_acc_add.text = "Aktif"
                                } else {
                                    itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                                }
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: UserCreatorResp,
                                itemIndex: Int
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
                        }
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_operator.gone()
                    }
                }
            })
    }

    private fun listAdmin() {
        rl_pb.visible()
        NetworkDummy().getService().getAdmin().enqueue(object : Callback<ArrayList<AdminResp>> {
            override fun onFailure(call: Call<ArrayList<AdminResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_operator.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<AdminResp>>,
                response: Response<ArrayList<AdminResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackAdmin = object : AdapterCallback<AdminResp> {
                        @SuppressLint("SetTextI18n")
                        override fun initComponent(
                            itemView: View,
                            data: AdminResp,
                            itemIndex: Int
                        ) {
                            itemView.txt_nama_acc_add.text = "Nama : ${data.personel?.nama}"
                            itemView.txt_pangkat_acc_add.text =
                                "Pangkat: ${data.personel?.pangkat.toString().toUpperCase()}"
                            itemView.txt_nrp_acc_add.text = "NRP : ${data.personel?.nrp}"
                            itemView.txt_jabatan_acc_add.text =
                                "Jabatan : ${data.personel?.jabatan}"
                            itemView.txt_kesatuan_acc_add.text =
                                "Kesatuan : ${data.personel?.kesatuan.toString().toUpperCase()}"
                            if (data.is_aktif == 1) {
                                itemView.txt_aktif_acc_add.text = "Aktif"
                            } else {
                                itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                            }
                        }

                        override fun onItemClicked(
                            itemView: View,
                            data: AdminResp,
                            itemIndex: Int
                        ) {
                            val status = intent.extras?.getString("manajemen")
                            val intent = Intent(
                                this@ManajemenOperatorActivity,
                                EditOperatorActivity::class.java
                            )
                            intent.putExtra("manajemen", status)
                            intent.putExtra("acc", data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapterAdmin.adapterCallback(callbackAdmin)
                            .isVerticalView().filterable()
                            .addData(it)
                            .build(rv_list_operator)
                            .setLayout(R.layout.item_account)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_operator.gone()
                }
            }
        })
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