import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class GraphProgram 
{
	public static void main(String[] args) throws Exception                 
	{
		//Defines scanner for input
		Scanner scanner = new Scanner(System.in);
		//Prompts user for how many vertices to check from file
		System.out.print("Enter number of vertices in file: ");
		int V = scanner.nextInt();
		//Creates a LL of integers with size of number of vertices defined above
		LinkedList<Integer>[] graphAdjacencyList = new LinkedList[V];
                //Creates a second dimension to LL for each row
		for(int i = 0; i < V; i++){
			graphAdjacencyList[i] = new LinkedList<>();
                }
                //Creates a 2D array for AdjacencyMatrix
		int[][] graphAdjacencyMatrix = new int[V][V];
		//Prompts user for file to read from
		System.out.print("Enter name of CSV file: ");
		String fileName = scanner.next();
		//Initializes a buffered reader to read from a file
		BufferedReader br = null;
		String line;
		//Attempts to read from user specified file
		try
		{
			br = new BufferedReader(new FileReader(fileName));
		}
                //Exception for if file is not found
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
			return;
		}
		
		//Preconditions--> A defined buffered reader, and a non-empty file
                //Postconditions-->Read file is unchaged, Array and List are filled
		while((line = br.readLine()) != null)
		{
                    String ints[] = line.split(",");
			//If more than 2 characters are read, puts out an error
			if(ints.length != 2)
			{
				System.out.println("Format error. Terminating program.");
				return;
			}
			
			int u = Integer.parseInt(ints[0]);
			int v = Integer.parseInt(ints[1]);
			//Checks that the vertices being read are in range
			if(u < 0 || u >= V || v < 0 || v >= V)
			{
				System.out.println("Vertex values out of range! Terminating program!");
				return;
			}
			//Fills row and column of Adjacency matrix
			graphAdjacencyMatrix[u][v]++;
			graphAdjacencyMatrix[v][u]++;
			//Fills Adjacency List
			graphAdjacencyList[u].add(v);
			graphAdjacencyList[v].add(u);
			
		}
                //Closes the file reader
		br.close();
		
		//Initializes maxDegree to arbitrary small value
		int maxDegree = Integer.MIN_VALUE;
                //Compares size of each row in List to maxdegree and finds largest
                //Preconditions--> List must not be empty
                //Postconditions--> maxDegree is set
		for(int i = 0; i < V; i++)
		{
			if(graphAdjacencyList[i].size() > maxDegree)
			{
				maxDegree = graphAdjacencyList[i].size();
			}
		}
		//Initializes minDegree to arbitrary large value
		int minDegree = Integer.MAX_VALUE;
                //Compares size of each row in List to minDegree and finds smallest
                //Preconditions--> List must not be empty
                //Postconditions--> minDegree is set
		for(int i = 0; i < V; i++)
		{
			if(graphAdjacencyList[i].size() < minDegree)
			{
				minDegree = graphAdjacencyList[i].size();
			}
		}
		//Prints out info for MPV
		System.out.println("Number of neighbors for MPV: " + maxDegree);
		System.out.println();
		System.out.println("MPV Neighbors: ");
		//Displays the vertices for MPV
                //Preconditions--> List is not empty
                //Postconditions-->List is unchanged
		for(int i = 0; i <  V; i++)
		{
			if(graphAdjacencyList[i].size() == maxDegree)
			{
				if(maxDegree == 0)
					System.out.println(i);
				else
					System.out.print(i + ",");
				for(int j = 0; j < graphAdjacencyList[i].size(); j++)
				{
					if(j == graphAdjacencyList[i].size() - 1)
					{
						System.out.println(graphAdjacencyList[i].get(j));
						
					}
					else
					{
						System.out.print(graphAdjacencyList[i].get(j) + ",");
					}
				}
			}
		}
		
		System.out.println();

		//Prints out info for LPV
		System.out.println("Number of neighbors for LPV: " + minDegree);
		System.out.println();
		System.out.println("LPV Neighbors: ");
		//Displays the vertices for LPV
                //Preconditions--> List is not empty
                //Postconditions--> List is unchanged
		for(int i = 0; i < V; i++)
		{
			if(graphAdjacencyList[i].size() == minDegree)
			{
				if(minDegree == 0)
					System.out.println(i);
				else
					System.out.print(i + ",");
				for(int j = 0; j < graphAdjacencyList[i].size(); j++)
				{
					if(j == graphAdjacencyList[i].size() - 1)
					{
						System.out.println(graphAdjacencyList[i].get(j));
					}
					else
					{
						System.out.print(graphAdjacencyList[i].get(j) + ",");
					}
				}
			}
		}
		
		System.out.println();
		
		//Initialize printwriter
		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(new File("AdjacencyMatrix.CSV"));
		}
                //Exception if cannot write to file
		catch(FileNotFoundException e)
		{
			System.out.println("Could not write to AdjacencyMatrix.CSV");
			return;
		}
		//Writes to file
                //Pr econditions--> No catch exception.
                //Postconditions--> Each line is numbered in the file, no errors.
		pw.print("X,");
		for(int i = 0; i < V; i++)
		{
			if(i == V-1)
			{
				pw.println(i);
			}
			else
			{
				pw.print(i + ",");
			}
		}
		//Writes to file
                //Preconditions--> No catch exception, Array is not empty
                //Postconditions--> Array is unchanged, file has been written without error.
		for(int i = 0; i < V; i++)
		{
			pw.print(i + ",");
			for(int j = 0; j < V; j++)
			{
				if(j == V - 1)
				{
					pw.println(graphAdjacencyMatrix[i][j]);
				}
				else
				{
					pw.print(graphAdjacencyMatrix[i][j] + ",");
				}
			}
		}
		pw.close();
		
		try
		{
			pw = new PrintWriter(new File("AdjacencyList.CSV"));
		}
                //Exception if not able to write to Adjacency list
		catch(FileNotFoundException e)
		{
			System.out.println("Could not write to AdjacencyList.CSV");
			return;
		}
		//Writes to file
                //Preconditions--> List is not empty
                //Postconditions--> List is unchaged, file is written to without errors
		for(int i = 0; i < V; i++)
		{
			if (graphAdjacencyList[i].isEmpty())
				pw.println(i);
			else
				pw.print(i + ",");
			for(int j = 0; j < graphAdjacencyList[i].size(); j++)
			{
				if(j == graphAdjacencyList[i].size() - 1)
				{
					pw.println(graphAdjacencyList[i].get(j));
				}
				else
				{
					pw.print(graphAdjacencyList[i].get(j) + ",");
				}
			}
		}
		pw.close();
	}

}