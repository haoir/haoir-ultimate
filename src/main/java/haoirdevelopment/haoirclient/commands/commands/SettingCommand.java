/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import haoirdevelopment.haoirclient.commands.Command;
import haoirdevelopment.haoirclient.commands.arguments.ModuleArgumentType;
import haoirdevelopment.haoirclient.commands.arguments.SettingArgumentType;
import haoirdevelopment.haoirclient.commands.arguments.SettingValueArgumentType;
import haoirdevelopment.haoirclient.gui.GuiThemes;
import haoirdevelopment.haoirclient.gui.WidgetScreen;
import haoirdevelopment.haoirclient.gui.tabs.TabScreen;
import haoirdevelopment.haoirclient.gui.tabs.Tabs;
import haoirdevelopment.haoirclient.gui.tabs.builtin.ConfigTab;
import haoirdevelopment.haoirclient.gui.tabs.builtin.HudTab;
import haoirdevelopment.haoirclient.settings.Setting;
import haoirdevelopment.haoirclient.systems.config.Config;
import haoirdevelopment.haoirclient.systems.modules.Module;
import haoirdevelopment.haoirclient.utils.Utils;
import haoirdevelopment.haoirclient.utils.player.ChatUtils;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

public class SettingCommand extends Command {
    public SettingCommand() {
        super("settings", "Allows you to view and change module settings.", "s");
    }

    @Override
    public void build(LiteralArgumentBuilder<ClientSuggestionProvider> builder) {
        // Open hud screen
        builder.then(
            literal("hud")
                .executes(_ -> {
                    TabScreen screen = Tabs.get(HudTab.class).createScreen(GuiThemes.get());
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // Open config screen
        builder.then(
            literal("config")
                .executes(_ -> {
                    TabScreen screen = Tabs.get(ConfigTab.class).createScreen(GuiThemes.get());
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // View or change config settings
        builder.then(
            literal("config").then(
                argument("setting", SettingArgumentType.create())
                    .executes(context -> {
                        // Get setting value
                        Setting<?> setting = SettingArgumentType.get(context, Config.get().settings);

                        ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) is (highlight)%s(default).", setting.title, setting.get());

                        return SINGLE_SUCCESS;
                    }).suggests((_, suggestionsBuilder) ->
                        SettingArgumentType.listSuggestions(suggestionsBuilder, Config.get().settings)
                    )
                    .then(
                        argument("value", SettingValueArgumentType.create())
                            .executes(context -> {
                                // Set setting value
                                Setting<?> setting = SettingArgumentType.get(context, Config.get().settings);
                                String value = SettingValueArgumentType.get(context);

                                if (setting.parse(value)) {
                                    ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) changed to (highlight)%s(default).", setting.title, value);
                                }

                                return SINGLE_SUCCESS;
                            }).suggests((context, suggestionsBuilder) ->
                                SettingValueArgumentType.listSuggestions(context, suggestionsBuilder, Config.get().settings)
                            )
                    )
            )
        );

        // Open module screen
        builder.then(
            argument("module", ModuleArgumentType.create())
                .executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    WidgetScreen screen = GuiThemes.get().moduleScreen(module);
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // View or change module settings
        builder.then(
            argument("module", ModuleArgumentType.create())
                .then(
                    argument("setting", SettingArgumentType.create())
                        .executes(context -> {
                            // Get setting value
                            Setting<?> setting = SettingArgumentType.get(context);

                            ModuleArgumentType.get(context).info("Setting (highlight)%s(default) is (highlight)%s(default).", setting.title, setting.get());

                            return SINGLE_SUCCESS;
                        })
                        .then(
                            argument("value", SettingValueArgumentType.create())
                                .executes(context -> {
                                    // Set setting value
                                    Setting<?> setting = SettingArgumentType.get(context);
                                    String value = SettingValueArgumentType.get(context);

                                    if (setting.parse(value)) {
                                        ModuleArgumentType.get(context).info("Setting (highlight)%s(default) changed to (highlight)%s(default).", setting.title, value);
                                    }

                                    return SINGLE_SUCCESS;
                                })
                        )
                )
        );
    }
}
