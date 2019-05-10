package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.player.PlayerType;
import model.ui.mainmenu.BtnTemp;
import model.ui.mainmenu.PlayerPicker;
import model.ui.mainmenu.SmallLabel;
import model.ui.mainmenu.SubsceneTemp;

import java.util.ArrayList;

@Deprecated
public class ViewManager {

    private BorderPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private static final int HEIGHT = 800;
    private static final int WIDTH = 900;

    private TilePane menuButtons;
    private HBox centerHbx;
    private StackPane stkPane;
    private SubsceneTemp currentSubScene;
    private ArrayList<PlayerPicker> playerList;
    private PlayerType chosenPlayer;

    public ViewManager() {
        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainPane.setPadding(new Insets(13, 25, 13, 10));
        setCursorHand(mainPane);
        createLogo();
        createCenterHBox();
    }

    public static void setCursorHand(Pane pane) {
        Image image = new Image("file:resources/sprites/ui/cursorHand_blue.png");
        pane.setCursor(new ImageCursor(image,
                image.getWidth() / 2,
                image.getHeight() / 2));
    }

    private void CreateButtons() {
        createMenuBtnTilePane();
        createStartBtn();
        createScoreBtn();
        creatHelpBtn();
        createCreditsBtn();
        createExitBtn();
        createBackground();
    }

    private void createCenterHBox() {
        centerHbx = new HBox();
        centerHbx.setSpacing(20);
        setCursorHand(centerHbx);
        stkPane = new StackPane();
        CreateButtons();

        Region r = new Region();
        HBox.setHgrow(r, Priority.SOMETIMES);
        HBox.setMargin(centerHbx, (new Insets(13, 25, 13, 10)));
        centerHbx.setPadding((new Insets(13, 25, 13, 10)));
        centerHbx.getChildren().addAll(r, stkPane);
        mainPane.setCenter(centerHbx);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void createBackground() {
        Image backgroundImage = new Image("file:resources/sprites/tiles/floor2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo() {
        Image logoImage = new Image("file:resources/sprites/ui/logo.png", 80, 80, true, false);
        ImageView logo = new ImageView(logoImage);
        HBox hbx_logo = new HBox();
        hbx_logo.getChildren().add(logo);
        createLogoLabel(hbx_logo);
        hbx_logo.setAlignment(Pos.CENTER);
        mainPane.setTop(hbx_logo);
    }

    private void createLogoLabel(HBox hbx_logo) {
        final SmallLabel lbl_logo = new SmallLabel("El Game");
        lbl_logo.setFont(Font.font("Cambria", 32));
        lbl_logo.setTextFill(Color.WHITE);
        lbl_logo.setOnMouseEntered(e -> lbl_logo.setEffect(new Glow(2)));
        lbl_logo.setOnMouseExited(e -> lbl_logo.setEffect(null));
        hbx_logo.getChildren().add(lbl_logo);
    }

    private void createMenuBtnTilePane() {
        menuButtons = new TilePane(Orientation.VERTICAL);
        menuButtons.setVgap(20);
        menuButtons.setTileAlignment(Pos.CENTER);
        menuButtons.setAlignment(Pos.CENTER);
        menuButtons.setPrefRows(5);
        BorderPane.setAlignment(menuButtons, Pos.CENTER_RIGHT);
        BorderPane.setMargin(menuButtons, new Insets(12, 12, 12, 12));
        centerHbx.getChildren().add(menuButtons);
    }

    private void createStartBtn() {
        BtnTemp btn = new BtnTemp("Play");
        menuButtons.getChildren().add(btn);
        SubsceneTemp subscene = new SubsceneTemp();
        stkPane.getChildren().add(subscene);
        fadeOnAction(btn, subscene);
        HBox hbx_start = createHbxStart();
        BtnTemp begin = new BtnTemp("begin");
        GameViewManager gm = new GameViewManager();
        begin.setOnAction(e -> gm.createNewGame(mainStage, chosenPlayer));
        subscene.getPane().getChildren().addAll(hbx_start, begin);
    }

    private HBox createHbxStart() {
        HBox hbx_start = new HBox();
        hbx_start.setSpacing(20);
        playerList = new ArrayList<>();
        for (PlayerType p : PlayerType.values()) {
            PlayerPicker pp = new PlayerPicker(p);
            hbx_start.getChildren().add(pp);
            playerList.add(pp);
            pp.setOnMouseClicked(e -> {
                for (PlayerPicker playerPick : playerList) {
                    playerPick.setChosen(false);
                }
                pp.setChosen(true);
                chosenPlayer = pp.getPlayer();
            });
        }
        return hbx_start;
    }

    private void createScoreBtn() {
        BtnTemp btn = new BtnTemp("score");
        menuButtons.getChildren().add(btn);
        SubsceneTemp subscene = new SubsceneTemp();
        stkPane.getChildren().add(subscene);
        fadeOnAction(btn, subscene);
    }

    private void creatHelpBtn() {
        BtnTemp btn = new BtnTemp("Help");
        menuButtons.getChildren().add(btn);
        SubsceneTemp subscene = new SubsceneTemp();
        stkPane.getChildren().add(subscene);
        fadeOnAction(btn, subscene);
    }

    private void createCreditsBtn() {
        BtnTemp btn = new BtnTemp("Credits");
        menuButtons.getChildren().add(btn);
        SubsceneTemp subscene = new SubsceneTemp();
        stkPane.getChildren().add(subscene);
        fadeOnAction(btn, subscene);
    }

    private void createExitBtn() {
        BtnTemp btn = new BtnTemp("Exit");
        menuButtons.getChildren().add(btn);
        btn.setOnAction(event -> mainStage.close());
    }

    private void fadeOnAction(BtnTemp btn, SubsceneTemp subscene) {


        btn.setOnAction(e -> {
            if (currentSubScene != null) {
                if (subscene.opacityProperty().getValue() == 0) {
                    if (currentSubScene.opacityProperty().getValue() == 0) {
                        subscene.fadeInSubscene();
                        currentSubScene = subscene;
                    } else {
                        currentSubScene.fadeOutSubscene();
                        currentSubScene = subscene;
                        currentSubScene.fadeInSubscene();
                    }
                } else {
                    subscene.fadeOutSubscene();
                }

            } else {
                subscene.fadeInSubscene();
                currentSubScene = subscene;
            }

        });
    }


}