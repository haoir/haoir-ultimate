package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import haoirdevelopment.haoirclient.settings.SettingGroup;
import haoirdevelopment.haoirclient.settings.Setting;
import meteordevelopment.orbit.EventHandler;
import haoirdevelopment.haoirclient.events.entity.player.AttackEntityEvent;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.StatusOnly;

public class HaOirCriticals extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> mode = sgGeneral.add(new Setting.Builder<Mode>()
            .name("mode")
            .description("暴击发包模式")
            .defaultValue(Mode.Mode2)
            .build()
    );

    public HaOirCriticals() {
        super(Categories.HaOir, "haoir-criticals", "Phobos 暴击发包");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (mc.player == null || mc.getConnection() == null) return;
        if (!mc.player.onGround()) return;
        if (mc.player.isInWater() || mc.player.isInLava()) return;
        if (mc.options.keyJump.isDown()) return;

        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        switch (mode.get()) {
            case Mode1 -> {
                sendPos(x, y + 0.1, z);
                sendPos(x, y, z);
            }
            case Mode2 -> {
                sendPos(x, y + 0.0625101, z);
                sendPos(x, y, z);
                sendPos(x, y + 1.1E-5, z);
                sendPos(x, y, z);
            }
            case Mode3 -> {
                sendPos(x, y + 0.0625101, z);
                sendPos(x, y, z);
                sendPos(x, y + 0.0125, z);
                sendPos(x, y, z);
            }
            case Mode4 -> {
                sendPos(x, y + 0.05, z);
                sendPos(x, y, z);
                sendPos(x, y + 0.03, z);
                sendPos(x, y, z);
            }
            case Mode5 -> {
                sendPos(x, y + 0.1625, z);
                sendPos(x, y, z);
                sendPos(x, y + 4.0E-6, z);
                sendPos(x, y, z);
                sendPos(x, y + 1.0E-6, z);
                sendPos(x, y, z);
                mc.getConnection().send(new StatusOnly(false, false));
            }
            case Bypass -> {
                sendPos(x, y + 0.11, z);
                sendPos(x, y + 0.1100013579, z);
                sendPos(x, y + 0.1100013579, z);
            }
        }
    }

    private void sendPos(double x, double y, double z) {
        mc.getConnection().send(new ServerboundMovePlayerPacket.Pos(x, y, z, false, false));
    }

    public enum Mode {
        Mode1, Mode2, Mode3, Mode4, Mode5, Bypass
    }
}
