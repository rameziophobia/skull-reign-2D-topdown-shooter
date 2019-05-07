package view.menu.mainmenu.menus;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.ui.menu.Menu;
import model.ui.menu.MenuButtonTransition;
import view.HighScores;
import view.Main;
import view.ScoreBoard;
import view.menu.mainmenu.MenuScene;


public class HallOfFameMenu extends Menu {
    private TableView<HighScores> highScoreTable;
    private ObservableList<HighScores> nameList;
    ScoreBoard leaderBoards;
    public HallOfFameMenu(MenuScene menuScene) {
        super(menuScene);
        menuScene.getStylesheets().add( Main.PATH_RESOURCES+"styles.css");
        createLeaderBoardsTable(menuScene.getWidth());

        Label lbl_hallOfFameMenu = MenuScene.createMenuTitle("Hall Of Fame");

        addNodeAll(
                lbl_hallOfFameMenu, highScoreTable,
                new MenuButtonTransition("Back", MenuScene.BUTTON_SCALE, this, "Main"));
    }

    private void createLeaderBoardsTable(Double width) {
        highScoreTable = new TableView<>();
        leaderBoards = new ScoreBoard();

        TableColumn<HighScores, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        nameList = leaderBoards.getHighScores();

        TableColumn<HighScores, String> scoreColumn = new TableColumn<>("High Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        highScoreTable.setItems(nameList);
        highScoreTable.setPrefWidth(400);
        highScoreTable.getStyleClass().addAll("NoFocus");
        nameColumn.setMinWidth( width/2);
        scoreColumn.setMinWidth(width/2);
        highScoreTable.setId("highScore");
        highScoreTable.getColumns().addAll( scoreColumn,nameColumn);

        VBox.setMargin(highScoreTable, new Insets(0, 0, 0, 10));
    }
    public void updateTableUI(){
        nameList.clear();
        nameList = leaderBoards.getHighScores();
    }
    public void setANewRecord(String Name,int Score){
        leaderBoards.addNewScore(Name,Score);
        updateTableUI();
    }

}
