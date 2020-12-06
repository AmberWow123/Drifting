package com.example.drifting.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }
    String setDisplayname(String newName) {
        this.displayName = newName;
        return newName;}

    String getDisplayName() {
        return displayName;
    }
}