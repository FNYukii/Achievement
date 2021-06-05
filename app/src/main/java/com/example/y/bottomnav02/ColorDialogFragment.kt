package com.example.y.bottomnav02

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.Exception

class ColorDialogFragment : DialogFragment() {

    //ActivityへcolorIdを渡すためのインターフェース
    public interface DialogListener{
        public fun onDialogColorIdReceive(dialog: DialogFragment, colorId: Long)
    }
    var listener:DialogListener? = null

    //配列や変数を宣言
    private val colors = arrayOf("white", "green", "blue", "purple", "orange", "gold")
    private var achievementId: Long? = null
    private var colorId: Long = 0L


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        //dialogを作る
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("色を選択")
                    .setSingleChoiceItems(colors, -1){ dialog, which ->
                        colorId = which.toLong()
                    }
                    .setPositiveButton("Yes",
                            DialogInterface.OnClickListener { dialog, id ->
                                listener?.onDialogColorIdReceive(this, colorId)
                            })
                    .setNegativeButton("No",
                            DialogInterface.OnClickListener { dialog, id ->

                            })
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