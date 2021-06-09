package com.example.y.achievement

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.Exception

class DeleteDialogFragment : DialogFragment() {


    //EditActivityへcolorIdを渡すためのインターフェース
    interface DialogListener{
        fun onDialogIsDeleteReceive(dialog: DialogFragment)
    }
    private var listener:DialogListener? = null



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            builder.setTitle("確認")
                .setMessage("アチーブメントを削除しますか?")
                .setPositiveButton("OK"
                ) { _, _ ->
                    //EditActivityへ削除命令を送る
                    listener?.onDialogIsDeleteReceive(this)
                }
                .setNegativeButton("キャンセル"
                ) { _, _ ->
                    //do nothing
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        }catch (e: Exception){
            Toast.makeText(this.context, "Error! Can not find listener", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}