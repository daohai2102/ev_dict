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
import javax.swing.border.EmptyBorder;

class ModifyWordFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public ModifyWordFrame(MainPanel panel, Dictionary dict, String word) {
		setResizable(false);
		setTitle("Modify");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblWord = new JLabel("Word:");
		lblWord.setBounds(10, 11, 59, 25);
		contentPane.add(lblWord);
		
		JLabel lblNewLabel = new JLabel(word);
		lblNewLabel.setBounds(79, 11, 304, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblMeaning = new JLabel("Meaning:");
		lblMeaning.setBounds(10, 47, 59, 25);
		contentPane.add(lblMeaning);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(70, 47, 364, 177);
		contentPane.add(scrollPane);
		
		JTextArea editorPane = new JTextArea();
		editorPane.setFont(new Font("Arial", Font.PLAIN, 12));
		editorPane.setLineWrap(true);
		editorPane.setWrapStyleWord(true);
		//editorPane.setContentType("text/plain;charset=UTF-8");
		editorPane.setText(dict.search(word));
		scrollPane.setViewportView(editorPane);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oldMeaning = dict.search(word);
				String newMeaning = editorPane.getText();
				if (newMeaning.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter the meaning", "Error", JOptionPane.ERROR_MESSAGE);
				else
					if (oldMeaning.equals(newMeaning))
						dispose();
					else{
						int confirm = JOptionPane.showConfirmDialog(null, "Do you really want to modify \"" + word + "\"?",
								"Comfirm", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION){
							dict.addNewWord(word, newMeaning);
							panel.setMeaningPane(newMeaning);
							dispose();
						}
					}
			}
		});
		btnOk.setBounds(79, 237, 73, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(288, 237, 73, 23);
		contentPane.add(btnCancel);
		setVisible(true);
	}

}