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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	private String[] hours = new String[24];
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
	private List<JCheckBox> boxes;
	private List<JCheckBox> sboxes = new ArrayList<>();
	private JPanel subPl;
	private JScrollPane subScroll;
	private JTextField checkTxt;
	private JComboBox dayBox;
	private JComboBox hourBox;
	private JComboBox dayBox2;
	private JComboBox hourBox2;
	private JComboBox checkBox;
	private JTextField fromTxt;
	private JTextField toTxt;
	private JTextField starvTxt;
	private JTextField votevTxt;
	private JComboBox starRevBox;
	private JComboBox voteRevBox;
	
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
		
		for(int i=0; i<hours.length; i++) {
			if(i<10)
				hours[i] = "0" + i + ":00";
			else
				hours[i] = i + ":00";
		}

		JLabel mainLb = new JLabel("Category");
		mainLb.setFont(new Font("Serif", Font.PLAIN, 18));
		mainLb.setBounds(66, 4, 65, 27);
		contentPane.add(mainLb);
		
		
		// main category checkbox list
		JPanel mainPl = new JPanel(new GridLayout(0, 1));
		JScrollPane maincatScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		maincatScroll.setBounds(10, 37, 210, 346);
		contentPane.add(maincatScroll);
		
		boxes = queryMainCategory();
		for(JCheckBox box: boxes) { //add listener when click main cate
			box.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					System.out.println("clicked");
					votevTxt.setText("");
					starvTxt.setText("");
					toTxt.setText("");
					fromTxt.setText("");
					checkTxt.setText("");
					queryArea.setText("");
					subPl.removeAll();
					sboxes.clear();
					
					for(JCheckBox bo: boxes) {
						if(bo.isSelected()) {
							createSubCategory();
							createMainQuery();
							break;
						}
					}
					
					subPl.revalidate();
					subPl.repaint();
					subScroll.setViewportView(subPl);
				}
			});
			mainPl.add(box);
		}
		
		maincatScroll.setViewportView(mainPl);
		
		//sub category
		JLabel subLb = new JLabel("Sub-Category");
		subLb.setFont(new Font("Serif", Font.PLAIN, 18));
		subLb.setBounds(274, 4, 120, 27);
		contentPane.add(subLb);
		
		subScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		subScroll.setBounds(234, 37, 215, 345);
		contentPane.add(subScroll);
		
		subPl = new JPanel(new GridLayout(0, 1));
		subScroll.setViewportView(subPl);

		//checkin
		JLabel checkinLb = new JLabel("Checkin");
		checkinLb.setFont(new Font("Serif", Font.PLAIN, 18));
		checkinLb.setBounds(508, 7, 120, 21);
		contentPane.add(checkinLb);
		
		JPanel checkPn = new JPanel();
		checkPn.setBackground(Color.WHITE);
		checkPn.setBounds(460, 29, 168, 376);
		contentPane.add(checkPn);
		checkPn.setLayout(new GridLayout(0, 1, 1, 1));
		
		JLabel lblNewLabel = new JLabel("From");
		checkPn.add(lblNewLabel);
		
		dayBox = new JComboBox(days);
		checkPn.add(dayBox);
		
		hourBox = new JComboBox(hours);
		checkPn.add(hourBox);

		JLabel lblNewLabel1 = new JLabel("To");
		checkPn.add(lblNewLabel1);
		
		dayBox2 = new JComboBox(days);
		checkPn.add(dayBox2);
		
		hourBox2 = new JComboBox(hours);
		checkPn.add(hourBox2);
		
		JLabel lblNewLabel2 = new JLabel("Num. of Checkins");
		checkPn.add(lblNewLabel2);
		
		checkBox = new JComboBox(compare);
		checkPn.add(checkBox);
		
		JLabel lblNewLabel3 = new JLabel("Value");
		checkPn.add(lblNewLabel3);
		
		checkTxt = new JTextField();
		checkPn.add(checkTxt);
		
		checkTxt.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				createCheckQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				createCheckQuery();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				createCheckQuery();
			}
		});
		
		//review
		JLabel revLb = new JLabel("Review");
		revLb.setFont(new Font("Serif", Font.PLAIN, 18));
		revLb.setBounds(678, 4, 120, 21);
		contentPane.add(revLb);
		
		JPanel revPn = new JPanel();
		revPn.setBackground(Color.WHITE);
		revPn.setBounds(638, 29, 162, 376);
		contentPane.add(revPn);
		revPn.setLayout(new GridLayout(0, 2, 1, 1));
		
		JLabel lblNewLabel_1 = new JLabel("<html>From<br>(YYYY-MM-DD)</html>");
		revPn.add(lblNewLabel_1);
		
		fromTxt = new JTextField();
		fromTxt.setToolTipText("YYYY-MM-DD");
		revPn.add(fromTxt);
		fromTxt.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("<html>To<br>(YYYY-MM-DD)</html>");
		revPn.add(lblNewLabel_2);
		
		toTxt = new JTextField();
		revPn.add(toTxt);
		toTxt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Star");
		revPn.add(lblNewLabel_3);
		
		starRevBox = new JComboBox(compare);
		revPn.add(starRevBox);
		
		JLabel lblNewLabel_4 = new JLabel("Value");
		revPn.add(lblNewLabel_4);
		
		starvTxt = new JTextField();
		revPn.add(starvTxt);
		starvTxt.setColumns(10);
		starvTxt.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				 createRevQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				createRevQuery();
				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				createRevQuery();
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel("Votes");
		revPn.add(lblNewLabel_5);
		
		voteRevBox = new JComboBox(compare);
		revPn.add(voteRevBox);
		
		JLabel lblNewLabel_6 = new JLabel("Value");
		revPn.add(lblNewLabel_6);
		
		votevTxt = new JTextField();
		revPn.add(votevTxt);
		votevTxt.setColumns(10);
		votevTxt.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				 createRevQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				createRevQuery();
				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				createRevQuery();
			}
		});
	}
	
	private void createRevQuery() {
		boolean isCat = false;
		StringBuilder defa = new StringBuilder("SELECT * FROM BUSINESS WHERE BUSINESS_ID IN (\n");
		StringBuilder cur = new StringBuilder("SELECT R.BUSINESS_ID FROM REVIEWS R \nWHERE ");
		StringBuilder sb = new StringBuilder(" AND B.BUSINESS_ID IN (\n");

		
		if(!fromTxt.getText().isEmpty()) {
			cur.append("REVIEW_DATE >= TO_DATE('").append(fromTxt.getText()).append("','yyyy-mm-dd') ");
		}
		
		if(!toTxt.getText().isEmpty()) {
			if(!cur.toString().endsWith("WHERE ")) {
				cur.append("AND ");
			}
			
			cur.append("REVIEW_DATE <= TO_DATE('").append(toTxt.getText()).append("','yyyy-mm-dd') ");
		}
		
		if(!starvTxt.getText().isEmpty()) {
			if(!cur.toString().endsWith("WHERE ")) {
				cur.append("AND ");
			}
			
			cur.append("STARS").append(starRevBox.getSelectedItem()).append(starvTxt.getText()).append(" ");
		}
		
		if(!votevTxt.getText().isEmpty()) {
			if(!cur.toString().endsWith("WHERE ")) {
				cur.append("AND ");
			}
			
			cur.append("(USEFUL+FUNNY+COOL)").append(voteRevBox.getSelectedItem()).append(votevTxt.getText());
		}
		
		cur.append(") ");
		
		//check cate selected
		for(JCheckBox b: boxes) {
			if(b.isSelected()) {
				isCat = true;
				break;
			}
		}
		
		if(!isCat) {
			for(JCheckBox b: sboxes) {
				if(b.isSelected()) {
					isCat = true;
					break;
				}
			}
		}
		
		if(isCat) {
			createSubQuery();
			createCheckQuery();
			queryArea.setText(queryArea.getText() + sb.toString() + cur.toString());
		} else {
			queryArea.setText(defa.toString() + cur.toString());
		}
	}
	
	private void createCheckQuery() {
		StringBuilder defa = new StringBuilder("SELECT * FROM BUSINESS WHERE BUSINESS_ID IN (\n");
		StringBuilder sb = new StringBuilder(" AND B.BUSINESS_ID IN (\n");//WHERE BUSINESS_ID IN (\
		StringBuilder res = new StringBuilder("SELECT BUSINESS_ID FROM CHECKIN \nWHERE ");
		boolean isCat = false;
		
		if(checkTxt.getText().isEmpty()) {
			createSubQuery();
			return;
		}
		
		for(JCheckBox b: boxes) {
			if(b.isSelected()) {
				isCat = true;
				break;
			}
		}
		
		if(!isCat) {
			for(JCheckBox b: sboxes) {
				if(b.isSelected()) {
					isCat = true;
					break;
				}
			}
		}
		
		res.append("DAY>=").append(dayBox.getSelectedIndex()).append(" AND ").append("DAY<=").append(dayBox2.getSelectedIndex());
		res.append(" AND ").append("HOUR>=").append(hourBox.getSelectedIndex());
		res.append(" AND ").append("HOUR<").append(hourBox2.getSelectedIndex()).append(" \n");
		res.append("GROUP BY BUSINESS_ID HAVING SUM(COUNT)").append(checkBox.getSelectedItem()).append(checkTxt.getText()).append(")");
		
		if(isCat) {
			createSubQuery();
			queryArea.setText(queryArea.getText() + sb.toString() + res.toString());
			
		} else
			queryArea.setText(defa + res.toString());
	}
	
	//SELECT * FROM BUSINESS B, MAIN_CATE M, SUB_CATE S WHERE M.CAT_ID=B.CATEGORIES AND (M.CAT_ID=1) 
	//AND B.BUSINESS_ID=S.BUSINESS_ID AND (S.NAME = 'Swimming Lessons/Schools')
	private void createMainQuery() {
		StringBuilder sb = new StringBuilder("SELECT B.*, M.NAME AS MAIN_CATEGORY FROM BUSINESS B, MAIN_CATE M \nWHERE M.CAT_ID=B.CATEGORIES AND (M.CAT_ID=");
		boolean first = true;
		
		for(JCheckBox bo: boxes) {
			if(bo.isSelected()) {
				if(first) {
					sb.append(bo.getActionCommand());
					first = false;
				} else
					sb.append(" OR M.CAT_ID=").append(bo.getActionCommand());
			}
		}
		sb.append(")");
		queryArea.setText(sb.toString());
	}
	
	private void createSubQuery() {
		StringBuilder ands = new StringBuilder("SELECT B.*, M.NAME AS MAIN_CATEGORY, S.NAME AS SUB_CATEGORY"
				+ " FROM BUSINESS B, MAIN_CATE M, SUB_CATE S \nWHERE M.CAT_ID=B.CATEGORIES AND (M.CAT_ID=");
		boolean isSubSelected = false;
		boolean isFirst = true;
		boolean first = true;
		
		for(JCheckBox bo: boxes) {
			if(bo.isSelected()) {
				if(first) {
					ands.append(bo.getActionCommand());
					first = false;
				} else
					ands.append(" OR M.CAT_ID=").append(bo.getActionCommand());
			}
		}
		ands.append(") ");
		
		ands.append("AND B.BUSINESS_ID=S.BUSINESS_ID AND (S.NAME='");
		
		for(JCheckBox bo: sboxes) {
			if(bo.isSelected()) {
				isSubSelected = true;
				
				if(isFirst) {
					isFirst = false;
					ands.append(bo.getText()).append("' ");
				} else {
					ands.append("OR S.NAME='").append(bo.getText()).append("' ");
				}
			}
		}
		
		ands.append(")");
		
		if(!isSubSelected) {
			createMainQuery();
		} else {
			queryArea.setText(ands.toString());
		}
	}
	
	private void createSubCategory() {
		DBconn db = new DBconn();
		//ArrayList<String> ids = new ArrayList<>();
		StringBuilder ands = new StringBuilder();
		boolean isFirst = true;
		int count = 0;
		
		for(JCheckBox bo: boxes) {
			if(bo.isSelected()) {
				if(isFirst) {
					ands.append("B.CATEGORIES=").append(bo.getActionCommand()).append(" ");
					isFirst = false;
				} else {
					ands.append("OR ").append("B.CATEGORIES=").append(bo.getActionCommand()).append(" ");
				}
			}
		}
		System.out.println("SELECT DISTINCT C.NAME FROM SUB_CATE C, BUSINESS B WHERE (" + ands.toString() +
				") AND C.BUSINESS_ID=B.BUSINESS_ID");
		
		ResultSet res = db.executeQuery("SELECT DISTINCT C.NAME FROM SUB_CATE C, BUSINESS B WHERE (" + ands.toString() +
								") AND C.BUSINESS_ID=B.BUSINESS_ID");
		
		try {
			while(res.next()) {
				JCheckBox b = new JCheckBox(res.getString(1));
				
				//sub cate listener (make query)
				b.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent e) {
						votevTxt.setText("");
						starvTxt.setText("");
						toTxt.setText("");
						fromTxt.setText("");
						checkTxt.setText("");
						createSubQuery();
					}
				});
				
				sboxes.add(b);
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(count);
		
		//add panel
		for(JCheckBox bb: sboxes)
			subPl.add(bb);
		
		db.closeDB();
	}
	
	private List<JCheckBox> queryMainCategory() {
		//JCheckBox b = new JCheckBox("test");
		List<JCheckBox> boxes = new ArrayList<>();
		//boxes.add(b);
		
		DBconn db = new DBconn();
		
		ResultSet res = db.executeQuery("SELECT * FROM MAIN_CATE");
		
		try {
			while(res.next()) {
				JCheckBox b = new JCheckBox(res.getString(2));				
				b.setActionCommand(Integer.toString(res.getInt(1)));
				boxes.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		db.closeDB();
		
		return boxes;
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
		queryLb.setBounds(604, 407, 46, 21);
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
		userLb.setBounds(198, 404, 41, 27);
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
