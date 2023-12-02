package User;

import Main.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountManager {
    public enum Status {
        LOGGED_IN,
        REGISTERED,
        INVALID_CREDENTIALS,
        INVALID_CHARACTERS,
        OFFLINE,
        USERNAME_TAKEN,
        NULL_FIELD
    }

    private static AccountManager instance;
    private AccountManager() {

    }

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public Status register(String userName, String passWord) throws SQLException, ClassNotFoundException {
        String usrNameRegex = "^[A-Za-z0-9.]+$";
        Pattern usrNamePattern = Pattern.compile(usrNameRegex);

        if (userName.isEmpty() || passWord.isEmpty()) {
            return Status.NULL_FIELD;
        }
        try {
            Matcher matcher = usrNamePattern.matcher(userName);
            if (matcher.matches()) {
                User.getCurrentUser().reset();
                passWord = hashPassword(passWord);
                User.getCurrentUser().newAccount(userName, passWord);
                User.getCurrentUser().getUserDao().save(User.getCurrentUser());
                return Status.REGISTERED;
            } else {
                return Status.INVALID_CHARACTERS;
            }
        } catch (Exception e) {
            if (!Database.getUserDB().isClosed()) {
                return Status.USERNAME_TAKEN;
            } else {
                return Status.OFFLINE;
            }
        }
    }

    public Status login(String userName, String passWord) throws SQLException, ClassNotFoundException {
        if (userName.isEmpty() || passWord.isEmpty()) {
            return Status.NULL_FIELD;
        }
        try {
            User.getCurrentUser().getUserDao().get(userName);
            String dbPassword = User.getCurrentUser().getPassWord();
            passWord = hashPassword(passWord);

            if (!Database.getUserDB().isClosed()) {
                if (passWord.equals(dbPassword)) {

                    User.getCurrentUser().loginHandler();

                    return Status.LOGGED_IN;
                } else {
                    return Status.INVALID_CREDENTIALS;
                }
            } else {
                return Status.OFFLINE;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (!Database.getUserDB().isClosed()) {
                return Status.INVALID_CREDENTIALS;
            } else {
                return Status.OFFLINE;
            }
        }
    }

    public String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(password.getBytes());

        byte[] hash = md.digest();

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hash) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
