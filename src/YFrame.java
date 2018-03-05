import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.GridLayout;

public class YFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		
		JButton submitBtn = new JButton("Execute Query");
		submitBtn.setBounds(532, 629, 181, 23);
		contentPane.add(submitBtn);
		
		JTextArea queryArea = new JTextArea();
		
		JScrollPane scrollPane = new JScrollPane(queryArea);
		scrollPane.setBounds(460, 427, 338, 180);
		contentPane.add(scrollPane);
		
		JPanel userPn = new JPanel();
		userPn.setForeground(Color.BLACK);
		userPn.setBackground(Color.WHITE);
		userPn.setBounds(10, 427, 439, 229);
		userPn.setLayout(new GridLayout(5, 4, 1, 1));
		contentPane.add(userPn);
		
		JLabel memLb = new JLabel("Member since:");
		userPn.add(memLb);
		JLabel memLb2 = new JLabel("Review Count:");
		userPn.add(memLb2);
		JLabel memLb3 = new JLabel("Number of Friends:");
		userPn.add(memLb3);
		JLabel memLb4 = new JLabel("Avg. Stars:");
		userPn.add(memLb4);
		JLabel memLb5 = new JLabel("Select:");
		userPn.add(memLb5);
		
		
		JScrollPane resultScroll = new JScrollPane();
		resultScroll.setBounds(807, 29, 467, 623);
		contentPane.add(resultScroll);
		
		JLabel userLb = new JLabel("Users");
		userLb.setBounds(200, 390, 41, 27);
		userLb.setFont(new Font("Serif", Font.PLAIN, 18));
		contentPane.add(userLb);
		
		JLabel resultLb = new JLabel("Result");
		resultLb.setBounds(1027, 10, 46, 15);
		resultLb.setFont(new Font("Serif", Font.PLAIN, 18));
		contentPane.add(resultLb);
		
		
		
	}
}
