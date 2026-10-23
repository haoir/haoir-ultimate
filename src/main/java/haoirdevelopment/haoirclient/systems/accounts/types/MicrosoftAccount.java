/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.systems.accounts.types;

import com.mojang.util.UndashedUuid;
import haoirdevelopment.haoirclient.systems.accounts.Account;
import haoirdevelopment.haoirclient.systems.accounts.AccountType;
import haoirdevelopment.haoirclient.systems.accounts.MicrosoftLogin;
import net.minecraft.client.User;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class MicrosoftAccount extends Account<MicrosoftAccount> {
    private @Nullable String token;

    public MicrosoftAccount(String refreshToken) {
        super(AccountType.Microsoft, refreshToken);
    }

    @Override
    public boolean fetchInfo() {
        token = auth();
        return token != null;
    }

    @Override
    public boolean login() {
        if (token == null) return false;

        super.login();

        setSession(new User(cache.username, UndashedUuid.fromStringLenient(cache.uuid), token, Optional.empty(), Optional.empty()));
        return true;
    }

    private @Nullable String auth() {
        MicrosoftLogin.LoginData data = MicrosoftLogin.login(name);
        if (data == null || data.newRefreshToken() == null) return null;

        name = data.newRefreshToken();
        cache.username = data.username();
        cache.uuid = data.uuid();

        return data.mcToken();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MicrosoftAccount account)) return false;
        return account.name.equals(this.name);
    }
}
