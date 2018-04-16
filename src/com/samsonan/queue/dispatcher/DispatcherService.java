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
     * ќстановка диспетчера. ѕечать документов в очереди отмен€етс€. Ќа выходе должен быть список ненапечатанных документов. 
     */
    List<DocumentItem> stop();

    /**
     * Submit a document of a given type.
     * ѕрин€ть документ на печать. ћетод не должен блокировать выполнение программы. 
     * ¬озвращает уникальны guid прин€того документа 
     *
     * @param documentType “ип документа дл€ печати
     */
    String submitDocument(DocumentTypesEnum documentType);

    /**
     * ќтменить печать прин€того документа, если он еще не был напечатан. 
     * @param guid идентификатор документа
     */
    void cancelDocument(String guid);

    /**
     * ќтменить печать всех прин€тых документов, если они еще не были напечатаны. 
     */
    void cancelAllDocuments();
    
    /**
     * ѕолучить отсортированный список напечатанных документов. 
     * —писок может быть отсортирован на выбор: по пор€дку печати, по типу документов, по продолжительности печати, по размеру бумаги. 
     */
    List<DocumentItem> getPrintedDocuments(DocumentOrderEnum order);
    
    /**
     * –ассчитать среднюю продолжительность печати напечатанных (мс).
     */
    double calculateAveragePrintTimeMs();

    enum DocumentOrderEnum {BY_PRINT_ORDER, BY_DOC_TYPE, BY_TIME_LENGTH, BY_PAGE_SIZE};
    
}