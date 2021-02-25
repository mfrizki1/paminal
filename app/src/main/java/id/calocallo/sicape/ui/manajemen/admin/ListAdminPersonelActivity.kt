package id.calocallo.sicape.ui.manajemen.admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.UserCreatorResp
import id.calocallo.sicape.network.response.UserResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_admin_personel.*
import kotlinx.android.synthetic.main.item_account.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAdminPersonelActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterAdmin = ReusableAdapter<UserResp>(this)
    private lateinit var callbackAdmin: AdapterCallback<UserResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_admin_personel)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "List Data Admin Personel"
        getListAdmin()
        rv_list_admin_personel
        btn_add_single_admin_personel.setOnClickListener {
            startActivity(Intent(this, AddAdminPersonelActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun getListAdmin() {
        rl_pb.visible()
        NetworkConfig().getServUser().getListAdmin("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<UserResp>> {
                override fun onFailure(call: Call<ArrayList<UserResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_admin_personel.gone()

                }

                override fun onResponse(
                    call: Call<ArrayList<UserResp>>,
                    response: Response<ArrayList<UserResp>>
                ) {
                    rl_pb.gone()
                    if (response.isSuccessful) {
                        listAdmin(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_admin_personel.gone()
                    }
                }
            })
    }

    private fun listAdmin(list: ArrayList<UserResp>?) {
        callbackAdmin = object : AdapterCallback<UserResp> {
            override fun initComponent(itemView: View, data: UserResp, itemIndex: Int) {
                itemView.txt_nama_acc_add.text = "Nama : ${data.nama}"
                itemView.txt_username_acc_add.text = "NRP : ${data.username}"
                /*                     itemView.txt_username_acc_add.text ="NRP : ${data.nr}"
 "Pangkat: ${data.personel?.pangkat.toString().toUpperCase()}"
                 itemView.txt_jabatan_acc_add.text =
                     "Jabatan : ${data.personel?.jabatan}"
                 itemView.txt_kesatuan_acc_add.text =
                     "Kesatuan : ${data.satuan_kerja?.kesatuan.toString().toUpperCase()}"*/
                if (data.is_aktif == 1) {
                    itemView.txt_aktif_acc_add.text = "Aktif"
                } else {
                    itemView.txt_aktif_acc_add.text = "Tidak Aktif"
                }
            }

            override fun onItemClicked(itemView: View, data: UserResp, itemIndex: Int) {
                val intent =
                    Intent(this@ListAdminPersonelActivity, DetailAdminPersonelActivity::class.java)
                intent.putExtra("admin", data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterAdmin.adapterCallback(callbackAdmin)
                .isVerticalView().filterable()
                .addData(it)
                .build(rv_list_admin_personel)
                .setLayout(R.layout.item_account)
        }
    }

    override fun onResume() {
        super.onResume()
        getListAdmin()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Nama Admin"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterAdmin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)

    }
}