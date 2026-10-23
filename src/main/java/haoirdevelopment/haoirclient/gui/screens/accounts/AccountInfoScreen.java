/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.screens.accounts;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.WindowScreen;
import haoirdevelopment.haoirclient.gui.widgets.containers.WHorizontalList;
import haoirdevelopment.haoirclient.gui.widgets.pressable.WButton;
import haoirdevelopment.haoirclient.systems.accounts.Account;
import haoirdevelopment.haoirclient.systems.accounts.AccountType;
import haoirdevelopment.haoirclient.systems.accounts.TokenAccount;
import haoirdevelopment.haoirclient.utils.render.color.Color;

import static haoirdevelopment.haoirclient.HaOirClient.mc;

public class AccountInfoScreen extends WindowScreen {
    private final Account<?> account;

    public AccountInfoScreen(GuiTheme theme, Account<?> account) {
        super(theme, account.getUsername() + " details");
        this.account = account;
    }

    @Override
    public void initWidgets() {
        TokenAccount e = (TokenAccount) account;
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        String tokenLabel = account.getType() + " token:";
        if (account.getType() == AccountType.Session) tokenLabel = "";

        WButton copy = theme.button("Copy");
        copy.action = () -> mc.keyboardHandler.setClipboard(e.getToken());

        l.add(theme.label(tokenLabel));
        l.add(theme.label(account.getType() == AccountType.Session ? "Click to copy Token" : e.getToken()).color(Color.GRAY)).pad(5);
        l.add(copy);
    }
}
