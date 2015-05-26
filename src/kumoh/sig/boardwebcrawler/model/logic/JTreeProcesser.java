package kumoh.sig.boardwebcrawler.model.logic;

import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;

import org.jsoup.nodes.Element;

/** 
* @FileName    	: JTreeProcesser.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 22. 
* @작성자     	: YuJoo 
* @프로그램 설명		: JTree의 기능을 처리해주는 함수
* @프로그램 기능		: JTree 구축
* @변경이력		:  
*/
public class JTreeProcesser {		
	/** 
	* @Method Name	: buildTree 
	* @Method 설명    	: 트리를 구축하는 함수
	* @변경이력      	:
	* @param url 
	*/
	public void buildTree(JTree tree, Element document) {
		 
		// HtmlDocument가 NULL인 경우
		if (document == null) {
			System.out.println("JTreeProcesser/buildTree :: Tree 구축 실패");
		} 
		
		else {
			// tree model을 얻는다.
			DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
			
			// 루트 노드를 얻어 온다.
			UserMutableTreeNode root = (UserMutableTreeNode)treeModel.getRoot();
			
			// 루트의 모든 자식을 삭제 한다.
			root.removeAllChildren();
			
			// 루트의 자식노드를 초기화 한다.
			root.setTagName("Root");
			
			// Tree 노드 생성(Reculsive Function)
			createTreeNode(root, document);
			
			// 실제 tree를 갱신하여 화면에 보여준다.
			treeModel.reload();
		}
	}

	/** 
	* @Method Name	: buildTree 
	* @Method 설명    	: Root에 모든 웹문서 Dom트리를 자식노드로 생성한다.
	* @변경이력      	: JTree 구축
	* @param tree
	* @param documents 
	*/
	public void buildTree(JTree tree, List<Element> documents) {
		
		// Document가 하나도 존재 하지 않는 경우
		if (documents.isEmpty()) {
			System.out.println("JTreeProcesser/buildTree :: Tree 구축 실패");
		} 
		
		else {
			// tree model을 얻는다.
			DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
			
			// 루트 노드를 얻어 온다.
			UserMutableTreeNode root = (UserMutableTreeNode)treeModel.getRoot();
			
			// 루트의 모든 자식을 삭제 한다.
			root.removeAllChildren();
			
			// 루트의 자식노드를 초기화 한다.
			root.setTagName("Root");
			
			// Root에 모든 웹문서 Dom트리를 자식노드로 생성한다.
			for(Element document : documents){
				if(document != null)
					// Tree 노드 생성(Reculsive Function)
					createTreeNode(root, document);
			}
			
			// 실제 tree를 갱신하여 화면에 보여준다.
			treeModel.reload();
		}
	}
	
	/** 
	* @Method Name	: createTreeNode 
	* @Method 설명    	: 트리노드를 생성하는 함수 재귀적으로 자식노드를 생성한다.
	* @변경이력      	:
	* @param parentNode
	* @param parentElement 
	*/
	private void createTreeNode(UserMutableTreeNode parentNode, Element parentElement) {
		// 자식노드가 존재 하지 않는 경우
		if (parentElement.children().isEmpty())	return;
		
		// 자식노드가 존재 할 경우
		else {			
			// JTree에서 현재 부모노드에 자식노드를 추가
			for (int i = 0; i < parentElement.children().size(); i++) {
				
				// 자식노드를 얻는다.
				Element childElement = parentElement.child(i);	
					
				// 현재 태그 
				String currTag = childElement.tagName();
				
				// Jsoup라이브러리에서 처리 하지 못하는 태그가 아닌 경우
				if(!isExeptionTag(currTag)){
					// 자식 노드 생성
					UserMutableTreeNode childNode = new UserMutableTreeNode(
							childElement.tagName(), childElement.text(),
							childElement.cssSelector(),
							childElement.attr("abs:href"));

					// 부모노드에 자식노드 추가
					parentNode.add(childNode);

					// 자식노드의 자식노드가 존재 하지 않는 경우
					if (childElement.children().isEmpty())
						continue;

					// 재귀적으로 자식노드 생성
					createTreeNode(childNode, childElement);
				}
			}
		}
	}
	
	/** 
	* @Method Name	: isExeptionTag 
	* @Method 설명    	: jsoup에서 처리 하지 못하는 예외 테크인지 확인하는 함수
	* @변경이력      	:
	* @param tag
	* @return 
	*/
	private boolean isExeptionTag(String tag){
		switch(tag){
		case "o:p" :
			return true;
		}
		return false;
	}
	
	/** 
	* @Method Name	: searchTreeNode 
	* @Method 설명    	: 검색 텍스트 필드에 입력된 값과 일치하는 노드들을 확장시켜주는 함수
	* @변경이력      	:
	* @param tree
	* @param query
	* @param cbItem 
	*/
	public void searchTreeNode(JTree tree, String query, String cbItem){
		// Tree의 최상위 노드를 구한다.
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		UserMutableTreeNode root = (UserMutableTreeNode)model.getRoot();

		// 트리를 접는다.
		collapseAll(tree);
		
		// 전위 순회
		Enumeration e = root.preorderEnumeration();

		while (e.hasMoreElements()) {
			// 현재 트리 노드
			UserMutableTreeNode currNode = null;
			currNode = (UserMutableTreeNode) e.nextElement();
			
			// 비교할 문자를 얻는다.
			String cmpStr = null;
			switch (cbItem) {
			case "Tag":
				cmpStr = currNode.getTagName();
				break;

			case "Content":
				cmpStr = currNode.getContent();
				break;

			case "CssSelector":
				cmpStr = currNode.getCssSelector();
				break;

			case "Href":
				cmpStr = currNode.getHref();
				break;
			}
			// 문자열에 검색하고자 하는 문자가 포함되어 있는 경우
			if(cmpStr.contains(query) || cmpStr.equals(query)){	
				// 현재 노드의 path를 구한다.
				TreePath currPath = new TreePath(currNode.getPath());
				
				// 현재노드까지 펼친다.
				tree.expandPath(currPath);
			}
		}
	}
	
	public void collapseAll(JTree tree) {
		int row = tree.getRowCount() - 1;
		while (row >= 0) {
			tree.collapseRow(row);
			row--;
		}
	}
}
