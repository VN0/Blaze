package com.tac.blaze;

import android.app.Application;

import com.tac.blaze.models.User;


public class UserClient extends Application {

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OverrideFonts.setDefaultFont(this, "MONOSPACE", "fonts/google1.ttf");
    }
}
