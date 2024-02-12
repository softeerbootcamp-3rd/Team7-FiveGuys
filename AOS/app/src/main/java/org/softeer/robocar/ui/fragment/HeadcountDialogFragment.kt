package org.softeer.robocar.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.softeer.robocar.R

class HeadcountDialogFragment : DialogFragment(R.layout.fragment_headcount_dialog) {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(R.layout.fragment_headcount_dialog)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}