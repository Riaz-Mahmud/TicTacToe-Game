package com.backdoor.tictactoe.models

data class GameMoves constructor(val gameMoves: ArrayList<IntArray>) {
    @JvmName("getGameMoves1")
    fun getGameMoves(): ArrayList<IntArray> {
        return gameMoves
    }
}