package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
//every piece has a piece position
    protected final int piecePosition;
//piece will either be black or white
    //it takes a position or an alliance
    //has an alliance
    protected final Alliance pieceAlliance;
    //declare Alliance as an enum
    protected final boolean isFirstMove;
    private final int cachedHashCode;
    Piece(final PieceType pieceType,
            final int piecePosition ,
          final Alliance pieceAlliance,
          final boolean isFirstMove){
        this.pieceType=pieceType;
        this.pieceAlliance=pieceAlliance;
        this.piecePosition=piecePosition;
        this.isFirstMove=isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceAlliance.hashCode();
        result = 31* result + this.piecePosition;
        result= 31* result + (this.isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){// we want object equality not reference equality
        if(this==other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition== otherPiece.getPiecePosition() && pieceType==otherPiece.getPieceType() &&
                pieceAlliance==otherPiece.getPieceAlliance() && isFirstMove==otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public  int getPiecePosition(){
        return this.piecePosition;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
    //alliance useful for players
    //method for calculating illegal moves of a piece
    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }


    public abstract Collection<Move> calculateLegalMoves(final Board board);
    //all piece were going tocalculateLegalMoves create will override CalLillegalmoves moves
    //takes a board, for a piece calc legal moves
    //concrete piece we will override this method , do right move
    //Diff between list and set
    //set-no duplicate,unordered
    //list-opposite
    //The Collection in Java is a framework that provides an architecture to store and manipulate the group of objects.

    public abstract Piece movePiece(Move move);



    public enum PieceType{
        PAWN(100,"P"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT(300,"N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP(300,"B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK(500,"R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN(900,"Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING(10000,"K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };
        private String pieceName;
        private int pieceValue;

        PieceType(final int pieceValue, final String pieceName){

            this.pieceName = pieceName;
            this.pieceValue=pieceValue;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public int getPieceValue(){
            return this.pieceValue;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }
}
