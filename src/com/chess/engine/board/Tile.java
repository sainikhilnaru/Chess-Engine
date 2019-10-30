package com.chess.engine.board;


import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
//make this class immutable
//exactly one state//use p.s.f keyword ZERO=new keyword(params)
//use existing instances by reusing classes

public abstract class Tile {//abstract- cant instantiate object
    //implememntaion like an interface
    protected final int tileCoordinate;//represent tile number
    //protected-only accessed by subclasses
    //final- meber field set, cannot be set again, once only
    // represent an abtract tile- empty or occupied tile
    private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();//understoodd
    //Map- Given a key and a value, you can store the value in a Map object. After the value is stored,
    // you can retrieve it by using its key.
    private static final Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();//hashmap data structure -has a key and value for each empty tile
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
        //java.util.Arrays.copyOf() method is in java.util.Arrays class. It copies the specified array,
        // truncating or padding with false (if necessary) so the copy has the specified length.
        //map container, after constructing emptytile map nobody c
        //an change it. emptyTile.get(63)-last tile
        //immutable map - t means that the content of the map are fixed or constant after declaration, that is, they are read-only.
        //An ImmutableMap does not allow null element either.
        //Since they are immutable, hence they can be passed over to third party libraries without any problem.
    }//undertood

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece ) : EMPTY_TILES_CACHE.get(tileCoordinate);
        //if piece not null, create new OccupiedTile otherwise get tile coordinate of cashed emptytile
        //A cache is an area of local memory that holds a copy of
        // frequently accessed data that is otherwise expensive to
        // get or compute. Examples of such data include a result
        // of a query to a database, a disk file or a report.

    }
    private Tile(final int tileCoordinate){	//constructor//create a new tile
        this.tileCoordinate=tileCoordinate; //construct a new
        //instance of a tile//assigns a tile coordinate
    }
    //public static void main(String[] args) {
    // Prints "Hello, World" to the terminal window.
    //  System.out.println("Hello, World");
    //}
    //testing if program works
    public abstract boolean isTileOccupied();
    //abstract means defined not in this class but subclass
    public abstract Piece getPiece();//get piece off given tile
    //piece is data type returned by getPiece
    //if empty tile , it will return null
    public int getTileCoordinate(){
        return this.tileCoordinate;
    }
    public static final class EmptyTile extends Tile {//final means constant

        //static -must be called before any object is created
        //it can be called without first instantiating the class
        //finals means no inheritence
        private EmptyTile(final int coordinate) {
            super(coordinate);//call superclass constructor
            //when  you create a child object,parent class instantiated
            //super-instance of direct superclass of current object
            //you cal call parent's constructor from subclass constructor
            //by using super, but first statement of constructor
            //*calls argument constructor of parent class
        }
        @Override
        public String toString(){
            return "-";
        }//overridden statement

        @Override
        public boolean isTileOccupied() {
            return false;//by definition it is empty
        }

        @Override
        public Piece getPiece() {
            return null;//no piece on empty tile to return
        }
    }
        public static final class OccupiedTile extends Tile{
         private final Piece pieceOnTile;//no way to reference this variable
         //outside without calling getpiece
            private OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
             super(tileCoordinate);
             this.pieceOnTile = pieceOnTile;
         }

            @Override
            public String toString(){
                return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                        getPiece().toString();
            }
             @Override
             public boolean isTileOccupied(){
                 return true;
             }
             @Override
             public Piece getPiece(){

                return this.pieceOnTile;
         }
        }
}
//logical packages
//llok up empty tilese in the cashe
/*
import java.util.*;
public class CollectionsDemo {

    public static void main(String[] args) {
        Map m1 = new HashMap();
        m1.put("Zara", "8");
        m1.put("Mahnaz", "31");
        m1.put("Ayan", "12");
        m1.put("Daisy", "14");

        System.out.println();
        System.out.println(" Map Elements");
        System.out.print("\t" + m1);
    }
}
    This will produce the following result −

        Output
        Map Elements
        {Daisy = 14, Ayan = 12, Zara = 8, Mahnaz = 31}


 */