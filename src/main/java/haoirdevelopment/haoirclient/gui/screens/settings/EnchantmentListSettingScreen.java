/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.screens.settings;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.screens.settings.base.DynamicRegistryListSettingScreen;
import haoirdevelopment.haoirclient.gui.widgets.WWidget;
import haoirdevelopment.haoirclient.settings.Setting;
import haoirdevelopment.haoirclient.utils.misc.Names;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;

public class EnchantmentListSettingScreen extends DynamicRegistryListSettingScreen<Enchantment> {
    public EnchantmentListSettingScreen(GuiTheme theme, Setting<Set<ResourceKey<Enchantment>>> setting) {
        super(theme, "Select Enchantments", setting, setting.get(), Registries.ENCHANTMENT);
    }

    @Override
    protected WWidget getValueWidget(ResourceKey<Enchantment> value) {
        return theme.label(Names.get(value));
    }

    @Override
    protected String[] getValueNames(ResourceKey<Enchantment> value) {
        return new String[]{
            Names.get(value),
            value.identifier().toString()
        };
    }
}
