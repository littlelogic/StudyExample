package com.study.dataSave.serializable;

import java.io.Serializable;

public class Animal extends  AnimalParent implements Serializable {

    private String color = "init- black ";

    public Animal(){
        super();
        super.kind = "Animal";
        super.live = "forest";
    }

    @Override
    public String toString() {
        return "Animal [color=" + color +"]";
    }


}
