package haoirdevelopment.haoirclient.systems.modules.haoir;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class HaOirPositionManager {
    private static double x, y, z;
    private static boolean onground;

    public static void updatePosition() {
        if (Minecraft.getInstance().player != null) {
            x = Minecraft.getInstance().player.getX(); y = Minecraft.getInstance().player.getY(); z = Minecraft.getInstance().player.getZ();
            onground = Minecraft.getInstance().player.onGround();
        }
    }
    public static void restorePosition() {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.setPos(x, y, z);
            Minecraft.getInstance().player.setOnGround(onground);
        }
    }
    public static void setPositionPacket(double px, double py, double pz, boolean pOnGround, boolean setPos, boolean noLagBack) {
        if (Minecraft.getInstance().player == null) return;
        Minecraft.getInstance().player.connection.send(new ServerboundMovePlayerPacket.Pos(px, py, pz, pOnGround, false));
        if (setPos) {
            Minecraft.getInstance().player.setPos(px, py, pz);
            if (noLagBack) updatePosition();
        }
    }
}
