/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.themes.meteor.widgets;

import haoirdevelopment.haoirclient.gui.renderer.GuiRenderer;
import haoirdevelopment.haoirclient.gui.themes.meteor.HaOirWidget;
import haoirdevelopment.haoirclient.gui.widgets.WLabel;

public class WHaOirLabel extends WLabel implements HaOirWidget {
    public WHaOirLabel(String text, boolean title) {
        super(text, title);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!text.isEmpty()) {
            renderer.text(text, x, y, color != null ? color : (title ? theme().titleTextColor.get() : theme().textColor.get()), title);
        }
    }
}
