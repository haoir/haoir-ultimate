/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.modules.player;

import haoirdevelopment.haoirclient.events.world.TickEvent;
import haoirdevelopment.haoirclient.systems.modules.Categories;
import haoirdevelopment.haoirclient.systems.modules.Module;
import haoirdevelopment.haoirclient.utils.player.FindItemResult;
import haoirdevelopment.haoirclient.utils.player.InvUtils;
import haoirdevelopment.haoirclient.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.item.Items;

public class EXPThrower extends Module {
    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles from your hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult exp = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);
        if (!exp.found()) return;

        Rotations.rotate(mc.player.getYRot(), 90, () -> {
            if (exp.getHand() != null) {
                mc.gameMode.useItem(mc.player, exp.getHand());
            } else {
                InvUtils.swap(exp.slot(), true);
                mc.gameMode.useItem(mc.player, exp.getHand());
                InvUtils.swapBack();
            }
        });
    }
}
