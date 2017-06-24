package com.game.box2d.mapobjects;

/**
 * Eine Absperrung ist entweder ein- oder ausgefahren und kann entsprechend Teile des Levels blockieren
 */
public class Barrier {

    /**
     * Gibt an, ob die Absperrung aktiviert/ausgefahren ist. Standardweise false
     */
    private boolean isActivated = false;

    /**
     * Fährt die Absperrung aus
     */
    public void activate() {
        this.isActivated = true;
    }

    /**
     * Fährt die Absperrung ein
     */
    public void deactivate() {
        this.isActivated = false;
    }

    /**
     * @return Gibt an, ob die Absperrung ein- oder ausgefahren ist
     */
    public boolean isActivated() {
        return isActivated;
    }
}
