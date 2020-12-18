package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PekerjaanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import id.calocallo.sicape.network.response.PekerjaanLuarResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_pick_pekerjaan.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickPekerjaanFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: EditPkrjnAdapter
    private lateinit var list: ArrayList<PekerjaanModel>
    private lateinit var adapterPkrjnLuar : ReusableAdapter<PekerjaanLuarResp>
    private lateinit var callbackPkrjnLuar : AdapterCallback<PekerjaanLuarResp>
    private var pekerjaan = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        adapterPkrjnLuar = ReusableAdapter(activity!!)
        list = ArrayList()
        ApiPekerjaan("pekerjaan")
//        initRV(list)
        initSP()
        Log.e("pekerjaanteal", pekerjaan)
        sp_jenis_pekerjaan.setText(R.string.pekerjaan)

        val hak = sessionManager.fetchHakAkses()
        if(hak == "operator"){
            btn_add_edit_pekerjaan.gone()
        }

        btn_add_edit_pekerjaan.setOnClickListener {
            goToAddSinglePekerjaan()
        }

    }

    private fun initSP() {
        val item = listOf("Pekerjaan", "Pekerjaan Diluar Dinas")
        val adapter = activity?.let { ArrayAdapter(it, R.layout.item_spinner, item) }
        sp_jenis_pekerjaan.setAdapter(adapter)
        sp_jenis_pekerjaan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                pekerjaan = "pekerjaan"
                ApiPekerjaan(pekerjaan)
            } else {
                pekerjaan = "pekerjaan_luar_dinas"
                //initAPI(pekerjaan)
                ApiPekerjaan(pekerjaan)

            }
        }
    }


    private fun ApiPekerjaan(name: String) {
        if (name == "pekerjaan") {
            NetworkConfig().getService().showPekerjaan(
                "Bearer ${sessionManager.fetchAuthToken()}",
                "4"
//            sessionManager.fetchID().toString()
            ).enqueue(object : Callback<ArrayList<PekerjaanModel>> {
                override fun onFailure(call: Call<ArrayList<PekerjaanModel>>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<PekerjaanModel>>,
                    response: Response<ArrayList<PekerjaanModel>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        if (list != null) {
                            initRV(list)
                        } else {
                            rl_pb.visible()
                        }

                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }else if(name == "pekerjaan_luar_dinas"){
            NetworkConfig().getService().showPekerjaanLuar(
                "Bearer ${sessionManager.fetchAuthToken()}",
                "4"
                //            sessionManager.fetchID().toString()
            ).enqueue(object :Callback<ArrayList<PekerjaanLuarResp>>{
                override fun onFailure(call: Call<ArrayList<PekerjaanLuarResp>>, t: Throwable) {
                    Toast.makeText(activity, "Eror Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<PekerjaanLuarResp>>,
                    response: Response<ArrayList<PekerjaanLuarResp>>
                ) {
                    if(response.isSuccessful){
                        val list = response.body()
                        if (list != null) {
                            RVPekerjaanLuar(list)
                        }else{
                            rl_pb.visible()
                        }
                    }else{
                        Toast.makeText(activity, "Eror", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

    private fun RVPekerjaanLuar(list: ArrayList<PekerjaanLuarResp>) {
        callbackPkrjnLuar = object : AdapterCallback<PekerjaanLuarResp>{
            override fun initComponent(itemView: View, data: PekerjaanLuarResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.pekerjaan

            }

            override fun onItemClicked(itemView: View, data: PekerjaanLuarResp, itemIndex: Int) {
                val editPekerjaanLuar = EditPekerjaanLuarFragment()
                    .apply {
                        enterTransition = Slide(Gravity.END)
                        exitTransition = Slide(Gravity.START)
                    }

                val bundle = Bundle()
                bundle.putParcelable("PEKERJAAN", data)
                editPekerjaanLuar.arguments = bundle

                val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                ft.replace(R.id.fl_edit_pekerjaan, editPekerjaanLuar).addToBackStack(null)
                ft.commit()
            }
        }

        adapterPkrjnLuar.adapterCallback(callbackPkrjnLuar)
            .isVerticalView()
            .setLayout(R.layout.layout_edit_1_text)
            .addData(list)
            .build(rv_list_pekerjaan)
    }


    private fun initRV(list: ArrayList<PekerjaanModel>) {
        rv_list_pekerjaan.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            EditPkrjnAdapter(it, list, object : EditPkrjnAdapter.OnClickEditPekerjaan {
                override fun onClick(position: Int) {
                    Log.e("onClick", list[position].pekerjaan.toString())
                    goToEditPekerjaan(list[position])
                }
            })
        }!!
        rv_list_pekerjaan.adapter = adapter
    }

    private fun goToEditPekerjaan(pekerjaanModel: PekerjaanModel) {
        val editPekerjaanFragment = EditPekerjaanFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }

        val bundle = Bundle()
        bundle.putParcelable("PEKERJAAN", pekerjaanModel)
        editPekerjaanFragment.arguments = bundle

        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pekerjaan, editPekerjaanFragment).addToBackStack(null)
        ft.commit()
    }

    private fun goToAddSinglePekerjaan() {
        val addSinglePekerjaanFrag = AddSinglePekerjaanFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }

        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pekerjaan, addSinglePekerjaanFrag).addToBackStack(null)
        ft.commit()
    }

    override fun onResume() {
        super.onResume()
        ApiPekerjaan(pekerjaan)
        initSP()
    }
}

