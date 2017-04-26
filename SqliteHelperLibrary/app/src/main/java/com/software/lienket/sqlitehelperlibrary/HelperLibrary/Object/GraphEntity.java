package com.software.lienket.sqlitehelperlibrary.HelperLibrary.Object;

import java.util.ArrayList;

/**
 * Created by KIEN on 4/26/2017.
 */

public class GraphEntity extends Entity {
    private ArrayList<GraphEntity> belongsTo;

    public ArrayList<GraphEntity> getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(ArrayList<GraphEntity> belongsTo) {
        this.belongsTo = belongsTo;
    }
}
