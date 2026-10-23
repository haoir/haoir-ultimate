package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.systems.modules.Modules;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public class HaOirBoatMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        HaOirBoatFly module = Modules.get().get(HaOirBoatFly.class);
        if (module != null && module.isActive()) {
            ci.cancel();
        }
    }

    @Inject(method = "positionRider", at = @At("HEAD"), cancellable = true)
    private void onPositionRider(Entity passenger, Entity.MoveFunction moveFunction, CallbackInfo ci) {
        HaOirBoatFly module = Modules.get().get(HaOirBoatFly.class);
        if (module != null && module.isActive()) {
            Boat boat = (Boat) (Object) this;
            passenger.setPos(boat.getX(), boat.getY() + 0.5, boat.getZ());
            ci.cancel();
        }
    }
}
