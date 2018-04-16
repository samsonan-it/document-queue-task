package com.samsonan.queue.dispatcher;

import java.util.Comparator;
import java.util.UUID;

/**
 * DocumentItem represents a document to be printed
 */
public class DocumentItem {

    private final String guid;
    private final DocumentTypesEnum type;

    public DocumentItem(DocumentTypesEnum type) {
        this.type = type;
        this.guid = UUID.randomUUID().toString();
    }

    public String getGuid() {
        return guid;
    }

    public DocumentTypesEnum getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DocumentItem [guid=" + guid + ", type=" + type + "]";
    }
   
    
    public static class DocumentTypeComparator implements Comparator<DocumentItem> {

        @Override
        public int compare(DocumentItem doc1, DocumentItem doc2) {
            return doc1.type.ordinal() - doc2.type.ordinal();
        }
        
    }

    public static class DocumentTimeComparator implements Comparator<DocumentItem> {

        @Override
        public int compare(DocumentItem doc1, DocumentItem doc2) {
            long v1 = doc1.type.getPrintTimeMs();
            long v2 = doc2.type.getPrintTimeMs();
            return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
        }
        
    }    

    public static class DocumentPageSizeComparator implements Comparator<DocumentItem> {

        @Override
        public int compare(DocumentItem doc1, DocumentItem doc2) {
            return doc1.type.getPageSize().compareTo(doc2.type.getPageSize());
        }
        
    }    
    
}
