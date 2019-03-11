import dto.Document;
import dto.DocumentMeta;
import dto.PermissionType;
import dto.SecurityToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UseCases
{
    private Registration  registration  = new RegistrationImpl();
    private Auth2         auth          = new Auth2Impl();
    private DocManagement docManagement = new DocManagementImpl();
    private DocSharing    docSharing    = new DocSharingImpl();
    private ApiFacade     facade        = new ApiFacade();

    @Test
    public void testUserRegister()
    {
        Assert.assertFalse(registration.isUserExist("testUser"));
        registration.registerUser("testUser", "testEncryptedPass", "testUserData");
        Assert.assertTrue(registration.isUserExist("testUser"));
    }

    @Test
    public void testUserLogin()
    {
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "testEncryptedPass");
        Assert.assertNotNull(token);
    }

    @Test
    public void testPasswordChange()
    {
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "testEncryptedPass");
        Assert.assertNotNull(token);
        auth.changePassword(token, "newEncryptedPass");
        SecurityToken newToken = auth.loginUser("testUser", "newEncryptedPass");
        Assert.assertNotNull(newToken);
    }

    @Test
    public void testPasswordRestore()
    {
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "wrongEncryptedPass");
        Assert.assertNull(token);
        auth.restorePassword("testUser");
        SecurityToken newToken = auth.loginUser("testUser", "newEncryptedGeneratedPass");
        Assert.assertNotNull(newToken);
    }

    @Test
    public void testCRUDDocument()
    {
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "testEncryptedPass");
        Assert.assertNotNull(token);

        DocumentMeta meta = docManagement.getDocumentMeta(token, "testUser", "testDocName");
        Assert.assertNull(meta);
        try
        {
            DocumentMeta createdDocMeta = facade.createDocument(token, "testUser", "testDocName", new Document());
            Assert.assertNotNull(createdDocMeta);
        }
        catch (Exception e)
        {
            //exception handling
        }

        try
        {
            DocumentMeta updatedDocMeta = facade.updateDocument(token, "testUser", "testDocName", new Document());
            Assert.assertNotNull(updatedDocMeta);
        }
        catch (Exception e)
        {
            //exception handling
        }

        try
        {
            facade.deleteDocument(token, "testUser", "testDocName");
            DocumentMeta deletedDocMeta = docManagement.getDocumentMeta(token, "testUser", "testDocName");
            Assert.assertNull(meta);
        }
        catch (Exception e)
        {
            //exception handling
        }
    }

    @Test
    public void testSharingDocumentList()
    {
        //Docs owner part
        Assert.assertTrue(registration.isUserExist("testUserWithSharedDocs"));
        SecurityToken sharingUserToken = auth.loginUser("testUserWithSharedDocs", "testSharingUserEncryptedPass");
        Assert.assertNotNull(sharingUserToken);
        List<DocumentMeta> sharedDocList = facade.grantDocumentList(sharingUserToken, "testUser", PermissionType.READ);

        //User accessing shared docs' part
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "testEncryptedPass");
        Assert.assertNotNull(token);
        Assert.assertTrue(docSharing.checkPermission(token, PermissionType.READ, sharedDocList));
    }

    @Test
    public void testReadSharedDocumentList()
    {
        //Docs owner part
        Assert.assertTrue(registration.isUserExist("testUserWithSharedDocs"));
        SecurityToken sharingUserToken = auth.loginUser("testUserWithSharedDocs", "testSharingUserEncryptedPass");
        Assert.assertNotNull(sharingUserToken);
        List<DocumentMeta> sharedDocList =
            docManagement.getDocumentMetaList(sharingUserToken, "testUserWithSharedDocs");
        Assert.assertTrue(sharedDocList.size() > 0);

        //User accessing shared docs' part
        Assert.assertTrue(registration.isUserExist("testUser"));
        SecurityToken token = auth.loginUser("testUser", "testEncryptedPass");
        Assert.assertNotNull(token);

        Assert.assertTrue(docSharing.checkPermission(token, PermissionType.READ, sharedDocList));
        List<DocumentMeta> availableDocList = docManagement.getDocumentMetaList(token, "testUserWithSharedDocs");
        Assert.assertTrue(availableDocList.size() > 0);
    }

    private class RegistrationImpl
        implements Registration
    {
        @Override
        public boolean isUserExist(String login)
        {
            return false;
        }

        @Override
        public void registerUser(String login, String encryptedPass, String userData)
        {
        }
    }

    private class Auth2Impl
        implements Auth2
    {
        @Override
        public SecurityToken loginUser(String userLogin, String encryptedPass)
        {
            return null;
        }

        @Override
        public void changePassword(SecurityToken token, String newEncryptedPass)
        {

        }

        @Override
        public void restorePassword(String userLogin)
        {

        }
    }

    private class DocManagementImpl
        implements DocManagement
    {
        @Override
        public List<DocumentMeta> getDocumentMetaList(SecurityToken token, String userLogin)
        {
            return null;
        }

        @Override
        public DocumentMeta getDocumentMeta(SecurityToken token, String userLogin, String docName)
        {
            return null;
        }

        @Override
        public Document getDocument(SecurityToken token, String userLogin, DocumentMeta meta)
        {
            return null;
        }

        @Override
        public DocumentMeta createDocumentMeta(SecurityToken token, DocumentMeta meta)
        {
            return null;
        }

        @Override
        public Document createDocument(SecurityToken token, Document doc, DocumentMeta meta)
        {
            return null;
        }

        @Override
        public DocumentMeta updateDocumentMeta(SecurityToken token, DocumentMeta meta)
        {
            return null;
        }

        @Override
        public Document updateDocument(SecurityToken token, Document doc, DocumentMeta meta)
        {
            return null;
        }

        @Override
        public void deleteDocumentMeta(SecurityToken token, DocumentMeta meta)
        {

        }

        @Override
        public void deleteDocument(SecurityToken token, DocumentMeta meta)
        {

        }
    }

    private class DocSharingImpl
        implements DocSharing
    {
        @Override
        public void grantPermission(SecurityToken token, String granteeLogin, PermissionType permission,
                                    DocumentMeta meta)
        {

        }

        @Override
        public void revokePermission(SecurityToken token, String granteeLogin, PermissionType permission,
                                     DocumentMeta meta)
        {

        }

        @Override
        public boolean checkPermission(SecurityToken token, PermissionType permission, List<DocumentMeta> docList)
        {
            return false;
        }
    }
}
