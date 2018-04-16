package com.samsonan.queue.dispatcher;

import java.util.List;

public interface DispatcherService {

    /**
     * Start Dispatcher Service. If not started, documents will be placed in the queue, but never processed.
     */
    void start();

    /**
     * Stop Dispatcher Service
     * 
     * ��������� ����������. ������ ���������� � ������� ����������. �� ������ ������ ���� ������ �������������� ����������. 
     */
    List<DocumentItem> stop();

    /**
     * Submit a document of a given type.
     * ������� �������� �� ������. ����� �� ������ ����������� ���������� ���������. 
     * ���������� ��������� guid ��������� ��������� 
     *
     * @param documentType ��� ��������� ��� ������
     */
    String submitDocument(DocumentTypesEnum documentType);

    /**
     * �������� ������ ��������� ���������, ���� �� ��� �� ��� ���������. 
     * @param guid ������������� ���������
     */
    void cancelDocument(String guid);

    /**
     * �������� ������ ���� �������� ����������, ���� ��� ��� �� ���� ����������. 
     */
    void cancelAllDocuments();
    
    /**
     * �������� ��������������� ������ ������������ ����������. 
     * ������ ����� ���� ������������ �� �����: �� ������� ������, �� ���� ����������, �� ����������������� ������, �� ������� ������. 
     */
    List<DocumentItem> getPrintedDocuments(DocumentOrderEnum order);
    
    /**
     * ���������� ������� ����������������� ������ ������������ (��).
     */
    double calculateAveragePrintTimeMs();

    enum DocumentOrderEnum {BY_PRINT_ORDER, BY_DOC_TYPE, BY_TIME_LENGTH, BY_PAGE_SIZE};
    
}