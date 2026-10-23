package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import haoirdevelopment.haoirclient.settings.*;
import meteordevelopment.orbit.EventHandler;
import haoirdevelopment.haoirclient.events.world.TickEvent;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public class HaOirAutoReconnect extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder().name("delay").defaultValue(3).min(1).sliderMax(30).build());
    private int timer = 0;

    public HaOirAutoReconnect() { 
        super(Categories.Player, "haoir-reconnect", "自动重连."); 
    }

    @Override
    public void onActivate() { timer = 0; }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.screen instanceof DisconnectedScreen) {
            timer++;
            if (timer >= delay.get() * 20) {
                timer = 0;
                info("正在尝试重新连接...");
                if (mc.getCurrentServer() != null) {
                    ServerAddress address = ServerAddress.parseString(mc.getCurrentServer().ip);
                    ServerData info = new ServerData(mc.getCurrentServer().name, address.getHost(), false);
                    net.minecraft.client.gui.screens.ConnectScreen.startConnecting(new JoinMultiplayerScreen(new TitleScreen()), mc, address, info, false, null);
                } else mc.setScreen(new TitleScreen());
            }
        } else timer = 0;
    }
}
