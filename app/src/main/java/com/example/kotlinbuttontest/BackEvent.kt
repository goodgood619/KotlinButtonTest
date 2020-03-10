package com.example.kotlinbuttontest

import android.content.Context
import android.content.DialogInterface
import android.database.Observable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.R


class BackEvent : AppCompatActivity() {


    fun askMainFinish(context: Context) {
        var show = AlertDialog.Builder(context)
        show.setTitle("종료창")
        show.setMessage("종료하시겠습니까?")
        show.setIcon(R.drawable.g)
        fun toast_p() {
            Toast.makeText(context, "예를 눌렀습니다", Toast.LENGTH_LONG).show()
        }

        fun toast_n() {
            Toast.makeText(context, "아니오를 눌렀습니다", Toast.LENGTH_LONG).show()
        }

        var dialog_listner = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> toast_p()
                    DialogInterface.BUTTON_NEGATIVE -> toast_n()
                }
            }
        }

        show.setPositiveButton("Yes", dialog_listner)
        show.setNegativeButton("No", dialog_listner)
        show.show()
    }
}