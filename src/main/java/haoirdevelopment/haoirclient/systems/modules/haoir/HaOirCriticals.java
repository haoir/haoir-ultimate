package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.events.packets.PacketEvent;
import haoirdevelopment.haoirclient.settings.*;
import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class HaOirCriticals extends Module {
    public enum Mode { PACKET, BYPASS }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>().name("mode").defaultValue(Mode.PACKET).build());
    private final Setting<Integer> packets = sgGeneral.add(new IntSetting.Builder().name("packets").defaultValue(2).min(1).sliderMax(5).visible(() -> mode.get() == Mode.PACKET).build());
    private final Setting<Integer> desyncDelay = sgGeneral.add(new IntSetting.Builder().name("desync-delay").defaultValue(10).min(0).sliderMax(500).visible(() -> mode.get() == Mode.PACKET).build());
    
    private long lastPacketTime = 0;

    public HaOirCriticals() { 
        super(Categories.Combat, "haoir-criticals", "原味Phobos暴击."); 
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof ServerboundInteractPacket interactPacket) {
            if (interactPacket.getType() == ServerboundInteractPacket.ActionType.ATTACK) {
                if (mc.player == null || mc.level == null) return;
                if (!mc.player.onGround() || mc.options.keyJump.isDown() || mc.player.isInWater() || mc.player.isInLava()) return;
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastPacketTime < desyncDelay.get()) return;
                lastPacketTime = currentTime;

                double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
                
                if (mode.get() == Mode.PACKET) {
                    switch (packets.get()) {
                        case 1 -> { sendP(x, y + 0.1f, z); sendP(x, y, z); }
                        case 2 -> { sendP(x, y + 0.0625101, z); sendP(x, y, z); sendP(x, y + 1.1E-5, z); sendP(x, y, z); }
                        case 3 -> { sendP(x, y + 0.0625101, z); sendP(x, y, z); sendP(x, y + 0.0125, z); sendP(x, y, z); }
                        case 4 -> { sendP(x, y + 0.05, z); sendP(x, y, z); sendP(x, y + 0.03, z); sendP(x, y, z); }
                        case 5 -> { 
                            sendP(x, y + 0.1625, z); sendP(x, y, z); sendP(x, y + 4.0E-6, z); sendP(x, y, z); sendP(x, y + 1.0E-6, z); sendP(x, y, z); 
                            mc.getConnection().send(new ServerboundMovePlayerPacket.StatusOnly(false, false)); 
                        }
                    }
                } else {
                    sendP(x, y + 0.11, z); sendP(x, y + 0.1100013579, z); sendP(x, y + 0.1100013579, z);
                }
            }
        }
    }
    private void sendP(double x, double y, double z) { 
        mc.getConnection().send(new ServerboundMovePlayerPacket.Pos(x, y, z, false, false)); 
    }
}
