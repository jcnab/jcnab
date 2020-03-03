package org.chelonix.cnab.action;

import org.chelonix.cnab.core.Claim;

public interface Action {

    static boolean isBuiltIn(String action) {
        return action.equals("install") || action.equals("upgrade") || action.equals("uninstall");
    }

    Claim run();

    String getName();
}
