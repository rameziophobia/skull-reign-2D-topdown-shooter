package controller.animation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimationClip {

    public static final int INF_REPEATS = -1;

    private final SpriteSheet spriteSheet;
    private final ImageView gameObject;

    private float frameRate;
    private boolean autoReverse;
    private int repeats;

    private int currentFrame = -1;
    private long nextFrameTime;
    private boolean reverse;

    public AnimationClip(SpriteSheet spriteSheet, float frameRate, ImageView gameObject) {
        this(spriteSheet, frameRate, false, 1, gameObject);
    }


    public AnimationClip(SpriteSheet spriteSheet, float frameRate, boolean autoReverse, int repeats, ImageView gameObject) {
        this.spriteSheet = spriteSheet;
        if (repeats < -1)
            throw new IllegalArgumentException("repeats < -1");
        this.repeats = repeats;
        this.frameRate = frameRate;
        this.autoReverse = autoReverse;
        this.gameObject = gameObject;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    private Image nextFrame() {
        if (repeats != 0) {
            if (nextFrameTime < System.currentTimeMillis()) {
                nextFrameTime = System.currentTimeMillis() + (int) (1000 / frameRate);

                if (currentFrame == spriteSheet.getFrameCount() - 1) {
                    if (autoReverse) {
                        reverse = true;
                    } else {
                        repeats = repeats == -1 ? -1 : repeats - 1;
                        if (repeats == 0) {
                            return null;
                        }
                        currentFrame = -1;
                    }
                }

                if (reverse) {
                    if (currentFrame == 1) {
                        repeats = repeats == -1 ? -1 : repeats - 1;
                        if (repeats == 0) {
                            return null;
                        }
                        reverse = false;
                    }
                    return spriteSheet.getSheetAtIndex(--currentFrame);
                } else {
                    return spriteSheet.getSheetAtIndex(++currentFrame);
                }
            }
        }
        return null;
    }

    public void animate() {
        Image image = nextFrame();

        if (image != null)
            gameObject.setImage(image);
    }

    public boolean isDone() {
        return repeats == 0;
    }

    public boolean isAtEnd() {
        return isAtFrame(spriteSheet.getFrameCount() - 1);
    }

    public boolean isAtStart() {
        return isAtFrame(0);
    }

    public boolean isAtFrame(int index) {
        return currentFrame == index;
    }
}
