/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.themes.meteor;

import haoirdevelopment.haoirclient.gui.renderer.GuiRenderer;
import haoirdevelopment.haoirclient.gui.utils.BaseWidget;
import haoirdevelopment.haoirclient.gui.widgets.WWidget;
import haoirdevelopment.haoirclient.utils.render.color.Color;

public interface HaOirWidget extends BaseWidget {
    default HaOirGuiTheme theme() {
        return (HaOirGuiTheme) getTheme();
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, Color outlineColor, Color backgroundColor) {
        HaOirGuiTheme theme = theme();
        double s = theme.scale(2);

        renderer.quad(widget.x + s, widget.y + s, widget.width - s * 2, widget.height - s * 2, backgroundColor);

        renderer.quad(widget.x, widget.y, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + widget.height - s, widget.width, s, outlineColor);
        renderer.quad(widget.x, widget.y + s, s, widget.height - s * 2, outlineColor);
        renderer.quad(widget.x + widget.width - s, widget.y + s, s, widget.height - s * 2, outlineColor);
    }

    default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
        HaOirGuiTheme theme = theme();
        renderBackground(renderer, widget, theme.outlineColor.get(pressed, mouseOver), theme.backgroundColor.get(pressed, mouseOver));
    }
}
