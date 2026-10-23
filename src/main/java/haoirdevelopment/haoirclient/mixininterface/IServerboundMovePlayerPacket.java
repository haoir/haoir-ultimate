/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixininterface;

public interface IServerboundMovePlayerPacket {
    int meteor$getTag();

    void meteor$setTag(int tag);
}
