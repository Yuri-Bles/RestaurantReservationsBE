package nl.fontys.restaurantreservations.bll.services;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordService {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verifyPassword(String enteredPassword, String storedPasswordHash) {
        return BCrypt.checkpw(
                enteredPassword,
                storedPasswordHash
        );
    }
}
