package kumoh.sig.boardwebcrawler.view;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;

/** 
* @FileName    	: CenterTablePanel.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 23. 
* @작성자     	: YuJoo 
* @프로그램 설명		: 클릭된 트리의 노드에 대한 정보를 테이블에 출력하는 패널
* @프로그램 기능		:
* @변경이력		:  
*/
public class CenterTablePanel extends JPanel{
private static final long serialVersionUID = 1L;
	
	// 패널에 추가 할 맴버 컴포넌트
	private CenterTableModel aTable = null;
	private JTable jTable = null;
	
	private UserMutableTreeNode node = null;
	
	private CenterTableManager tableMng;
	
	public CenterTablePanel(UserMutableTreeNode node) {
		this.node = node;
	
		// 멤버 변수 초기화
		createMemberInstance();
		// 패널 설정
		settingPanel();
		// 그외 초기 정보 설정
		initialize();
		// 테이블 구축
		buildTable();
	}
	
	/** 
	* @Method Name	: createMemberInstance 
	* @Method 설명    	: 멤버 변수 초기화
	* @변경이력      	: 
	*/
	private void createMemberInstance(){
		// Table 생성
		aTable = new CenterTableModel();
		jTable = new JTable(aTable);
		
		// Table Manager 생성
		tableMng = new CenterTableManager();
	}
	
	/** 
	* @Method Name	: settingPanel 
	* @Method 설명    	: 패널 설정
	* @변경이력      	: 
	*/
	private void settingPanel() {
		setLayout(new GridLayout(1,0));
		
		JScrollPane scrollPane = new JScrollPane(jTable);
		add(scrollPane);
	}

	/** 
	* @Method Name	: initialize 
	* @Method 설명    	: 그외 초기 정보 설정
	* @변경이력      	: 
	*/
	private void initialize() {
		// 테이블 헤더 설정
		tableMng.setHeaderProperty(jTable.getTableHeader());

		// 툴팁 메니저
		tableMng.setToolTip();
	}
	
	/** 
	* @Method Name	: buildTable 
	* @Method 설명    	: table을 빌드 한다.
	* @변경이력      	: 
	*/
	private void buildTable(){
		Vector<Vector<Object>> rows = new Vector<Vector<Object>>();
		Vector<Object> cols;
		
		// COLUMN 명 추가
		cols = new Vector<Object>();
		cols.add("TagName");
		cols.add("Description");
		cols.add("CssSelector");
		cols.add("Href");
		rows.add(cols);
		
		// 자식노드 갯수 
		int childeNodeSize = node.getChildCount();
		
		// 자식노드가 없는 경우 
		if(node.getChildCount() == 0){
				// 현재 노드의 데이터 입력
				cols = new Vector<Object>();
				cols.add(node.getTagName());
				cols.add(node.getContent());
				cols.add(node.getCssSelector());
				cols.add(node.getHref());
				rows.add(cols);
		}
		else{
			for (int i = 0; i < childeNodeSize; i++) {
				// 자식노드 호출
				UserMutableTreeNode childNode = (UserMutableTreeNode) node
						.getChildAt(i);

				// 자식노드 데이터 입력
				cols = new Vector<Object>();
				cols.add(childNode.getTagName());
				cols.add(childNode.getContent());
				cols.add(childNode.getCssSelector());
				cols.add(childNode.getHref());
				rows.add(cols);
			}
		}
		// table을 다시 구축 한다.
		tableMng.reloadTable(jTable, rows);
	}
}
