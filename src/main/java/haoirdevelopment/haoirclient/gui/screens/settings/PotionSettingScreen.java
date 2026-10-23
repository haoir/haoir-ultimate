/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.screens.settings;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.WindowScreen;
import haoirdevelopment.haoirclient.gui.widgets.containers.WTable;
import haoirdevelopment.haoirclient.gui.widgets.pressable.WButton;
import haoirdevelopment.haoirclient.settings.PotionSetting;
import haoirdevelopment.haoirclient.utils.misc.MyPotion;
import net.minecraft.client.resources.language.I18n;

public class PotionSettingScreen extends WindowScreen {
    private final PotionSetting setting;

    public PotionSettingScreen(GuiTheme theme, PotionSetting setting) {
        super(theme, "Select Potion");

        this.setting = setting;
    }

    @Override
    public void initWidgets() {
        WTable table = add(theme.table()).expandX().widget();

        for (MyPotion potion : MyPotion.values()) {
            var stack = potion.potion.get();
            table.add(theme.itemWithLabel(stack, I18n.get(stack.getItem().getDescriptionId())));

            WButton select = table.add(theme.button("Select")).widget();
            select.action = () -> {
                setting.set(potion);
                onClose();
            };

            table.row();
        }
    }
}
