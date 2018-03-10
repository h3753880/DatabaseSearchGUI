import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserTxtListener implements DocumentListener {
	JTextField member;
	JTextField review;
	JTextField friend;
	JTextField star;
	JComboBox revBox;
	JComboBox friendBox;
	JComboBox starBox;
	JComboBox selectBox;
	JTextArea queryArea;

	public UserTxtListener(JTextField member, JTextField review, JTextField friend, JTextField star, 
			JComboBox revBox, JComboBox friendBox, JComboBox starBox, JComboBox selectBox, JTextArea queryArea) {
		// TODO Auto-generated constructor stub
		this.member = member;
		this.review = review;
		this.friend = friend;
		this.star = star;
		this.revBox = revBox;
		this.friendBox = friendBox;
		this.starBox = starBox;
		this.selectBox = selectBox;
		this.queryArea = queryArea;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		update();
		
	}

	//select * from users u where u.yelping_since = to_date('2013-05-01', 'yyyy-mm-dd') and 5=(select count(*) from user_friends f where u.user_id=f.user_id)
	private void update() {
		String concat = selectBox.getSelectedItem().toString();
		String begin = "SELECT * FROM USERS U \nWHERE ";
		StringBuilder sb = new StringBuilder(begin);
		
		if(!member.getText().isEmpty()) {
			sb.append("YELPING_SINCE >= TO_DATE('").append(member.getText()).append("-01', 'yyyy-mm-dd') ");
		}
		
		if(!review.getText().isEmpty()) {
			if(!sb.toString().endsWith("WHERE ")) {
				sb.append(concat).append(" ");
			}
			
			sb.append("REVIEW_COUNT").append(revBox.getSelectedItem().toString()).append(review.getText()).append(" ");
		}
		
		if(!star.getText().isEmpty()) {
			if(!sb.toString().endsWith("WHERE ")) {
				sb.append(concat).append(" ");
			}
			
			sb.append("AVERAGE_STARS").append(starBox.getSelectedItem().toString()).append(star.getText()).append(" ");
		}
		
		if(!friend.getText().isEmpty()) {
			if(!sb.toString().endsWith("WHERE ")) {
				sb.append(concat).append(" ");
			}
			
			sb.append("(SELECT COUNT(*) FROM USER_FRIENDS F WHERE U.USER_ID=F.USER_ID)");
			sb.append(friendBox.getSelectedItem().toString()).append(friend.getText());
		}
 		
		if(!begin.equals(sb.toString()))
			queryArea.setText(sb.toString());
	}
}
