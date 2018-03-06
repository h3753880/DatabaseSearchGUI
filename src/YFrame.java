import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class YFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] compare = {"=", ">", "<"};
	private String[] compare2 = {"AND", "OR", "BETWEEN"};
	private JPanel userPn;
	private JTable table;
	
	/**
	 * Create the frame.
	 */
	public YFrame() {
		super("DB HW3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1300, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userPanel(); //user part
		
		queryPanel(); //query part
		
		resultPanel(); //result
		
	}

	private void exeQuery(String sql) {
		DBconn db = new DBconn();
		
		ResultSet res = db.executeQuery(sql);
		ResultSetMetaData meta;
		int colCount = 0;
		int rowCount = 0;
		ArrayList<String> col = new ArrayList<String>();
		List<List<Object>> data = new ArrayList<>();
		
		try {
			meta = res.getMetaData();
			colCount = meta.getColumnCount();
			
			for(int i=1; i<=colCount; i++) {
				col.add(meta.getColumnName(i));
			}
			
			while(res.next()) {
				ArrayList<Object> rows = new ArrayList<>();
				
				for(String name: col) {
					rows.add(res.getString(name));
				}
				
				data.add(rows);
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String[] col = {"id", "name"};
		//Object[][] data = {{1,"Howard"},{2,"Jason"}};col.toArray(), rowCount
		DefaultTableModel mod = new DefaultTableModel();
		
		for(String c: col)
			mod.addColumn(c);
		
		for(List<Object> ea: data)
			mod.addRow(ea.toArray());
		
		table.setModel(mod);
		
		db.closeDB();
	}
	  
	private void resultPanel() {
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane resultScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultScroll.setBounds(807, 29, 467, 623);
		contentPane.add(resultScroll);
	}
	
	private void queryPanel() {
		JLabel resultLb = new JLabel("Result");
		resultLb.setBounds(1027, 10, 46, 15);
		resultLb.setFont(new Font("Serif", Font.PLAIN, 18));
		contentPane.add(resultLb);
		
		JLabel queryLb = new JLabel("Query");
		queryLb.setBounds(588, 396, 46, 21);
		queryLb.setFont(new Font("Serif", Font.PLAIN, 18));
		contentPane.add(queryLb);
		
		JTextArea queryArea = new JTextArea();
		JScrollPane queryScroll = new JScrollPane(queryArea);
		queryScroll.setBounds(460, 427, 338, 180);
		contentPane.add(queryScroll);
		
		JButton submitBtn = new JButton("Execute Query");
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exeQuery(queryArea.getText());
			}
		});
		submitBtn.setBounds(532, 629, 181, 23);
		contentPane.add(submitBtn);
	}
	
	
	private void userPanel() {
		userPn = new JPanel();
		userPn.setForeground(Color.BLACK);
		userPn.setBackground(Color.WHITE);
		userPn.setBounds(10, 427, 439, 229);
		userPn.setLayout(new GridLayout(0, 4, 1, 1));
		contentPane.add(userPn);
		
		JLabel userLb = new JLabel("Users");
		userLb.setBounds(195, 393, 41, 27);
		userLb.setFont(new Font("Serif", Font.PLAIN, 18));
		contentPane.add(userLb);
		
		JLabel memLb = new JLabel("Member since:");
		userPn.add(memLb);
		JTextField meTxt1 = new JTextField();
		userPn.add(meTxt1);
		JLabel memtLb2 = new JLabel("(YYYY-MM-DD)");
		userPn.add(memtLb2);
		JLabel emptyLb1 = new JLabel("");
		userPn.add(emptyLb1);
		
		JLabel memLb2 = new JLabel("Review Count:");
		userPn.add(memLb2);
		JComboBox revCbox = new JComboBox(compare);
		userPn.add(revCbox);
		JLabel valLb = new JLabel("Value:");
		userPn.add(valLb);
		JTextField revCountTxt1 = new JTextField();
		userPn.add(revCountTxt1);
		
		JLabel memLb3 = new JLabel("Number of Friends:");
		userPn.add(memLb3);
		JComboBox revCbox2 = new JComboBox(compare);
		userPn.add(revCbox2);
		JLabel valLb2 = new JLabel("Value:");
		userPn.add(valLb2);
		JTextField friendTxt = new JTextField();
		userPn.add(friendTxt);
		
		JLabel memLb4 = new JLabel("Avg. Stars:");
		userPn.add(memLb4);
		JComboBox revCbox3 = new JComboBox(compare);
		userPn.add(revCbox3);
		JLabel valLb3 = new JLabel("Value:");
		userPn.add(valLb3);
		JTextField starTxt = new JTextField();
		userPn.add(starTxt);
		
		
		JLabel memLb5 = new JLabel("Select:");
		userPn.add(memLb5);
		JComboBox revCbox4 = new JComboBox(compare2);
		userPn.add(revCbox4);
	}
}
