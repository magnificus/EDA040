package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;
	private ClockTime cTime;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		cTime = new ClockTime(output);
		cTime.start();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {
		
		while (true) {

			sem.take();
			handleInput();
			
		}
	}

	private void handleInput() {
		int choice = input.getChoice();
		int value = input.getValue();

		if (choice == ClockInput.SET_ALARM) {
			cTime.setAlarm(value);

			//
		} else if (choice == ClockInput.SET_TIME) {
			cTime.setCurrentTime(value);
			//
		}
		
		cTime.setAlarmStatus(input.getAlarmFlag());
		System.out.println("turning alarm off");
		cTime.turnAlarmOff();

	}
}