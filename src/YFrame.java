import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import javax.swing.JCheckBox;

public class YFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] compare = {"=", ">", "<"};
	private String[] compare2 = {"AND", "OR"};
	private JPanel userPn;
	private JTable table;
	private JTextArea queryArea; //query result
	private JTextField meTxt1; //member since
	private JTextField revCountTxt1;
	private JTextField friendTxt;
	private JTextField starTxt;
	private JComboBox revCbox;
	private JComboBox friendCbox;
	private JComboBox starCbox;
	private JComboBox selectCbox;
	private String preRevBox = "=";
	private String preFriendBox = "=";
	private String preStarBox = "=";
	
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
		
		addListener();

		JLabel mainLb = new JLabel("Category");
		mainLb.setFont(new Font("Serif", Font.PLAIN, 18));
		mainLb.setBounds(66, 4, 65, 27);
		contentPane.add(mainLb);
		
		JScrollPane maincatScroll = new JScrollPane();
		maincatScroll.setBounds(10, 37, 210, 346);
		contentPane.add(maincatScroll);
		
		
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
					String temp = res.getString(name);
					
					if(!name.equals("YELPING_SINCE"))
						rows.add(temp);
					else
						rows.add(temp.substring(0, 7));
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
	
	private void queryReviewByUserId(String userId) {
		DBconn db = new DBconn();
		
		ResultSet res = db.executeQuery("SELECT * FROM REVIEWS WHERE USER_ID='" + userId + "'");
		ResultSetMetaData meta;
		int colCount = 0;
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
					String temp = res.getString(name);
					
					rows.add(temp);
				}
				
				data.add(rows);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(table.getSelectedRow() != -1 && !e.getValueIsAdjusting()) {
					System.out.println(table.getValueAt(table.getSelectedRow(), 0)); //print user_id
					
					queryReviewByUserId(table.getValueAt(table.getSelectedRow(), 0).toString());
				}
			}
		});
		
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
		
		queryArea = new JTextArea();
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
		meTxt1 = new JTextField();
		userPn.add(meTxt1);
		JLabel memtLb2 = new JLabel("(YYYY-MM)");
		userPn.add(memtLb2);
		JLabel emptyLb1 = new JLabel("");
		userPn.add(emptyLb1);
		
		
		JLabel memLb2 = new JLabel("Review Count:");
		userPn.add(memLb2);
		revCbox = new JComboBox(compare);
		userPn.add(revCbox);
		JLabel valLb = new JLabel("Value:");
		userPn.add(valLb);
		revCountTxt1 = new JTextField();
		userPn.add(revCountTxt1);
		revCbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				queryArea.setText(queryArea.getText().replace("REVIEW_COUNT" + preRevBox, "REVIEW_COUNT" + revCbox.getSelectedItem().toString()));
				preRevBox = revCbox.getSelectedItem().toString();
			}
		});
		
		JLabel memLb3 = new JLabel("Number of Friends:");
		userPn.add(memLb3);
		friendCbox = new JComboBox(compare);
		userPn.add(friendCbox);
		JLabel valLb2 = new JLabel("Value:");
		userPn.add(valLb2);
		friendTxt = new JTextField();
		userPn.add(friendTxt);
		friendCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = "(SELECT COUNT(*) FROM USER_FRIENDS F WHERE U.USER_ID=F.USER_ID)";
				queryArea.setText(queryArea.getText().replace(s + preFriendBox, s + friendCbox.getSelectedItem().toString()));
				preFriendBox = friendCbox.getSelectedItem().toString();
			}
		});
		
		
		JLabel memLb4 = new JLabel("Avg. Stars:");
		userPn.add(memLb4);
		starCbox = new JComboBox(compare);
		userPn.add(starCbox);
		JLabel valLb3 = new JLabel("Value:");
		userPn.add(valLb3);
		starTxt = new JTextField();
		userPn.add(starTxt);
		starCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				queryArea.setText(queryArea.getText().replace("AVERAGE_STARS" + preStarBox, "AVERAGE_STARS" + starCbox.getSelectedItem().toString()));
				preStarBox = starCbox.getSelectedItem().toString();
			}
		});
		
		
		JLabel memLb5 = new JLabel("Select:");
		userPn.add(memLb5);
		selectCbox = new JComboBox(compare2);
		userPn.add(selectCbox);
		selectCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String concat = selectCbox.getSelectedItem().toString();
				queryArea.setText(queryArea.getText().replace("AND", concat));
				queryArea.setText(queryArea.getText().replace("OR", concat));
			}
		});
	}
	
	private void addListener() {
		DocumentListener listener = new UserTxtListener(meTxt1, revCountTxt1, friendTxt, starTxt, 
				revCbox, friendCbox, starCbox, selectCbox, queryArea);

		meTxt1.getDocument().addDocumentListener(listener);
		revCountTxt1.getDocument().addDocumentListener(listener);
		friendTxt.getDocument().addDocumentListener(listener);
		starTxt.getDocument().addDocumentListener(listener);
	}
}
