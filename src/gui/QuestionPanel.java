package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

public class QuestionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int id=0;
	public int myId;
	Window parent;
	String question,answer;
	int correctIndex;
	ButtonGroup possibilities;
	JRadioButton[] radioButtons;
	Vector<String> answers;
	public QuestionPanel(Window Parent, final String text, String Answer, int correctAnswerIndex, Vector<String> possibleAnswers) {
		this.parent=Parent;
		this.setPreferredSize(parent.getSize());
		answers = possibleAnswers;
		correctIndex=correctAnswerIndex;
		radioButtons = new JRadioButton[possibleAnswers.size()];
		myId=++id;
		question = text;
		this.answer=Answer;
		possibilities = new ButtonGroup();
		setLayout(new BorderLayout(0, 0));
		
		JLabel QuestionLabel = new JLabel(text);
		QuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		QuestionLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(QuestionLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNext = new JButton("Next");
		btnNext.setHorizontalAlignment(SwingConstants.LEFT);
		btnNext.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(btnNext);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(15, 0, 0, 0));
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		//int i;
		for(int i=0;i<possibleAnswers.size();i++)
		{
			radioButtons[i] = new JRadioButton(possibleAnswers.get(i));
			radioButtons[i].setSize(radioButtons[i].getPreferredSize());
			if(i==0)
				radioButtons[i].setSelected(true);
			radioButtons[i].setActionCommand(Integer.toString(i));
			panel_1.add(radioButtons[i],BorderLayout.CENTER);
			possibilities.add(radioButtons[i]);
		}
		revalidate();
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.saveAnswer(myId,text,answers.get(Integer.parseInt(possibilities.getSelection().getActionCommand())),answer);
				if(Integer.parseInt(possibilities.getSelection().getActionCommand())==correctIndex)
					parent.nextQuestion(myId,true);
				else 
					parent.nextQuestion(myId,false);

			}
		});
		correctIndex=correctAnswerIndex;
	}
	public static void reset() {
		id=0;		
	}
}
