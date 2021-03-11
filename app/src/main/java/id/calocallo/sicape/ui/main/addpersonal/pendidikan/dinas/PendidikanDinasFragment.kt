package id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas

import android.os.Bundle
import android.transition.Slide
import android.util.Log
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
import id.calocallo.sicape.utils.SessionManager1
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_dinas.*


class PendidikanDinasFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<AddPendidikanModel>
    private lateinit var adapter: PendDinasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pendidikan_dinas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        list = ArrayList()
        sheenValidator = activity?.let { SheenValidator(it) }!!

        btn_next_pend_dinas.setOnClickListener {

            if (list.size == 1 && list[0].pendidikan == "") {
                list.clear()
            }
            sessionManager1.setPendDinas(list)
            Log.e("pendDinas", "${sessionManager1.getPendDinas()}")
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
        val dinasCreated = sessionManager1.getPendDinas()
        if (dinasCreated.size == 0) {
            list.add(AddPendidikanModel())
        }
        adapter = activity?.let {
            PendDinasAdapter(it, list, object : PendDinasAdapter.OnClickDinas {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!
        rv_pend_dinas.adapter = adapter

        btn_add_pend_dinas.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(AddPendidikanModel())
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)
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
        val dinas = sessionManager1.getPendDinas()
        for (i in 0 until dinas.size) {
            list.add(
                i, AddPendidikanModel(
                    dinas[i].pendidikan,
                    dinas[i].jenis,
                    dinas[i].tahun_awal,
                    dinas[i].tahun_akhir,
                    dinas[i].kota,
                    dinas[i].yang_membiayai,
                    dinas[i].keterangan
                )
            )

        }

    }
}