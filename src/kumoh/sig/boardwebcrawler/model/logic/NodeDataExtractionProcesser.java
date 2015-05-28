package kumoh.sig.boardwebcrawler.model.logic;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import kumoh.sig.boardwebcrawler.model.data.TableRow;
import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;
import kumoh.sig.boardwebcrawler.model.data.XmlNode;

/** 
* @FileName    	: NodeDataExtractionProcesser.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 26. 
* @작성자     	: YuJoo 
* @프로그램 설명		: Tree에서 Node의 data를 추출하는 처리기
* @프로그램 기능		:
* @변경이력		:  
*/
public class NodeDataExtractionProcesser {
	
	/** 
	* @Method Name	: execute 
	* @Method 설명    	: 추출 실행 함수
	* @변경이력      	:
	* @param tree
	* @param jTable
	* @return 
	*/
	public List<LinkedList<XmlNode>> execute(JTree tree, JTable jTable){
		// scraperTable의 값을 얻어온다.
		List<TableRow> table = getTable(jTable);
		
		// row의 수를 얻는다.
		int rowCount = table.size();
		
		// table의 row값이 없으면 null값 반환
		if (rowCount < 1)
			return null;

		// Tree의 최상위 노드를 구한다.
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		UserMutableTreeNode root = (UserMutableTreeNode) model.getRoot();

		// xml파일에 저장될 모든 HashSet
		HashSet<LinkedList<XmlNode>> xmlNodesList = new HashSet<LinkedList<XmlNode>>();
		// table값에 row값들이 저장되는 xmlNode들
		LinkedList<XmlNode> xmlNodes = new LinkedList<XmlNode>();

		// 전위 순회
		Enumeration e = root.preorderEnumeration();

		while (e.hasMoreElements()) {
			// 현재 트리 노드
			UserMutableTreeNode currNode = null;
			currNode = (UserMutableTreeNode) e.nextElement();

			// 현재 CssSelector
			String currCssSelector = currNode.getCssSelector();
			
			boolean beforeCheck = true;
			for (TableRow row : table) {
				
				
				// 이전 row의 check가 true인 경우
				if(beforeCheck){
					// check를 얻는다.
					boolean check = row.getCheck();
					// cssSelector을 얻는다.
					String cssSelector = row.getCssSelector();
					
					// check가 아니고 CssSelector가 table의 RexCssSelector와 일치하는 경우
					if (!check && isCheckRex(cssSelector, currCssSelector)) {
						xmlNodes.add(new XmlNode(row.getDescription(), currNode
								.getContent()));
						row.setCheck(true);
						break;
					}
					
					beforeCheck = row.getCheck();
				}
				else
					break;
			}
			// check 배열이 모두 true인 경우
			if (isFullTrue(table)) {
				// 테이블 행의 check변수를 모두 false로 변경
				setFalseCheckInTableRows(table);
				// url 노드 생성 후 저장
				xmlNodes.add(new XmlNode("Url", currNode
						.getUrl()));
				// xmlNodes를 추가
				xmlNodesList.add(xmlNodes);
				// 새로 객체 생성
				xmlNodes = new LinkedList<XmlNode>();
			}
		}
		if (xmlNodesList.isEmpty())
			return null;

		// HashSet을 LinkedList로 변환
		List<LinkedList<XmlNode>> temp = new LinkedList<LinkedList<XmlNode>>(xmlNodesList);
		
		return temp;
	}

	/** 
	* @Method Name	: getTable 
	* @Method 설명    	: scraperTable의 값을 얻어오는 테이블
	* @변경이력      	:
	* @param table
	* @return 
	*/
	private List<TableRow> getTable(JTable jTable){
		// Scraper Table의 값이 저장되는 List
		List<TableRow> table = new LinkedList<TableRow>();

		// JTable의 모델을 얻어 온다.
		DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();
		
		for (int i = 0; i < dtm.getRowCount(); i++) {
			// row의 check값을 얻어온다.
			boolean rowCheck = (boolean)dtm.getValueAt(i, 0);
	
			// rowCheck가 ture인 경우
			if (rowCheck) {
				// Table의 Row값을 얻어온다.
				String description = null, cssSelector = null;
				description = (String) dtm.getValueAt(i, 1);
				cssSelector = (String) dtm.getValueAt(i, 2);
				
				
				// null check
				if (description == null || cssSelector == null)
					continue;
					
				// 정규식 변환 후에 넣는다.
				String rexCssSelector = getRegexCssSelector(cssSelector);
				// tableRowList에 행 추가
				table.add(new TableRow(false, description, rexCssSelector));
			}
		}
		
		return table;
	}

