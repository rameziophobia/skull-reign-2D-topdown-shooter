package Control.animation;

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

//    public SpriteSheet(Image image, int extraFrames) {
//        this(image,
//                Integer.valueOf(image.getUrl().substring(image.getUrl().lastIndexOf("-") + 1, image.getUrl().lastIndexOf("x"))),
//                Integer.valueOf(image.getUrl().substring(image.getUrl().lastIndexOf("x") + 1, image.getUrl().lastIndexOf("."))),
//                extraFrames);
//    }

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

    private WritableImage getFrame(int frameWidth, int frameHeight, PixelReader pixelReader, int j, int i) {
        return new WritableImage(pixelReader,
                frameWidth * i,
                frameHeight * j,
                frameWidth,
                frameHeight);
    }

    public static Image getFirstSheet(String path){//todo: do this the right way :D
        return new SpriteSheet(path,0).getSheetAtIndex(0);
    }

    public Image getSheetAtIndex(int i) {
        return sheet[i];
    }

    public int getFrameCount() {
        return frameCount;
    }
}
