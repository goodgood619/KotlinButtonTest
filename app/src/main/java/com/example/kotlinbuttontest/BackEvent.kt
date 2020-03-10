package com.example.kotlinbuttontest

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.R


class BackEvent : AppCompatActivity() {

    fun askMainFinish(context: Activity) {
        val show = AlertDialog.Builder(context)
        show.setTitle("종료창")
        show.setMessage("종료하시겠습니까?")
        show.setIcon(R.drawable.g)
        fun toastp() {
            Toast.makeText(context, "예를 눌렀습니다", Toast.LENGTH_LONG).show()
            context.finishAffinity()
        }

        fun toastn() {
            Toast.makeText(context, "아니오를 눌렀습니다", Toast.LENGTH_LONG).show()
        }

        val dialogListner = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> toastp()
                    DialogInterface.BUTTON_NEGATIVE -> toastn()
                }
            }
        }

        show.setPositiveButton("Yes", dialogListner)
        show.setNegativeButton("No", dialogListner)
        show.show()
    }

}