/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.utils.misc.input;

import com.mojang.blaze3d.platform.InputConstants;

public enum KeyAction {
    Press,
    Repeat,
    Release;

    public static KeyAction get(int action) {
        return switch (action) {
            case InputConstants.PRESS -> Press;
            case InputConstants.RELEASE -> Release;
            default -> Repeat;
        };
    }
}
