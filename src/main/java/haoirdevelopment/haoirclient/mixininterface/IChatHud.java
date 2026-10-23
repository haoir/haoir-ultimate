/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixininterface;

import net.minecraft.network.chat.Component;

public interface IChatHud {
    void meteor$add(Component message, int id);
}
