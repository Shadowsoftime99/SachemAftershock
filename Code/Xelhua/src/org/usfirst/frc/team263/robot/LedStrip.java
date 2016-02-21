package org.usfirst.frc.team263.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Timer;

public class LedStrip {
	private static final int i2cAddress_Arduino = 10;
	static I2C i2c = new I2C(I2C.Port.kOnboard, i2cAddress_Arduino);

	public static enum Colors {
		eRed, eGreen, eBlue, ePink, ePurple, eOrange, eWhite, eTeal,
	}

	public static void sendColor(int red, int green, int blue) {
		int colors[] = new int[] { red, green, blue };
		for (int i : colors) {
			System.out.println("sendColor: " + i);
			byte byteArray[] = { (byte) i };
			if (i2c.writeBulk(byteArray)) {
				System.out.println("Sent Command: " + i + " : Did not work!\n");
			} else {
				System.out.println("Sent Command: " + i);
			}
			Timer.delay(0.15);

		}
	}

	public static void setColor(Colors colors) {
		switch (colors) {
		case eRed:
			sendColor(255, 0, 0);
			break;
		case eGreen:
			sendColor(0, 255, 0);
			break;

		case eBlue:
			sendColor(0, 0, 255);
			break;

		case ePink:
			sendColor(255, 0, 255);
			break;

		case ePurple:
			sendColor(255, 0, 127);
			break;

		case eOrange:
			sendColor(255, 128, 0);
			break;

		case eWhite:
			sendColor(255, 255, 255);
			break;

		case eTeal:
			sendColor(0, 255, 255);
			break;
		}
	}
}
