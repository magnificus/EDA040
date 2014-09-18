package todo;

import done.ClockOutput;

public class ClockTime extends Thread {
	private long systemTime, alarmTime, startSystemTime, diff;
	private ClockOutput output;
	boolean hasSlept = false;
	private int outTime;
	private SharedData d;

	public ClockTime(ClockOutput output, SharedData d) {
		alarmTime = Integer.MAX_VALUE;
		this.output = output;
		systemTime = d.getTime();
		this.d = d;
	}

	public void run() {
		long t0;
		while (true) {
			t0 = System.currentTimeMillis();
			updateClock();
			checkAlarm();
			t0 += 1000;
			diff = t0 - System.currentTimeMillis();
			if (diff > 0) {
				try {
					sleep(diff);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void checkAlarm() {
		if (systemTime >= alarmTime && systemTime < alarmTime + 20000
				&& d.getAlarm()) {
			output.doAlarm();
		}

	}

	private void updateClock() {
		systemTime = d.getTime()
				+ (System.currentTimeMillis() - startSystemTime);
		outTime = convertToOutFormat(systemTime);
		System.out.println("Outtime is " + outTime);
		output.showTime(outTime);

	}

	public void setCurrentTime(int time) {
		d.setTime(time);
		startSystemTime = System.currentTimeMillis();
	}

	private int convertToOutFormat(long number) {
		int seconds = (int) (number / 1000) % 60;
		int minutes = (int) ((number / (1000 * 60)) % 60);
		int hours = (int) ((number / (1000 * 60 * 60)) % 24);
		System.out.println(2 + hours + " " + minutes + " " + seconds);
		outTime = (2 + hours) * 10000 + minutes * 100 + seconds;
		return outTime;

	}

	public void turnAlarmOff() {
		if (systemTime >= alarmTime && systemTime < alarmTime + 20000
				&& d.getAlarm()) {
			d.setAlarm(false);
		}

	}

}
