package id.calocallo.sicape.ui.main.skhd

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListHukumanSkhd
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSkhdActivity : BaseActivity() {
    //    private lateinit var adapterHukSkhd: ReusableAdapter<ListHukumanSkhd>
    private lateinit var adapterHukSkhd: AddHukSkhdAdapter

    //    private lateinit var adapterCallbackHukSkhd: AdapterCallback<ListHukumanSkhd>
    private lateinit var listHukumanSkhd: ArrayList<ListHukumanSkhd>

    //    private lateinit var listHukSkhd: ArrayList<HukumanReq>
//    private lateinit var adapterHuk: PernahHukumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skhd)
        listHukumanSkhd = ArrayList()

        /*
        adapterHukSkhd = ReusableAdapter(this)
        adapterCallbackHukSkhd = object : AdapterCallback<ListHukumanSkhd> {
            override fun initComponent(itemView: View, data: ListHukumanSkhd, itemIndex: Int) {
                listHukumanSkhd.add(ListHukumanSkhd(""))
                itemView.edt_hukuman_skhd.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        data.hukuman = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })


                itemView.btn_add_hukuman_skhd.setOnClickListener {
                    val position =
                        listHukumanSkhd.add(ListHukumanSkhd(""))
                }

                itemView.btn_delete_hukuman_skhd.setOnClickListener {

                }
            }

            override fun onItemClicked(itemView: View, data: ListHukumanSkhd, itemIndex: Int) {
                TODO("Not yet implemented")
            }
        }

         */

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKHD"



        //LIST
        listHukumanSkhd.add(ListHukumanSkhd(""))
        rv_huk_skhd.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterHukSkhd =
            AddHukSkhdAdapter(this, listHukumanSkhd, object : AddHukSkhdAdapter.OnClickHukSKHD {
                override fun onAdd() {
                    listHukumanSkhd.add(ListHukumanSkhd(""))
                    val position = if (listHukumanSkhd.isEmpty()) 0 else listHukumanSkhd.size - 1
                    adapterHukSkhd.notifyItemInserted(position)
                    adapterHukSkhd.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listHukumanSkhd.removeAt(position)
                    adapterHukSkhd.notifyDataSetChanged()

                }
            })
        rv_huk_skhd.adapter = adapterHukSkhd



        btn_save_skhd.setOnClickListener {
            if (listHukumanSkhd.size == 1 && listHukumanSkhd[0].hukuman == "") {
                listHukumanSkhd.clear()
            }
            Log.e(
                "add_SKHD", "${edt_no_skhd.text.toString()}, ${edt_no_lp_skhd.text.toString()}" +
                        "${edt_satker_skhd.text.toString()}, ${edt_memperlihat_skhd.text.toString()}" +
                        "${edt_tgl_disampaikan_skhd.text.toString()}, ${edt_pukul_disampaikan_skhd.text.toString()}" +
                        "${edt_tgl_dibuat_skhd.text.toString()}, ${edt_kepala_bidang_skhd.text.toString()}" +
                        "${edt_nama_bidang_skhd.text.toString()}, ${edt_pangkat_bidang_skhd.text.toString()}" +
                        "${edt_nrp_bidang_skhd.text.toString()}, ${listHukumanSkhd.size}"
            )
            edt_no_skhd.text.toString()
            edt_no_lp_skhd.text.toString()
            edt_satker_skhd.text.toString()
            edt_memperlihat_skhd.text.toString()
            edt_tgl_disampaikan_skhd.text.toString()
            edt_pukul_disampaikan_skhd.text.toString()
            edt_tgl_dibuat_skhd.text.toString()
            edt_kepala_bidang_skhd.text.toString()
            edt_nama_bidang_skhd.text.toString()
            edt_pangkat_bidang_skhd.text.toString()
            edt_nrp_bidang_skhd.text.toString()
            listHukumanSkhd
        }
    }
}