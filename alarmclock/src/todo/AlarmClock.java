package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;

public class AlarmClock extends Thread {

	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;
	private ClockTime cTime;
	private SharedData d;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		d = new SharedData();
		sem = input.getSemaphoreInstance();
		cTime = new ClockTime(output, d);
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
			d.setAlarmTime(value);
		} else if (choice == ClockInput.SET_TIME) {
			d.setTime(value);
		}

		d.setAlarm(input.getAlarmFlag());
		if (d.getTime() >= d.getAlarmTime()
				&& d.getTime() < d.getAlarmTime() + 20000 && d.getAlarm()) {
			System.out.println("turning alarm off");
			d.setAlarm(false);
		}
	}
}