package competition.cs4701;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.ai.BasicAIAgent;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.environments.Environment;

public class ForwardJumpingAgent2 extends BasicAIAgent implements Agent {
	private float previousMarioX;
	private float previousMarioY;
	private int timeInSameSpot;
	private boolean tryingToJump;

	public ForwardJumpingAgent2() {
		super("ForwardJumpingAgent2");
		reset();
	}

	public void reset() {
		action = new boolean[Environment.numberOfButtons];
		action[Mario.KEY_RIGHT] = true;
		action[Mario.KEY_SPEED] = true;
		tryingToJump = false;
		timeInSameSpot = 0;
	}

	public boolean[] getAction(Environment observation) {
		float marioXpos = observation.getMarioFloatPos()[0];
		float marioYpos = observation.getMarioFloatPos()[1];

		if (marioXpos == previousMarioX) {
			timeInSameSpot++;
		} else {
			previousMarioX = marioXpos;
			timeInSameSpot = 0;
		}

		if (timeInSameSpot > 3 && !tryingToJump) {
			action[Mario.KEY_JUMP] = true;
			action[Mario.KEY_RIGHT] = false;
			action[Mario.KEY_LEFT] = true;
			tryingToJump = true;
		} else if (tryingToJump) {
			action[Mario.KEY_JUMP] = false;

			if (previousMarioY == marioYpos) {
				tryingToJump = false;
				action[Mario.KEY_LEFT] = false;
				action[Mario.KEY_RIGHT] = true;
				action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = observation
						.mayMarioJump() || !observation.isMarioOnGround();
			}

			if (timeInSameSpot > 3) {
				tryingToJump = false;
				action[Mario.KEY_RIGHT] = true;
				action[Mario.KEY_JUMP] = true;
			}
		} else {
			action[Mario.KEY_SPEED] = action[Mario.KEY_JUMP] = observation
					.mayMarioJump() || !observation.isMarioOnGround();
		}

		previousMarioY = marioYpos;

		return action;
	}
}
