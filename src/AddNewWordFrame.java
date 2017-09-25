import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class AddNewWordFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Dictionary dict;
	private String word;
	private JTextField wordField;
	
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public AddNewWordFrame(MainPanel panel, Dictionary dict) {
		super("New word");
		setResizable(false);
		this.dict = dict;
		word = null;
		newFrame(panel);
	}
	
	public AddNewWordFrame(MainPanel panel, Dictionary dict, String word){
		super("New word");
		this.dict = dict;
		this.word = word;
		newFrame(panel);
	}
	
	public void newFrame(MainPanel panel){
		setBounds(100, 100, 450, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setVisible(true);
		
		JLabel lblWord = new JLabel("Word: ");
		lblWord.setBounds(10, 42, 43, 27);
		contentPane.add(lblWord);
		
		JLabel lblMeaning = new JLabel("Meaning:");
		lblMeaning.setBounds(10, 80, 54, 27);
		contentPane.add(lblMeaning);
		
		wordField = new JTextField();
		wordField.setBounds(63, 45, 361, 24);
		if (word != null)
			wordField.setText(word);
		contentPane.add(wordField);
		wordField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(63, 80, 361, 185);
		contentPane.add(scrollPane);
		
		JTextArea meaningField = new JTextArea();
		meaningField.setFont(new Font("Arial", Font.PLAIN, 12));
		meaningField.setLineWrap(true);
		meaningField.setWrapStyleWord(true);
		scrollPane.setViewportView(meaningField);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typedWord = wordField.getText();
				String meaning = meaningField.getText();
				if (typedWord.equals("") || meaning.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a word and its meaning", "Error", JOptionPane.ERROR_MESSAGE);
				else{
					if (dict.search(typedWord) != null)
						JOptionPane.showMessageDialog(null, '\"' + typedWord + "\" was existed", "Error", JOptionPane.ERROR_MESSAGE);
					else{
						int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to add this word?", "Comfirm", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.OK_OPTION){
							dict.addNewWord(typedWord, meaning);
							panel.refreshWordsList();//refresh wordsList after adding
							dispose();
						}
					}
				}
			}
		});
		btnOk.setBounds(96, 270, 73, 23);
		contentPane.add(btnOk);
		
		JButton btnCanc = new JButton("Cancel");
		btnCanc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCanc.setBounds(284, 270, 73, 23);
		contentPane.add(btnCanc);
		
		JLabel lblAddNewWord = new JLabel("Add new word to" + ' ' + dict.getName());
		lblAddNewWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewWord.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAddNewWord.setBounds(10, 11, 414, 23);
		contentPane.add(lblAddNewWord);
	}
}