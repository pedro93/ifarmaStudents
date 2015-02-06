package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Database {
	private static final int GROUP = 0;
	private static final int SUBGROUP = 1;
	private static int MAXDBSIZE=1000;
	private static int index;
	public HashMap<String, Integer> nameDictionary=null; 
	public HashMap<String, ArrayList<Integer>> groupDictionary=null;
	public HashMap<String,ArrayList<Integer>> subGroupDictionary=null;
	public HashMap<String,ArrayList<Integer>> actionDictionary=null;
	private ArrayList<Drug> drugs=null;

	private static Database instance = null;

	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public Database(){
		index=0; //row 0 doesnt exist, row 1->column identification, row 2 is the first drug
		nameDictionary = new HashMap<String,Integer>();
		groupDictionary = new HashMap<String,ArrayList<Integer>>();
		actionDictionary = new HashMap<String,ArrayList<Integer>>();
		subGroupDictionary = new HashMap<String,ArrayList<Integer>>();
		drugs = new ArrayList<Drug>(MAXDBSIZE);
	}

	public void addDrug(String grp,String subGrp,String name, String action)
	{
		nameDictionary.put(name, index);
		if(subGroupDictionary.get(subGrp)==null)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(index);
			subGroupDictionary.put(subGrp, list);
		}
		else
		{
			ArrayList<Integer> list = subGroupDictionary.get(subGrp);
			list.add(index);
			subGroupDictionary.put(subGrp, list);
		}
		if(groupDictionary.get(grp)==null)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(index);
			groupDictionary.put(grp, list);
		}
		else
		{
			ArrayList<Integer> list = groupDictionary.get(grp);
			list.add(index);
			groupDictionary.put(grp, list);
		}
		if(actionDictionary.get(action)==null)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(index);
			actionDictionary.put(action, list);
		}
		else
		{
			ArrayList<Integer> list = actionDictionary.get(action);
			list.add(index);
			actionDictionary.put(action, list);
		}

		Drug element = new Drug(grp,subGrp,name,action); 
		drugs.add(element);
		/*
		System.out.println("Drug:" +element);
		System.out.println("GroupDictionary: "+ groupDictionary.get(grp));
		System.out.println("SubGroupDictionary: "+ subGroupDictionary.get(subGrp));
		System.out.println("NameDictionary: "+ nameDictionary.get(name));
		System.out.println("DB: "+ drugs.get(index));
*/
		index++;
	}

	public Drug searchByName(String name)
	{
		return drugs.get(nameDictionary.get(name));
	}

	public ArrayList<Drug> searchBySubGroup(String subGroup)
	{
		ArrayList<Drug> list = new ArrayList<Drug>();
		for(Integer x:subGroupDictionary.get(subGroup))
			list.add(drugs.get(x));
		return list;
	}

	public ArrayList<Drug> searchByGroup(String group)
	{
		ArrayList<Drug> list = new ArrayList<Drug>();
		//System.out.println("SearchByGroup: "+group+", size: "+groupDictionary.get(group).size());
		//System.out.println("result: "+groupDictionary.get(group));
		//System.out.println("db size: "+drugs.size());
		for(Integer x:groupDictionary.get(group))
		{
			list.add(drugs.get(x));
		}
		return list;
	}

	public ArrayList<Drug> searchByAction(String action)
	{
		ArrayList<Drug> list = new ArrayList<Drug>();
		for(Integer x:actionDictionary.get(action))
			list.add(drugs.get(x));
		return list;
	}

	/*
	 * Gets totally random object, may want to get object which has a certain characteristic 
	 */
	public Drug getRandom()
	{
		Random generator = new Random();
		int i = generator.nextInt(drugs.size());
		return drugs.get(i);
	}

	public Drug getRandom(ArrayList<Drug> list)
	{
		Random generator = new Random();
		int i = generator.nextInt(list.size());
		return list.get(i);
	}

	public Drug getRandomBySubGroup(String subGroup)
	{
		Random generator = new Random();
		ArrayList<Drug> list = searchBySubGroup(subGroup);
		int i = generator.nextInt(list.size());
		return list.get(i);
	}

	public Drug getRandomByGroup(String group)
	{
		Random generator = new Random();
		ArrayList<Drug> list = searchByGroup(group);
		int i = generator.nextInt(list.size());
		return list.get(i);
	}

	public Drug getRandomByAction(String action)
	{
		Random generator = new Random();
		ArrayList<Drug> list = searchByAction(action);
		int i = generator.nextInt(list.size());
		return list.get(i);
	}

	public ArrayList<Drug> getCommon(List<Integer> l1, List<Integer>l2)
	{
		ArrayList<Drug> common = new ArrayList<Drug>();
		List<Integer> l3 = new ArrayList<Integer>(l2);
		l3.retainAll(l1);
		for(Integer x:l3)
			common.add(drugs.get(x));
		return common;
	}
	
	public ArrayList<Drug> getDifferent(ArrayList<Drug> l1, ArrayList<Drug>l2)
	{
		ArrayList<Drug> l3 = new ArrayList<Drug>(l1);
		l3.removeAll(l2);
		return l3;
	}
	
	public Drug getRandomNotInGroup(String text, int index)
	{
		ArrayList<Drug> result=null;
		switch (index) {
		case GROUP:
			result = getDifferent(drugs,searchByGroup(text));
			break;
		case SUBGROUP:
			result = getDifferent(drugs,searchBySubGroup(text));
			break;
		}
		Random generator = new Random();
		int i = generator.nextInt(result.size());
		return result.get(i);
	}
}
