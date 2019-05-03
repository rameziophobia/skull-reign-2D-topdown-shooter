package model.ui.menu;

public class MenuButtonTransition extends MenuButton {

    public MenuButtonTransition(String text, double scale, Menu parentMenu, String nextMenuKey) {
        super(text, scale);
        setOnAnimationEndAction(() -> parentMenu.transition(nextMenuKey, this));
    }

    public void reset() {
        setTranslateY(0);
        setOpacity(1);
    }
}
