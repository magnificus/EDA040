package todo;

import done.*;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 * <LI>Switches off heating
 * <LI>Switches off spin
 * <LI>Switched off draining/filling of water
 * </UL>
 */
class WashingProgram1 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param mach
	 *            The washing machine to control
	 * @param speed
	 *            Simulation speed
	 * @param tempController
	 *            The TemperatureController to use
	 * @param waterController
	 *            The WaterController to use
	 * @param spinController
	 *            The SpinController to use
	 */
	public WashingProgram1(AbstractWashingMachine mach, double speed,
			TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {

		// Lock the hatch
		myMachine.setLock(true);

		// Fill with water
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL,
				20.0));
		mailbox.doFetch(); // Wait for Ack

		// Switch temp regulation on
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_SET, 60.0));

		// Wait 30 mins!?!?!?!?!?!?

		// Switch temp regulation off
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_IDLE, 0.0));

		// Drain
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN,
				0.0));
		mailbox.doFetch(); // Wait for Ack

		// Rinse
		for (int i = 0; i < 5; i++) {
			// Fill
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_FILL, 20.0));
			mailbox.doFetch(); // Wait for Ack

			// Wait 2 minutes!!?!?!?!?

			// Drain
			myWaterController.putEvent(new WaterEvent(this,
					WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch(); // Wait for Ack

		}

		// Drain
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN,
				0.0));
		mailbox.doFetch(); // Wait for Ack

		// Centrifuge
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));

		// Wait 5 minutes!!?!?!?!?

		// Set water regulation to idle => drain pump stops
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE,
				0.0));

		// Unlock the hatch
		myMachine.setLock(false);
	}
}
