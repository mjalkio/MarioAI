package ch.idsia.scenarios;

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
		Agent controller = AgentsPool
				.load("ch.idsia.ai.agents.human.HumanKeyboardAgent");
		AgentsPool.addAgent(controller);
		EvaluationOptions options = new CmdLineOptions(new String[0]);
		options.setAgent(controller);
		Task task = new ProgressTask(options);
		options.setMaxFPS(false);
		options.setVisualization(true);
		options.setNumberOfTrials(3);
		options.setMatlabFileName("");
		options.setLevelRandSeed((int) (Math.random() * Integer.MAX_VALUE));
		task.setOptions(options);

		for (int i = 0; i < options.getNumberOfTrials(); i++) {
			options.setLevelType(i % 3);
			options.setLevelDifficulty((i + 1) * 3);
			options.setLevelLength((i + 1) * 300);
			System.out.println("Score: " + task.evaluate(controller)[0]);
		}

		System.exit(0);
	}
}
