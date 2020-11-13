package id.calocallo.sicape.ui.main.addpersonal.pendidikan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendUmum
import id.calocallo.sicape.model.PendUmumModel
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum.PendidikanUmumFragment
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum.UmumAdapter
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class PendPersonelActivity : BaseActivity() {

    private lateinit var sheenValidator: SheenValidator
    private var parentPendidikan: LinearLayout? = null

    private lateinit var list: ArrayList<PendUmumModel>
    private lateinit var adapter: UmumAdapter
    private lateinit var parent: ParentListPendUmum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pend_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pendidikan"
/*

        sheenValidator = SheenValidator(this)
        sheenValidator.registerAsRequired(edt_nama_umum_custom)
        sheenValidator.registerAsRequired(edt_thn_awal_umum_custom)
        sheenValidator.registerAsRequired(edt_thn_akhir_umum_custom)
        sheenValidator.registerAsRequired(edt_tempat_umum_custom)
        sheenValidator.registerAsRequired(edt_membiayai_umum_custom)
        sheenValidator.registerAsRequired(edt_ket_umum_custom)

        list = ArrayList()
        parent = ParentListPendUmum(list)

        btn_next_pend.setOnClickListener {
            Log.e("parent", parent.pendUmumList[0].nama_pend)
            Log.e("parent", parent.pendUmumList[1].nama_pend)
            Log.e("parent", parent.pendUmumList[2].nama_pend)
            sheenValidator.validate()

            startActivity(Intent(this, PendDinasActivity::class.java))
        }
                setAdapter()

         */


        val pendUmumFrag = PendidikanUmumFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_pendidikan, pendUmumFrag)
//        ft.addToBackStack(null)
        ft.commit()

    }

    private fun setAdapter() {
        rv_pend_umum.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.add(PendUmumModel("", "", "", "", "", ""))
        list.add(PendUmumModel("", "", "", "", "", ""))
        list.add(PendUmumModel("", "", "", "", "", ""))
        list.add(PendUmumModel("", "", "", "", "", ""))
        adapter = UmumAdapter(this, list, object : UmumAdapter.OnClick {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()

            }
        })
        rv_pend_umum.adapter = adapter

        btn_add_pend_umum.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(PendUmumModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }
    }
}
