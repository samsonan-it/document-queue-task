package com.samsonan.queue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.samsonan.queue.dispatcher.DispatcherService;
import com.samsonan.queue.dispatcher.DispatcherServiceImpl;
import com.samsonan.queue.dispatcher.DocumentItem;
import com.samsonan.queue.dispatcher.DocumentTypesEnum;

import static com.samsonan.queue.dispatcher.DispatcherService.DocumentOrderEnum;

public class DocumentQueueSwingApp {

    private JTextArea textArea;
    private DispatcherService dispatcherService;
    
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DocumentQueueSwingApp().createAndShowGUI();
            }
        });

    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("Document Dispatcher Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentsToPane(Container pane) {

        JButton printDoc1Btn = new JButton("������� �������� ��� 1");
        JButton printDoc2Btn = new JButton("������� �������� ��� 2");
        JButton printDoc3Btn = new JButton("������� �������� ��� 3");

        JButton cancelQueueBtn = new JButton("�������� ��� ��������");
        JButton stopServiceBtn = new JButton("���������� ���������");
        JButton listPrintedByOrderBtn = new JButton("������ ������������ (�� �������)");
        JButton listPrintedByTypeBtn = new JButton("������ ������������ (�� ����)");
        JButton listPrintedByLengthBtn = new JButton("������ ������������ (�� �����������������)");
        JButton listPrintedBySizeBtn = new JButton("������ ������������ (�� �������)");
        JButton getAvgPrintTimeBtn = new JButton("�������� ������� ����� ������");
        
        printDoc1Btn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatcherService.submitDocument(DocumentTypesEnum.AGREEMENT);
                textArea.append("�������� ��� 1 ������\n");
            }
        });

        printDoc2Btn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatcherService.submitDocument(DocumentTypesEnum.LETTER);
                textArea.append("�������� ��� 2 ������\n");
            }
        });

        printDoc3Btn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatcherService.submitDocument(DocumentTypesEnum.NOTE);
                textArea.append("�������� ��� 3 ������\n");
            }
        });
        
        cancelQueueBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatcherService.cancelAllDocuments();
                textArea.append("������ ���� �������������� ���������� ��������.\n");
            }
        });        

        stopServiceBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("�������� ��������� �������\n");
                List<DocumentItem> docs = dispatcherService.stop();
                textArea.append("�������������� ���������: \n");
                for (DocumentItem doc : docs) {
                    textArea.append(doc + "\n");
                }
            }
        });        

        listPrintedByOrderBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("������������ ���������: \n");
                for (DocumentItem doc : dispatcherService.getPrintedDocuments(DocumentOrderEnum.BY_PRINT_ORDER)) {
                    textArea.append(doc + "\n");
                }
            }
        });        

        listPrintedByLengthBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("������������ ���������: \n");
                for (DocumentItem doc : dispatcherService.getPrintedDocuments(DocumentOrderEnum.BY_TIME_LENGTH)) {
                    textArea.append(doc + "\n");
                }
            }
        });        

        listPrintedBySizeBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("������������ ���������: \n");
                for (DocumentItem doc : dispatcherService.getPrintedDocuments(DocumentOrderEnum.BY_PAGE_SIZE)) {
                    textArea.append(doc + "\n");
                }
            }
        });        
        
        listPrintedByTypeBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("������������ ���������: \n");
                for (DocumentItem doc : dispatcherService.getPrintedDocuments(DocumentOrderEnum.BY_DOC_TYPE)) {
                    textArea.append(doc + "\n");
                }
            }
        });        

        getAvgPrintTimeBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append("������� ����� ������: " + dispatcherService.calculateAveragePrintTimeMs() + "\n");
            }
        });   
        
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.Y_AXIS));
        
        buttonsPane.add(printDoc1Btn);
        buttonsPane.add(printDoc2Btn);
        buttonsPane.add(printDoc3Btn);
        
        buttonsPane.add(cancelQueueBtn);
        buttonsPane.add(stopServiceBtn);
        buttonsPane.add(listPrintedByOrderBtn);
        buttonsPane.add(listPrintedByTypeBtn);
        buttonsPane.add(listPrintedByLengthBtn);
        buttonsPane.add(listPrintedBySizeBtn);
        buttonsPane.add(getAvgPrintTimeBtn);
        
        pane.add(buttonsPane, BorderLayout.LINE_START);
        
        textArea = new JTextArea(20, 50);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);
        
        pane.add(scrollPane, BorderLayout.LINE_END);
    }

    public DocumentQueueSwingApp() {

        // We could be using Factory here if the app was more sophisticated
        dispatcherService = new DispatcherServiceImpl();
        dispatcherService.start();

    }

}
