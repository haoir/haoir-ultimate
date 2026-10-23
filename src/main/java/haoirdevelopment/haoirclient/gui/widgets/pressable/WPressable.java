/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.widgets.pressable;

import haoirdevelopment.haoirclient.gui.widgets.WWidget;
import net.minecraft.client.input.MouseButtonEvent;

import static com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_LEFT;
import static com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_RIGHT;

public abstract class WPressable extends WWidget {
    public Runnable action;

    protected boolean pressed;

    @Override
    public boolean onMouseClicked(MouseButtonEvent click, boolean doubled) {
        if (mouseOver && (click.button() == MOUSE_BUTTON_LEFT || click.button() == MOUSE_BUTTON_RIGHT))
            pressed = true;
        return pressed;
    }

    @Override
    public boolean onMouseReleased(MouseButtonEvent click) {
        if (pressed) {
            onPressed(click.button());
            if (action != null) action.run();

            pressed = false;
        }

        return false;
    }

    protected void onPressed(int button) {
    }
}
