/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.settings;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.WidgetScreen;
import haoirdevelopment.haoirclient.utils.misc.IChangeable;
import haoirdevelopment.haoirclient.utils.misc.ICopyable;
import haoirdevelopment.haoirclient.utils.misc.ISerializable;
import net.minecraft.world.level.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}
