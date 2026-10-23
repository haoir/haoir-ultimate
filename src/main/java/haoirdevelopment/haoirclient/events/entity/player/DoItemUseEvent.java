/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.events.entity.player;

import haoirdevelopment.haoirclient.events.Cancellable;

/**
 * Some of our other injections coming from {@link net.minecraft.client.Minecraft#startUseItem()}
 * (e.g. InteractItemEvent) are called twice because the method loops over the Mainhand and the Offhand. This event is
 * only called once, before any interaction logic is called.
 */
public class DoItemUseEvent extends Cancellable {
    private static final DoItemUseEvent INSTANCE = new DoItemUseEvent();

    public static DoItemUseEvent get() {
        return INSTANCE;
    }
}
