/**
 * 
 */
package kumoh.sig.boardscraper.view;

import java.awt.Component;
import java.awt.Font;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *  
 * 
 * @FileName : TableHoriCenterAlignCellRenderer.java 
 * @Project : D445WebCrawler 
 * @Date : 2015. 2. 14. 
 * @작성자 : YuJoo 
 * @프로그램 설명 : 일반적인 셀의 속성지정
 * @프로그램 기능 : 
 * @변경이력 : 
 */
class TableHoriCenterAlignCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// setText를 재설정 안하면 셀안의 값이 보이지 않는다
		if(value instanceof Date)
			setText(((Date)value).toString());
		else
			setText((String) value);
		setFont(new Font("돋움", Font.PLAIN, 12));
		setHorizontalAlignment(SwingConstants.CENTER);

		return this;
	}
}