package com.example.y.bottomnav02

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.Exception

class ColorDialogFragment : DialogFragment() {

    //ActivityへcolorIdを渡すためのインターフェース
    interface DialogListener{
        fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Int)
    }
    private var listener:DialogListener? = null

    //配列や変数を宣言
    private val colors = arrayOf("white", "green", "blue", "purple", "orange", "gold")
    private var colorId: Int = 10


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //dialogをセッティング
        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            builder.setTitle("色を選択")
                    .setSingleChoiceItems(colors, -1){ _, which ->
                        colorId = which
                    }
                    .setPositiveButton("OK"
                    ) { _, _ ->
                        //ラジオボタンで色が選択されていたら、ホストActivityへcolorIdを渡す
                        if (colorId != 10) {
                            listener?.onDialogColorIdReceive(this, colorId)
                        }
                    }
                .setNegativeButton("キャンセル"
                ) { _, _ ->

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
            Toast.makeText(this.context, "Error! Can not find listener",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }






}