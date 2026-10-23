/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.screens.settings.base;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.widgets.containers.WHorizontalList;
import haoirdevelopment.haoirclient.gui.widgets.containers.WTable;
import haoirdevelopment.haoirclient.gui.widgets.input.WTextBox;
import haoirdevelopment.haoirclient.settings.Setting;
import net.minecraft.IdentifierException;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public abstract class DynamicRegistryListSettingScreen<T> extends CollectionListSettingScreen<ResourceKey<T>> {
    protected final ResourceKey<Registry<T>> registryKey;

    public DynamicRegistryListSettingScreen(GuiTheme theme, String title, Setting<?> setting, Collection<ResourceKey<T>> collection, ResourceKey<Registry<T>> registryKey) {
        super(theme, title, setting, collection, createUniverse(collection, registryKey));

        this.registryKey = registryKey;
    }

    private static <T> Iterable<ResourceKey<T>> createUniverse(Collection<ResourceKey<T>> collection, ResourceKey<Registry<T>> registryKey) {
        Set<ResourceKey<T>> set = new ReferenceOpenHashSet<>(collection);

        Optional.ofNullable(Minecraft.getInstance().getConnection())
            .map(networkHandler -> (HolderLookup.Provider) networkHandler.registryAccess())
            .orElseGet(VanillaRegistries::createWorldLookup)
            .lookup(registryKey)
            .ifPresent(registry -> registry.listElementIds().forEach(set::add));

        return set;
    }

    @Override
    protected void postWidgets(WTable left, WTable right) {
        if (!left.cells.isEmpty()) {
            left.add(theme.horizontalSeparator()).expandX();
            left.row();
        }

        WHorizontalList manualEntry = left.add(theme.horizontalList()).expandX().widget();
        WTextBox textBox = manualEntry.add(theme.textBox("minecraft:")).expandX().minWidth(120d).widget();
        manualEntry.add(theme.plus()).expandCellX().right().widget().action = () -> {
            String entry = textBox.get().trim();
            try {
                Identifier id = entry.contains(":") ? Identifier.parse(entry) : Identifier.withDefaultNamespace(entry);
                addValue(ResourceKey.create(registryKey, id));
            } catch (IdentifierException _) {
            }
        };
    }
}
