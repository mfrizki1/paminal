package id.calocallo.sicape.ui.main.wanjak

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.WanjakResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_wanjak.*
import kotlinx.android.synthetic.main.item_wanjak.*
import kotlinx.android.synthetic.main.item_wanjak.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListWanjakActivity : BaseActivity() {
    private var noRps = ""
    private var noRpph = ""
    private var noSktb = ""
    private var noSktt = ""
    private var noSp4 = ""
    private var adapterWanjak = ReusableAdapter<WanjakResp>(this)
    private lateinit var callbackWanjak: AdapterCallback<WanjakResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_wanjak)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Dewan Pertimbangan Karir"

        getListWanjak()

    }

    private fun getListWanjak() {
        rl_pb.visible()
        NetworkDummy().getService().getWanjak().enqueue(object : Callback<ArrayList<WanjakResp>> {
            override fun onFailure(call: Call<ArrayList<WanjakResp>>, t: Throwable) {
                rl_pb.gone()
                rv_list_wanjak.gone()
                rl_no_data.visible()
            }

            override fun onResponse(
                call: Call<ArrayList<WanjakResp>>,
                response: Response<ArrayList<WanjakResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackWanjak = object : AdapterCallback<WanjakResp> {
                        @SuppressLint("SetTextI18n")
                        override fun initComponent(
                            itemView: View,
                            data: WanjakResp,
                            itemIndex: Int
                        ) {
                            itemView.txt_nama_personel_wanjak.text = "Nama : ${data.personel?.nama}"
                            itemView.txt_pangkat_nrp_personel_wanjak.text =
                                "Pangkat : ${data.personel?.pangkat.toString()
                                    .toUpperCase()} / ${data?.personel?.nrp}"
                            itemView.txt_jabatan_personel_wanjak.text =
                                "Jabatan : ${data?.personel?.jabatan}"
                            itemView.txt_kesatuan_personel_wanjak.text =
                                "Kesatuan : ${data?.personel?.kesatuan.toString().toUpperCase()}"
                            itemView.txt_no_lp_wanjak.text = "No LP : ${data?.kasus?.lp?.no_lp}"
                            if (data.kasus?.skhd?.id != null) {
                                itemView.txt_no_skhd_putkke_wanjak.text =
                                    "No SKHD : ${data.kasus?.skhd?.no_skhd}"
                            } else {
                                itemView.txt_no_skhd_putkke_wanjak.text =
                                    "No PUTKKE : ${data.kasus?.putkke?.no_putkke}"
                            }


                            itemView.txt_stts_terlapor_wanjak.text =
                                "Status Terlapor : ${data.kasus?.status_terlapor}"
                            itemView.txt_stts_kasus_wanjak.text =
                                "Status Kasus : ${data.kasus?.status_kasus}"

                            if (data.is_pernah_tersangkut_kasus == 1) {
                                itemView.txt_pernah_tersangkut_kasus_wanjak.text = "Ya"
                            } else {
                                itemView.txt_pernah_tersangkut_kasus_wanjak.text = "Tidak"
                            }
                            itemView.txt_total_kasus_bersalah_wanjak.text =
                                "Total Kasus Bersalah : ${data.total_kasus_bersalah}"
                            itemView.txt_total_kasus_tidak_bersalah_wanjak.text =
                                "Total Kasus Tidak Bersalah : ${data.total_kasus_tidak_bersalah}"
                            itemView.txt_total_kasus_tidak_terbukti_wanjak.text =
                                "Total Kasus Tidak Terbukti : ${data.total_kasus_tidak_terbukti}"
                            itemView.txt_total_kasus_berjalan_wanjak.text =
                                "Total Kasus Sedang Berjalan : ${data.total_kasus_berjalan}"
                            itemView.txt_total_kasus_selesai_wanjak.text =
                                "Total Kasus Selesai : ${data.total_kasus_selesai}"

                            when {
                                data.kasus?.rps?.id != null -> {
                                    noRps = data.kasus?.rps?.no_rps!!
                                    Log.e("no RPS", noRps)
                                }
                                data.kasus?.rpph?.id != null -> {
                                    noRpph = data.kasus?.rpph?.no_rpph!!
                                    Log.e("noRpph", "$noRpph")
                                }
                                data.kasus?.sktb?.id != null -> {
                                    noSktb = data.kasus?.sktb?.no_sktb!!
                                    Log.e("noSktb", "$noSktb")
                                }
                                data.kasus?.sktt?.id != null -> {
                                    noSktt = data.kasus?.sktt?.no_sktt!!
                                    Log.e("noSktt", "$noSktt")
                                }
                                data.kasus?.sp4?.id != null -> {
                                    noSp4 = data.kasus?.sp4?.no_sp4!!
                                    Log.e("noSp4", "$noSp4")
                                }
                            }

                            itemView.txt_no_rps_wanjak.text =
                                "No RPS : ${if (data.kasus?.rps?.no_rps.isNullOrBlank()) "Tidak Ada" else data.kasus?.rps?.no_rps}"
                            itemView.txt_no_rpph_wanjak.text =
                                "No RPPH : ${if (data.kasus?.rpph?.no_rpph.isNullOrBlank()) "Tidak Ada" else data.kasus?.rpph?.no_rpph}"
                            itemView.txt_no_sktb_wanjak.text =
                                "No SKTB : ${if (data.kasus?.sktb?.no_sktb.isNullOrBlank()) "Tidak Ada" else data.kasus?.sktb?.no_sktb}"
                            itemView.txt_no_sktt_wanjak.text =
                                "NO SKTT : ${if (data.kasus?.sktt?.no_sktt.isNullOrBlank()) "Tidak Ada" else data.kasus?.sktt?.no_sktt} "
                            itemView.txt_no_s4_wanjak.text =
                                "NO SP3 : ${if (data.kasus?.sp4?.no_sp4.isNullOrBlank()) "Tidak Ada" else data.kasus?.sp4?.no_sp4}"

                        }

                        override fun onItemClicked(
                            itemView: View,
                            data: WanjakResp,
                            itemIndex: Int
                        ) {
                        }

                    }
                    response.body()?.let {
                        adapterWanjak.adapterCallback(callbackWanjak)
                            .isVerticalView().filterable()
                            .addData(it)
                            .build(rv_list_wanjak)
                            .setLayout(R.layout.item_wanjak)
                    }
                } else {
                    rl_pb.gone()
                    rv_list_wanjak.gone()
                    rl_no_data.visible()
                }
            }
        })


    }
}