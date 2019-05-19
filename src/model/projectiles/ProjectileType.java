package model.projectiles;

import controller.audiomanager.AudioFile;
import view.Main;

public enum ProjectileType {
    BULLET("bullet-13x3.png", 15, 3, 10, 3, false, AudioFile.MACHINE, 1, 1),
    FIRE("fire-32x12.png", 1, 35, 6, 3, false, AudioFile.WOOSH, 1, 1),
    REDLASER01("laserRed01-54x9.png", 3, 15, 15, 3, false, AudioFile.LASER, 1, 1),
    GREENLASER01("laserGreen01-54x9.png", 3, 15, 15, 3, false, AudioFile.LASER, 1, 1),
    BLUELASER01("laserBlue01-54x9.png", 3, 15, 15, 3, false, AudioFile.LASER, 1, 1),

    REDLASER02("laserRed02-37x13.png", 2, 10, 8, 2, false, AudioFile.LASER, 1, 1),
    GREENLASER02("laserGreen02-37x13.png", 2, 10, 8, 2, false, AudioFile.LASER, 1, 1),
    BLUELASER02("laserBlue02-37x13.png", 2, 10, 8, 2, false, AudioFile.LASER, 1, 1),

    REDLASER03("laserRed03-37x9.png", 20, 1.5f, 10, 3, false, AudioFile.BURST, 1, 1),
    GREENLASER03("laserGreen03-37x9.png", 20, 1.5f, 10, 3, false, AudioFile.LASER, 1, 1),
    BLUELASER03("laserBlue03-37x9.png", 20, 1.5f, 10, 3, false, AudioFile.BURST, 1, 1),

    LASERRED08("laserRed08-48x45.png", 1.5, 50, 6, 5, false, AudioFile.LASER, 1, 1),

    EYEBALL("eyeball-animated-40x40.png", 1.5, 50, 6, 5, true, AudioFile.S2, 1, 1),
    FIREBALL("fireball_0 -animated-64x16.png", 1.5, 50, 6, 5, true, AudioFile.WOOSH, 1, 1),
    FLAMEBALL("flameball-animated-32x32.png", 1.5, 50, 6, 5, true, AudioFile.WOOSH, 1, 1),
    SHOCK("shock-animated-64x13.png", 1.5, 50, 6, 5, true, AudioFile.ELECTRIC1, 1, 1),
    ICEICLE("icicle-right-animated-64x11.png", 1.5, 50, 6, 5, true, AudioFile.S2, 1, 1),
    ELECTRIC("electric-animated-63x83.png", 2, 40, 7, 10, true, AudioFile.ELECTRIC1, 1, 1),
    CAT("black-cat-animated-32x26.png", 1.3, 40, 5, 10, true, AudioFile.S2, 1, 1),
    WHIRLWIND("whirlwind-animated-16x19.png", 2, 40, 7, 5, true, AudioFile.WOOSH, 1, 1),
    KNIFE("spr_spell_knife-42x32.png", 2, 50, 8, 5, false, null, 1, 1),
    SKULL("fire-skull-96x88.png", 1.3, 100, 8, 10, true, null, 1, 1);

    private final String URL;
    private final double FIRERATE;
    private final float DAMAGE;
    private final double MULTANGLE;
    private final boolean ANIMATED;
    private final AudioFile SOUND;

    private int speed;
    private int defaultSpeed;
    private float currentMult;
    private float currentScale;

    ProjectileType(String fileName, double fireRate, float damage, int speed, double multAngle, boolean animated, AudioFile sound, float currentScale, float currentMult) {
        this.URL = Constants.PATH_RESOURCES_SPRITES_PROJECTILES + fileName;
        this.FIRERATE = fireRate;
        this.DAMAGE = damage;
        this.speed = speed;
        this.defaultSpeed = speed;
        this.MULTANGLE = multAngle;
        this.ANIMATED = animated;
        this.SOUND = sound;
        this.currentMult = currentMult;
        this.currentScale = currentScale;
    }

    public String getURL() {
        return URL;
    }

    public double getFIRERATE() {
        return FIRERATE;
    }

    public float getDAMAGE() {
        return DAMAGE;
    }

    public int getSPEED() {
        return speed;
    }

    public double getMULTANGLE() {
        return MULTANGLE;
    }

    public float getCurrentMult() {
        return currentMult;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public boolean isANIMATED() {
        return ANIMATED;
    }

    public void setToDefault() {
        this.currentScale = 1;
        this.currentMult = 1;
        this.speed = defaultSpeed;
    }

    public void incCurrentMult(float mult) {
        this.currentMult += mult;
    }

    public void incCurrentScale(float scale) {
        this.currentScale += scale;
    }

    public void incCurrentSpeed(float speed) {
        this.speed += speed;
    }

    public AudioFile getSound() {
        return SOUND;
    }

    private static class Constants {
        private static final String PATH_RESOURCES_SPRITES_PROJECTILES = Main.PATH_RESOURCES_SPRITES + "projectiles/";
    }
}
