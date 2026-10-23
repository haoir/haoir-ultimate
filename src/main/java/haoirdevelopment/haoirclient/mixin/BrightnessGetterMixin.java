/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixin;

import haoirdevelopment.haoirclient.systems.modules.Modules;
import haoirdevelopment.haoirclient.systems.modules.render.Fullbright;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LightCoordsUtil.BrightnessGetter.class)
public interface BrightnessGetterMixin {
    @ModifyVariable(method = "lambda$static$0", at = @At(value = "STORE"), name = "sky")
    private static int getLightmapCoordinatesModifySkyLight(int sky) {
        return Math.max(Modules.get().get(Fullbright.class).getLuminance(LightLayer.SKY), sky);
    }

    @ModifyVariable(method = "lambda$static$0", at = @At(value = "STORE"), name = "block")
    private static int getLightmapCoordinatesModifyBlockLight(int block) {
        return Math.max(Modules.get().get(Fullbright.class).getLuminance(LightLayer.BLOCK), block);
    }
}
