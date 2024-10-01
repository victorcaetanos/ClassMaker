import controller.menu.MenuController;
import view.menu.IMenuView;
import view.menu.MenuView;

public class AppInit {
    public static void main(String[] args) {
        IMenuView view = new MenuView();
        new MenuController(view);
    }
}
