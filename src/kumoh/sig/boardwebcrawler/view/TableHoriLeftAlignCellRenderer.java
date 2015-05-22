/**
 * 
 */
package kumoh.sig.boardwebcrawler.view;

import java.awt.Component;
import java.awt.Font;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *  
 * 
 * @FileName : DateCellRenderer.java 
 * @Project : D445WebCrawler 
 * @Date : 2015. 2. 14. 
 * @작성자 : YuJoo 
 * @프로그램 설명 : 셀내 데이터의 오른쪽 정렬을 담당
 * @프로그램 기능 : 
 * @변경이력 : 
 */
class TableHoriLeftAlignCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if(DEBUG){
			System.out.println("TableHoriRightAlignCellRenderer\nrow : " + row + "\ncolumn : " + column);
		}
			
		// setText를 재설정 안하면 셀안의 값이 보이지 않는다
		// Object의 타입이 Date인 경우
		if(value instanceof Date)
			setText(((Date)value).toString());
		else
			setText((String) value);
		
		setFont(new Font("돋움", Font.PLAIN, 12));
		setHorizontalAlignment(SwingConstants.LEFT);

		return this;

	}

}
