package ch.idsia.scenarios;

import java.util.Scanner;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.AgentsPool;
import ch.idsia.ai.tasks.ProgressTask;
import ch.idsia.ai.tasks.Task;
import ch.idsia.tools.CmdLineOptions;
import ch.idsia.tools.EvaluationOptions;

/**
 * Created by IntelliJ IDEA. User: julian Date: May 5, 2009 Time: 12:46:43 PM
 */
public class Play {

	public static void main(String[] args) {
		System.out.println("Yo specify a controller.  Examples:");
		System.out.println("ch.idsia.ai.agents.human.HumanKeyboardAgent");
		System.out.println("ch.idsia.ai.agents.ai.ForwardAgent");
		System.out.println("competition.cig.robinbaumgarten.AStarAgent");
		System.out.println();
		Scanner controllerInput = new Scanner(System.in);
		Agent controller = AgentsPool.load(controllerInput.next());
		controllerInput.close();
		AgentsPool.addAgent(controller);
		EvaluationOptions options = new CmdLineOptions(new String[0]);
		options.setAgent(controller);
		Task task = new ProgressTask(options);
		options.setMaxFPS(false);
		options.setVisualization(true);
		options.setNumberOfTrials(1);
		options.setMatlabFileName("");
		options.setLevelRandSeed((int) (Math.random() * Integer.MAX_VALUE));
		options.setLevelDifficulty(8);
		task.setOptions(options);

		System.out.println("Score: " + task.evaluate(controller)[0]);
	}
}
