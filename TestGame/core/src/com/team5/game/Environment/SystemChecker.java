package com.team5.game.Environment;

import com.badlogic.gdx.Gdx;

public class SystemChecker {

    int systemsBroken;

    public SystemChecker(){
    }

    public void breakSystem(){
        systemsBroken++;
        Gdx.app.log("Systems broken", String.valueOf(systemsBroken));
    }

    public boolean allSystemsBroken(){
        return (systemsBroken>=15);
    }

}
