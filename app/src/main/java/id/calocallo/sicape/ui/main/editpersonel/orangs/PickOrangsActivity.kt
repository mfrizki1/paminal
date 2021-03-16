package id.calocallo.sicape.ui.main.editpersonel.orangs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.OrangsResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_orangs.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickOrangsActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var orangsAdapter: ReusableAdapter<OrangsResp>
    private lateinit var orangsCallback: AdapterCallback<OrangsResp>
    private var menu: String? = null
    private var namaPersonel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_orangs)

        sessionManager1 = SessionManager1(this)
        orangsAdapter = ReusableAdapter(this)

        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        namaPersonel = personel?.nama.toString()
        spinner_orangs.setText("Orang Berjasa Selain Orang Tua")
        menu = "berjasa"
        initSp()

        getOrangs(menu)

        btn_add_single_orangs.setOnClickListener {
            val intent = Intent(this@PickOrangsActivity, AddSingleOrangsActivity::class.java)
            intent.putExtra("ORANGS", menu)
            intent.putExtra("PERSONEL", personel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initSp() {
        val item = listOf("Orang Berjasa Selain Orang Tua", "Orang Disegani Karena Adat")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_orangs.setAdapter(adapter)
        spinner_orangs.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    menu = "berjasa"
                    getOrangs(menu)
                }
                1 -> {
                    menu = "disegani"
                    getOrangs(menu)
                }
            }
        }
    }

    private fun getOrangs(menu: String?) {
        rl_pb.visible()
        rv_orangs.gone()
        NetworkConfig().getServPers().showOrangs(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString(),
            menu.toString()
        ).enqueue(object : Callback<ArrayList<OrangsResp>> {
            override fun onFailure(call: Call<ArrayList<OrangsResp>>, t: Throwable) {
                Toast.makeText(this@PickOrangsActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<OrangsResp>>,
                response: Response<ArrayList<OrangsResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_orangs.gone()
                    } else {
                        rl_no_data.gone()
                        rv_orangs.visible()
                        OrangsRV(response.body(), menu)

                    }
                } else {
                    rl_no_data.visible()
                    Toast.makeText(this@PickOrangsActivity, "Error", Toast.LENGTH_SHORT).show()


                }
            }
        })
    }

    private fun OrangsRV(list: ArrayList<OrangsResp>?, menu: String?) {
        orangsCallback = object : AdapterCallback<OrangsResp> {
            override fun initComponent(itemView: View, data: OrangsResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: OrangsResp, itemIndex: Int) {
                val intent = Intent(this@PickOrangsActivity, EditOrangsActivity::class.java)
                intent.putExtra("NAMA_PERSONEL", namaPersonel)
                intent.putExtra("ORANGS", data)
                intent.putExtra("MENU", menu)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        orangsAdapter.adapterCallback(orangsCallback)
            .isVerticalView()
            .addData(list!!)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_orangs)
    }

    override fun onResume() {
        super.onResume()
        initSp()
        getOrangs(menu)
    }
}