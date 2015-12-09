package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.Timer;

public class ElevatorSpeedAlgorithm {
	Timer levelTimer = new Timer();
	double minVelocityInPercentThatOvercomesMotorInertia;
	double accelerationStepSizeInPercent;
	int deadbandInEncoderCounts;
	double maxSpeedUpwardInPercent;
	double maxSpeedDownwardInPercent;
	double delayBetweenSpeedEvaluations;
	double currentVelocityCommandInPercent;
	int slowBandMultiplier;
	double speedDownDivisor;
	int remainingEncoderCountsToDeadbandWidened;

	public ElevatorSpeedAlgorithm(double theMinVelocityInPercentThatOvercomesMotorInertia,
			double theAccelerationStepSizeInPercent, int theDeadbandInEncoderCounts, double theMaxSpeedUpwardInPercent,
			double theMaxSpeedDownwardInPercent, double theDelayBetweenSpeedEvaluations, int theSlowBandMultiplier,
			double theSpeedDownDivisor) {

		minVelocityInPercentThatOvercomesMotorInertia = theMinVelocityInPercentThatOvercomesMotorInertia;
		accelerationStepSizeInPercent = theAccelerationStepSizeInPercent;
		deadbandInEncoderCounts = theDeadbandInEncoderCounts;
		maxSpeedUpwardInPercent = theMaxSpeedUpwardInPercent;
		maxSpeedDownwardInPercent = theMaxSpeedDownwardInPercent;
		delayBetweenSpeedEvaluations = theDelayBetweenSpeedEvaluations;
		slowBandMultiplier = theSlowBandMultiplier;
		speedDownDivisor = theSpeedDownDivisor;
		currentVelocityCommandInPercent = 0;
		levelTimer.start();
	}

	public ElevatorSpeedAlgorithm() {
		minVelocityInPercentThatOvercomesMotorInertia = 0.15;
		accelerationStepSizeInPercent =  0.01;
		deadbandInEncoderCounts = 25;
		maxSpeedUpwardInPercent =  1;
		maxSpeedDownwardInPercent = 0.5;
		delayBetweenSpeedEvaluations = 0.005;
		slowBandMultiplier = 5;
		speedDownDivisor =  5;
		currentVelocityCommandInPercent = 0;
		levelTimer.start();
	}

	public double ComputeNextMotorSpeedCommand(int currentEncoderCount, int targetEncoderCount) {

		if (levelTimer.hasPeriodPassed(delayBetweenSpeedEvaluations) == true) {
			levelTimer.reset();
			int remainingEncoderCountsToTarget = targetEncoderCount - currentEncoderCount;
			if (Utilities.fabs(remainingEncoderCountsToTarget) > deadbandInEncoderCounts) {
				remainingEncoderCountsToDeadbandWidened = ((Utilities.fabs(remainingEncoderCountsToTarget))
						- deadbandInEncoderCounts * slowBandMultiplier);
				boolean needToRampDown = (remainingEncoderCountsToDeadbandWidened < 0);

				if (needToRampDown) {
					currentVelocityCommandInPercent /= speedDownDivisor;
				} else {
					if (Utilities.fabs(currentVelocityCommandInPercent) < minVelocityInPercentThatOvercomesMotorInertia)
						currentVelocityCommandInPercent = ((remainingEncoderCountsToTarget > 0) ? 1 : 1)
								* minVelocityInPercentThatOvercomesMotorInertia;
					else
						currentVelocityCommandInPercent += ((remainingEncoderCountsToTarget > 0) ? 1 : -1)
								* accelerationStepSizeInPercent;

				}
			} else {
				currentVelocityCommandInPercent = 0;

			}
		}
		if ((targetEncoderCount == 0) && (currentEncoderCount < remainingEncoderCountsToDeadbandWidened)) {
			currentVelocityCommandInPercent = -minVelocityInPercentThatOvercomesMotorInertia * 2;
		}

		if (currentVelocityCommandInPercent > maxSpeedUpwardInPercent)
			currentVelocityCommandInPercent = maxSpeedUpwardInPercent;
		else if (currentVelocityCommandInPercent < -maxSpeedDownwardInPercent)
			currentVelocityCommandInPercent = -maxSpeedDownwardInPercent;
		else if (Utilities.fabs(currentVelocityCommandInPercent) < minVelocityInPercentThatOvercomesMotorInertia)
			currentVelocityCommandInPercent = 0.0;

		return currentVelocityCommandInPercent;
	}

}
