package id.calocallo.sicape.ui.main.editpersonel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PendUmumModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pendidikan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPendidikanActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pendidikan)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Nama Personel"

        val pickPendFragment = PickPendFragment().apply {
            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pend, pickPendFragment)
        ft.commit()


    }


}