package kumoh.sig.boardscraper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
* @FileName    	: SelectToParsingDialog.java 
* @Project    	: BoardWebCrawler 
* @Date       	: 2015. 5. 23. 
* @작성자     	: YuJoo 
* @프로그램 설명		: 우클릭된 노드의 링크를 이용하여 웹 문서를 파싱할 것인지 선택하는 다이얼로그
* @프로그램 기능		:
* @변경이력		:  
*/
public class SelectToParsingDialog implements ActionListener, ItemListener{
	
	private JFrame frame;
	private JPanel panel;
	private JDialog dialog;
	
	// Dialog size
	private final int DialogWidth = 400;
	private final int DialogHeight = 140;
	
	// Panel Component
	private JLabel lbText;
	private JCheckBox cbSibling;
	private JButton btnOk;
	private JButton btnCancel;
	
	// Return Value
	private boolean isCheckBtnOk = false;
	private boolean isCheckSibiling = false;
	
	public SelectToParsingDialog(JFrame frame){
		this.frame = frame;
	}
	
	/** 
	* @Method Name	: display 
	* @Method 설명    	: dialog를 생성하는 함수
	* @변경이력      	: 
	*/
	public void display() {
		dialog = new JDialog(frame, "SelectToParsingDialog", true);
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

		// 리스너 설정
		settingListener();

		return panel;
	}
	/** 
	* @Method Name	: createMemberInstance 
	* @Method 설명    	: panel의 component들을 초기화
	* @변경이력      	: 
	*/
	private void createMemberInstance() {
		panel = new JPanel(new BorderLayout());
		lbText = new JLabel("새로운 웹 문서를 파싱 하시겠습니까?");
		cbSibling = new JCheckBox("형제노드 함께 생성");
		btnOk = new JButton("OK");
		btnCancel = new JButton("Cancel");
	}
	
	/** 
	* @Method Name	: settingPanel 
	* @Method 설명    	: panel 구성
	* @변경이력      	: 
	*/
	private void settingPanel() {
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		lbText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		lbText.setFont(new Font("맑은 고딕", Font.BOLD, 21));
		lbText.setForeground(Color.BLUE);
		textPanel.add(lbText);
		
		JPanel cbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cbPanel.add(cbSibling);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnOk.setPreferredSize(new Dimension(155, 25));
		btnCancel.setPreferredSize(new Dimension(155, 25));
		btnPanel.add(btnOk);
		btnPanel.add(btnCancel);
		
		panel.add("North", textPanel);
		panel.add("Center", cbPanel);
		panel.add("South", btnPanel);
	}
	/** 
	* @Method Name	: settingListener 
	* @Method 설명    	: panel 이벤트 리스너 설정
	* @변경이력      	: 
	*/
	private void settingListener() {
		cbSibling.addItemListener(this);
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	
	public boolean getIsCheckBtnOk(){
		return isCheckBtnOk;
	}
	public boolean getIsCheckSibling(){
		return isCheckSibiling;
	}
	
	/**************************************************************************************/
	/**										이벤트 처리		     				    	     **/
	/**************************************************************************************/
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		//ComboBox의 이벤트가 발생한 경우
		if (e.getSource() == cbSibling) {
			if (isCheckSibiling)
				isCheckSibiling = false;
			else
				isCheckSibiling = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			isCheckBtnOk = true;
			dialog.dispose();
		}
		else{
			dialog.dispose();
		}
	
	}

}

