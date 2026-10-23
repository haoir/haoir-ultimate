/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixininterface;

public interface IClientboundExplodePacket {
    void meteor$setVelocityX(float velocity);

    void meteor$setVelocityY(float velocity);

    void meteor$setVelocityZ(float velocity);
}
