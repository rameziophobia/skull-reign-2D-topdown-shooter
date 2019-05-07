package model.ui.menu;

import view.menu.mainmenu.menus.Menus;

public class MenuButtonTransition extends MenuButton {

    public MenuButtonTransition(String text, Menu parentMenu, Menus nextMenuKey) {
        this(text, parentMenu, nextMenuKey, () -> {});
    }

    public MenuButtonTransition(String text, Menu parentMenu, Menus nextMenuKey, Action onAnimationEndAction) {
        super(text);
        addEndAction(parentMenu, nextMenuKey, onAnimationEndAction);
    }

    private void addEndAction(Menu parentMenu, Menus nextMenuKey, Action onAnimationEndAction) {
        setOnAnimationEndAction(() -> {
            parentMenu.transition(nextMenuKey, this);
            onAnimationEndAction.run();
        });
    }

    public void reset() {
        setTranslateY(0);
        setOpacity(1);
    }
}
