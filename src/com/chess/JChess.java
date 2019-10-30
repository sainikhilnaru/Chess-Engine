package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

public class JChess {

    public static void main(String[] args){
        Board board = Board.createStandardBoard();

        System.out.println(board);
        //System.out.println(Tile.EMPTY_TILES_CACHE);
        //Immutable Map of all chess tiles integer, Empty tile pairs where integer is key
        //System.out.println(board.currentPlayer().getOpponent().getLegalMoves().size());
        Table.get().show();
    }

}
