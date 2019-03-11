import dto.DocumentMeta;
import dto.PermissionType;
import dto.SecurityToken;

import java.util.List;

public interface DocSharing
{
    void grantPermission(SecurityToken token, String granteeLogin, PermissionType permission, DocumentMeta meta);

    void revokePermission(SecurityToken token, String granteeLogin, PermissionType permission, DocumentMeta meta);

    boolean checkPermission(SecurityToken token, PermissionType permission, List<DocumentMeta> docList);
}
