/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTree;

import kumoh.sig.boardwebcrawler.main.MainFrame;

/** 
 * @FileName		: ScraperMainPanel.java 
 * @Project		: D445WebCrawler 
 * @Date			: 2015. 2. 15. 
 * @작성자			: YuJoo 
 * @프로그램 설명		: 
 * @프로그램 기능		: 
 * @변경이력		: 
 */
public class MainPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	// 패널에 추가할 멤버 컴포넌트들
	private CenterPanel panelCenter;
	private SouthPanel panelSouth;
	
	public MainPanel(){
		setLayout(new GridLayout(2,0));
		
		
		panelCenter = new CenterPanel();
		panelSouth = new SouthPanel();
		
		add(panelCenter);
		add(panelSouth);
		
		this.setVisible(true);
	}
}
