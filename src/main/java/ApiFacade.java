import dto.Document;
import dto.DocumentMeta;
import dto.PermissionType;
import dto.SecurityToken;

import java.io.IOException;
import java.util.List;

public class ApiFacade
{
    private Registration  registration  = new RegistrationImpl();
    private Auth2         auth          = new Auth2Impl();
    private DocManagement docManagement = new DocManagementImpl();
    private DocSharing    docSharing    = new DocSharingImpl();

    public void registerUser(String userLogin, String encryptedPass, String userData)
        throws UserAlreadyExistException
    {
        if (!registration.isUserExist(userLogin))
        {
            registration.registerUser(userLogin, encryptedPass, userData);
        }
        else
        {
            throw new UserAlreadyExistException();
        }
    }

    public SecurityToken loginUser(String userLogin, String encryptedPass)
        throws NoSuchUserException
    {
        if (registration.isUserExist(userLogin))
        {
            return auth.loginUser(userLogin, encryptedPass);
        }
        else
        {
            throw new NoSuchUserException();
        }
    }

    public DocumentMeta createDocument(SecurityToken token, String userLogin, String docName, Document doc)
        throws IOException
    {
        if (docManagement.getDocumentMeta(token, userLogin, docName) == null)
        {
            DocumentMeta inputMeta = new DocumentMeta(userLogin, docName);
            docManagement.createDocumentMeta(token, inputMeta);
            DocumentMeta meta = docManagement.getDocumentMeta(token, userLogin, docName);
            docManagement.createDocument(token, doc, meta);
            return meta;
        }
        else
        {
            throw new IOException();
        }
    }

    public DocumentMeta updateDocument(SecurityToken token, String userLogin, String docName, Document doc)
        throws IOException
    {
        if (docManagement.getDocumentMeta(token, userLogin, docName) == null)
        {
            DocumentMeta inputMeta = new DocumentMeta(userLogin, docName);
            docManagement.updateDocumentMeta(token, inputMeta);
            DocumentMeta meta = docManagement.getDocumentMeta(token, userLogin, docName);
            docManagement.updateDocument(token, doc, meta);
            return meta;
        }
        else
        {
            throw new IOException();
        }
    }

    public void deleteDocument(SecurityToken token, String userLogin, String docName)
        throws IOException
    {
        if (docManagement.getDocumentMeta(token, userLogin, docName) == null)
        {
            DocumentMeta inputMeta = new DocumentMeta(userLogin, docName);
            docManagement.deleteDocumentMeta(token, inputMeta);
            docManagement.deleteDocument(token, inputMeta);
        }
        else
        {
            throw new IOException();
        }
    }

    public List<DocumentMeta> grantDocumentList(SecurityToken token, String granteeLogin, PermissionType permissionType)
    {
        String ownerLogin = getLoginFromToken(token);
        List<DocumentMeta> sharedDocList = docManagement.getDocumentMetaList(token, ownerLogin);
        for (DocumentMeta meta : sharedDocList)
        {
            docSharing.grantPermission(token, granteeLogin, permissionType, meta);
        }
        return sharedDocList;
    }

    private String getLoginFromToken(SecurityToken token)
    {
        return "";
    }

    public List<DocumentMeta> revokeDocumentList(SecurityToken token, String granteeLogin,
                                                 PermissionType permissionType)
    {
        String ownerLogin = getLoginFromToken(token);
        List<DocumentMeta> sharedDocList = docManagement.getDocumentMetaList(token, ownerLogin);
        for (DocumentMeta meta : sharedDocList)
        {
            docSharing.revokePermission(token, granteeLogin, permissionType, meta);
        }
        return sharedDocList;
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

        @Override
        public List<DocumentMeta> getFolderContent(SecurityToken token, String userLogin, String folderName)
        {
            return null;
        }

        @Override
        public List<DocumentMeta> createFolder(SecurityToken token, String userLogin, String folderName)
        {
            return null;
        }

        @Override
        public List<DocumentMeta> renameFolder(SecurityToken token, String userLogin, String folderName)
        {
            return null;
        }

        @Override
        public List<DocumentMeta> deleteFolder(SecurityToken token, String userLogin, String folderName)
        {
            return null;
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

    private class UserAlreadyExistException
        extends Exception
    {
    }

    private class NoSuchUserException
        extends Exception
    {
    }
}
