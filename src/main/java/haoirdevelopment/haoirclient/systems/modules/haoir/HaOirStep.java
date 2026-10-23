package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.events.world.TickEvent;
import haoirdevelopment.haoirclient.settings.*;
import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class HaOirStep extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Boolean> vanilla = sgGeneral.add(new BoolSetting.Builder().name("vanilla").defaultValue(false).build());
    private final Setting<Integer> stepHeight = sgGeneral.add(new IntSetting.Builder().name("height").defaultValue(2).min(1).max(2).build());

    public HaOirStep() { 
        super(Categories.Movement, "haoir-step", "原味Phobos爬台阶."); 
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.level == null) return;
        if (mc.player.onGround() && !mc.player.isInWater() && !mc.player.isInLava() && mc.player.fallDistance == 0.0f && !mc.options.keyJump.isDown() && !mc.player.isShiftKeyDown()) {
            double rheight = mc.player.getBoundingBox().minY - mc.player.getY();
            if (rheight >= 0.625) {
                if (!vanilla.get()) { ncpStep(rheight); toggle(); }
            }
        }
    }

    private void ncpStep(double height) {
        double posX = mc.player.getX(), posZ = mc.player.getZ(), y = mc.player.getY();
        if (height < 1.1) {
            double first = 0.42, second = 0.75;
            if (height != 1.0) {
                first *= height; second *= height;
                if (first > 0.425) first = 0.425;
                if (second > 0.78) second = 0.78;
                if (second < 0.49) second = 0.49;
            }
            sendP(posX, y + first, posZ);
            if (y + second < y + height) sendP(posX, y + second, posZ);
        } else if (height < 1.6) {
            for (double off : new double[]{0.42, 0.33, 0.24, 0.083, -0.078}) { y += off; sendP(posX, y, posZ); }
        } else if (height < 2.1) {
            for (double off : new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869}) { sendP(posX, y + off, posZ); }
        } else {
            for (double off : new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907}) { sendP(posX, y + off, posZ); }
        }
    }
    private void sendP(double x, double y, double z) { 
        mc.getConnection().send(new ServerboundMovePlayerPacket.Pos(x, y, z, false, false)); 
    }
}
