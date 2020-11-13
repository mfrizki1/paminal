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
import com.google.android.material.button.MaterialButton
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendDinas
import id.calocallo.sicape.model.PendDinasModel
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya.PendidikanLainFragment
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_dinas.*


class PendidikanDinasFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
//    private lateinit var parentPendidikan: ViewGroup

    private lateinit var list: ArrayList<PendDinasModel>
    private lateinit var adapter: PendDinasAdapter
    private lateinit var parentList: ParentListPendDinas
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
//        comunicator = activity as Comunicator
        sheenValidator = activity?.let { SheenValidator(it) }!!
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        list = ArrayList()
        parentList = ParentListPendDinas(list)
        val btnNext = view.findViewById<MaterialButton>(R.id.btn_next_pend_dinas)

//        btnNext.setOnClickListener {
//            comunicator.gotoOther()
//        }

        /*v1
        parentPendidikan = view.findViewById<LinearLayout>(R.id.ll_parent_pendidikan_dinas)

        btn_add_pddkn_kedinasan.setOnClickListener {
            val inflater =
                activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.layout_pendidikan_kedinasan, null)
            parentPendidikan.addView(rowView, parentPendidikan.childCount - 3)
        }

        btn_next_pendidikan_dinas.setOnClickListener {
            sheenValidator.validate()
            val ft : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_pendidikan, PendidikanLainFragment())
            ft.commit()
        }
         */

//        parentPendidikan = view.findViewById(R.id.ll_parent_pendidikan_dinas)
//        val newLayout: View = layoutInflater.inflate(R.layout.layout_pendidikan_kedinasan, null)

        /* delete v1
        btn_add_pddkn_kedinasan.setOnClickListener {
            addlayout(newLayout)
        }
        btn_delete_dinas.setOnClickListener {
            deleteLayout(newLayout)
        }

         */

//        layoutToggle(btn_add_pddkn_kedinasan, btn_delete_dinas)

        btn_next_pend_dinas.setOnClickListener {
//            Log.e("parent", parentList.parentDinas[0].nama_pend)
//            Log.e("parent", parentList.parentDinas[1].nama_pend)
//            Log.e("parent", parentList.parentDinas[2].nama_pend)

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


//            activity!!.supportFragmentManager.beginTransaction()
//                .replace(R.id.fl_pendidikan, pendOtherFrag).addToBackStack(null).commit()
        setAdapter()


    }

    private fun setAdapter() {
        rv_pend_dinas.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(PendDinasModel("", "", "", "", "", ""))
        list.add(PendDinasModel("", "", "", "", "", ""))
        list.add(PendDinasModel("", "", "", "", "", ""))
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
            list.add(PendDinasModel("", "", "", "", "", ""))
            adapter.notifyItemChanged(position)
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

}