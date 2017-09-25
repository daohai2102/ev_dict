import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class DeleteWordFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	/**
	 * Create the frame.
	 */
	public DeleteWordFrame(MainPanel panel, Dictionary dict){
		setTitle("Delete word");
		setVisible(true);
		setResizable(false);
		setBounds(100, 100, 408, 159);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblDeleteWordFrom = new JLabel("Delete word from" + ' ' + dict.getName());
		lblDeleteWordFrom.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteWordFrom.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDeleteWordFrom.setBounds(10, 11, 382, 35);
		contentPane.add(lblDeleteWordFrom);
		
		JLabel lblWord = new JLabel("Word:");
		lblWord.setBounds(10, 49, 52, 25);
		contentPane.add(lblWord);
		
		textField = new JTextField();
		textField.setBounds(72, 51, 299, 25);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = textField.getText();
				if (word.equals(""))
					JOptionPane.showMessageDialog(null, "Please enter a word", "Error", JOptionPane.ERROR_MESSAGE);
				else{
					int comfirm = JOptionPane.showConfirmDialog(null, "Do you really want to delete the word \"" + 
															word + "\"?", "Comfirm", JOptionPane.YES_NO_OPTION);
					if (comfirm == JOptionPane.YES_OPTION){
						dict.remove(word);
						panel.refreshWordsList();//refresh wordsList after deleting
						dispose();
					}
				}
			}
		});
		btnOk.setBounds(55, 96, 73, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(265, 96, 73, 23);
		contentPane.add(btnCancel);
					
		}
}