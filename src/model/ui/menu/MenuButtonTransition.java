package model.ui.menu;

import view.menu.mainmenu.menus.Menus;

public class MenuButtonTransition extends MenuButton {

    public MenuButtonTransition(String text, Menu parentMenu, Menus nextMenuKey) {
        super(text);
        addEndAction(parentMenu, nextMenuKey);
    }

    public MenuButtonTransition(String text, double scale, Menu parentMenu, Menus nextMenuKey) {
        super(text, scale);
        addEndAction(parentMenu, nextMenuKey);
    }

    private void addEndAction(Menu parentMenu, Menus nextMenuKey) {
        setOnAnimationEndAction(() -> parentMenu.transition(nextMenuKey, this));
    }

    public void reset() {
        setTranslateY(0);
        setOpacity(1);
    }
}
