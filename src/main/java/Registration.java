public interface Registration
{
    boolean isUserExist(String userLogin);

    void registerUser(String userLogin, String encryptedPass, String userData);
}
