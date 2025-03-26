package com.example.tictoctoe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Color
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var board: Array<Array<Button>>
    private var playerX = true
    private var movesCount = 0
    private lateinit var tvStatus: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvStatus = findViewById(R.id.tvStatus)
        val btnReset: Button = findViewById(R.id.btnReset)

        board = arrayOf(
            arrayOf(findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3)),
            arrayOf(findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6)),
            arrayOf(findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9))
        )

        for (row in board) {
            for (button in row) {
                button.setOnClickListener { makeMove(it) }
            }
        }

        btnReset.setOnClickListener { resetGame() }
    }

    private fun makeMove(view: View) {
        val button = view as Button
        if (button.text.isNotEmpty()) return

        button.text = if (playerX) "X" else "O"
        button.setTextColor(if (playerX) Color.RED else Color.CYAN)
        movesCount++

        if (checkWin()) {
            tvStatus.text = "Player ${if (playerX) "X" else "O"} Wins!"
            animateWin()
            return
        }

        if (movesCount == 9) {
            tvStatus.text = "It's a Draw!"
            return
        }

        playerX = !playerX
        tvStatus.text = "Player ${if (playerX) "X" else "O"}'s Turn"
    }

    private fun checkWin(): Boolean {
        val boardText = Array(3) { row -> Array(3) { col -> board[row][col].text.toString() } }

        for (i in 0..2) {
            if (boardText[i][0] == boardText[i][1] && boardText[i][1] == boardText[i][2] && boardText[i][0].isNotEmpty()) return true
            if (boardText[0][i] == boardText[1][i] && boardText[1][i] == boardText[2][i] && boardText[0][i].isNotEmpty()) return true
            if (boardText[0][2] == boardText[1][1] && boardText[1][1] == boardText[2][0] && boardText[2][0].isNotEmpty()) return true

        }

        return boardText[0][0] == boardText[1][1] && boardText[1][1] == boardText[2][2] && boardText[0][0].isNotEmpty()
    }

    private fun animateWin() {
        Handler().postDelayed({ resetGame() }, 2000)
    }

    private fun resetGame() {
        for (row in board) {
            for (button in row) {
                button.text = ""
            }
        }
        playerX = true
        movesCount = 0
        tvStatus.text = "Player X's Turn"
    }
}