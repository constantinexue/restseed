package com.constx.desktop.ui;

import com.constx.Console;
import net.miginfocom.swing.MigLayout;

import javax.print.DocFlavor.STRING;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Window extends JFrame implements ActionListener, PropertyChangeListener {

    private ProgressMonitor progressMonitor;
    private JProgressBar progressBar;
    private Task task = new Task();
    private JDialog dialog = new JDialog();

    public Window() {
        //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        // http://icarusliu.iteye.com/blog/1197336
        // http://www.miglayout.com/mavensite/docs/QuickStart.pdf
        MigLayout layout = new MigLayout("wrap 2");
        JPanel panel = new JPanel(layout);
        //panel.setSize(640, 480);
        //panel.add
        add(panel);

        panel.setBackground(new Color(255, 0, 0));

        dialog = new JDialog(this);
        dialog.setModal(true);
        dialog.setSize(400, 300);
        progressMonitor = new ProgressMonitor(dialog, "Displaying", "step1", 0, 100);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(500, 100));


        JButton button = new JButton("Test is here");
        //panel.add(button, "dock south");

        panel.add(new JTextField("Test is here"), "height 100!");
        panel.add(progressBar, "width 100:1000:1000");
        panel.add(button);

        task.addPropertyChangeListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startup() {
        setSize(640, 480);
        setVisible(true);
        task.execute();
        //dialog.setVisible(true);
        //progressMonitor.setProgress(50);
    }

    public void actionPerformed(ActionEvent evt) {
        //        startButton.setEnabled(false);
        //        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //        done = false;
        //        task = new Task();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "progress":
                int progress = (int)evt.getNewValue();
                progressBar.setValue(progress);
                //progressMonitor.setProgress(progress);
                break;
            default:
                break;
        }
        Console.out(evt.toString());
    }

    public class Task extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(100);
                setProgress(i + 1);
            }
            return null;
        }
    }
}