import dto.Document;
import dto.DocumentMeta;
import dto.SecurityToken;

import java.util.List;

public interface DocManagement
{
    List<DocumentMeta> getDocumentMetaList(SecurityToken token, String userLogin);

    DocumentMeta getDocumentMeta(SecurityToken token, String userLogin, String docName);

    Document getDocument(SecurityToken token, String userLogin, DocumentMeta meta);

    DocumentMeta createDocumentMeta(SecurityToken token, DocumentMeta meta);

    Document createDocument(SecurityToken token, Document doc, DocumentMeta meta);

    DocumentMeta updateDocumentMeta(SecurityToken token, DocumentMeta meta);

    Document updateDocument(SecurityToken token, Document doc, DocumentMeta meta);

    void deleteDocumentMeta(SecurityToken token, DocumentMeta meta);

    void deleteDocument(SecurityToken token, DocumentMeta meta);
}