/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.screens.accounts;

import haoirdevelopment.haoirclient.gui.GuiTheme;
import haoirdevelopment.haoirclient.gui.widgets.containers.WTable;
import haoirdevelopment.haoirclient.gui.widgets.input.WTextBox;
import haoirdevelopment.haoirclient.systems.accounts.Accounts;
import haoirdevelopment.haoirclient.systems.accounts.types.CrackedAccount;

public class AddCrackedAccountScreen extends AddAccountScreen {
    public AddCrackedAccountScreen(GuiTheme theme, AccountsScreen parent) {
        super(theme, "Add Cracked Account", parent);
    }

    @Override
    public void initWidgets() {
        WTable t = add(theme.table()).widget();

        // Name
        t.add(theme.label("Name: "));
        WTextBox name = t.add(theme.textBox("", "seasnail8169", (_, c) ->
            /// @see net.minecraft.util.StringUtil#isValidPlayerName
            c > 32 && c < 127
        )).minWidth(400).expandX().widget();
        name.setFocused(true);
        t.row();

        // Add
        add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            String username = name.get().trim();
            if (username.length() > 16) return;

            CrackedAccount account = new CrackedAccount(username);
            if (!(Accounts.get().exists(account))) {
                AccountsScreen.addAccount(this, parent, account);
            }
        };

        enterAction = add.action;
    }
}
