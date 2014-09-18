package todo;

import done.ClockOutput;

public class ClockTime extends Thread {
	private long sleepTime;
	private long systemTime;
	private long alarmTime;
	private ClockOutput output;
	boolean hasSlept = false;
	private long startTime;
	private long startSystemTime;
	private int outTime;
	private long systemTimeBeforeSleep;
	private long diff;
	private boolean alarmEnabled;

	public ClockTime(ClockOutput output) {
		alarmTime = Integer.MAX_VALUE;
		this.output = output;
		startTime = 000000;
		systemTime = startTime;
		alarmEnabled = false;

	}

	public void run() {
		sleepTime = 1000;
		while (true) {
			systemTimeBeforeSleep = System.currentTimeMillis();
			updateClock();
			checkAlarm();
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			diff = System.currentTimeMillis() - systemTimeBeforeSleep;
			sleepTime = 2000 - diff;
		}

	}

	private void checkAlarm() {
		if (systemTime >= alarmTime && systemTime < alarmTime + 20000 && alarmEnabled) {
			output.doAlarm();
		}

	}

	private void updateClock() {
		systemTime = startTime + (System.currentTimeMillis() - startSystemTime);
		outTime = convertToOutFormat(systemTime);
		System.out.println("Outtime is " + outTime);
		output.showTime(outTime);

	}

	public void setCurrentTime(int time) {
		startTime = convertToMilliseconds(time);

		startSystemTime = System.currentTimeMillis();

	}

	public void setAlarm(int time) {
		alarmTime = convertToMilliseconds(time);

	}

	private int convertToOutFormat(long number) {
		int seconds = (int) (number / 1000) % 60;
		int minutes = (int) ((number / (1000 * 60)) % 60);
		int hours = (int) ((number / (1000 * 60 * 60)) % 24);
		System.out.println(hours + " " + minutes + " " + seconds);
		outTime = hours * 10000 + minutes * 100 + seconds;
		return outTime;

	}

	private long convertToMilliseconds(int number) {
		int hours = number / 10000;
		int minutes = (number - hours * 10000) / 100;
		int seconds = (number - hours * 10000 - minutes * 100);

		return (hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000);

	}

	public void setAlarmStatus(boolean alarmFlag) {
		alarmEnabled = alarmFlag;

	}

	public void turnAlarmOff() {
		if (systemTime >= alarmTime && systemTime < alarmTime + 20000 && alarmEnabled) {
			alarmTime = Integer.MAX_VALUE;
		}
		
	}

}
