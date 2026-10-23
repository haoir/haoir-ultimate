/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.themes.meteor.widgets.pressable;

import haoirdevelopment.haoirclient.gui.themes.meteor.HaOirWidget;
import haoirdevelopment.haoirclient.gui.widgets.pressable.WFavorite;
import haoirdevelopment.haoirclient.utils.render.color.Color;

public class WHaOirFavorite extends WFavorite implements HaOirWidget {
    public WHaOirFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
