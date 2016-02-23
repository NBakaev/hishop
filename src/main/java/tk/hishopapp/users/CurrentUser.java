package tk.hishopapp.users;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/23/2016.
 * All Rights Reserved
 */
public interface CurrentUser {

    /**
     * Get current user that is auth with current HTTP request
     * If current user is anonymous - without credentials - return null
     *
     * @return
     */
    UserAccount getCurrentUser();
}
