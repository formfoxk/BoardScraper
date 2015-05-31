package kumoh.sig.boardscraper.main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import kumoh.sig.boardscraper.view.MainPanel;

/** 
* @FileName    	: MainFrame.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 22. 
* @작성자     	: YuJoo 
* @프로그램 설명		: MainFrame 생성 및 실행
* @프로그램 기능		:
* @변경이력		:  
*/
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout = null;
	private MainPanel scraperMainPanel = null;
	
	public MainFrame() {
		// 맴버 인스턴스 생성
		creatingMemberInstance();
		
		// 나머지 초기화
		initialize();

	}
	private void creatingMemberInstance(){		
		cardLayout = new CardLayout();
		scraperMainPanel = new MainPanel(this);
	}
	
	private void initialize() {
		/** Frame의 크기 초기화 */
		Dimension dimScreen, dimFrame;
		int xpos, ypos;
		
		setTitle("Board Scraper");
		// frame의 크기
		setSize(1200, 800);
		// User의 화면 크기를 얻는다.
		dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		// Frame의 크기를 얻는다.
		dimFrame = getSize();
		// X 좌표
		xpos = (int) (dimScreen.getWidth() / 2 - dimFrame.getWidth() / 2);
		// Y 좌표
		ypos = (int) (dimScreen.getHeight() / 2 - dimFrame.getHeight() / 2);
		// Frame의 초기 위치 설정
		setLocation(xpos, ypos);

		/** Frame Layout 설정 */
		setLayout(cardLayout);
		//getContentPane().add("Scrapers", scrapersMainPanel);
		getContentPane().add("Scraper", scraperMainPanel);
		
		/** Frame close 설정*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** Frame을 화면상에 출력 */
		setVisible(true);
	}
	
	public CardLayout getCardLayout(){
		return cardLayout;
	}
	
	public static void main(String[] args){
		new MainFrame();
	}
}
