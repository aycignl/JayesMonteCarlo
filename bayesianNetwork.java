
import java.util.Arrays;

import org.eclipse.recommenders.jayes.BayesNet;
import org.eclipse.recommenders.jayes.BayesNode;
import org.eclipse.recommenders.tests.jayes.util.ArrayUtils;

public class bayesianNetwork {
	public static void main(String args[]) {

		// ****************** Create Bayesian Network *********************//
		
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
}
}
