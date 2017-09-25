import javax.swing.JPanel;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JTextArea meaningPane;
	private JList<String> wordsList;
	private Dictionary dict;
	
	/**
	 * Create the panel.
	 */
	public MainPanel(Dictionary dict1, boolean aFlag) {
		MainPanel that = this;
		this.dict = dict1;
		setLayout(null);
		setVisible(aFlag);
		JLabel label = new JLabel(dict.getName());
		label.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label.setBounds(10, 11, 250, 25);
		add(label);
		
		JScrollPane meaningScrollPane = new JScrollPane();
		meaningScrollPane.setBounds(222, 90, 350, 327);
		add(meaningScrollPane);
		
		meaningPane = new JTextArea();
		meaningPane.setFont(new Font("Arial", Font.PLAIN, 12));
		meaningPane.setLineWrap(true);
		meaningPane.setWrapStyleWord(true);
		//meaningPane.setContentType("text/plain;charset=UTF-8");
		meaningPane.setEditable(false);
		meaningScrollPane.setViewportView(meaningPane);
		
		searchField = new JTextField();
		searchField.setToolTipText("search");
		searchField.setBounds(10, 47, 250, 30);
		add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton(new ImageIcon("icon/search-icon.png"));
		btnSearch.setToolTipText("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = searchField.getText();
				if (word.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a word!", "Text Field is empty", JOptionPane.ERROR_MESSAGE);
				else{
					String meaning = dict.search(word);
					if (meaning != null)
						meaningPane.setText(meaning);
					else
						meaningPane.setText("Từ này chưa có trong từ điển");
				}
			}
		});
		btnSearch.setBounds(270, 47, 30, 30);
		add(btnSearch);
		
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSearch.doClick();
			}
		});
		
		JScrollPane wordsListScrollPane = new JScrollPane();
		wordsListScrollPane.setBounds(10, 90, 170, 329);
		add(wordsListScrollPane);
		
		DefaultListModel<String> listModel = createListModel(dict);
		wordsList = new JList<>(listModel);
		wordsList.setFont(new Font("Arial", Font.PLAIN, 12));
		wordsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String selectedWord = wordsList.getSelectedValue();
				if (selectedWord != null){
					searchField.setText(selectedWord);
					meaningPane.setText(dict.search(selectedWord));
				}	
			}
		});
		wordsListScrollPane.setViewportView(wordsList);
		
		JButton btnDelete = new JButton(new ImageIcon("icon/trash-icon.png"));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = searchField.getText();
				if (word.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a word", "Error", JOptionPane.ERROR_MESSAGE);
				else{
					int comfirm = JOptionPane.showConfirmDialog(null, "Do you really want to delete the word \"" + 
															word + "\"?", "Comfirm", JOptionPane.YES_NO_OPTION);
					if (comfirm == JOptionPane.YES_OPTION){
						dict.remove(word);
						meaningPane.setText("");
						searchField.setText("");
						wordsList.setModel(createListModel(dict));//refresh wordsList after deleting
					}
				}
			}
		});
		btnDelete.setToolTipText("Delete this word");
		btnDelete.setBounds(542, 49, 30, 30);
		add(btnDelete);
		
		JButton btnModify = new JButton(new ImageIcon("icon/modify-icon.png"));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = searchField.getText();
				if (word.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a word", "Error", JOptionPane.ERROR_MESSAGE);
				else{
					new ModifyWordFrame(that, dict, word);
				}
						
			}
		});
		btnModify.setToolTipText("Modify this word");
		btnModify.setBounds(503, 49, 30, 30);
		add(btnModify);
		
		JButton btnAddNewWord = new JButton(new ImageIcon("icon/new-icon.jpg"));
		btnAddNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = searchField.getText();
				if (dict.search(word) == null){
					new AddNewWordFrame(that, dict, word);
				}
				else{
					JOptionPane.showMessageDialog(null, '\"' + word + "\" was existed");
				}
			}
		});
		btnAddNewWord.setToolTipText("Add this word");
		btnAddNewWord.setBounds(463, 49, 30, 30);
		add(btnAddNewWord);
		
		JLabel narrow = new JLabel(new ImageIcon("icon/narrow.png"));
		narrow.setBounds(182, 90, 37, 327);
		add(narrow);
	}
	
	private DefaultListModel<String> createListModel(Dictionary dict){
		DefaultListModel<String> listModel = new DefaultListModel<>();
		ArrayList<String> wordsList = dict.getWordList();
		for (String word:wordsList){
			listModel.addElement(word);
		}
		return listModel;
	}
	
	public void addWord(){
		new AddNewWordFrame(this, dict);
	}
	
	public void deleteWord(){
		new DeleteWordFrame(this, dict);
	}
	
	public void refreshWordsList(){
		wordsList.setModel(createListModel(dict));
	}
	
	public void setMeaningPane(String text){
		meaningPane.setText(text);
	}
}
