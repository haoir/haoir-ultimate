/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.gui.themes.meteor.widgets;

import haoirdevelopment.haoirclient.gui.WidgetScreen;
import haoirdevelopment.haoirclient.gui.themes.meteor.HaOirWidget;
import haoirdevelopment.haoirclient.gui.widgets.WAccount;
import haoirdevelopment.haoirclient.systems.accounts.Account;
import haoirdevelopment.haoirclient.utils.render.color.Color;

public class WHaOirAccount extends WAccount implements HaOirWidget {
    public WHaOirAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
