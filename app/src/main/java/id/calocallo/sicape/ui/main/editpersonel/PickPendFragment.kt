package id.calocallo.sicape.ui.main.editpersonel

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PendUmumModel
import kotlinx.android.synthetic.main.fragment_pick_pend.*


class PickPendFragment : Fragment() {
    private lateinit var list: ArrayList<PendUmumModel>
    private lateinit var adapter: EditPendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_pend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        initRV()
        initSP()
    }

    private fun initSP() {
        val item = listOf("Umum", "Kedinasan", "Lain-lain")
        val adapterSP = activity?.let { ArrayAdapter(it, R.layout.item_spinner, item) }
        sp_jenis_pendidikan.setAdapter(adapterSP)
        sp_jenis_pendidikan.setOnItemClickListener { parent, view, position, id ->
            val onItem = parent.getItemAtPosition(position).toString()
            Log.e("onItemEdit", onItem)
        }
    }

    private fun initRV() {
        list.add(PendUmumModel("SDN BUMI", "2000", "2001", "BJM", "AYAH", ""))
        list.add(PendUmumModel("SMP API", "2000", "2001", "BJM", "AYAH", ""))
        list.add(PendUmumModel("SMK AIR", "2000", "2001", "BJM", "AYAH", ""))
        list.add(PendUmumModel("UNIV ANGIN", "2000", "2001", "BJM", "AYAH", ""))

        rv_edit_pendidikan.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            EditPendAdapter(it, list, object : EditPendAdapter.OnClickEditPend {
                override fun onClick(position: Int) {
                    Log.e("ini data edit", list[0].nama_pend)
                    goToEditPend(list[position].nama_pend)
                }
            })
        }!!
        rv_edit_pendidikan.adapter = adapter
    }

    private fun goToEditPend(namaPend: String) {
        val editPendFragment = EditPendFragment().apply {
            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }

        val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pend, editPendFragment)
        ft.commit()
    }

}