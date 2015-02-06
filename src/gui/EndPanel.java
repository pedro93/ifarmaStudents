package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.BevelBorder;

public class EndPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JLabel scoreLbl,rightAnswerlbl;
	private JPanel panel_2;
	private JScrollPane pane;
	Window parent;
	int totalQuestion,totalRight;
	/**
	 * Create the panel.
	 * @param window 
	 */

	public EndPanel(Window window) {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				panel_2.setBounds(0, 0, getSize().width, 50);
				pane.setBounds(10, 49, getSize().width, getSize().height-50);

			}
		});
		this.parent=window;
		//this.setPreferredSize(parent.getSize());
		totalQuestion=0;
		totalRight=0;
		setLayout(new BorderLayout(0, 0));

		JLabel lblResults = new JLabel("Results:");
		lblResults.setHorizontalAlignment(SwingConstants.CENTER);
		lblResults.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblResults, BorderLayout.NORTH);


		model = new DefaultTableModel(); 
		model.addColumn("Number"); 
		model.addColumn("Question");
		model.addColumn("Correct answer"); 
		model.addColumn("Your Answer");

		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);

		JButton reQuiz = new JButton("Re-Quiz");
		reQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getRowCount() > 0) {
					for (int i = model.getRowCount() - 1; i > -1; i--) {
						model.removeRow(i);
					}
				}

				totalQuestion=0;
				totalRight=0;
				parent.clean();
			}
		});
		panel.add(reQuiz);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		//panel_1.setPreferredSize(getSize());
		panel_1.setLayout(null);
		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		table.setEnabled(false);

		pane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		pane.setBounds(10, 49, getSize().width-20, getSize().height-120);
		panel_1.add(pane);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		panel_2 = new JPanel();
		panel_2.setBounds(0, 0, getSize().width-20, 50);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblTotalScore = new JLabel("Total Score: ");
		lblTotalScore.setBounds(5, 5, 61, 14);
		panel_2.add(lblTotalScore);

		scoreLbl = new JLabel("0.0"+"/"+1.0*parent.benefit*parent.questions.size());
		scoreLbl.setBounds(66, 5, 168, 14);
		panel_2.add(scoreLbl);

		JLabel lblRightAnswers = new JLabel("Right Answers: ");
		lblRightAnswers.setBounds(5, 25, 79, 14);
		panel_2.add(lblRightAnswers);

		rightAnswerlbl = new JLabel("");
		rightAnswerlbl.setBounds(79, 25, 128, 14);
		panel_2.add(rightAnswerlbl);
	}

	public void addAnswer(int index,String question,String correctAnswer, String SubmittedAnswer)
	{
		//System.out.println("addAnswer: "+index+", "+correctAnswer+", "+SubmittedAnswer);
		totalQuestion++;
		if(correctAnswer.equals(SubmittedAnswer))
			totalRight++;

		model.addRow(new Object[]{index, question, correctAnswer,SubmittedAnswer});
	}

	public void updateResult(float score) {
		System.out.println("score: "+score);
		if(score>=0.0)
			scoreLbl.setText(Float.toString(parent.score)+"/"+1.0*parent.benefit*parent.questions.size());
		else 
			scoreLbl.setText("-"+Float.toString(Math.abs(parent.score))+"/"+1.0*parent.benefit*parent.questions.size());

		rightAnswerlbl.setText(totalRight+"/"+totalQuestion);
	}
}
