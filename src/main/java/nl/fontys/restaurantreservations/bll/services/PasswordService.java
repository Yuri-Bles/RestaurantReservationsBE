package nl.fontys.restaurantreservations.bll.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public final class PasswordService {

    private static final int BCRYPT_COST = 12;

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_COST));
    }

    public boolean verify(String enteredPassword, String storedPasswordHash) {
        return BCrypt.checkpw(
                enteredPassword,
                storedPasswordHash
        );
    }
}
