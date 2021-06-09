package com.example.y.achievement

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //dialogをセッティング
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            builder.setTitle("確認")
                .setMessage("アチーブメントを削除しますか?")
                .setPositiveButton("OK"
                ) { _, _ ->
                    //Todo: delete the achievement
                }
                .setNegativeButton("キャンセル"
                ) { _, _ ->
                    //do nothing
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }


}