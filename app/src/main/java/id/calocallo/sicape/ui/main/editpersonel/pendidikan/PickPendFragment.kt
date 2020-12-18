package id.calocallo.sicape.ui.main.editpersonel.pendidikan

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
import id.calocallo.sicape.model.PendidikanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_pick_pend.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.view_no_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PickPendFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<PendidikanModel>
    private lateinit var adapter: EditPendAdapter
    private var jenis: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_pend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!

        val hakAkses = sessionManager.fetchHakAkses()

        jenis = "umum"
        list = ArrayList()
        sp_jenis_pendidikan.setText(R.string.umum)
        initSP()
        initAPI(jenis.toString())

        if (hakAkses == "operator") {
            btn_add_edit_pend.gone()
        }
        btn_add_edit_pend.setOnClickListener {
            addSinglePend()
        }
    }

    private fun initAPI(nama_jenis: String) {
        rl_pb.visible()
        NetworkConfig().getService().showPendByJenis(
            "Bearer ${sessionManager.fetchAuthToken()}",
            "4",
//            sessionManager.fetchID().toString(), //Constants.ID_PERSONEL
            nama_jenis
        ).enqueue(object : Callback<ArrayList<PendidikanModel>> {
            override fun onFailure(call: Call<ArrayList<PendidikanModel>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<PendidikanModel>>,
                response: Response<ArrayList<PendidikanModel>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    list = response.body()!!
                    if (list == null) {
                        rl_no_data.visible()
                    } else {
                        initRV(list)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                }
            }
        })
    }


    private fun initRV(list: ArrayList<PendidikanModel>?) {
        rv_edit_pendidikan.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            EditPendAdapter(it, list, object : EditPendAdapter.OnClickEditPend {
                override fun onClick(position: Int) {
                    Log.e("ini data edit", this@PickPendFragment.list[0].id.toString())
                    goToEditPend(this@PickPendFragment.list[position])
                }
            })
        }!!
        rv_edit_pendidikan.adapter = adapter
    }

    private fun initSP() {
        val item = listOf("Umum", "Kedinasan", "Lain-lain")
        val adapterSP = activity?.let { ArrayAdapter(it, R.layout.item_spinner, item) }
        sp_jenis_pendidikan.setAdapter(adapterSP)
        sp_jenis_pendidikan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                jenis = "umum"
                initAPI(jenis.toString())
            } else if (position == 1) {
                jenis = "kedinasan"
                initAPI(jenis.toString())
            } else {
                jenis = "lain_lain"
                initAPI(jenis.toString())
            }
//            val onItem = parent.getItemAtPosition(position).toString()
//            Log.e("onItemEdit", onItem)
        }
    }


    private fun goToEditPend(itemPend: PendidikanModel) {
        val editPendFragment = EditPendFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }

        val bundle = Bundle()
        bundle.putParcelable("PENDIDIKAN", itemPend)
        editPendFragment.arguments = bundle

        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pend, editPendFragment).addToBackStack(null)
        ft.commit()
    }

    private fun addSinglePend() {
        val addSinglePendFragment = AddSinglePendFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }

        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pend, addSinglePendFragment).addToBackStack(null)
        ft.commit()
    }

}