	/** 
	* @Method Name	: isFullTrue 
	* @Method 설명    	: table의 row에서 check변수가 모두 true인지 확인하는 변수
	* @변경이력      	:
	* @param table
	* @return 
	*/
	private boolean isFullTrue(List<TableRow> table){
		for(TableRow row : table)
			if(!row.getCheck())
				return false;
		return true;
	}
	
	/** 
	* @Method Name	: setFalseCheckInTableRows 
	* @Method 설명    	: 테이블 행의 check변수를 모두 false로 변경
	* @변경이력      	:
	* @param table 
	*/
	private void setFalseCheckInTableRows(List<TableRow> table){
		//table의 row값에 check를 false로 변경
		for(TableRow row : table){
			row.setCheck(false);
		}
	}
	
	/** 
	* @Method Name	: isCheckRex 
	* @Method 설명    	: 정규식과 데이터가 매치 된다면 True 아니면 False
	* @변경이력      	:
	* @param rex
	* @param data
	* @return 
	*/
	private boolean isCheckRex(String rex, String data) {
		Matcher match = null;

		try {
			Pattern numP = Pattern.compile(rex);
			match = numP.matcher(data);
		} catch (PatternSyntaxException e) {
			e.getStackTrace();
		}
		return match.find();
	}
	
	/** 
	* @Method Name	: getRegexCssSelector 
	* @Method 설명    	: cssSelctor를 정규식으로 변경
	* @변경이력      	:
	* @param cssSelector 
	*/
	private String getRegexCssSelector(String cssSelector){
		int strSize = cssSelector.length()-1;
		int i = strSize;
		
		boolean isBeforeNum = false;
		boolean isCheckFirst = true;
		
		// 정규식으로 변환할 인덱스
		List<RegexIndex> indexList = new LinkedList<RegexIndex>();
		// 자를 문자열의 범위
		int firstIndex = -1, lastIndex = -1;
		while(i >= 0){
			// 문자 타입을 정수 타입으로 캐스팅
			char ch = cssSelector.charAt(i);
			int castingChar = ch-'0';
			

			// 1~9사이의 첫 숫자이고 다음 문자가 ')'인 경우
			if((castingChar >= 0 && castingChar < 10)
					&& (i < strSize)
					&& cssSelector.charAt(i+1) == ')'
					&& isCheckFirst 
					&& !isBeforeNum){
				isBeforeNum = true;
				isCheckFirst = false;
				firstIndex = i;
				lastIndex = i+1;
				i--;
				continue;
			}
			
			// 1~9사이의 숫자이고 이전의 문자가 숫자인 경우
			if(castingChar >= 0 && castingChar < 10 && !isCheckFirst && isBeforeNum){
				firstIndex = i;
				i--;
				continue;
			}
			
			// 이전의 문자가 숫자이고 현재 문자인 경우
			if(ch == '(' && !isCheckFirst && isBeforeNum){
				indexList.add(new RegexIndex(firstIndex-1, lastIndex));
				
				isBeforeNum = false;
				isCheckFirst = true;
				i--;
				continue;
			}
			
			i--;
		}
		// 숫자가 없는 경우
		if(firstIndex == -1 || lastIndex == -1) 
			return cssSelector;
		
		StringBuffer sb = new StringBuffer(cssSelector);
		String regexStr = "\\([0-9]+\\)";
		for(RegexIndex ri : indexList){
			// 지정된 범위의 숫자를 삭제한다.
			sb.delete(ri.firstIndex, ri.lastIndex+1);
			// 삭제된 숫자 위치에 정규식을 삽입한다.
			sb.insert(ri.firstIndex, "\\([0-9]+\\)");
		}
		sb.append("$");
		
		return sb.toString();
	}
	private class RegexIndex{
		int firstIndex;
		int lastIndex;
		
		public RegexIndex(int firstIndex, int lastIndex){
			this.firstIndex = firstIndex;
			this.lastIndex = lastIndex;
		}
	}
}
