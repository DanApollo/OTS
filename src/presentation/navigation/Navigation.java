package presentation.navigation;

import domain.interfaces.IUser;
import model.impl.Show;

public interface Navigation {
    void navigateTo(String page);

    void onLogin(IUser user);

    void onShowSelect(Show selectedShow);

    void onLogout();
}
