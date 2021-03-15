package id.calocallo.sicape.ui.main.editpersonel.keluarga

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.KeluargaMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_keluarga.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickKeluargaActivity : BaseActivity() {
    private var adapterKeluarga = ReusableAdapter<KeluargaMinResp>(this)
    private lateinit var callbackKeluarga: AdapterCallback<KeluargaMinResp>
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_keluarga)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Data Keluarga"

        val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
        getListKeluarga(personel)

        btn_add_single_keluarga.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java).apply {
                this.putExtra("ADD_KELUARGA", true)
                this.putExtra("PERSONEL_DETAIL", personel)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getListKeluarga(personel: AllPersonelModel1?) {
        rl_pb.visible()
        NetworkConfig().getServPers().showKeluarga(
            "Bearer ${sessionManager1.fetchAuthToken()}", personel?.id.toString()
        ).enqueue(object : Callback<ArrayList<KeluargaMinResp>> {
            override fun onFailure(call: Call<ArrayList<KeluargaMinResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_keluarga.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<KeluargaMinResp>>,
                response: Response<ArrayList<KeluargaMinResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    listKeluarga(response.body(), personel)
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_keluarga.gone()
                }
            }
        })
    }

    private fun listKeluarga(
        list: ArrayList<KeluargaMinResp>?, personel: AllPersonelModel1?
    ) {
        callbackKeluarga = object : AdapterCallback<KeluargaMinResp> {
            override fun initComponent(itemView: View, data: KeluargaMinResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data?.nama
                when (data.status_hubungan) {
                    "ayah_kandung" -> itemView.txt_detail_2.text = "Ayah Kandung"
                    "ayah_tiri" -> itemView.txt_detail_2.text = "Ayah Tiri"
                    "ibu_kandung" -> itemView.txt_detail_2.text = "Ibu Kandung"
                    "ibu_tiri" -> itemView.txt_detail_2.text = "Ibu Tiri"
                    "mertua_laki_laki" -> itemView.txt_detail_2.text = "Mertua Laki-laki"
                    "mertua_perempuan" -> itemView.txt_detail_2.text = "Mertua Perempuan"
                }
            }

            override fun onItemClicked(itemView: View, data: KeluargaMinResp, itemIndex: Int) {
                val intent =
                    Intent(this@PickKeluargaActivity, EditKeluargaActivity::class.java).apply {
                        this.putExtra("KELUARGA", data)
                        this.putExtra("PERSONEL_DETAIL", personel)
                    }
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        list?.let { it ->
            sortKeluarga(it).let {
                adapterKeluarga.adapterCallback(callbackKeluarga)
                    .addData(it).setLayout(R.layout.item_2_text)
                    .isVerticalView()
                    .build(rv_list_keluarga)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
        getListKeluarga(personel)
    }

    private val roles: HashMap<String, Int> = hashMapOf(
        "ayah_kandung" to 0,
        "ayah_tiri" to 1,
        "ibu_kandung" to 2,
        "ibu_tiri" to 3,
        "mertua_laki_laki" to 4,
        "mertua_perempuan" to 5
    )

    fun sortKeluarga(keluarga: ArrayList<KeluargaMinResp>): ArrayList<KeluargaMinResp> {
        val comparator = Comparator { o1: KeluargaMinResp, o2: KeluargaMinResp ->
            return@Comparator roles[o1.status_hubungan]!! - roles[o2.status_hubungan]!!
        }
        val copy = arrayListOf<KeluargaMinResp>().apply { addAll(keluarga) }
        copy.sortWith(comparator)
        return copy
    }

}