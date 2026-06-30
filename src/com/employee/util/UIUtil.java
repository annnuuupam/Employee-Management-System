package com.employee.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UIUtil {

    /**
     * Styles a JButton with specific colors, cursor, font, and adds hover effect.
     */
    public static void styleButton(JButton button, Color bg, Color fg) {
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFont(Constants.FONT_BUTTON);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(bg.darker(), 1));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bg.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
    }

    /**
     * Styles JTable headers, alternates row colors, sets row height, and centers all columns.
     */
    public static void styleTable(JTable table) {
        table.setFont(Constants.FONT_FIELD);
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(Constants.COLOR_GRID_ALTERNATIVE);
        table.setSelectionBackground(Constants.COLOR_PRIMARY);
        table.setSelectionForeground(Color.WHITE);

        // Header Styling with custom renderer to override Windows Look-and-Feel default drawing
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setBackground(Constants.COLOR_PRIMARY);
                setForeground(Color.WHITE);
                setFont(new Font("Segoe UI", Font.BOLD, 14));
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(220, 224, 230)));
                return this;
            }
        });
        
        // Custom renderer for alternating row background colors and center alignments
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                
                if (isSelected) {
                    c.setBackground(Constants.COLOR_PRIMARY);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setForeground(Constants.COLOR_TEXT_DARK);
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(Constants.COLOR_GRID_ALTERNATIVE);
                    }
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    /**
     * Styles JLabels with standard typography.
     */
    public static void styleLabel(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }

    /**
     * Styles JTextFields with clean borders and Segoe UI fonts.
     */
    public static void styleTextField(JTextField textField) {
        textField.setFont(Constants.FONT_FIELD);
        Border lineBorder = BorderFactory.createLineBorder(Constants.COLOR_BORDER, 1);
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 7, 5, 7);
        textField.setBorder(BorderFactory.createCompoundBorder(lineBorder, paddingBorder));
        
        // Add focus border changes
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constants.COLOR_PRIMARY, 1),
                    paddingBorder
                ));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constants.COLOR_BORDER, 1),
                    paddingBorder
                ));
            }
        });
    }
}
