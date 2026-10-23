/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.themes.meteor.widgets;

import haoirdevelopment.haoirclient.gui.renderer.GuiRenderer;
import haoirdevelopment.haoirclient.gui.themes.meteor.HaOirWidget;
import haoirdevelopment.haoirclient.gui.widgets.WTooltip;
import haoirdevelopment.haoirclient.renderer.text.TextRenderer;
import haoirdevelopment.haoirclient.renderer.text.VanillaTextRenderer;

public class WHaOirTooltip extends WTooltip implements HaOirWidget {
    public WHaOirTooltip(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (TextRenderer.get() == VanillaTextRenderer.INSTANCE) {
            renderer.fill(this, theme().backgroundColor.get());
        } else {
            renderer.quad(this, theme().backgroundColor.get());
        }
    }
}
