
package bayesian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;
import org.eclipse.recommenders.tests.jayes.util.ArrayUtils;

public class sampling {

	public static void main(String args[]) {

		// ****************** Bayesian Network *********************//
		
		BayesNet bnet = new BayesNet();

		// create first node with its prior
		BayesNode firstNode = bnet.createNode("n_1");
		firstNode.addOutcomes("true", "false");
		firstNode.setProbabilities(0.6, 0.4);
		// double r = Math.random();
		// node_first.setProbabilities(r, 1 - r);
		
		// create node:f2 with its prior
		BayesNode secondNode = bnet.createNode("n_2");
		secondNode.addOutcomes("true", "false");
		secondNode.setProbabilities(0.25, 0.75);
		// r = Math.random();
	        // secondNode.setProbabilities(1 - r, r);
				
		// create new node
		BayesNode classNode = bnet.createNode("n_class");
		// class node has three types of outcomes
		classNode.addOutcomes("first", "second", "third");
		classNode.setParents(Arrays.asList(firstNode, secondNode));
		// @formatter:off
		classNode.setProbabilities(ArrayUtils.flatten(new double[][][] {
				{ { 0.45, 0.25, 0.3 }, // f1=true,f2=true,
						{ 0.25, 0.7, 0.05 } // f1=true,f2=false,

				}, { { 0.2, 0.4, 0.4 }, // f1=false,f2=true,
						{ 0.1, 0.1, 0.8 } // f1=false,f2=false.
				} }));
		

		// ****************** Monte Carlo Simulation *********************//

		// number_of_iteration for loop.
		int numberOfIter = 750;
		// number_of_nodes in this Bayesian Network--->numberNodes;
		int numberOfNodes = 3;
		// table members are Monte Carlo sampling.
		int[][] table = new int[numberOfIter][numberOfNodes];
		ArrayList<double[]> nodeValues = new ArrayList<double[]>();
		nodeValues.add(firstNode.getProbabilities());
		nodeValues.add(secondNode.getProbabilities());
		nodeValues.add(classNode.getProbabilities());

		fillTableContent(numberOfIter, numberOfNodes, table, nodeValues);

		displayTableContent(numberOfIter, numberOfNodes, table);
	}

	// fillTableContent method goal is to fill the sample_Table.
	private static void fillTableContent(int numberIter, int numberNodes,
			int[][] table, ArrayList<double[]> nodeValues) {
		// create random generator.
		Random random = new Random();
		for (int i = 0; i < numberIter; i++) {
			for (int j = 0; j < numberNodes - 1; j++) {
				double rand = random.nextDouble();

				if (rand < nodeValues.get(j)[0]) {
					table[i][j] = 1;
				} else {
					table[i][j] = 0;
				}

			}
			// for p(n_class|n_1,n_2). outputs are difference with previous columns
			// table[i][5]={1,2,3}
			int j = numberNodes - 1;
			double randd = random.nextDouble();
			// f_1 = true and f_2 = true.
			if (table[i][0] == 1 && table[i][1] == 1) {
				if (randd < nodeValues.get(j)[0]) {
					table[i][j] = 1;
				} else if (randd < nodeValues.get(j)[0] + nodeValues.get(j)[1]) {
					table[i][j] = 2;

				} else if (randd < 1) {

					table[i][j] = 3;
				}

			}

			// n_1 = true and n_2 = false.
			else if (table[i][0] == 1 && table[i][1] == 0) {
				if (randd < nodeValues.get(j)[3]) {
					table[i][j] = 1;
				} else if (randd < nodeValues.get(j)[3] + nodeValues.get(j)[4]) {
					table[i][j] = 2;

				} else if (randd < 1) {

					table[i][j] = 3;
				}

			}

			// n_1 = false and n_2 = true.
			else if (table[i][0] == 0 && table[i][1] == 1) {
				if (randd < nodeValues.get(j)[6]) {
					table[i][j] = 1;
				} else if (randd < nodeValues.get(j)[6] + nodeValues.get(j)[7]) {
					table[i][j] = 2;

				} else if (randd < 1) {

					table[i][j] = 3;
				}

			}
			// n_1 = false and n_2 = false.
			else if (table[i][0] == 0 && table[i][1] == 0) {
				if (randd < nodeValues.get(j)[9]) {
					table[i][j] = 1;
				} else if (randd < nodeValues.get(j)[9] + nodeValues.get(j)[10]) {
					table[i][j] = 2;

				} else if (randd < 1) {

					table[i][j] = 3;
				}

			}
		}
	}

	// displayTableContent method goal is to display filling table.
	private static void displayTableContent(int numberIter, int numberNodes,
			int[][] table) {
		for (int i = 0; i < numberIter; i++) {

			for (int j = 0; j < numberNodes; j++) {
				System.out.print(table[i][j] + " ");

			}
			System.out.println();

		}
	}

}
