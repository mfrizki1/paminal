package id.calocallo.sicape.utils

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.ActionMode

class ActionBarCallback(var context: Context) : ActionMode.Callback {

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
        //as we no longer have a selection so the actionMode can be finished
        mode.finish()
        //we consume the event
        return true
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {}

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }
}
