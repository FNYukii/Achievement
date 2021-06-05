package com.example.y.bottomnav02

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class ColorDialogFragment : DialogFragment() {

    private val colors = arrayOf("white", "green", "blue", "purple", "orange", "gold")
    private var colorId: Int? = null
    private var achievementId: Long? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        achievementId = arguments?.getLong("achievementId", 10L)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("色を選択${achievementId}")
                    .setSingleChoiceItems(colors, -1){ dialog, which ->
                        colorId = which
                    }
                    .setPositiveButton("Yes",
                            DialogInterface.OnClickListener { dialog, id ->

                            })
                    .setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog, id ->

                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }






}