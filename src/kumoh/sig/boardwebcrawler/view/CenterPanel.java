/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import kumoh.sig.boardwebcrawler.controller.ScraperController;
import kumoh.sig.boardwebcrawler.model.data.UserMutableTreeNode;
import kumoh.sig.boardwebcrawler.model.logic.JTreeProcesser;

import org.jsoup.nodes.Element;


/** 
* @FileName    	: CenterPanel.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 22. 
* @작성자     	: YuJoo 
* @프로그램 설명 	: Scraper의 Url 패널, Html의 Dom트리 패널, 트리의 노드에 요소를 테이블 패널 GUI를 가지는 패널
* @프로그램 기능 	: 1. Scraper의 Url 2. Html의 Dom트리 3. 트리의 노드에 요소를 테이블에 출력
* @변경이력		:  
*/
public class CenterPanel extends JPanel implements
		TreeSelectionListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CenterPanel.class.getName());
	
	private JFrame frame;
	
	// 패널에 추가할 멤버 컴포넌트들
	private JTextField tfUrl;
	private JButton btnUrl;
	private JTextField tfSearch;
	private JButton btnSearch;
	private JComboBox cbSearch;
	private JSplitPane jSplitPane;
	private JPanel panelUrl;
	private JPanel panelSearchAndTree; 
	private JPanel panelTable = null; 
	
	// 트리
	private static JTree tree = null;
	
	// 생성자
	public CenterPanel(JFrame frame) {
		this.frame = frame;
		
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
	* @Method Name	: creatingMemberInstance 
	* @Method 설명    	: 맴버 변수 초기화
	* @변경이력      	: 
	*/
	private void creatingMemberInstance() {
		tfUrl = new JTextField();
		tfSearch = new JTextField();
		btnUrl = new JButton("Start");
		btnSearch = new JButton("Search");
		String[] cbStr = {"Tag", "Content", "CssSelector", "Href"};
		cbSearch = new JComboBox(cbStr);
		panelUrl = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelSearchAndTree = new JPanel(new BorderLayout());
		panelTable = new JPanel();
	}

	/** 
	* @Method Name	: settingPanel 
	* @Method 설명    	: 패널 배치
	* @변경이력      	: 
	*/
	private void settingPanel() {
		setLayout(new BorderLayout());

		/** Url Panel 설정 */
		// Center Panel 테두리
		Border borderPanelUrl = BorderFactory.createTitledBorder(null,
				"Scraper Url", TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"times new roman", Font.PLAIN, 20), Color.BLACK);
		panelUrl.setBorder(borderPanelUrl);

		JLabel lbUrl = new JLabel("URL");
		tfUrl.setPreferredSize(new Dimension(600, 20));
		
		// Author panel 생성 및 설정
		JPanel panelAuthor = new JPanel(new BorderLayout());
		// GridLayout Row1
		JLabel lbName1 = new JLabel(" Developed by ");
		JLabel lbName2 = new JLabel("JooHyun Yu");
		lbName1.setFont(new Font("돋움", Font.BOLD, 10));
		lbName2.setFont(new Font("돋움", Font.BOLD, 10));
		lbName2.setForeground(Color.BLUE);
		
		JPanel panelName = new JPanel(new BorderLayout());
		panelName.add(lbName1, "West");
		panelName.add(lbName2, "Center");
		// GridLayout Row2
		JLabel lbGit = new JLabel(" Github ");
		JLabel lbGitAddress = new JLabel("https://github.com/formfoxk/BoardWebCrawler");
		lbGit.setFont(new Font("돋움", Font.BOLD, 10));
		lbGitAddress.setFont(new Font("돋움", Font.BOLD, 10));
		lbGitAddress.setForeground(Color.BLUE);
		lbGitAddress.setCursor(new Cursor(Cursor.HAND_CURSOR));
		String gitAddress = "https://github.com/formfoxk/BoardWebCrawler";
		hyperLink(lbGitAddress, gitAddress);
		
		JPanel panelGit = new JPanel(new BorderLayout());
		panelGit.add(lbGit, "West");
		panelGit.add(lbGitAddress, "Center");
		
		panelAuthor.add(panelName, "North");
		panelAuthor.add(panelGit, "Center");
			
		// Url Panel에 추가
		panelUrl.add(lbUrl);
		panelUrl.add(tfUrl);
		panelUrl.add(btnUrl);
		panelUrl.add(panelAuthor);
		
		// Tree Panel 설정 
		JScrollPane jspTree = null;

		UserMutableTreeNode rootNode = new UserMutableTreeNode("NULL","","","");
		tree = new JTree(rootNode);
		jspTree = new JScrollPane(tree);
		jspTree.setAutoscrolls(true);

		// Search Panel 생성 및 설정
		JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		tfSearch.setPreferredSize(new Dimension(250, 20));
		btnSearch.setPreferredSize(new Dimension(80,20));
		panelSearch.add(cbSearch);
		panelSearch.add(tfSearch);
		panelSearch.add(btnSearch);
		
		// Search+Tree Panel 설정
		panelSearchAndTree.add("North", panelSearch);
		panelSearchAndTree.add("Center", jspTree);
		
		/** JSplitPane 설정 */
		jSplitPane = new JSplitPane();
		Border borderJSplitPane = BorderFactory.createTitledBorder(null,
				"Scraper Tree", TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"times new roman", Font.PLAIN, 20), Color.BLACK);
		jSplitPane.setBorder(borderJSplitPane);
		
		jSplitPane.setLeftComponent(panelSearchAndTree);				// 왼쪽 tree 삽입
		jSplitPane.setRightComponent(panelTable);	// 오른쪽 테이블 삽입

		add("North", panelUrl);
		add("Center", jSplitPane); // Container에 추가

	}

	private void settingListener() {
		// TreeSelection 이벤트의 Listener 추가
		tree.addTreeSelectionListener(this); 
		
		// MouseCliecked 이벤트의 Listener 추가
		tree.addMouseListener(new TtreeMouseEventHandler());

		// ActionListener 이벤트의 Listener 추가
		btnUrl.addActionListener(this);
		btnSearch.addActionListener(this);
	}

	/** 
	* @Method Name	: initialize 
	* @Method 설명    	: 나머지 모든 인스턴스 초기화
	* @변경이력      	: 
	*/
	private void initialize() {
		

	}

