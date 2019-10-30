package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES={-17,-15,-10,-6,6,10,15,17};//know the coordinates,store values
    //8 CANDIDATE MAX positions for any knight
    public Knight(final Alliance pieceAlliance,
                  final int piecePosition) {
        super(PieceType.KNIGHT,piecePosition, pieceAlliance,true);
    }//constructor

    public Knight(final Alliance pieceAlliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT,piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        //int candidateDestinationCoordinate;
        final List<Move> legalMoves= new ArrayList<>();
        for (final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){
            if(isFirstCollumnExclusion(this.piecePosition,currentCandidateOffset)||
                    isSecondCollumnExclusion(this.piecePosition,currentCandidateOffset)||
                    isSeventhCollumnExclusion(this.piecePosition,currentCandidateOffset)||
                    isEighthCollumnExclusion(this.piecePosition,currentCandidateOffset)){
                continue;
            }
            final int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
            if (BoardUtils.isValidCoordinate(candidateDestinationCoordinate) ){//is valid coordinate
                final Tile candidateDestinationTile=board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));//non-attacking legal move
                }
                else{
                    final Piece pieceAtDestination= candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance= pieceAtDestination.getPieceAlliance();
                    //whether it is black or white

                    if(this.pieceAlliance!= pieceAlliance){//not equal to current knight piece allinace
                        legalMoves.add(new MajorAttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));//attacking legal move
                    }//knight we are examining
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);//return all of the legal moves
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    //doesn't work for the if coordinate if outside board
    private static boolean isFirstCollumnExclusion(final int currentPosition, final int candidateOffset){
        //pieces sitting on first collumn of chess board
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-17|| candidateOffset==-10||
                candidateOffset==6|| candidateOffset==15);
        //introduce an array of bools in Boardutils
        //2d array of bool values
    }

    private static boolean isSecondCollumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset==-10|| candidateOffset==6);
    }

    private static boolean isSeventhCollumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset==-6|| candidateOffset==10);

    }

    private static boolean isEighthCollumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset==-15)|| candidateOffset==-6||
                candidateOffset==10|| candidateOffset==17;
    }

}