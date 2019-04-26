package controller.animation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteSheet {
    private final Image[] sheet;
    private final int frameCount;

    public SpriteSheet(String path, int extraFrames) {
        this(new Image(path),
                Integer.valueOf(path.substring(path.lastIndexOf("-") + 1, path.lastIndexOf("x"))),
                Integer.valueOf(path.substring(path.lastIndexOf("x") + 1, path.lastIndexOf("."))),
                extraFrames);
    }

    public SpriteSheet(Image image, int extraFrames) {
        //impl_getUrl() was added to javafx 11
        this(image,
                Integer.valueOf(image.impl_getUrl().substring(image.impl_getUrl().lastIndexOf("-") + 1, image.impl_getUrl().lastIndexOf("x"))),
                Integer.valueOf(image.impl_getUrl().substring(image.impl_getUrl().lastIndexOf("x") + 1, image.impl_getUrl().lastIndexOf("."))),
                extraFrames);

    }

    public SpriteSheet(Image image, int frameWidth, int frameHeight, int extraFrames) {
        final int columns = (int) image.getWidth() / frameWidth;
        final int rows = (int) image.getHeight() / frameHeight;

        if (extraFrames > columns)
            throw new IllegalArgumentException("extraFrames > Number of elements in a row");


        frameCount = columns * rows - extraFrames;
        sheet = new Image[frameCount];

        PixelReader pixelReader = image.getPixelReader();
        for (int j = 0; j < rows - 1; j++) {
            for (int i = 0; i < columns; i++) {
                sheet[i + (columns * j)] = getFrame(frameWidth, frameHeight, pixelReader, j, i);
            }
        }

        for (int i = 0; i < columns - extraFrames; i++) {
            sheet[i + (columns * (rows - 1))] = getFrame(frameWidth, frameHeight, pixelReader, rows - 1, i);
        }
    }

    private static WritableImage getFrame(int frameWidth, int frameHeight, PixelReader pixelReader, int j, int i) {
        return new WritableImage(pixelReader,
                frameWidth * i,
                frameHeight * j,
                frameWidth,
                frameHeight);
    }

    public Image getSheetAtIndex(int i) {
        return sheet[i];
    }

    public int getFrameCount() {
        return frameCount;
    }

    public static Image getFirstSprite(String path) {
        Image image = new Image(path);

        return getFrame(Integer.valueOf(path.substring(path.lastIndexOf("-") + 1, path.lastIndexOf("x"))),
                Integer.valueOf(path.substring(path.lastIndexOf("x") + 1, path.lastIndexOf("."))),
                image.getPixelReader(),
                0, 0);
    }
}
