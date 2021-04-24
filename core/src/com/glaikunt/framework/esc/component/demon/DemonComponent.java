package com.glaikunt.framework.esc.component.demon;

import com.badlogic.ashley.core.Component;

import java.util.LinkedList;
import java.util.List;

public class DemonComponent implements Component {

    private List<Integer> hitDmg = new LinkedList<>();

    public List<Integer> getHitDmg() {
        return hitDmg;
    }
}
