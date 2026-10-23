/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.modules.world;

import haoirdevelopment.haoirclient.events.entity.player.BreakBlockEvent;
import haoirdevelopment.haoirclient.events.entity.player.PlaceBlockEvent;
import haoirdevelopment.haoirclient.settings.BoolSetting;
import haoirdevelopment.haoirclient.settings.Setting;
import haoirdevelopment.haoirclient.settings.SettingGroup;
import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.level.block.state.BlockState;

public class NoGhostBlocks extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> breaking = sgGeneral.add(new BoolSetting.Builder()
        .name("breaking")
        .description("Whether to apply for block breaking actions.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> placing = sgGeneral.add(new BoolSetting.Builder()
        .name("placing")
        .description("Whether to apply for block placement actions.")
        .defaultValue(true)
        .build()
    );

    public NoGhostBlocks() {
        super(Categories.World, "no-ghost-blocks", "Attempts to prevent ghost blocks arising.");
    }

    @EventHandler
    private void onBreakBlock(BreakBlockEvent event) {
        if (mc.isLocalServer() || !breaking.get()) return;

        event.cancel();

        BlockState blockState = mc.level.getBlockState(event.blockPos);
        blockState.getBlock().playerWillDestroy(mc.level, event.blockPos, blockState, mc.player);
    }

    @EventHandler
    private void onPlaceBlock(PlaceBlockEvent event) {
        if (!placing.get()) return;

        event.cancel();
    }
}
