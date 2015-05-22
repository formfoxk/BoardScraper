package kumoh.sig.boardwebcrawler.model.logic;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

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
	 *  
	 * 
	 * @Method Name : createTreeNode 
	 * @Method 기능 : 트리노드를 생성하는 함수 재귀적으로 자식노드를 생성한다.
	 * @변경이력 : 
	 * @param e
	 * @param parentNode
	 *             
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
				
				//자식노드의 자식노드가 존재 하지 않는  경우
				if (childElement.children().isEmpty()) continue;
				
				// 자식 노드 생성
				UserMutableTreeNode childNode = new UserMutableTreeNode(
						childElement.tagName()
						, childElement.text()
						, childElement.cssSelector()
						, childElement.attr("abs:href"));
						
				// 부모노드에 자식노드 추가
				parentNode.add(childNode);
				
				// 재귀적으로 자식노드 생성
				createTreeNode(childNode, childElement);
			}
		}
	}
}
