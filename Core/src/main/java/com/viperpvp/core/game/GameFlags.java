package com.viperpvp.core.game;

/**
 * Created by Matt on 02/09/2016.
 */
public class GameFlags {

    private boolean isBlockBreakAllowed = false;
    private boolean isBlockPlaceAllowed = false;
    private boolean isDamageAllowed = true;
    private boolean isTeamDamageAllowed = false;

    public GameFlags(boolean isBlockBreakAllowed, boolean isBlockPlaceAllowed, boolean isDamageAllowed, boolean isTeamDamageAllowed) {
        this.isBlockBreakAllowed = isBlockBreakAllowed;
        this.isBlockPlaceAllowed = isBlockPlaceAllowed;
        this.isDamageAllowed = isDamageAllowed;
        this.isTeamDamageAllowed = isTeamDamageAllowed;
    }

    public boolean isBlockBreakAllowed() {
        return isBlockBreakAllowed;
    }

    public void setBlockBreakAllowed(boolean blockBreakAllowed) {
        isBlockBreakAllowed = blockBreakAllowed;
    }

    public boolean isBlockPlaceAllowed() {
        return isBlockPlaceAllowed;
    }

    public void setBlockPlaceAllowed(boolean blockPlaceAllowed) {
        isBlockPlaceAllowed = blockPlaceAllowed;
    }

    public boolean isDamageAllowed() {
        return isDamageAllowed;
    }

    public void setDamageAllowed(boolean damageAllowed) {
        isDamageAllowed = damageAllowed;
    }

    public boolean isTeamDamageAllowed() {
        return isTeamDamageAllowed;
    }

    public void setTeamDamageAllowed(boolean teamDamageAllowed) {
        isTeamDamageAllowed = teamDamageAllowed;
    }
}
