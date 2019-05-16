package view;

import javafx.scene.Group;
import view.game.ProjectileUI;
import view.game.stats.HealthBars;

public class GameViewUI {

    private Group group = new Group();
    private HealthBars playerHealthBars = new HealthBars();

    public GameViewUI() {
        createWeaponBar();
        createSpecialAttackBar();
    }

    private static void createSpecialAttackBar() {
    }

    private static void createWeaponBar() {
        GameViewManager.addGameObjectTOScene(new ProjectileUI());
    }

    public Group getGroup() {
        return group;
    }

    public HealthBars getPlayerHealthBars() {
        return playerHealthBars;
    }

}
