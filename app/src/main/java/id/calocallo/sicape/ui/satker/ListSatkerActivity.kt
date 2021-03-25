package id.calocallo.sicape.ui.satker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_list_satker.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSatkerActivity : AppCompatActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterSatker = ReusableAdapter<SatKerResp>(this)
    private lateinit var callbackSatker: AdapterCallback<SatKerResp>
    private var jenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_satker)
        sessionManager1 = SessionManager1(this)

        jenis = intent.getStringExtra(AddSatkerActivity.JENIS_SATKERN)

        btn_add_single_satker.setOnClickListener {
            startActivity(Intent(this, AddSatkerActivity::class.java).apply {
                this.putExtra(AddSatkerActivity.JENIS_SATKERN, jenis)
            })
        }

        apiListSatker()
    }

    private fun apiListSatker() {
        rl_pb.visible()
        jenis?.let {
            NetworkConfig().getSatker().listSatker(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object : Callback<ArrayList<SatKerResp>> {
                override fun onResponse(
                    call: Call<ArrayList<SatKerResp>>, response: Response<ArrayList<SatKerResp>>
                ) {
                    if (response.body() != null) {
                        rl_pb.gone()
                        listSatker(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_satker.gone()
                    }
                }

                override fun onFailure(call: Call<ArrayList<SatKerResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_satker.gone()
                }
            })
        }
    }


    private fun listSatker(list: java.util.ArrayList<SatKerResp>?) {
        callbackSatker =
            object : AdapterCallback<SatKerResp> {
                override fun initComponent(itemView: View, data: SatKerResp, itemIndex: Int) {
                    itemView.txt_edit_pendidikan.text = data.kesatuan
                }

                override fun onItemClicked(itemView: View, data: SatKerResp, itemIndex: Int) {
                    val intent =
                        Intent(this@ListSatkerActivity, EditSatkerActivity::class.java).apply {
                            this.putExtra(AddSatkerActivity.JENIS_SATKERN, jenis)
                            this.putExtra(DATA_SATKER, data)
                        }
                    startActivity(intent)
                }
            }

        list?.let {
            adapterSatker.adapterCallback(callbackSatker)
                .isVerticalView().filterable()
                .setLayout(R.layout.layout_edit_1_text)
                .addData(it)
                .build(rv_list_satker)
        }

    }


    companion object {
        const val DATA_SATKER = "DATA_SATKER"
        const val token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImNlOWU0MmJiNGU0MTAxMjVmZWQ4ZDgzNWI4M2RlZDZjMGFlMGUyZjJkMjBmMWNjOGQyMmIzYWI1ZDZmMWE4ZTY3OTc0MmY3OWE3NTllMGZmIn0.eyJhdWQiOiIxIiwianRpIjoiY2U5ZTQyYmI0ZTQxMDEyNWZlZDhkODM1YjgzZGVkNmMwYWUwZTJmMmQyMGYxY2M4ZDIyYjNhYjVkNmYxYThlNjc5NzQyZjc5YTc1OWUwZmYiLCJpYXQiOjE2MTY2MzYzNDQsIm5iZiI6MTYxNjYzNjM0NCwiZXhwIjoxNjQ4MTcyMzQ0LCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.YfJ2Nqc3qtTNv1wAWG7Zk74c7qy3jpkXvUd4OEfoa4I5dAss_IRW8rUUOBT5fWUMx70hPo_zMFJAcww5o25xyUcv-2s-VCH2U1mlsWwfYiR3v46CAtn1sPIRS8pdHqpgYi25kSDuqY1J83dOV1VxXckivPOhkFxJ36T92mCF3DAYiTeLqgXm0_d4XN3Er_m4SF9RhFEcqplapt3T3wcEp7LyzywPraJOtqtJm1CferYMxRrmBjfgK5x57ymBYYb6Th3d6UoK6Mwjq7ZWz7MFTb61XJvg2TFOEAp5HWczWCSHEo9EPHN9MxZM98HkUUJyKb4fmJ0gT59pB3Gep2ItAPvPDwShAQ_-hgB4U0qD2YTSQueHsSwmF9QYftDyFClvXOQEgkwbyIgjVokrDB4tB6LYS0CJ2NjawO3o1-8oyQ1rhlrkGXPjQkyaCReXh-Yl9hu6ttvxkOt1HIY4kxITrr0B0lTju2phxag4zxc6NQqxicMtw4QIhv0dNvEiVcUK3uw826bTQhJqKKCcpj-8xzWg-TEALy01fJQ15RdTzgxlBnYGeICAlvhxdYuQK9bXep6n1tbwsmx7DMica6VyybgtpIOy5zn2WAeEVRwjsmDwveTy310kysriPv1Xr8nyOekzaSfD2OzLnhY9xaNz-rIuKL3paRW7nmWaXZ-5Vac"
    }

    override fun onResume() {
        super.onResume()
//        jenis?.let { viewModel.getListSatker("Bearer $token", it) }
        apiListSatker()

    }
}