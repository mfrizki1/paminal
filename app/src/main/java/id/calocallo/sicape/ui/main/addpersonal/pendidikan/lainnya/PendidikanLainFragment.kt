package id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendOther
import id.calocallo.sicape.model.AddPendidikanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPendReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.addpersonal.pekerjaan.PekerjaanPersonelActivity
import id.calocallo.sicape.utils.Constants
import id.calocallo.sicape.utils.SessionManager
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.*
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendidikanLainFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var sheenValidator: SheenValidator
//    private lateinit var parentPendidikan: LinearLayout

    var addPendResp = AddPendReq()
    private lateinit var listOther: ArrayList<AddPendidikanModel>
    private lateinit var adapter: PendOtherAdapter
    private lateinit var parentList: ParentListPendOther
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendidikan_lain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheenValidator = activity?.let { SheenValidator(it) }!!
        sessionManager = activity?.let { SessionManager(it) }!!

        //TODO get All List Pend
        val listUmum = sessionManager.getPendUmum()
        val listDinas = sessionManager.getPendDinas()
        listOther = ArrayList()

        addPendResp.riwayat_Add_pendidikan_umum = listUmum
        addPendResp.riwayat_Add_pendidikan_dinas = listDinas
        addPendResp.riwayat_Add_pendidikan_lain_lain = listOther
        setAdapter()

        //buttonNext
        view.btn_next_pend_other.setOnClickListener {
            //validasi list kosong apa tidak
            if (listOther.size == 1 && listOther[0].pendidikan == "") {
                listOther.clear()
            }
            sessionManager.setPendOther(listOther)
            val intent = Intent(activity, PekerjaanPersonelActivity::class.java)
            startActivity(intent)
            /*
            NetworkConfig().getService().addPendMany(
                "Bearer ${sessionManager.fetchAuthToken()},",
                Constants.ID_PERSONEL, //Constansts.ID_PERSONEL
                addPendResp
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data riwayat pendidikan saved succesfully") {
                            Log.e("berhasil pendidikan", "Berhasil Pendidikan")
                            val intent = Intent(activity, PekerjaanPersonelActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
             */

            sheenValidator.validate()

        }
    }


    private fun setAdapter() {
        rv_pend_other.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val lainnyaCreated = sessionManager.getPendOther()
        if (lainnyaCreated.size == 0) {
            listOther.add(AddPendidikanModel("", "", "", "", "", ""))
        }
        adapter = activity?.let {
            PendOtherAdapter(it, listOther, object : PendOtherAdapter.OnCLickOther {
                override fun onDelete(position: Int) {
                    listOther.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pend_other.adapter = adapter
        btn_add_pend_other.setOnClickListener {
            val position = if (listOther.isEmpty()) 0 else listOther.size - 1
            listOther.add(AddPendidikanModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }
    }

    override fun onResume() {
        super.onResume()

        val lainnya = sessionManager.getPendOther()
        for (i in 0 until lainnya.size) {
            if (lainnya.size == 0) {
                listOther.add(AddPendidikanModel("", "", "", "", "", ""))
            } else {
                listOther.add(
                    i, AddPendidikanModel(
                        lainnya[i].pendidikan,
                        lainnya[i].tahun_awal,
                        lainnya[i].tahun_akhir,
                        lainnya[i].kota,
                        lainnya[i].yang_membiayai,
                        lainnya[i].keterangan
                    )
                )
            }

        }
    }
}