package ui;

import model.TimeBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static ui.WeekPlannerFrame.*;

// tools panel containing open and save buttons in addition to red and yellow radio buttons
public class ToolsPanel extends JPanel implements ActionListener {
    WeekPanel weekPanel;
    private final JButton open;
    private final JButton save;
    private final JFileChooser fileChooser;
    private final JRadioButton red;
    private final JRadioButton yellow;

    // EFFECTS: setup this panel with flow layout
    //          Add open and save buttons
    //          Add red and yellow radio buttons
    public ToolsPanel(WeekPanel weekPanel) {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(7 * COLUMN_WIDTH, 35));
        this.setBackground(MIDNIGHT_BLUE);
        this.setBorder(BorderFactory.createLineBorder(WET_ASPHALT));
        this.setOpaque(true);

        this.weekPanel = weekPanel;
        open = new JButton("Open");
        open.addActionListener(this);
        this.add(open);

        save = new JButton("Save");
        save.addActionListener(this);
        this.add(save);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./data/"));

        red = new JRadioButton("Red", true);
        yellow = new JRadioButton("Yellow");
        red.addActionListener(this);
        yellow.addActionListener(this);
        this.add(red);
        this.add(yellow);

        ButtonGroup group = new ButtonGroup();
        group.add(red);
        group.add(yellow);
    }

    // EFFECTS: Handle open and save buttons
    //          Handle red and yellow radio buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                weekPanel.loadWeekPlan(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == save) {
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                weekPanel.saveWeekPlan(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getSource() == red) {
            weekPanel.setTimeBlockColor(TimeBlock.Color.RED);
        } else if (e.getSource() == yellow) {
            weekPanel.setTimeBlockColor(TimeBlock.Color.YELLOW);
        }
    }
}
