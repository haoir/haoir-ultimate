/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixin.sodium;

import haoirdevelopment.haoirclient.systems.modules.Modules;
import haoirdevelopment.haoirclient.systems.modules.render.Xray;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.SortBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ChunkBuilderMeshingTask.class, remap = false)
public abstract class SodiumChunkBuilderMeshingTaskMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, name = "sortBehavior")
    private static SortBehavior modifySortBehavior(SortBehavior sortBehavior) {
        return Modules.get().isActive(Xray.class) && Modules.get().get(Xray.class).opacity.get() > 0 && Modules.get().get(Xray.class).opacity.get() < 255 ? SortBehavior.STATIC : sortBehavior;
    }
}
