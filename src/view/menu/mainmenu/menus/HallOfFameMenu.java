package view.menu.mainmenu.menus;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import view.GameViewManager;
import view.Main;
import view.menu.mainmenu.MenuScene;
import view.score.HighScores;
import view.score.ScoreBoard;

public class HallOfFameMenu extends Menu {
    private static TableView<HighScores> highScoreTable;
    private static ScoreBoard leaderBoards;

    public HallOfFameMenu(MenuScene menuScene) {
        super(menuScene);
        menuScene.getStylesheets().add(Main.PATH_RESOURCES + "styles.css");
        createLeaderBoardsTable(menuScene.getWidth());

        Label lbl_hallOfFameMenu = MenuScene.createMenuTitle("Hall Of Fame");

        addNodeAll(
                lbl_hallOfFameMenu,
                highScoreTable,
                new MenuButtonTransition("Back", this, Menus.MAIN));
    }

    private void createLeaderBoardsTable(Double width) {
        highScoreTable = new TableView<>();
        leaderBoards = new ScoreBoard();

        final TableColumn<HighScores, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(width / 2);

        final TableColumn<HighScores, String> scoreColumn = new TableColumn<>("High score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreColumn.setMinWidth(width / 2);
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);

//        highScoreTable.setItems(leaderBoards.getHighScores());
        highScoreTable.setPrefWidth(400);
        highScoreTable.getStyleClass().addAll("NoFocus");
        highScoreTable.setId("highScore");

        ObservableList<TableColumn<HighScores, ?>> columns = highScoreTable.getColumns();
        columns.add(scoreColumn);
        columns.add(nameColumn);

        VBox.setMargin(highScoreTable, new Insets(0, 0, 0, 10));
    }

    @Deprecated
    public static void addScoreInput(int val, boolean gameEnded) {
        String text = null;
        TextInputDialog input = new TextInputDialog("");
        input.setTitle("Save high score");
        input.setHeaderText("Please enter your name");
        input.show();
        input.setOnCloseRequest(e -> {
            if (!input.getEditor().getText().isEmpty())
                setNewRecord(input.getEditor().getText(), val);
            else
                setNewRecord("NoName", val);
            if (gameEnded) {
                GameViewManager.endGame();
            }
        });

    }

    public static void setNewRecord(String Name, int Score) {
        leaderBoards.addNewScore(Name, Score);
    }

    @Override
    public void fadeIn() {
        super.fadeIn();
        highScoreTable.setItems(leaderBoards.getHighScores());
    }
}
