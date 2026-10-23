/*
 * This file is part of the HaOir Client distribution (https://github.com/HaOirDevelopment/meteor-client).
 * Copyright (c) HaOir Development.
 */

package haoirdevelopment.haoirclient.utils.misc;

public enum ListMode {
    Whitelist,
    Blacklist;

    public boolean allows(boolean contains) {
        return switch (this) {
            case Whitelist -> contains;
            case Blacklist -> !contains;
        };
    }
}
