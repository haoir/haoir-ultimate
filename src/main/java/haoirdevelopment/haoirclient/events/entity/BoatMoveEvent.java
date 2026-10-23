/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.events.entity;

import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public class BoatMoveEvent {
    private static final BoatMoveEvent INSTANCE = new BoatMoveEvent();

    public AbstractBoat boat;

    public static BoatMoveEvent get(AbstractBoat entity) {
        INSTANCE.boat = entity;
        return INSTANCE;
    }
}
