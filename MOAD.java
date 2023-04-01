import java.io.*;
import java.util.*;

public class MOAD 
{
	private ArrayList<String>[] data;
	
	public MOAD() throws IOException
	{
		Scanner inFile = new Scanner(new File("bindingMOAD.csv")); //MOAD file
		int ctr = 0;
		String row = "";
		while (inFile.hasNextLine())
		{
			row = inFile.nextLine();
			Scanner lineScan = new Scanner(row).useDelimiter("\\,");
			data[ctr] = new ArrayList<String>();
			while (lineScan.hasNext()) 
				data[ctr].add(lineScan.next());	
			ctr++;
			lineScan.close();
		}
		
		for (int i=0; i<data.length;i++)
		{
			if (!data[i].get(0).equals("empty"))
			{
				if (data[i].get(0).substring(1,2).equals("."))
				{
					data[i].set(0, data[i].get(0).substring(0,1) + 
							data[i].get(0).substring(3,4) + data[i].get(0).substring(5,7));

				}
			}
		}
		
		inFile.close();
	}
}
