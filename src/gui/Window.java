package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import resources.Question;

import miguel.Main;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Create a file chooser
	final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
	
	public Vector<QuestionPanel> questions = new Vector<QuestionPanel>();
	StartPanel start = new StartPanel(this);
	EndPanel end = new EndPanel(this);
	public int numberQuestions,numberOptions;
	public float benefit,penalty,score;

	//In response to a button click:
	/**
	 * Create the frame.
	 */
	public Window() {
		setResizable(true);
		numberOptions=0;
		numberQuestions=0;
		score=0;
		benefit=0;
		penalty=0;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				fc.setFileFilter(new FileFilter() {

					public String getDescription() {
						return "Excel Documents (*.xlsx)";
					}

					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						} else {
							return f.getName().toLowerCase().endsWith(".xlsx");
						}
					}
				});
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					start.FileName.setText("Loading: " + file.getName());
					start.FileName.setSize(start.FileName.getPreferredSize());
					revalidate();
					Thread t1 = new Thread(new Runnable() 
					{
						public void run()
						{
							try {
								Main.load(file.getAbsolutePath());
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Error reading file:\n"+e1.toString());
								e1.printStackTrace();
							}
							start.FileName.setText("Sucessfully loaded: "+file.getName());
							start.FileName.setSize(start.FileName.getPreferredSize());
							revalidate();
						}
					});
					t1.start();
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setContentPane(start);
		//getContentPane().add(end,0);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoad = new JMenuItem("Load file");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					start.FileName.setText("Loading: " + file.getName());
					Thread t1 = new Thread(new Runnable() 
					{
						public void run()
						{
							try {
								Main.load(file.getAbsolutePath());
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Error reading file:\n"+e1.toString());
								e1.printStackTrace();
							}
							start.FileName.setText("Sucessfully loaded: "+file.getName());
						}
					});
					t1.start();
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}});

		mnFile.add(mntmLoad);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		mnFile.add(mntmExit);		
	}

	public void createQuestions()
	{
		//System.out.println("Number questions: "+numberQuestions);
		Question aux;	
		for(int i=0; i<numberQuestions;i++)
		{
			aux = new Question(numberOptions);
			aux.generate();
			//System.out.println(aux);
			QuestionPanel qPanel = new QuestionPanel(this,aux.Text,aux.answer,aux.correctAnswerIndex,aux.possibleAnswers);
			questions.add(qPanel);
		}
		showPanel(1);
	}

	private void showPanel(int i) {
		//System.out.println("show panel :"+i);
		if(i==0)
			setContentPane(start);
		else if(i==-1)
		{
			end.updateResult(score);
			setContentPane(end);
		}
		else if(i<=numberQuestions)
			setContentPane(questions.get(i-1));
		revalidate();
	}

	public void nextQuestion(int myId, boolean isCorrect) {
		if(isCorrect)
			score+=benefit;
		else
			score-=penalty;

		//System.out.println("correct score: "+score+", id:" +myId);

		if(myId+1>numberQuestions)
		{showPanel(-1);}
		else
		{int i=myId+1;showPanel(i);}
	}

	public void saveAnswer(int myId,String question ,String string, String answer) {
		end.addAnswer(myId, question,string, answer);
	}

	public void clean() {
		QuestionPanel.reset();
		questions.removeAllElements();
		numberOptions=0;
		numberQuestions=0;
		score=0;
		benefit=0;
		penalty=0;
		//start=null;
		//end=null;
		System.gc();
		//end = new EndPanel(this);
		showPanel(0);
	}

}
