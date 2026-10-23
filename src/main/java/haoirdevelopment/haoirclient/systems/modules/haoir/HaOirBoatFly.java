package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.events.world.TickEvent;
import haoirdevelopment.haoirclient.settings.*;
import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public class HaOirBoatFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    public final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder().name("speed").defaultValue(1.5).min(0).sliderMax(10).build());
    public final Setting<Boolean> vertical = sgGeneral.add(new BoolSetting.Builder().name("vertical").defaultValue(true).build());

    public HaOirBoatFly() { 
        super(Categories.Movement, "haoir-boatfly", "骗过 2b2t 的底层船飞."); 
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.level == null || mc.player.getVehicle() == null) return;
        var vehicle = mc.player.getVehicle();
        double yMotion = 0;
        if (vertical.get()) {
            if (mc.options.keyJump.isDown()) yMotion = speed.get() * 0.5;
            else if (mc.options.keyShift.isDown()) yMotion = -speed.get() * 0.5;
        }
        vehicle.setPos(vehicle.getX(), vehicle.getY() + yMotion, vehicle.getZ());
        vehicle.setDeltaMovement(0, 0, 0); 
    }
}
