package org.chelonix.cnab.action;

public interface Action {

    public static boolean isBuiltIn(String action) {
        return action.equals("install") || action.equals("upgrade") || action.equals("uninstall");
    }

    void run();

    String getName();
}
