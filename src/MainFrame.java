import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.JLayeredPane;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String frameName = "Simple Dictionary";
	private static Dictionary veDict
		= new Dictionary("Vietnamese - English Dict", ".\\data\\viet_anh.index", ".\\data\\viet_anh.dict");
	private static Dictionary evDict
		= new Dictionary("English - Vietnamese Dict", ".\\data\\anh_viet.index", ".\\data\\anh_viet.dict");
			
	private MainPanel evPanel, vePanel;
	private JLayeredPane dictPane;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		super(frameName);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				evDict.exportData();
				veDict.exportData();
				System.exit(0);
			}
		});
		setResizable(false);
		setBounds(100, 100, 587, 487);
		
		dictPane = new JLayeredPane();
		dictPane.setLayout(null);
		setContentPane(dictPane);
		
		evPanel = new MainPanel(evDict, true);
		evPanel.setBounds(0, 11, 581, 427);
		dictPane.add(evPanel);
		
		vePanel = new MainPanel(veDict, false);
		vePanel.setBounds(0, 11, 581, 427);
		dictPane.add(vePanel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 599, 21);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				evDict.exportData();
				veDict.exportData();
				System.exit(0);
			}
		});
		
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnAddNewWord = new JMenu("Add new word");
		mnEdit.add(mnAddNewWord);
		
		JMenuItem mntmEnglishVietnamese = new JMenuItem("To English - Vietnamese Dict");
		mntmEnglishVietnamese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evPanel.addWord();
			}
		});
		mnAddNewWord.add(mntmEnglishVietnamese);
		
		JSeparator separator = new JSeparator();
		mnAddNewWord.add(separator);
		
		JMenuItem mntmVietnameseEnglish = new JMenuItem("To Vietnamese - English Dict");
		mntmVietnameseEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vePanel.addWord();
			}
		});
		mnAddNewWord.add(mntmVietnameseEnglish);
		
		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		JMenu mnDeleteWord = new JMenu("Delete word");
		mnEdit.add(mnDeleteWord);
		
		JMenuItem mntmFromEnglish = new JMenuItem("From English - Vietnamese Dict");
		mntmFromEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evPanel.deleteWord();
			}
		});
		mnDeleteWord.add(mntmFromEnglish);
		
		JSeparator separator_2 = new JSeparator();
		mnDeleteWord.add(separator_2);
		
		JMenuItem mntmFromVietnamese = new JMenuItem("From Vietnamese - English Dict");
		mntmFromVietnamese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vePanel.deleteWord();
			}
		});
		mnDeleteWord.add(mntmFromVietnamese);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String about = frameName + '\n' + "Dao Duy Hai" + "\n\n"
						+ evDict.getName() + ": " + evDict.size() + " words" + '\n'
						+ veDict.getName() + ": " + veDict.size() + " words\n\n"
						+ "This database was compiled by Ho Ngoc Duc \n"
						+ "and other members of the Free Vietnamese Dictionary Project "
						+ "\n(http://www.informatik.uni-leipzig.de/~duc/Dict/)";
				JOptionPane.showMessageDialog(null, about, "About Dictionary", JOptionPane.INFORMATION_MESSAGE);
		}});
		
		mnHelp.add(mntmAbout);
		
		JMenu menu = new JMenu("");
		menuBar.add(menu);
	
		JToggleButton tglbtnSwitchToVietnamese = new JToggleButton("Switch to V - E Dict");
		tglbtnSwitchToVietnamese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnSwitchToVietnamese.isSelected()){
					evPanel.setVisible(false);
					vePanel.setVisible(true);
					tglbtnSwitchToVietnamese.setText("Switch to E - V Dict");
				}else{
					evPanel.setVisible(true);
					vePanel.setVisible(false);
					tglbtnSwitchToVietnamese.setText("Switch to V - E Dict");
				}
			}
		});
		tglbtnSwitchToVietnamese.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dictPane.setLayer(tglbtnSwitchToVietnamese, 3);
		tglbtnSwitchToVietnamese.setBounds(433, 0, 148, 29);
		dictPane.add(tglbtnSwitchToVietnamese);
	}
	
	public void restart(){
		dispose();
		evDict.exportData();
		veDict.exportData();
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}

