/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import haoirdevelopment.haoirclient.systems.modules.Modules;
import haoirdevelopment.haoirclient.systems.modules.render.HandView;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static haoirdevelopment.haoirclient.HaOirClient.mc;

@Mixin(LivingEntity.SwingState.class)
public abstract class LivingEntitySwingStateMixin {
    @ModifyExpressionValue(method = "getAnimation", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity$SwingState;animation:F", opcode = Opcodes.GETFIELD))
    private float getHandSwingDuration(float original) {
        if ((Object) this != mc.player) return original;

        return Modules.get().get(HandView.class).isActive() && mc.options.getCameraType().isFirstPerson() ? Modules.get().get(HandView.class).swingSpeed.get().floatValue() : original;
    }
}
