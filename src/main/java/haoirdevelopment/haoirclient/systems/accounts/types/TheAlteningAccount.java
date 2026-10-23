/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.accounts.types;

import com.mojang.authlib.Environment;
import com.mojang.authlib.services.MinecraftServicesDiscoveryService;
import com.mojang.util.UndashedUuid;
import haoirdevelopment.haoirclient.HaOirClient;
import haoirdevelopment.haoirclient.systems.accounts.Account;
import haoirdevelopment.haoirclient.systems.accounts.AccountType;
import haoirdevelopment.haoirclient.systems.accounts.TokenAccount;
import haoirdevelopment.haoirclient.utils.misc.NbtException;
import haoirdevelopment.haoirclient.utils.network.Http;
import net.minecraft.client.User;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;
import java.util.UUID;

import static haoirdevelopment.haoirclient.HaOirClient.mc;

public class TheAlteningAccount extends Account<TheAlteningAccount> implements TokenAccount {
    private static final Environment ENVIRONMENT = new Environment("thealtening.com", "The Altening"); // todo figure this out
    private static final MinecraftServicesDiscoveryService SERVICE = MinecraftServicesDiscoveryService.create(mc.getProxy(), true, ENVIRONMENT);
    private String token;
    private String accessToken;

    public TheAlteningAccount(String token) {
        super(AccountType.TheAltening, token);
        this.token = token;
    }

    @Override
    public boolean fetchInfo() {
        try {
            AuthResponse res = authenticate();
            if (res == null || res.accessToken == null || res.selectedProfile == null) {
                HaOirClient.LOG.error("Invalid TheAltening credentials.");
                return false;
            }

            accessToken = res.accessToken;
            cache.username = res.selectedProfile.name;
            cache.uuid = res.selectedProfile.id;
            cache.loadHead();

            return true;
        } catch (Exception _) {
            HaOirClient.LOG.error("Failed to fetch info for TheAltening account!");
            return false;
        }
    }

    @Override
    public boolean login() {
        if (accessToken == null || cache.username.isEmpty() || cache.uuid.isEmpty()) return false;
        applyLoginEnvironment(SERVICE);

        try {
            setSession(new User(cache.username, UndashedUuid.fromStringLenient(cache.uuid), accessToken, Optional.empty(), Optional.empty()));
            return true;
        } catch (Exception _) {
            HaOirClient.LOG.error("Failed to login with TheAltening.");
            return false;
        }
    }

    private AuthResponse authenticate() {
        return Http.post(ENVIRONMENT.discoveryUrl() + "/authenticate") // todo i will eat my sock if this actually works
            .bodyJson(new AuthRequest("MINECRAFT", token, "HaOir on Crack!", UUID.randomUUID().toString(), true))
            .sendJson(AuthResponse.class);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putString("type", type.name());
        tag.putString("name", name);
        tag.putString("token", token);
        tag.put("cache", cache.toTag());

        return tag;
    }

    @Override
    public TheAlteningAccount fromTag(CompoundTag tag) {
        if (tag.getString("name").isEmpty() || tag.getCompound("cache").isEmpty() || tag.getString("token").isEmpty())
            throw new NbtException();

        name = tag.getString("name").get();
        token = tag.getString("token").get();
        cache.fromTag(tag.getCompound("cache").get());

        return this;
    }

    private record AuthRequest(String agent, String username, String password, String clientToken, boolean requestUser) {}

    private static class AuthResponse {
        public String accessToken;
        public AuthProfile selectedProfile;
    }

    private static class AuthProfile {
        public String id;
        public String name;
    }
}
