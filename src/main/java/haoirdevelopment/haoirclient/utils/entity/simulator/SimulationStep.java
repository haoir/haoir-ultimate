/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.utils.entity.simulator;

import net.minecraft.world.phys.HitResult;

public class SimulationStep {
    public static final SimulationStep MISS = new SimulationStep(true);

    public boolean shouldStop;
    public HitResult[] hitResults;

    public SimulationStep(boolean stop, HitResult... hitResults) {
        this.shouldStop = stop;
        this.hitResults = hitResults;
    }
}
