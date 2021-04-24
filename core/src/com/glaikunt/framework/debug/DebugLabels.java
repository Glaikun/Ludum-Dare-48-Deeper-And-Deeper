package com.glaikunt.framework.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.glaikunt.framework.Display;

public class DebugLabels {

    private Label debugProfilerLabel;
    private Label debugGCLabel;
    private Label debugPlayerLabel;
    private Label versionLabel;
    private BitmapFont font;
    private final Runtime runtime;

    public DebugLabels() {
        font = new BitmapFont();

        debugPlayerLabel = new Label("debugPlayerLabel", new Label.LabelStyle(font, Color.WHITE));

        debugGCLabel = new Label("debugGCLabel", new Label.LabelStyle(font, Color.YELLOW));
        debugGCLabel.setPosition(debugGCLabel.getX(), debugGCLabel.getY()+16);

        debugProfilerLabel = new Label("debugProfilerLabel", new Label.LabelStyle(font, Color.GREEN));
        debugProfilerLabel.setPosition(debugProfilerLabel.getX(), debugProfilerLabel.getY()+32);

        versionLabel = new Label(Display.VERSION, new Label.LabelStyle(font, Color.WHITE));
        versionLabel.setPosition((Gdx.graphics.getWidth()- versionLabel.getWidth()-5) + versionLabel.getX(), versionLabel.getY());

        runtime = Runtime.getRuntime();
    }

    public Label getDebugPlayerLabel() {
        return debugPlayerLabel;
    }

    public Label getDebugGCLabel() {
        return debugGCLabel;
    }

    public Label getDebugProfilerLabel() {
        return debugProfilerLabel;
    }

    public Label getVersionLabel() {
        return versionLabel;
    }

    public void update(GLProfiler glProfiler) {
        debugGCLabel.setText(getMemInUse()+" GC: "+getGcString());

        debugProfilerLabel.setText("GL: ["
                +" FPS: "+getFPS()
                +" DrawCalls: "+glProfiler.getDrawCalls()
                +" ShaderSwx: "+glProfiler.getShaderSwitches()
                +" TxBinds: "+glProfiler.getTextureBindings()
                +" Verts: "+glProfiler.getVertexCount().count
                +" Calls: "+glProfiler.getCalls()
                +"]");
    }

    private int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }

    private String getGcString() {
        return GcLogUtil.getGc() + " " + GcLogUtil.getLastMs() + "ms " + GcLogUtil.getLastMBytes() + "MB";
    }

    private String getMemInUse() {
        return humanReadableByteCount(runtime.totalMemory() - runtime.freeMemory(), false);
    }

    /**
     * Author http://stackoverflow.com/users/276052/aioobe
     *
     * @param bytes
     * @param si
     * @return String human readable conversion
     */
    public static String humanReadableByteCount(final long bytes, final boolean si) {
        final int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
