package com.glaikunt.framework.esc.component.text;

import com.badlogic.ashley.core.Component;
import com.glaikunt.framework.application.TickTimer;

import java.util.LinkedList;

public class TextQueueComponent implements Component {

    private final LinkedList<TextComponent> queue = new LinkedList<>();
    private TickTimer swapDelay;

    public LinkedList<TextComponent> getQueue() {
        return queue;
    }

    public TickTimer getSwapDelay() {
        return swapDelay;
    }

    public void setSwapDelay(TickTimer swapDelay) {
        this.swapDelay = swapDelay;
    }
}
