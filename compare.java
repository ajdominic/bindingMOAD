import java.io.*;
import java.util.*;

public class compare 
{
	
	// removes parentheses
	// PDBbind
	public static String removeP(String s)
	{
		return s.substring(1,s.length()-1);
	}
	
	// PDBbind
	public static String bindingType(String s)
	{
		if (s.substring(1,2).equals("i"))
			return "Ki";
		else if (s.substring(1,2).equals("d"))
			return "Kd";
		else 
			return "IC50";
	}
	
	// PDBbind
	public static String relationType(String s)
	{
		if (s.substring(1,2).equals("i") || s.substring(1,2).equals("d"))
			return s.substring(2,3);
		else 
			return s.substring(4,5);
	}
	
	// PDBbind
	public static String getUnits(String s)
	{
		return s.substring(s.length()-2,s.length());
	}
	
	// PDBbind
	public static String getNum(String s)
	{
		if (s.substring(1,2).equals("C"))
			return s.substring(5,s.length()-2);
		else 
			return s.substring(3,s.length()-2);
	}
	
	// MOAD
	public static String getPDBID(String newID, String oldID)
	{
		if (newID.equals("empty")){return oldID;}
		else if (newID.substring(1,2).equals("."))
			return fixID(newID);
		else {return newID;}
	}
	
	public static String fixID(String newID)
	{
		String fixed = newID.substring(0,1) + newID.substring(4,5) 
											+ newID.substring(6,8);
		return fixed;
	}
	