/**************************************************************************************/
/**										이벤트 처리		     				    	     **/
/**************************************************************************************/
	/** 
	* @Method Name	: hyperLink 
	* @Method 설명    	: 하이퍼링크 Listener 설정 및 mouseClicked시 이벤트를 처리 해주는 함수 
	* @변경이력      	:
	* @param website 
	*/
	private void hyperLink(JLabel website, String address) {
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URI(address));
				} catch (URISyntaxException | IOException ex) {
					// It looks like there's a problem
				}
			}
		});
	}
	/**
	 *  
	 * @Method Name : valueChanged 
	 * @Method 기능 : Tree의 범위가 변경된 경우(TreeNode 옆 +/-를 클릭한경우)
	 * @변경이력 : 
	 * @param e
	 *             
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// 현재 선택된 노드
		UserMutableTreeNode node = (UserMutableTreeNode)e.getPath().getLastPathComponent();
		
		// root를 클릭한 경우
		if(node.toString().equals("NULL"))
			JOptionPane.showMessageDialog(null, "자식노드가 없습니다.", "알림",
					JOptionPane.WARNING_MESSAGE);
		else{
			panelTable = new CenterTablePanel(node);
			jSplitPane.setRightComponent(panelTable);
		}
	}
	/**
	 * @Method Name : actionPerformed 
	 * @Method 기능 : 버튼 이벤트 처리 함수
	 * @변경이력 : 
	 * @param e
	 *             
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Search 버튼을 클릭 한 경우
		if (e.getSource() == btnUrl) {
			executeBtnUrl();
		}
		
		// Find 버튼을 클릭 한 경우
		if (e.getSource() == btnSearch){
			executeBtnSearch();
		}
	}

	/** 
	* @Method Name	: executeBtnUrl 
	* @Method 설명    	: Url버튼을 클릭시 실행되는 함수
	* @변경이력      	: 
	*/
	private void executeBtnUrl(){
		String url = tfUrl.getText();

		if(url.isEmpty())
			JOptionPane.showMessageDialog(null, "입력값이 없습니다.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		else{
			// 컨트롤 클레스를 얻어 온다.
			ScraperController sc = ScraperController.getInstance();
			// Html문서를 얻어 온다.
			Element document = sc.getDocument(url);

			// Html 문서를 얻지 못한 경우
			if (document == null) {
				// 오류 메시지 박스 출력
				JOptionPane.showMessageDialog(null, "Document를 얻지 못했습니다.",
						"ERROR", JOptionPane.ERROR_MESSAGE);

				// 텍스트 필드 초기화
				tfUrl.setText("");
			} else {
				// tree를 구축한다.
				sc.buildTree(tree, document);
				tfSearch.setText("");
			}
		}
	}
	
	/** 
	* @Method Name	: executeBtnSearch 
	* @Method 설명    	: 
	* @변경이력      	: 
	*/
	private void executeBtnSearch(){
		String searchStr = tfSearch.getText();
		if(searchStr.isEmpty()){
			// 오류 메시지 박스 출력
			JOptionPane.showMessageDialog(null, "입력값이 없습니다.", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
			
		else{
			ScraperController sc = ScraperController.getInstance();				
			sc.searchTreeNode(tree, searchStr, cbSearch.getSelectedItem().toString());
		}
	}
	
	/** 
	* @FileName    	: CenterPanel.java 
	* @Project    	: BoardWebCrawler 
	* @Date       	: 2015. 5. 22. 
	* @작성자     	: YuJoo 
	* @프로그램 설명		: Mouse Clicked Event 처리 클레스
	* @프로그램 기능		:
	* @변경이력		:  
	*/
	class TtreeMouseEventHandler extends MouseAdapter {

		/**
		 *  
		 * 
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 * @Method Name : mouseClicked 
		 * @Method 기능 : Table의 노드를 우클릭 Event 발생하는 경우 처리하는 함수
		 * @변경이력 : 
		 * @param e
		 *             
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (SwingUtilities.isRightMouseButton(e)) {
				// 좌표값을 얻는다.
				int x = e.getX();
				int y = e.getY();
				
				// 우클릭된 노드의 TreePath를 구한다.
				TreePath path = tree.getPathForLocation(x, y);
				// path가 존재 하는 경우
				if (path != null) {
					// 클릭된 현재 노드를 구한다.
					UserMutableTreeNode currNode = (UserMutableTreeNode) path
							.getLastPathComponent();
					
					// 현재 노드의 href와 cssSelector를 얻는다.
					String href = currNode.getHref();
					String cssSelector = currNode.getCssSelector();
					
					// 현재 입력된 Url을 얻는다.
					String url = tfUrl.getText();
					
					boolean isExistHref = (!href.isEmpty());
					ScraperController sc = ScraperController.getInstance();
					// href 또는 OnClick함수가 존재하는 경우
					if(isExistHref || sc.isExistOnClick(url, cssSelector)){
						SelectToParsingDialog dialog = new SelectToParsingDialog(frame);
						dialog.display();
						
						// dialog의 Ok버튼이 클릭된 경우
						if(dialog.getIsCheckBtnOk()){
							// 형제 노드 생성가 TRUE인 경우
							if(dialog.getIsCheckSibling()){
								// URL들을 파싱하여 Tree를 재구성한다.
								multiNewBuildTree(isExistHref, currNode);
							}
							else{
								// 하나의 URL을 파싱하여 Tree를 재구성한다.
								singleNewBuildTree(isExistHref, currNode);
							}
						}
					}
					// href 또는 OnClick함수가 존재하지 않는 경우
					else{
						// 오류 메시지 박스 출력
						JOptionPane.showMessageDialog(null, "우클릭된 노드는 문서를 파싱 할 수 없습니다.", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		}
	}
	
	/** 
	* @Method Name	: singleNewBuildTree 
	* @Method 설명    	: 하나의 URL을 파싱하여 Tree를 재구성한다.
	* @변경이력      	:
	* @param isExistHref 
	*/
	private void singleNewBuildTree(boolean isExistHref, UserMutableTreeNode node){
		ScraperController sc = ScraperController.getInstance();
		
		String url = null;
		// Href가  존재하는 경우
		if(isExistHref){
			// url을 얻는다.
			url = node.getHref();
		}
		// OnClick함수가 존재하는 경우
		else{
			// 현재 웹문서의 URL을 얻는다.
			String currUrl = tfUrl.getText();
			
			// CssSelector를 얻는다.
			String cssSelector = node.getCssSelector();
			
			// URL을 얻는다.
			url = sc.getUrl(currUrl, cssSelector);
		}
		// document를 얻는다.
		Element document = sc.getDocument(url);
		
		// URL 텍스트 필드를 재구성한다.
		tfUrl.setText(url);
		
		// 트리를 재구성한다.
		sc.buildTree(tree, document);
	}
	/** 
	* @Method Name	: multiParsing 
	* @Method 설명    	: URL들을 파싱하여 Tree를 재구성한다.
	* @변경이력      	:
	* @param isExistHref 
	*/
	private void multiNewBuildTree(boolean isExistHref, UserMutableTreeNode node){
		ScraperController sc = ScraperController.getInstance();
		
		// cssSelector와 일치하는 node들을 얻는다.
		List<UserMutableTreeNode> nodeList = sc.getNodes(tree, node.getCssSelector());
		
		List<String> urlList = null;
		
		// href가 존재하는 경우
		if(isExistHref)
			urlList = sc.getUrls(nodeList);
		// OnClick함수가 존재하는 경우
		else{
			urlList = sc.getUrls(tfUrl.getText(), nodeList);
		}

		// document들을 얻어온다.
		List<Element> documents = sc.getDocuments(urlList);
		
		// 트리를 재구축한다.
		logger.info("buildTree Start");
		sc.buildTree(tree, documents);
		logger.info("buildTree End");
	}
	
	public static JTree getTree(){
		return tree;
	}
}