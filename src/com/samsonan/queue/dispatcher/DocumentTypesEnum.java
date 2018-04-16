package com.samsonan.queue.dispatcher;

public enum DocumentTypesEnum {
    
    AGREEMENT(3000, "Agreement", "A4"),
    LETTER(1000, "Letter", "LetterA4"),
    NOTE(500, "Note", "A5");

    private final long printTimeMs;
    private final String typeName;
    private final String pageSize;
    
    private DocumentTypesEnum(long printTimeMs, String typeName, String pageSize) {
        this.printTimeMs = printTimeMs;
        this.typeName = typeName;
        this.pageSize = pageSize;
    }

    public long getPrintTimeMs() {
        return printTimeMs;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getPageSize() {
        return pageSize;
    }

}
