package resources;

import java.util.Random;
import java.util.Vector;

public class Question {
	private static final int GROUP = 0;
	private static final int SUBGROUP = 1;
	private static final int DRUGNAME = 2;

	public Vector<String> possibleAnswers;
	public int correctAnswerIndex, numberOfOptions; //index of possibleAnswers containing the correct answer
	public String answer;
	public String Text;
	Random generator;

	public Question(int numberOfOptions)
	{
		this.numberOfOptions=numberOfOptions;
		Text = new String();
		possibleAnswers = new Vector<String>(numberOfOptions);
		generator=new Random();
	}

	public void generate()
	{
		int questionType=generator.nextInt(2); //0-3

		Drug drug = Database.getInstance().getRandom();
		Text="";
		correctAnswerIndex=-1;
		answer="";
		possibleAnswers.removeAllElements();

		switch (questionType) {
		case GROUP:
			groupQuestion(drug);
			break;
		case SUBGROUP:
			subGroupQuestion(drug);
			break;
		case DRUGNAME:
			nameQuestion(drug);
			break;
		}
	}


	private void groupQuestion(Drug drug){
		Drug aux;
		Text = "What drug belongs to the group: "+drug.group+"?";
		correctAnswerIndex = generator.nextInt(numberOfOptions);
		answer=drug.name;

		for(int i=0; i<numberOfOptions;i++)
		{
			if(i==correctAnswerIndex)
			{
				possibleAnswers.add(answer);  //correct answer
			}
			else
			{
				String badAnswer = Database.getInstance().getRandomNotInGroup(drug.group,GROUP).name;
				while(possibleAnswers.contains(badAnswer))
				{
					aux = Database.getInstance().getRandomNotInGroup(drug.group,GROUP);
					if(!aux.group.equals(drug.group))
					{
						badAnswer=aux.name;
						drug.group=aux.group;
					}

				}
				possibleAnswers.add(badAnswer);
			}
		}
	}

	private void subGroupQuestion(Drug drug){
		Text = "Which of these subgroups is "+drug.name+" a part of?";
		correctAnswerIndex = generator.nextInt(numberOfOptions);
		answer=drug.subGroup;
		Drug aux;
		int almostCorrectAnswer=-1;
		if(Database.getInstance().subGroupDictionary.get(answer).size()>1)
		{	
			almostCorrectAnswer=correctAnswerIndex;
			while(almostCorrectAnswer==correctAnswerIndex)
			{
				almostCorrectAnswer=generator.nextInt(numberOfOptions);
			}
		}

		if(almostCorrectAnswer!=-1)
		{
			String closeAnswer = Database.getInstance().getRandomBySubGroup(drug.subGroup).name;
			while(possibleAnswers.contains(closeAnswer))
			{closeAnswer = Database.getInstance().getRandomBySubGroup(drug.subGroup).name;}
			possibleAnswers.add(closeAnswer);
		}			
		
		for(int i=0; i<numberOfOptions;i++)
		{
			if(i==correctAnswerIndex)
			{
				possibleAnswers.add(answer);
			}
			else if(i==almostCorrectAnswer)
			{}
			else
			{
				String badAnswer = Database.getInstance().getRandomNotInGroup(drug.subGroup,SUBGROUP).subGroup;
				while(possibleAnswers.contains(badAnswer))
				{
					aux = Database.getInstance().getRandomNotInGroup(drug.group,GROUP);
					if(!aux.group.equals(drug.group))
					{
						badAnswer=aux.name;
						drug.group=aux.group;
					}

				}
				possibleAnswers.add(badAnswer);
			}
		}
	}

	private void nameQuestion(Drug drug){
		Text = "What is the subgroup of : "+drug.name+"?";
		correctAnswerIndex = generator.nextInt(numberOfOptions);
		answer=drug.subGroup;
		Drug aux;
		for(int i=0; i<numberOfOptions;i++)
		{
			if(i==correctAnswerIndex)
				possibleAnswers.add(answer);
			else
			{
				String badAnswer = Database.getInstance().getRandomNotInGroup(drug.subGroup, SUBGROUP).name;
				while(possibleAnswers.contains(badAnswer))
				{
					aux = Database.getInstance().getRandomNotInGroup(drug.group,GROUP);
					if(!aux.group.equals(drug.group))
					{
						badAnswer=aux.name;
						drug.group=aux.group;
					}

				}
				possibleAnswers.add(badAnswer);
			}
		}
	}

	@Override
	public String toString() {
		String x = "Question: "+Text+"\n";
		for(int i=0;i<numberOfOptions;i++)
			x+="\t"+i+" - "+possibleAnswers.get(i)+"\n";
		x+="Answer: "+possibleAnswers.get(correctAnswerIndex)+"\n";
			
		return x;
	}
	

}
