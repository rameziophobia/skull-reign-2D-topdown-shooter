package model.ui.menu;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.menu.mainmenu.menus.Menus;

public class MenuButtonTransition extends MenuButton {

    private Menu parentMenu;

    public MenuButtonTransition(String text, Menu parentMenu, Menus nextMenuKey) {
        this(text, parentMenu, nextMenuKey, () -> {});
    }

    public MenuButtonTransition(String text, Menu parentMenu, Menus nextMenuKey, Action onAnimationEndAction) {
        super(text);
        this.parentMenu = parentMenu;
        addEndAction(nextMenuKey, onAnimationEndAction);
    }

    private void addEndAction(Menus nextMenuKey, Action onAnimationEndAction) {
        setOnAnimationEndAction(() -> {
            parentMenu.transition(nextMenuKey, this);
            onAnimationEndAction.run();
        });
    }

    @Override
    protected void setOnMouseClick() {
        super.setOnMouseClick();

        EventHandler<? super MouseEvent> eventHandler = getOnMouseClicked();

        setOnMouseClicked(e ->{
            parentMenu.disableMenu();
            eventHandler.handle(e);
        });
    }

    public void reset() {
        setTranslateY(0);
        setOpacity(1);
    }
}
