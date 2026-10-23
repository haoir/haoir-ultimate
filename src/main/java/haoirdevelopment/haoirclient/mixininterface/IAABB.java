/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixininterface;

import net.minecraft.core.BlockPos;

public interface IAABB {
    void meteor$expand(double v);

    void meteor$set(double x1, double y1, double z1, double x2, double y2, double z2);

    default void meteor$set(BlockPos pos) {
        meteor$set(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }
}
