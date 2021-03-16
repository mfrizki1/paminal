package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.OrganisasiResp
import id.calocallo.sicape.network.response.PenghargaanResp
import id.calocallo.sicape.network.response.PerjuanganResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_pick_menu_organisasi_dll.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickMenuOrganisasiDllActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var orgAdapter: ReusableAdapter<OrganisasiResp>
    private lateinit var orgCallback: AdapterCallback<OrganisasiResp>

    private lateinit var perjuanganAdapter: ReusableAdapter<PerjuanganResp>
    private lateinit var perjuanganCallback: AdapterCallback<PerjuanganResp>

    private lateinit var penghargaanAdapter: ReusableAdapter<PenghargaanResp>
    private lateinit var penghargaanCallback: AdapterCallback<PenghargaanResp>
    private val ORGANISASI = "organisasi"
    private val PERJUANGAN = "perjuangan"
    private val PENGHARGAAN = "penghargaan"
    private var tempJenis = ""

    //    private lateinit var personel: PersonelModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_menu_organisasi_dll)
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama.toString()

        sessionManager1 = SessionManager1(this)
        orgAdapter = ReusableAdapter(this)
        perjuanganAdapter = ReusableAdapter(this)
        penghargaanAdapter = ReusableAdapter(this)
        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_add_edit_organisasi_dll.gone()
        }

        btn_add_edit_organisasi_dll.setOnClickListener {
            val intent = Intent(this, AddSingleOrganisasiActivity::class.java)
            intent.putExtra("MISC", tempJenis)
            intent.putExtra("PERSONEL_DETAIL", personel)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        OrgSP()
        sp_jenis_organisasi_dll.setText("Organisasi")
        tempJenis = ORGANISASI
        ApiOrgDll(tempJenis)

    }

    private fun OrgSP() {
        val item = listOf("Organisasi", "Perjuangan Mencapai Cita-Cita", "Penghargaan")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        sp_jenis_organisasi_dll.setAdapter(adapter)
        sp_jenis_organisasi_dll.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    tempJenis = ORGANISASI
                    ApiOrgDll(ORGANISASI)
                }
                1 -> {
                    tempJenis = PERJUANGAN
                    ApiOrgDll(PERJUANGAN)
                }
                2 -> {
                    tempJenis = PENGHARGAAN
                    ApiOrgDll(PENGHARGAAN)
                }
            }
        }
    }

    private fun ApiOrgDll(name: String) {
        rl_pb.visible()
        rl_no_data.gone()
        when (name) {
            ORGANISASI -> {
                NetworkConfig().getServPers().showOrganisasi(
                    "Bearer ${sessionManager1.fetchAuthToken()}",
//                    "4"
                    sessionManager1.fetchID().toString()
                ).enqueue(object : Callback<ArrayList<OrganisasiResp>> {
                    override fun onFailure(call: Call<ArrayList<OrganisasiResp>>, t: Throwable) {
                        rl_pb.gone()
                        rl_no_data.visible()
                        toast("$t")
                    }

                    override fun onResponse(
                        call: Call<ArrayList<OrganisasiResp>>,
                        response: Response<ArrayList<OrganisasiResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            if (response.body()!!.isEmpty()) {
                                rl_no_data.visible()
                                rv_edit_organisasi_dll.gone()
                            } else {
                                rl_no_data.gone()
                                rv_edit_organisasi_dll.visible()
                                RVOrganisasi(response.body()!!)
                            }
                        } else {
                            rl_no_data.visible()
                            toast(R.string.error)

                        }
                    }
                })
            }
            PERJUANGAN -> {
                NetworkConfig().getServPers().showPerjuangan(
                    "Bearer ${sessionManager1.fetchAuthToken()}",
//                    "4"
                    sessionManager1.fetchID().toString()
                ).enqueue(object : Callback<ArrayList<PerjuanganResp>> {
                    override fun onFailure(call: Call<ArrayList<PerjuanganResp>>, t: Throwable) {
                        rl_pb.gone()
                        rl_no_data.visible()
                        toast("$t")
                    }

                    override fun onResponse(
                        call: Call<ArrayList<PerjuanganResp>>,
                        response: Response<ArrayList<PerjuanganResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            if (response.body()!!.isEmpty()) {
                                rl_no_data.visible()
                                rv_edit_organisasi_dll.gone()
                            } else {
                                rl_no_data.gone()
                                rv_edit_organisasi_dll.visible()
                                RVPerjuangan(response.body()!!)
                            }
                        } else {
                            rl_no_data.visible()
                            toast(R.string.error)
                        }
                    }
                })
            }
            PENGHARGAAN -> {
                NetworkConfig().getServPers().showPenghargaan(
                    "Bearer ${sessionManager1.fetchAuthToken()}",
//                    "4"
                    sessionManager1.fetchID().toString()
                ).enqueue(object : Callback<ArrayList<PenghargaanResp>> {
                    override fun onFailure(call: Call<ArrayList<PenghargaanResp>>, t: Throwable) {
                        rl_pb.gone()
                        rl_no_data.visible()
                        toast("$t")

                    }

                    override fun onResponse(
                        call: Call<ArrayList<PenghargaanResp>>,
                        response: Response<ArrayList<PenghargaanResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            if (response.body()!!.isEmpty()) {
                                rl_no_data.visible()
                                rv_edit_organisasi_dll.gone()
                            } else {
                                rl_no_data.gone()
                                rv_edit_organisasi_dll.visible()
                                RVPenghargaan(response.body()!!)
                            }
                        } else {
                            rl_no_data.visible()
                            toast(R.string.error)
                        }
                    }
                })
            }
        }

    }

    private fun RVPenghargaan(list: ArrayList<PenghargaanResp>) {
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        penghargaanCallback = object : AdapterCallback<PenghargaanResp> {
            override fun initComponent(itemView: View, data: PenghargaanResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.penghargaan

            }

            override fun onItemClicked(itemView: View, data: PenghargaanResp, itemIndex: Int) {
                val intent =
                    Intent(this@PickMenuOrganisasiDllActivity, EditPenghargaanActivity::class.java)
                intent.putExtra("PENGHARGAAN", data)
                intent.putExtra("PERSONEL", personel)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        penghargaanAdapter.adapterCallback(penghargaanCallback)
            .isVerticalView()
            .addData(list)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_edit_organisasi_dll)
    }


    private fun RVPerjuangan(list: ArrayList<PerjuanganResp>) {
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        perjuanganCallback = object : AdapterCallback<PerjuanganResp> {
            override fun initComponent(itemView: View, data: PerjuanganResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.peristiwa
            }

            override fun onItemClicked(itemView: View, data: PerjuanganResp, itemIndex: Int) {
                val intent = Intent(
                    this@PickMenuOrganisasiDllActivity,
                    EditPerjuanganCitaActivity::class.java
                )
                intent.putExtra("PERJUANGAN", data)
                intent.putExtra("PERSONEL", personel)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }

        }
        perjuanganAdapter.adapterCallback(perjuanganCallback)
            .isVerticalView()
            .addData(list)
            .setLayout(R.layout.layout_edit_1_text)
            .build(rv_edit_organisasi_dll)

    }


    private fun RVOrganisasi(list: ArrayList<OrganisasiResp>) {
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        orgCallback = object : AdapterCallback<OrganisasiResp> {
            override fun initComponent(itemView: View, data: OrganisasiResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.organisasi
            }

            override fun onItemClicked(itemView: View, data: OrganisasiResp, itemIndex: Int) {
                val intent =
                    Intent(this@PickMenuOrganisasiDllActivity, EditOrganisasiActivity::class.java)
                intent.putExtra("ORGANISASI", data)
                intent.putExtra("PERSONEL", personel)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }

        }
        orgAdapter.adapterCallback(orgCallback)
            .isVerticalView()
            .setLayout(R.layout.layout_edit_1_text)
            .addData(list)
            .build(rv_edit_organisasi_dll)
    }

    override fun onResume() {
        super.onResume()
        OrgSP()
        ApiOrgDll(tempJenis)

    }
}