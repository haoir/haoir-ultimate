/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.widgets;

import haoirdevelopment.haoirclient.gui.widgets.pressable.WPressable;
import haoirdevelopment.haoirclient.utils.render.color.Color;
import net.minecraft.client.input.MouseButtonEvent;

public abstract class WLabel extends WPressable {
    public Color color;

    protected String text;
    protected boolean title;

    public WLabel(String text, boolean title) {
        this.text = text;
        this.title = title;
    }

    @Override
    protected void onCalculateSize() {
        width = theme.textWidth(text, text.length(), title);
        height = theme.textHeight(title);
    }

    @Override
    public boolean onMouseClicked(MouseButtonEvent click, boolean doubled) {
        if (action != null) return super.onMouseClicked(click, doubled);
        return false;
    }

    @Override
    public boolean onMouseReleased(MouseButtonEvent click) {
        if (action != null) return super.onMouseReleased(click);
        return false;
    }

    public void set(String text) {
        if (Math.round(theme.textWidth(text, text.length(), title)) != width) invalidate();

        this.text = text;
    }

    public String get() {
        return text;
    }

    public WLabel color(Color color) {
        this.color = color;
        return this;
    }
}
