/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.hud.screens;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.WindowScreen;
import haoirdevelopment.haoirclient.gui.widgets.containers.WHorizontalList;
import haoirdevelopment.haoirclient.gui.widgets.input.WTextBox;
import haoirdevelopment.haoirclient.gui.widgets.pressable.WPlus;
import haoirdevelopment.haoirclient.systems.hud.Hud;
import haoirdevelopment.haoirclient.systems.hud.HudElementInfo;
import haoirdevelopment.haoirclient.utils.Utils;
import org.jspecify.annotations.Nullable;

public class HudElementPresetsScreen extends WindowScreen {
    private final HudElementInfo<?> info;
    private final int x, y;

    private final WTextBox searchBar;
    private HudElementInfo<?>.@Nullable Preset firstPreset;

    public HudElementPresetsScreen(GuiTheme theme, HudElementInfo<?> info, int x, int y) {
        super(theme, "Select preset for " + info.title);

        this.info = info;
        this.x = x + 9;
        this.y = y;

        searchBar = theme.textBox("");
        searchBar.action = () -> {
            clear();
            initWidgets();
        };

        enterAction = () -> {
            if (firstPreset == null) return;
            Hud.get().add(firstPreset, x, y);
            onClose();
        };
    }

    @Override
    public void initWidgets() {
        firstPreset = null;

        // Search bar
        add(searchBar).expandX();
        searchBar.setFocused(true);

        // Presets
        for (HudElementInfo<?>.Preset preset : info.presets) {
            if (!Utils.searchTextDefault(preset.title, searchBar.get(), false)) continue;

            WHorizontalList l = add(theme.horizontalList()).expandX().widget();

            l.add(theme.label(preset.title));

            WPlus add = l.add(theme.plus()).expandCellX().right().widget();
            add.action = () -> {
                Hud.get().add(preset, x, y);
                onClose();
            };

            if (firstPreset == null) firstPreset = preset;
        }
    }
}
