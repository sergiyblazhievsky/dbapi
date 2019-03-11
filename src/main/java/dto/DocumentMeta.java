package dto;

import java.util.Date;

public class DocumentMeta
{
    private String docName;
    private Date   updateDate;
    private String documentLink;
    private String ownerName;

    public DocumentMeta(String ownerName, String docName)
    {
        this.ownerName = ownerName;
        this.docName = docName;
    }
}
