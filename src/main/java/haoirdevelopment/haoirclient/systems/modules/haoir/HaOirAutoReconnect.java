package haoirdevelopment.haoirclient.systems.modules.haoir;

import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import haoirdevelopment.haoirclient.settings.SettingGroup;
import haoirdevelopment.haoirclient.settings.Setting;
import haoirdevelopment.haoirclient.eventbus.EventHandler;
import haoirdevelopment.haoirclient.events.world.TickEvent;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public class HaOirAutoReconnect extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> delay = sgGeneral.add(new Setting.Builder<Integer>()
            .name("delay")
            .description("重连延迟（tick）")
            .defaultValue(60)
            .min(20).max(200)
            .build()
    );

    private int timer = 0;
    private boolean counting = false;

    public HaOirAutoReconnect() {
        super(Categories.HaOir, "haoir-auto-reconnect", "自动重连：断开后 3 秒重连");
    }

    @Override
    public void onActivate() {
        timer = 0;
        counting = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!(mc.screen instanceof DisconnectedScreen)) {
            counting = false;
            return;
        }

        if (!counting) {
            counting = true;
            timer = delay.get();
            return;
        }

        if (timer > 0) {
            timer--;
            return;
        }

        counting = false;

        ServerData serverData = mc.getCurrentServer();
        if (serverData == null) {
            mc.setScreen(new TitleScreen());
            return;
        }

        ServerAddress address = ServerAddress.parseString(serverData.ip);
        
        ServerData info = new ServerData(serverData.name, address.getHost(), ServerData.Type.OTHER);
        
        ConnectScreen.startConnecting(
                new TitleScreen(), 
                mc,
                address,
                info,
                false,
                null
        );
    }
}
