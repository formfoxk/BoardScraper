package kumoh.sig.boardwebcrawler.view;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



/** 
* @FileName    	: LoadingDialog.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 24. 
* @작성자     	: YuJoo 
* @프로그램 설명		: 프로그램이 내부적으로 동작할 때 사용자에게 대기상태를 보여주는 다이얼로그
* @프로그램 기능		:
* @변경이력		:  
*/
public class LoadingDialog implements Runnable{
	
	private JFrame frame;
	private JPanel panel;
	private JDialog dialog;
	
	// Dialog size
	private final int DialogWidth = 140;
	private final int DialogHeight = 140;
	
	// Panel Component
	private ImageIcon icon;
	private JLabel lbText;
	
	public LoadingDialog(JFrame frame){
		this.frame = frame;
	}
	
	@Override
    public void run() {
		display();
    }
	
	/** 
	* @Method Name	: display 
	* @Method 설명    	: dialog를 생성하는 함수
	* @변경이력      	: 
	*/
	public void display() {
		dialog = new JDialog(frame, "Alert", true);
		dialog.setSize(DialogWidth, DialogHeight);
		dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		dialog.setContentPane(getPanel());

		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}
	
	/** 
	* @Method Name	: getPanel 
	* @Method 설명    	: panel을 초기화 후 반환하는 함수
	* @변경이력      	:
	* @return 
	*/
	private JPanel getPanel() {		
		// 맴버 인스턴스 초기화
		createMemberInstance();

		// 패널 설정
		settingPanel();

		return panel;
	}
	/** 
	* @Method Name	: createMemberInstance 
	* @Method 설명    	: panel의 component들을 초기화
	* @변경이력      	: 
	*/
	private void createMemberInstance() {
		panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// Icon 생성
		icon = new ImageIcon("images/ajax-loader.gif");
		// loading Text 생성
		lbText = new JLabel("loading");
	}
	
	/** 
	* @Method Name	: settingPanel 
	* @Method 설명    	: panel 구성
	* @변경이력      	: 
	*/
	private void settingPanel() {
		// 아이콘
		JLabel lbIcon = new JLabel(icon);
		
		// 텍스트
		lbText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		lbText.setFont(new Font("맑은 고딕", Font.BOLD, 21));
		
		panel.add(lbIcon);
		panel.add(lbText);

	}
}

