package com.samsonan.queue.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class DispatcherServiceImpl implements Runnable, DispatcherService {

    /**
     * One threaded service. We may be sure that queues are processed only by ONE
     * thread at a time.
     */
    private ExecutorService printExecutorService = Executors.newSingleThreadExecutor();

    private BlockingQueue<DocumentItem> documentsToPrintQueue = new LinkedBlockingQueue<>();
    private Queue<DocumentItem> printedDocumentsQueue = new ConcurrentLinkedQueue<>();

    private DocumentItem currentDocument = null;

    private AtomicLong totalTime = new AtomicLong();

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    synchronized public void start() {

        if (running.get()) {
            return;
        }
        running.set(true);
        printExecutorService.submit(this);
    }

    @Override
    synchronized public List<DocumentItem> stop() {
        running.set(false);
        printExecutorService.shutdownNow();

        List<DocumentItem> notPrinted = new ArrayList<DocumentItem>(documentsToPrintQueue);
        if (currentDocument != null) {
            notPrinted.add(currentDocument);
        }

        return notPrinted;
    }

    @Override
    public String submitDocument(DocumentTypesEnum documentType) {
        DocumentItem doc = new DocumentItem(documentType);
        // queue has "unlimited" capacity, so we assume that there is always a space in
        // the queue
        documentsToPrintQueue.offer(doc);

        System.out.println("Document submitted: " + doc);

        return doc.getGuid();
    }

    @Override
    public void run() {
        while (running.get()) {

            try {
                currentDocument = documentsToPrintQueue.take();

                System.out.println("Going to print: " + currentDocument);

                long printTimeMs = currentDocument.getType().getPrintTimeMs();

                Thread.sleep(printTimeMs);

                System.out.println("Finished printing: " + currentDocument);

                printedDocumentsQueue.add(currentDocument);

                totalTime.addAndGet(printTimeMs);

            } catch (InterruptedException e) {
                System.out.println("Printing interrupted. Current Doc: " + currentDocument);
            }
        }
    }

    @Override
    public void cancelDocument(String guid) {
        for (DocumentItem doc : documentsToPrintQueue) {
            // blocking queue iterator is weakly consistent, it's thread safe and we never
            // get ConcurrentModificationException
            if (doc.getGuid().equals(guid)) {
                documentsToPrintQueue.remove(doc);
                break;
            }
        }
    }

    @Override
    public void cancelAllDocuments() {
        documentsToPrintQueue.clear();
    }

    @Override
    public List<DocumentItem> getPrintedDocuments(DocumentOrderEnum documentOrder) {

        List<DocumentItem> docs = new ArrayList<DocumentItem>(printedDocumentsQueue);

        switch (documentOrder) {
        case BY_DOC_TYPE:

            docs.sort(new DocumentItem.DocumentTypeComparator());
            break;

        case BY_PAGE_SIZE:

            docs.sort(new DocumentItem.DocumentPageSizeComparator());
            break;

        case BY_TIME_LENGTH:
            docs.sort(new DocumentItem.DocumentTimeComparator());
            break;

        default:
            break;
        }

        // BY_PRINT_ORDER - default
        return docs;

    }

    @Override
    public double calculateAveragePrintTimeMs() {
        if (printedDocumentsQueue.isEmpty()) {
            return 0;
        }
        return totalTime.get() / printedDocumentsQueue.size();
    }

}
