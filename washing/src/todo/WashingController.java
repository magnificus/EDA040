package todo;

import done.*;

public class WashingController implements ButtonListener {
	// TODO: add suitable attributes
	private WashingProgram0 wp0;
	private WashingProgram1 wp1;
	private WashingProgram2 wp2;
	private WashingProgram3 wp3;

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		wp0 = new WashingProgram0(theMachine, theSpeed,
				new TemperatureController(theMachine, theSpeed),
				new WaterController(theMachine, theSpeed), new SpinController(
						theMachine, theSpeed));
		wp1 = new WashingProgram1(theMachine, theSpeed,
				new TemperatureController(theMachine, theSpeed),
				new WaterController(theMachine, theSpeed), new SpinController(
						theMachine, theSpeed));
		wp2 = new WashingProgram2(theMachine, theSpeed,
				new TemperatureController(theMachine, theSpeed),
				new WaterController(theMachine, theSpeed), new SpinController(
						theMachine, theSpeed));
		wp3 = new WashingProgram3(theMachine, theSpeed,
				new TemperatureController(theMachine, theSpeed),
				new WaterController(theMachine, theSpeed), new SpinController(
						theMachine, theSpeed));
		wp0.start();
		wp1.start();
		wp2.start();
		wp3.start();
	}

	public void processButton(int theButton) {
		switch (theButton) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;

		}

	}
}
