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
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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

	// 테이블 관리자
	private SouthTableManager tableMng;
	
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
		table = new SouthBaseTable();
		// JScrollPane 생성
		jsp = new JScrollPane(table);
		// Table Manager 생성
		tableMng = new SouthTableManager();
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
		// 테이블 헤더 설정
		tableMng.setHeaderProperty(table.getTableHeader());

		// 툴팁 메니저
		tableMng.setToolTip();
		
		// Table Header 생성
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
		Vector<Object> cols;

		cols = new Vector<Object>();
		cols.add("Check");
		cols.add("Description");
		cols.add("CssSelector");
		rows.add(cols);
		
		// table을 다시 구축 한다.
		tableMng.reloadTable(table, rows);
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
			executeBtnNew();
		} else if (e.getSource() == btnDelete) {
			executeBtnDelete();
		} else if (e.getSource() == btnImport) {

		} else if (e.getSource() == btnExport) {

		} else if (e.getSource() == btnExecute) {
			
		}
	}
	
	/**
	 *  
	 * 
	 * @Method Name : executeBtnNew 
	 * @Method 기능 : New버튼을 눌렀을 때 실행되는 함수로 Table의 행을 하나 생성한다.
	 * @변경이력 :  
	 */
	private void executeBtnNew() {
		Vector<Object> data = new Vector<Object>();
		data.add(new Boolean(true));
		data.add("");
		data.add("");

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(data);
	}
	

	/**
	 *  
	 * 
	 * @Method Name : executeBtnDelete 
	 * @Method 기능 : Check된 Table의 Row값을 삭제
	 * @변경이력 :  
	 */
	private void executeBtnDelete() {
		int checkCount = getTableFirshRowsCheckCount();

		// Scraper를 Check하지 않은 경우
		if (checkCount < 1)
			JOptionPane.showMessageDialog(null, "테이블의 행을  Check하지 않았습니다.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
		// Scraper를 하나 이상 Check 한 경우
		else {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();

			for (int i = dtm.getRowCount()-1; i >= 0; i--) {
				// check 된 행 삭제
				if (dtm.getValueAt(i, 0) != null
						&& (Boolean) dtm.getValueAt(i, 0) == true) {
					dtm.removeRow(i);
				}
			}
		}
	}
	
	/**
	 *  
	 * 
	 * @Method Name : getCheckCount 
	 * @Method 기능 : Table의 첫번 째 열의 CheckBox행 값들이 TRUE인 것의 갯수를 구한다.
	 * @변경이력 : 
	 * @return 
	 */
	private int getTableFirshRowsCheckCount() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		int count = 0;

		for (int i = 0; i < dtm.getRowCount(); i++) {
			if (dtm.getValueAt(i, 0) != null
					&& (Boolean) dtm.getValueAt(i, 0) == true) {
				count++;
			}
		}

		return count;
	}
}
