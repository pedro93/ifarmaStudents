package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField questionNumberTextArea,pointsPerRightAnswer,pointsPerWrongAnswer,optionPerQuestion;
	public JLabel FileName= new JLabel("");
	/**
	 * Create the panel.
	 */
	public StartPanel(final Window parent) {
		setLayout(null);
		this.setPreferredSize(parent.getSize());
		JLabel lblHowManyQuestions = new JLabel("How many questions: ");
		lblHowManyQuestions.setBounds(43, 84, 150, 14);
		add(lblHowManyQuestions);
		
		questionNumberTextArea = new JTextField ();
		questionNumberTextArea.setBounds(209, 80, 120, 22);
		add(questionNumberTextArea);
		
		JLabel lblPenaltyForQuestion = new JLabel("Penalty per wrong answer:");
		lblPenaltyForQuestion.setBounds(43, 173, 150, 14);
		add(lblPenaltyForQuestion);
		
		pointsPerWrongAnswer = new JTextField ();
		pointsPerWrongAnswer.setBounds(209, 169, 120, 22);
		add(pointsPerWrongAnswer);
		
		JLabel lblPointsPerQuestion = new JLabel("Points per question right:  ");
		lblPointsPerQuestion.setBounds(43, 144, 150, 14);
		add(lblPointsPerQuestion);
		
		pointsPerRightAnswer = new JTextField ();
		pointsPerRightAnswer.setBounds(209, 140, 120, 22);
		add(pointsPerRightAnswer);
		
		JButton btnStartQuiz = new JButton("Start Quiz");
		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//parent.createQuestions((Integer) questionNumberTextArea.getValue());
				if(FileName.getText()=="")
					JOptionPane.showMessageDialog(null, "Error no file selected");
				try {
					int quesNumber = Integer.parseInt(questionNumberTextArea.getText());
					int optionQuestion = Integer.parseInt(optionPerQuestion.getText());
					float valueAnswerCorrect = Float.parseFloat(pointsPerRightAnswer.getText());
					float valueAnswerWrong = Float.parseFloat(pointsPerWrongAnswer.getText());
					parent.penalty=valueAnswerWrong;
					parent.benefit=valueAnswerCorrect;
					parent.numberQuestions=quesNumber;
					parent.numberOptions=optionQuestion;
					parent.createQuestions();
				} catch (NumberFormatException e2) {
				}
			}
		});
		btnStartQuiz.setBounds(240, 202, 89, 23);
		add(btnStartQuiz);
		
		JLabel lblFileUsedTo = new JLabel("File used to generate questions: ");
		lblFileUsedTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFileUsedTo.setBounds(134, 11, 181, 14);
		add(lblFileUsedTo);
		
		FileName.setHorizontalAlignment(SwingConstants.CENTER);
		FileName.setBounds(79, 39, 291, 14);
		add(FileName);
		
		JLabel lblHowManyOptions = new JLabel("How many options per question: ");
		lblHowManyOptions.setBounds(43, 117, 168, 14);
		add(lblHowManyOptions);
		
		optionPerQuestion = new JTextField();
		optionPerQuestion.setBounds(209, 113, 120, 22);
		add(optionPerQuestion);
		optionPerQuestion.setColumns(10);

	}
}
