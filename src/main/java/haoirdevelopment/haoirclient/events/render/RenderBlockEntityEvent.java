/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.events.render;

import haoirdevelopment.haoirclient.events.Cancellable;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class RenderBlockEntityEvent extends Cancellable {
    private static final RenderBlockEntityEvent INSTANCE = new RenderBlockEntityEvent();

    public BlockEntityRenderState blockEntityState;

    public static RenderBlockEntityEvent get(BlockEntityRenderState blockEntityState) {
        INSTANCE.setCancelled(false);
        INSTANCE.blockEntityState = blockEntityState;
        return INSTANCE;
    }
}
