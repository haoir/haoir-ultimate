/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.events.world;

public class AmbientOcclusionEvent {
    private static final AmbientOcclusionEvent INSTANCE = new AmbientOcclusionEvent();

    public float lightLevel = -1;

    public static AmbientOcclusionEvent get() {
        INSTANCE.lightLevel = -1;
        return INSTANCE;
    }
}
