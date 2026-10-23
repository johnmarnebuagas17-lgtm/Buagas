package buagas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private List<GameCharacter> characterList = new ArrayList<>();

    private JTextField txtName;
    private JComboBox<String> comboType;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea txtConsole;

    public Main() {
        setTitle("Game Character Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Form Inputs
        JPanel topPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder("Character Controls"));

        topPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        topPanel.add(txtName);

        topPanel.add(new JLabel("Class:"));
        comboType = new JComboBox<>(new String[]{"Warrior", "Mage"});
        topPanel.add(comboType);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnAction = new JButton("Perform Moves");

        topPanel.add(btnAdd);
        topPanel.add(btnUpdate);
        topPanel.add(btnDelete);
        topPanel.add(btnAction);

        add(topPanel, BorderLayout.NORTH);

        // Center Search + Table
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search Name:"));
        txtSearch = new JTextField(12);
        JButton btnSearch = new JButton("Search");
        JButton btnReset = new JButton("Reset");
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Type"}, 0);
        table = new JTable(tableModel);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Output Log Console
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Console Output"));
        txtConsole = new JTextArea(5, 40);
        txtConsole.setEditable(false);
        bottomPanel.add(new JScrollPane(txtConsole), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnAdd.addActionListener(e -> addCharacter());
        btnUpdate.addActionListener(e -> updateCharacter());
        btnDelete.addActionListener(e -> deleteCharacter());
        btnAction.addActionListener(e -> runMoves());
        btnSearch.addActionListener(e -> searchCharacter());
        btnReset.addActionListener(e -> refreshTable());

        // Default initial data
        characterList.add(new Warrior("Brader"));
        characterList.add(new Mage("GabMids"));
        refreshTable();
    }

    private void addCharacter() {
        String name = txtName.getText().trim();
        String type = (String) comboType.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
            return;
        }

        GameCharacter character = type.equals("Warrior") ? new Warrior(name) : new Mage(name);
        characterList.add(character);
        txtName.setText("");
        refreshTable();
        txtConsole.append("Added: " + name + " (" + type + ")\n");
    }

    private void updateCharacter() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a character from table.");
            return;
        }

        String newName = txtName.getText().trim();
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a new name in text field.");
            return;
        }

        int index = Integer.parseInt((String) tableModel.getValueAt(selected, 0));
        String type = (String) comboType.getSelectedItem();
        GameCharacter updated = type.equals("Warrior") ? new Warrior(newName) : new Mage(newName);

        characterList.set(index, updated);
        txtName.setText("");
        refreshTable();
        txtConsole.append("Updated row " + index + " to " + newName + "\n");
    }

    private void deleteCharacter() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a character to delete.");
            return;
        }

        int index = Integer.parseInt((String) tableModel.getValueAt(selected, 0));
        GameCharacter removed = characterList.remove(index);
        refreshTable();
        txtConsole.append("Deleted: " + removed.getName() + "\n");
    }

    private void runMoves() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a character to perform moves.");
            return;
        }

        int index = Integer.parseInt((String) tableModel.getValueAt(selected, 0));
        GameCharacter c = characterList.get(index);
        txtConsole.append("--- " + c.showName() + " ---\n");
        txtConsole.append(c.attack() + "\n");
        txtConsole.append(c.specialMove() + "\n");
    }

    private void searchCharacter() {
        String query = txtSearch.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            refreshTable();
            return;
        }

        tableModel.setRowCount(0);
        for (int i = 0; i < characterList.size(); i++) {
            GameCharacter c = characterList.get(i);
            if (c.getName().toLowerCase().contains(query)) {
                String type = c instanceof Warrior ? "Warrior" : "Mage";
                tableModel.addRow(new Object[]{String.valueOf(i), c.getName(), type});
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < characterList.size(); i++) {
            GameCharacter c = characterList.get(i);
            String type = c instanceof Warrior ? "Warrior" : "Mage";
            tableModel.addRow(new Object[]{String.valueOf(i), c.getName(), type});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}