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
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import kumoh.sig.boardwebcrawler.controller.ScraperController;
import kumoh.sig.boardwebcrawler.model.data.XmlNode;

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

	// 패널에 추가할 멤버 컴포넌트들
	private JTextField tfNameOfScraper;
	private JTextField tfAuthor;
	private JTextField tfUrl;
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
		tfUrl = new JTextField(10);
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
		JLabel lbUrlView = new JLabel("Url :");
		panelLabelLeft.add(lbNameOfScraperView);
		panelLabelLeft.add(tfNameOfScraper);
		panelLabelLeft.add(lbAuthorView);
		panelLabelLeft.add(tfAuthor);
		panelLabelLeft.add(lbUrlView);
		panelLabelLeft.add(tfUrl);
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
			executeBtnImport();
		} else if (e.getSource() == btnExport) {
			executeBtnExport();
		} else if (e.getSource() == btnExecute) {
			executeBtnExecute();
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
	
	/** 
	* @Method Name	: executeBtnImport 
	* @Method 설명    	: scraper Table의 import를 처리하는 함수
	* @변경이력      	: 
	*/
	private void executeBtnImport(){
		
				
		JFileChooser fileChooser = new JFileChooser();
        
		// 모든 파일을 접근 하지 못하도록 설정
		fileChooser.setAcceptAllFileFilterUsed(false);
		
        //기본 Path의 경로 설정 (바탕화면)
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));    	
        
        //필터링될 확장자
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xml 파일", "xml");
         
        //필터링될 확장자 추가
        fileChooser.addChoosableFileFilter(filter);
         
        //파일오픈 다이얼로그 를 띄움
        int result = fileChooser.showOpenDialog(this);
         
        if (result == JFileChooser.APPROVE_OPTION) {
            //선택한 파일의 경로 반환
            File selectedFile = fileChooser.getSelectedFile();
             
            String[] subInfo = new String[3];
            
            ScraperController sc = ScraperController.getInstance();
			sc.importXmlFileOfScraperTable(selectedFile, table, subInfo);
			
			// 텍스트 필드 설정
			tfNameOfScraper.setText(subInfo[0]);
			tfAuthor.setText(subInfo[1]);
			tfUrl.setText(subInfo[2]);
        }
	}
	
	/** 
	* @Method Name	: executeBtnExport 
	* @Method 설명    	: scraper Table의 Export를 처리하는 함수
	* @변경이력      	: 
	*/
	private void executeBtnExport(){
		// 파일명과 파일생성자명 
		String tableName = tfNameOfScraper.getText();
		String author = tfAuthor.getText();
		String url = tfUrl.getText();
		
		if(tableName.isEmpty() || author.isEmpty() || url.isEmpty())
			JOptionPane.showMessageDialog(null, "NameOfScraper 또는 Author의 값이 입력 되지 않았습니다.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		else{
			JFileChooser fileChooser = new JFileChooser();

			// 모든 파일을 접근 하지 못하도록 설정
			fileChooser.setAcceptAllFileFilterUsed(false);

			// 기본 Path의 경로 설정 (바탕화면)
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home") + "//" + "Desktop"));

			// 파일명 지정
			fileChooser.setSelectedFile(new File(tableName + ".xml"));

			// 필터링될 확장자
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"xml 파일", "xml");

			// 필터링될 확장자 추가
			fileChooser.addChoosableFileFilter(filter);

			// 파일오픈 다이얼로그 를 띄움
			int result = fileChooser.showSaveDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				// 선택한 파일의 경로 반환
				String savePath = fileChooser.getSelectedFile().toString();

				ScraperController sc = ScraperController.getInstance();
				sc.exportXmlFileOfScraperTable(savePath, table, tableName,
						author, url);

				JOptionPane.showMessageDialog(null, "경로(" + savePath
						+ ")에 저장되었습니다.");
			}
		}
	}
	
	/** 
	* @Method Name	: executeBtnExecute 
	* @Method 설명    	: scraper Table의 Execute를 처리하는 함수
	* @변경이력      	: 
	*/
	private void executeBtnExecute(){
		ScraperController sc = ScraperController.getInstance();
		JTree tree = CenterPanel.getTree();
		List<LinkedList<XmlNode>> xmlNodesList = sc.NodeDataExtractionExecute(tree, table);
		
		if(xmlNodesList == null)
			JOptionPane.showMessageDialog(null, "저장할 값이 없습니다.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		else{
			JFileChooser fileChooser = new JFileChooser();

			// 모든 파일을 접근 하지 못하도록 설정
			fileChooser.setAcceptAllFileFilterUsed(false);

			// 기본 Path의 경로 설정 (바탕화면)
			fileChooser.setCurrentDirectory(new File(System
					.getProperty("user.home") + "//" + "Desktop"));

			// 파일명 지정
			fileChooser.setSelectedFile(new File("result.xml"));

			// 필터링될 확장자
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"xml 파일", "xml");

			// 필터링될 확장자 추가
			fileChooser.addChoosableFileFilter(filter);

			// 파일오픈 다이얼로그 를 띄움
			int result = fileChooser.showSaveDialog(this);

			if (result == JFileChooser.APPROVE_OPTION) {
				// 선택한 파일의 경로 반환
				String savePath = fileChooser.getSelectedFile().toString();

				sc.exportResultAsXmlFile(savePath, xmlNodesList);

				JOptionPane.showMessageDialog(null, "경로(" + savePath
						+ ")에 저장되었습니다.");
			}
			else{
				JOptionPane.showMessageDialog(null, "실행을 취소 하였습니다.");
			}
			
			/*
			for(LinkedList<XmlNode> xmlNodes : xmlNodesList){
				System.out.println("---Node---");
				for(XmlNode xmlNode : xmlNodes){
					System.out.println(xmlNode.getTag() + " : " + xmlNode.getContent());
				}
				System.out.println();
			}
			*/
		}
	}
	
}
