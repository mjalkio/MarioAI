package competition.cs4701;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.ai.BasicAIAgent;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.environments.Environment;

public class AmazingAgent extends BasicAIAgent implements Agent {
	private float previousMarioX;
	private int timeInSameSpot;

	public AmazingAgent() {
		super("AmazingAgent");
		reset();
	}

	public void reset() {
		action = new boolean[Environment.numberOfButtons];
		action[Mario.KEY_RIGHT] = true;
		action[Mario.KEY_SPEED] = true;
	}

	private boolean enemyBelow(byte[][] enemies) {
		for (int x = 12; x < 17; ++x) {
			for (int y = 12; y < 22; ++y) {
				if (enemies[y][x] != 0) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean enemyAbove(byte[][] enemies) {
		for (int y = 10; y > 5; --y) {
			for (int x = 11; x < 15; ++x) {
				if (enemies[y][x] != 0) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean enemyInFront(byte[][] enemies) {
		for (int y = 12; y > 9; --y) {
			for (int x = 11; x < 13; ++x) {
				if (enemies[y][x] != 0) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean gapAhead(byte[][] levelScene) {
		int y = 12;
		boolean gapDetected;

		for (int x = 15; x < 18; ++x) {
			if (levelScene[y][x] == 0) {
				gapDetected = true;
				for (int s = y; s < 22; ++s) {
					if (levelScene[s][x] != 0) {
						gapDetected = false;
					}
				}
				if (gapDetected) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean[] getAction(Environment observation) {
		byte[][] enemies = observation.getEnemiesObservation();
		float marioXpos = observation.getMarioFloatPos()[0];

		if (marioXpos == previousMarioX) {
			timeInSameSpot++;
		} else {
			previousMarioX = marioXpos;
			timeInSameSpot = 0;
		}

		if (enemyAbove(enemies)) {
			action[Mario.KEY_JUMP] = false;
			action[Mario.KEY_RIGHT] = true;
		} else if (enemyInFront(enemies)) {
			action[Mario.KEY_RIGHT] = false;
			action[Mario.KEY_JUMP] = true;
		} else {
			action[Mario.KEY_JUMP] = observation.mayMarioJump()
					|| !observation.isMarioOnGround();
			action[Mario.KEY_RIGHT] = true;
		}

		if (timeInSameSpot > 75) {
			action[Mario.KEY_JUMP] = observation.mayMarioJump()
					|| !observation.isMarioOnGround();
			action[Mario.KEY_RIGHT] = true;
		}

		return action;
	}
}
