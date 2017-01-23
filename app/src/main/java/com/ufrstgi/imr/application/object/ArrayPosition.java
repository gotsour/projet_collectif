package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Duduf on 22/01/2017.
 */

public class ArrayPosition {

    @SerializedName("positions")
    private ArrayList<PositionColis> positions;

    public ArrayPosition(ArrayList<PositionColis> positions) {
        this.positions = positions;
    }

    public ArrayList<PositionColis> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<PositionColis> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "ArrayPosition{" +
                "positions=" + positions +
                '}';
    }
}
