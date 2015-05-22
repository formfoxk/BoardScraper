/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *  
 * 
 * @FileName : ScraperSouthPanel.java 
 * @Project : D445WebCrawler 
 * @Date : 2015. 2. 14. 
 * @작성자 : YuJoo 
 * @프로그램 설명 : Scraper의 하위 Table을 나타낸다.
 * @프로그램 기능 : Scraper의 Table기능 구현
 * @변경이력 : 
 */
public class SouthPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Debug용 변수
	private boolean DEBUG = true;
	private static final Logger logger = Logger.getLogger(SouthPanel.class.getName());

	// 패널에 추가할 멤버 컴포넌트들
	private JTextField tfNameOfScraper;
	private JTextField tfAuthor;
	private JTable table;
	private JScrollPane jsp;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnImport;
	private JButton btnExport;
	private JButton btnExecute;

	// Default 생성자
	public SouthPanel() {
		
		// 멤버들의 인스턴스 생성
		creatingMemberInstance();
		// 패널 설정
		settingPanel();
		// 리스너 설정
		settingListener();
		// 그외 초기 정보 설정
		initialize();
	}

	/**
	 * @Method Name : _creagingMemberInstance 
	 * @Method 기능 : 멤버 변수 초기화
	 * @변경이력 :  
	 */
	private void creatingMemberInstance() {
		// TextField 생성
		tfNameOfScraper = new JTextField(10);
		tfAuthor = new JTextField(10);
		// Button 생성
		btnNew = new JButton("New");
		btnDelete = new JButton("Delete");
		btnImport = new JButton("Import");
		btnExport = new JButton("Export");
		btnExecute = new JButton("Execute");
		
		// Table 생성
		table = new BaseTable();
		// JScrollPane 생성
		jsp = new JScrollPane(table);
	}

	/**
	 *  
	 * 
	 * @Method Name : _settingPanel 
	 * @Method 기능 : 패널 배치
	 * @변경이력 :  
	 */
	private void settingPanel() {
		this.setLayout(new BorderLayout());

		/** Center Panel 설정 */
		// CenterPanel 생성
		JPanel panelCenter = new JPanel(new BorderLayout());

		// CenterPanel의 North
		JPanel panelLabel = new JPanel(new BorderLayout());
		JPanel panelLabelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel panelLabelright = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lbNameOfScraperView = new JLabel("NameOfScraper : ");
		JLabel lbAuthorView = new JLabel("Author :");
		panelLabelLeft.add(lbNameOfScraperView);
		panelLabelLeft.add(tfNameOfScraper);
		panelLabelLeft.add(lbAuthorView);
		panelLabelLeft.add(tfAuthor);
		panelLabel.add("West", panelLabelLeft);
		panelLabel.add("East", panelLabelright);

		// CenterPanel 설정
		panelCenter.add("North", panelLabel);
		panelCenter.add("Center", jsp);

		// Center Panel 테두리
		Border borderCenter = BorderFactory.createTitledBorder(null,
				"Scraper Table", TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"times new roman", Font.PLAIN, 20), Color.BLACK);
		panelCenter.setBorder(borderCenter);

		this.add("Center", panelCenter);

		/** 버튼들을 가지는 패널 생성 및 설정 */
		JPanel PanelButton = new JPanel(new BorderLayout());

		JPanel PanelLeftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		PanelLeftButtons.add(btnNew);
		PanelLeftButtons.add(btnDelete);
		PanelLeftButtons.add(btnImport);
		PanelLeftButtons.add(btnExport);

		JPanel PanelRightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		PanelRightButtons.add(btnExecute);

		PanelButton.add(PanelLeftButtons, "West");
		PanelButton.add(PanelRightButtons, "East");

		this.add("South", PanelButton);
		this.setVisible(true);
	}
	
	/**
	 *  
	 * 
	 * @Method Name : _settingListener 
	 * @Method 기능 : 리스너 설정
	 * @변경이력 :  
	 */
	private void settingListener() {
		btnNew.addActionListener(this);
		btnDelete.addActionListener(this);
		btnImport.addActionListener(this);
		btnExport.addActionListener(this);
		btnExecute.addActionListener(this);
	}
	
	/**
	 *  
	 * 
	 * @Method Name : _initialize 
	 * @Method 기능 : 나머지 초기 설정
	 * @변경이력 :  
	 */
	private void initialize() {
		
	}
	
/**************************************************************************************/
/**										이벤트 처리		     				    	     **/
/**************************************************************************************/
	/**
	 *  
	 * 
	 * @Method Name : actionPerformed 
	 * @Method 기능 : 버튼 이벤트 처리 함수
	 * @변경이력 :  
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNew) {
			
		} else if (e.getSource() == btnDelete) {
			
		} else if (e.getSource() == btnImport) {

		} else if (e.getSource() == btnExport) {

		} else if (e.getSource() == btnExecute) {
			
		}
	}
}
