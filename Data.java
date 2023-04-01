// create data object here

public class Data 
{
	private String PDBID;
	private String bindingType;
	private String relationType;
	private String measurement;
	private String units;
	private String ligandName;
	
	public void setData(String p, String b,String r,String m, String u, String n)
	{
		PDBID = p.toLowerCase();
		bindingType = b.toLowerCase();
		relationType = r;
		measurement = m;
		units = u.toLowerCase();
		ligandName = n.toLowerCase();
	}
	
	public String getData()
	{
		return String.format(PDBID); //+ "   " +
						//bindingType + relationType + measurement +
							//units + "  " + 
							//ligandName + "  ");
	}
	
	public String getPDBID() {return PDBID;}
	public String getBindingType() {return bindingType;}
	public String getRelationType() {return relationType;}
	public String getMeasurement() {return measurement;}
	public String getUnits() {return units;}
	public String getLigandName() {return ligandName;}
	
	public boolean hasPDBID(String s) 
	{
		if (s.equals(PDBID)) {return true;}
		else {return false;}
	}
	
}