	// MOAD
	public static String fixLigand(String s)
	{
		int index = s.indexOf(":");
		//String ligand = s.substring(0,ctr).replaceAll("\\s", "");
		
		String ligand = s.substring(0,index).replaceAll("\\s","");
		return ligand;
		
	}
	
	
	public static void main(String[] args) throws IOException
	{
		// PDBbind file
		// in this file the first four characters give the PDB
		// beginning at line 26, we have binding data:
		// Ki, IC50, Kd
		
		// begin here for PDBbind
		Scanner inFile1 = new Scanner(new File("INDEX_general_PL_data.2019"));
		for (int i=0; i<6; i++) // skip the preamble in the file
			inFile1.nextLine();
		
		ArrayList<Data> PDBbind = new ArrayList<Data>();
		
		String pdbid = "";
		String res = "";
		double newRes = 0.0;
		String temp = "";
		String type = ""; // kd, ki, IC50
		String relation = ""; // =, >, <, ~
		String binding = ""; // number
		String units = "";
		String name = ""; // ligand name
		
		
		int ctr = 0;
		while (inFile1.hasNextLine())
		{
			
			pdbid = inFile1.next(); // get PDB ID
			res = inFile1.next();
			
			// Default to 2.5 angstroms so I don't have to 
			// deal with the string "NMR"
			if (res.equals("NMR")){newRes = 2.7;}
			else {newRes = Double.parseDouble(res);}
			
			if (newRes <= 2.6) // check complimentary set with >2.6
			{
				inFile1.next();
				inFile1.next();
				
				temp = inFile1.next(); // take in data
										// in form Kd=x
				type = bindingType(temp);
				relation = relationType(temp);
				units = getUnits(temp);
				binding = getNum(temp);
				

				inFile1.next();
				inFile1.next();
				name = removeP(inFile1.next()); // ligand name
				
				Data data = new Data();
				data.setData(pdbid, type, relation, binding, units, name);
				PDBbind.add(data);
				
				// System.out.println(data.getData() + " " + newRes); (used to check)
				
				ctr++;
			}
			//ctr++;
			inFile1.nextLine();
		
		}
		inFile1.close();
		//System.out.println(ctr); (use to check)
		
		
		// MOAD starts here
		// reading in the file to make an array of ArrayLists
		ArrayList<String>[] data = new ArrayList[306535];
		Scanner inFile2 = new Scanner(new File("bindingMOAD.csv")); //MOAD file
		int ctr2 = 0;
		String row = "";
		while (inFile2.hasNextLine())
		{
			row = inFile2.nextLine();
			Scanner lineScan = new Scanner(row).useDelimiter("\\,");
			data[ctr2] = new ArrayList<String>();
			while (lineScan.hasNext()) 
				data[ctr2].add(lineScan.next());	
			ctr2++;
			lineScan.close();
		}
		inFile2.close();
		
		
		
		String pdbid2 = "empty";
		String name2 = ""; // ligand name
		String type2 = ""; // ka, kd, ki, IC50
		String relation2 = ""; // =, >, <, ~
		String binding2 = ""; // number
		String units2 = "";
		
		// making the stack
		Stack moadStack = new Stack();
		for (int i=0; i<data.length;i++)
		{
			pdbid2 = getPDBID(data[i].get(0), pdbid2);
			
			// only adding to the stack when there is useful data
			if (!data[i].get(2).equals("empty"))
			{
				name2 = fixLigand(data[i].get(1));
				type2 = data[i].get(2);
				relation2 = data[i].get(3);
				binding2 = data[i].get(4);
				units2 = data[i].get(5);
				Data moadData = new Data();
				moadData.setData(pdbid2,type2,relation2,binding2,units2,name2);
				moadStack.push(moadData);
			}
		}
		
		// prints the stack (just to check)
		// while (!moadStack.isEmpty()){System.out.println(moadStack.pop().getData());}
		
		int counter = 0;
		Data tempData;
		BufferedWriter writer = new BufferedWriter(new FileWriter("pdbids.txt"));
		while (!moadStack.isEmpty())
		{
			tempData = moadStack.pop();			
			String searchForID = tempData.getPDBID();
			
			int isIn = isIn(searchForID, PDBbind);
			if (isIn != -1)
			{
				if (!compare(tempData, PDBbind.get(isIn)))
				{
					counter++;
					writer.write(PDBbind.get(isIn).getData() + "\n"); //+ 
							//"          " +
							//tempData.getData() + "\n");

					//System.out.printf(PDBbind.get(isIn).getData() + 
					//		"          " +
					//		tempData.getData());
					
					System.out.printf("%s%s \n", PDBbind.get(isIn).getData(), tempData.getData());
				}
			}	
			
		}
		writer.close();
		System.out.println("done" + " " + counter);

	}
	 
	
	public static int isIn(String pdbid, ArrayList<Data> pdbbind)
	{
		int finder = -1;
		for (int i=0; i<pdbbind.size();i++)
		{
			if (pdbbind.get(i).getPDBID().equalsIgnoreCase(pdbid))
			{
				finder = i;
			}
		}
		if (finder == -1)
			return -1;
		else
			return finder;
	}
	
	
	public static boolean compare(Data moad, Data pdbbind)
	{
		double moadNum = 0.0;
		double pdbbindNum = 0.0;
		
		String measMOADStr = "";
		if (moad.getMeasurement().substring(0,1).equals("="))
		{
			measMOADStr = moad.getMeasurement().substring(1,moad.getMeasurement().length());
		}
		else
		{
			measMOADStr = moad.getMeasurement();
		}
		
		String measPDBStr = "";
		if (pdbbind.getMeasurement().substring(0,1).equals("="))
		{
			measPDBStr = pdbbind.getMeasurement().substring(1,pdbbind.getMeasurement().length());
		}
		else
		{
			measPDBStr = pdbbind.getMeasurement();
		}
		
		
		moadNum = Double.parseDouble(measMOADStr);
		pdbbindNum = Double.parseDouble(measPDBStr);
		
		if (moad.getBindingType().equalsIgnoreCase(pdbbind.getBindingType())
				&& moad.getRelationType().equalsIgnoreCase(pdbbind.getRelationType()) 
				&& moad.getUnits().equalsIgnoreCase(pdbbind.getUnits())
				&& (moadNum == pdbbindNum)
				)
			return true;
		
		else
			return false;
	}
	

}
