import dto.SecurityToken;

public interface Auth2
{
    SecurityToken loginUser(String userLogin, String encryptedPass);

    void changePassword(SecurityToken token, String newEncryptedPass);

    void restorePassword(String userLogin);
}
