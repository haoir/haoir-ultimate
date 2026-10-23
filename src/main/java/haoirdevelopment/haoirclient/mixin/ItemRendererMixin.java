/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import haoirdevelopment.haoirclient.systems.modules.Modules;
import haoirdevelopment.haoirclient.systems.modules.render.NoRender;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFeatureRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyExpressionValue(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ItemFeatureRenderer$Submit;foilType()Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;"))
    private ItemStackRenderState.FoilType modifyEnchant(ItemStackRenderState.FoilType foilType) {
        if (Modules.get().get(NoRender.class).noEnchantGlint()) {
            return ItemStackRenderState.FoilType.NONE;
        }

        return foilType;
    }
}
