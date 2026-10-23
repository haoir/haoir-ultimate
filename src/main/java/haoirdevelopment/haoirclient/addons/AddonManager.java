/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.addons;

import haoirdevelopment.haoirclient.HaOirClient;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;

import java.util.ArrayList;
import java.util.List;

public class AddonManager {
    public static final List<HaOirAddon> ADDONS = new ArrayList<>();

    public static void init() {
        // HaOir pseudo addon
        {
            HaOirClient.ADDON = new HaOirAddon() {
                @Override
                public void onInitialize() {}

                @Override
                public String getPackage() {
                    return "haoirdevelopment.haoirclient";
                }

                @Override
                public String getWebsite() {
                    return "https://meteorclient.com";
                }

                @Override
                public GithubRepo getRepo() {
                    return new GithubRepo("HaOirDevelopment", "meteor-client");
                }

                @Override
                public String getCommit() {
                    String commit = HaOirClient.MOD_META.getCustomValue(HaOirClient.MOD_ID + ":commit").getAsString();
                    return commit.isEmpty() ? null : commit;
                }
            };

            ModMetadata metadata = FabricLoader.getInstance().getModContainer(HaOirClient.MOD_ID).get().getMetadata();

            HaOirClient.ADDON.name = metadata.getName();
            HaOirClient.ADDON.authors = new String[metadata.getAuthors().size()];
            if (metadata.containsCustomValue(HaOirClient.MOD_ID + ":color")) {
                HaOirClient.ADDON.color.parse(metadata.getCustomValue(HaOirClient.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                HaOirClient.ADDON.authors[i++] = author.getName();
            }

            ADDONS.add(HaOirClient.ADDON);
        }

        // Addons
        for (EntrypointContainer<HaOirAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("meteor", HaOirAddon.class)) {
            ModMetadata metadata = entrypoint.getProvider().getMetadata();
            HaOirAddon addon;
            try {
                addon = entrypoint.getEntrypoint();
            } catch (Throwable throwable) {
                throw new RuntimeException("Exception during addon init \"%s\".".formatted(metadata.getName()), throwable);
            }

            addon.name = metadata.getName();

            if (metadata.getAuthors().isEmpty()) throw new RuntimeException("Addon \"%s\" requires at least 1 author to be defined in it's fabric.mod.json. See https://fabricmc.net/wiki/documentation:fabric_mod_json_spec".formatted(addon.name));
            addon.authors = new String[metadata.getAuthors().size()];

            if (metadata.containsCustomValue(HaOirClient.MOD_ID + ":color")) {
                addon.color.parse(metadata.getCustomValue(HaOirClient.MOD_ID + ":color").getAsString());
            }

            int i = 0;
            for (Person author : metadata.getAuthors()) {
                addon.authors[i++] = author.getName();
            }

            ADDONS.add(addon);
        }
    }
}
