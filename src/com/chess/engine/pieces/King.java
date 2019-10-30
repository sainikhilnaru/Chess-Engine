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

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES= {-9,-8,-7,-1,1,7,8,9};

    public King(final Alliance pieceAlliance,final int piecePosition)
    {
        super(PieceType.KING,piecePosition, pieceAlliance,true);
    }

    public King(final Alliance pieceAlliance,final int piecePosition,final boolean isFirstMove)
    {
        super(PieceType.KING,piecePosition, pieceAlliance,isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(isFirstCollumnExclusion(this.piecePosition, currentCandidateOffset)||
            isEigthCollumnExclusion(this.piecePosition, currentCandidateOffset)){
                continue;
            }

            if(BoardUtils.isValidCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
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
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    private static boolean isFirstCollumnExclusion(final int currentPosition, final int candidateOffset){
        //pieces sitting on first collumn of chess board
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset==-9|| candidateOffset==-1||
                candidateOffset==7);
        //introduce an array of bools in Boardutils
        //2d array of bool values
    }

    private static boolean isEigthCollumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset==-7|| candidateOffset==1 || candidateOffset== 1 || candidateOffset == 9 );
    }
}
