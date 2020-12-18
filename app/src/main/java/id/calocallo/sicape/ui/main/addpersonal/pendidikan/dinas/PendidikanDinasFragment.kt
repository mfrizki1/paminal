package id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendDinas
import id.calocallo.sicape.model.ParentListPendUmum
import id.calocallo.sicape.model.AddPendidikanModel
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya.PendidikanLainFragment
import id.calocallo.sicape.utils.SessionManager
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_dinas.*


class PendidikanDinasFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager: SessionManager
//    private lateinit var parentPendidikan: ViewGroup

    private lateinit var list: ArrayList<AddPendidikanModel>
    private lateinit var listUmum: ArrayList<AddPendidikanModel>
    private lateinit var adapter: PendDinasAdapter
    private lateinit var parentList: ParentListPendDinas
    private lateinit var parentListUmum: ParentListPendUmum
//    private lateinit var comunicator: Comunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendidikan_dinas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        list = ArrayList()
//        comunicator = activity as Comunicator
        sheenValidator = activity?.let { SheenValidator(it) }!!
//        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
//        list = ArrayList()

        btn_next_pend_dinas.setOnClickListener {

            if(list.size == 1 && list[0].pendidikan == ""){
                list.clear()
            }
            sessionManager.setPendDinas(list)

            sheenValidator.validate()
            val pendOtherFrag = PendidikanLainFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_pendidikan, pendOtherFrag)
            ft.addToBackStack(null)
            ft.commit()
        }


        setAdapter()


    }

    private fun setAdapter() {
        rv_pend_dinas.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val dinasCreated = sessionManager.getPendDinas()
        if (dinasCreated.size == 0) {
            list.add(AddPendidikanModel("", "", "", "", "", ""))
        }
        adapter = activity?.let {
            PendDinasAdapter(it, list, object : PendDinasAdapter.OnClickDinas {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pend_dinas.adapter = adapter

        btn_add_pend_dinas.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(AddPendidikanModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }

    fun loadFrag(f1: Fragment?, name: String?, fm: FragmentManager) {
        val ft = fm.beginTransaction()
        ft.replace(R.id.fl_pendidikan, f1!!, name)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    override fun onResume() {
        super.onResume()
        val dinas = sessionManager.getPendDinas()
        for (i in 0 until dinas.size) {
            list.add(
                i, AddPendidikanModel(
                    dinas[i].pendidikan,
                    dinas[i].tahun_awal,
                    dinas[i].tahun_akhir,
                    dinas[i].kota,
                    dinas[i].yang_membiayai,
                    dinas[i].keterangan
                )
            )
//            edt_nama_kedinasan.setText(dinas[i].pendidikan)
//            edt_thn_awal_kedinasan.setText(dinas[i].tahun_awal)
//            edt_thn_akhir_kedinasan.setText(dinas[i].tahun_akhir)
//            edt_tempat_kedinasan.setText(dinas[i].kota)
//            edt_membiayai_kedinasan.setText(dinas[i].yang_membiayai)
//            edt_ket_kedinasan.setText(dinas[i].keterangan)
        }

    }
}