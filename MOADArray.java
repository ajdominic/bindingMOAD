import java.io.*;
import java.util.*;

//System.out.println(data.length); // should be 306535
//System.out.println(data[23].size()); // should be 6

public class MOADArray 
{
	
	// method which returns the PDBID
	public static String getPDBID(String newID, String oldID)
	{
		if (newID.equals("empty")){return oldID;}
		else {return newID;}
	}

	public static void main(String[] args) throws IOException
	{
		
		// reading in the file to make an array of ArrayLists
		ArrayList<String>[] data = new ArrayList[306535];
		Scanner inFile2 = new Scanner(new File("bindingMOAD.csv")); //MOAD file
		int ctr = 0;
		String row = "";
		while (inFile2.hasNextLine())
		{
			row = inFile2.nextLine();
			Scanner lineScan = new Scanner(row).useDelimiter("\\,");
			data[ctr] = new ArrayList<String>();
			while (lineScan.hasNext()) 
				data[ctr].add(lineScan.next());	
			ctr++;
			lineScan.close();
		}
		inFile2.close();
		
		
		
		String pdbid = "empty";
		String name = ""; // ligand name
		String type = ""; // ka, kd, ki, IC50
		String relation = ""; // =, >, <, ~
		String binding = ""; // number
		String units = "";
		
		// making the stack
		Stack moadStack = new Stack();
		for (int i=0; i<data.length;i++)
		{
			pdbid = getPDBID(data[i].get(0), pdbid);
			
			// only adding to the stack when there is useful data
			if (!data[i].get(2).equals("empty"))
			{
				name = data[i].get(1);
				type = data[i].get(2);
				relation = data[i].get(3);
				binding = data[i].get(4);
				units = data[i].get(5);
				Data moadData = new Data();
				moadData.setData(pdbid,type,relation,binding,units,name);
				moadStack.push(moadData);
			}
		}
			
	}
	
}
