package com.example.y.achievement

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.Exception

class AchieveDialogFragment: DialogFragment() {


    //EditActivityへ命令を渡すためのインターフェース
    interface DialogListener{
        fun onDialogAchieveReceive(dialog: DialogFragment)
    }
    private var listener:DialogListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            //EditActivityから渡されたisAchievedの値を受け取る
            val isAchieved: Boolean = arguments?.getBoolean("isAchieved") ?: false

            //受け取ったisAchievedの真偽によって、メッセージを変える
            val message = if (isAchieved){
                "アチーブメントを未達成に変更しますか?"
            }else{
                "アチーブメントを達成済みに変更しますか?"
            }

            //Dialogのパーツを生成
            val builder = AlertDialog.Builder(it, R.style.CustomDialog)
            builder.setTitle("確認")
                .setMessage(message)
                .setPositiveButton("OK"
                ) { _, _ ->
                    //EditActivityへ命令を送る
                    listener?.onDialogAchieveReceive(this)
                }
                .setNegativeButton("キャンセル"
                ) { _, _ ->
                    //do nothing
                }
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