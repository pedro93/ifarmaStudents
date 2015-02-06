package resources;


public class Drug {
	@Override
	public boolean equals(Object obj) {
		Drug u=(Drug) obj;
		return (name.equals(u.name));
	}

	public String group;
	public String subGroup;
	public String name;
	public String mainAction;
	
	Drug(String grp,String subGrp,String name, String action)
	{
		this.group=grp;
		this.subGroup=subGrp;
		this.name=name;
		this.mainAction=action;
	}
	
	@Override
	public String toString() {
		super.toString();
		return "{"+group+", "+subGroup+", "+name+", "+mainAction+"}+\n";
	}
}
