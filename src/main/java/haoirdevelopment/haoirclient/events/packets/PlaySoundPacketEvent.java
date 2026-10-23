/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.events.packets;

import net.minecraft.network.protocol.game.ClientboundSoundPacket;

public class PlaySoundPacketEvent {

    private static final PlaySoundPacketEvent INSTANCE = new PlaySoundPacketEvent();

    public ClientboundSoundPacket packet;

    public static PlaySoundPacketEvent get(ClientboundSoundPacket packet) {
        INSTANCE.packet = packet;
        return INSTANCE;
    }
}
