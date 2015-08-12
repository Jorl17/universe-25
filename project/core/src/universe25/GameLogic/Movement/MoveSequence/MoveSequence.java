package universe25.GameLogic.Movement.MoveSequence;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 11/08/15.
 */
public class MoveSequence {
    private ArrayList<Vector2> moves;

    public MoveSequence() {
        this.moves = new ArrayList<>();
    }

    public MoveSequence(ArrayList<Vector2> moves) {
        this.moves = new ArrayList<>();
        addMoves(moves);
    }

    public MoveSequence(Vector2... moves) {
        this.moves = new ArrayList<>();
        addMoves(moves);
    }

    public void addMoves(ArrayList<Vector2> moves) {
        this.moves.addAll(moves);
    }

    public void addMoves(Vector2... moves) {
        Collections.addAll(this.moves, moves);
    }

    public void addMove(Vector2 moves) {
        this.moves.add(moves);
    }

    public Vector2 getMoveAt(int index) {
        return moves.get(index);
    }

    public void clearMoves() {
        moves.clear();
    }

    public int numMoves() {
        return this.moves.size();
    }

    public ArrayList<Vector2> getMovesCopy() {
        return (ArrayList<Vector2>) this.moves.clone();
    }

    public ArrayList<Vector2> getMoves() {
        return this.moves;
    }

    public void reverse() {
        Collections.reverse(moves);
    }
}
