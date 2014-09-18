package todo;

import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	private long time;
	private long alarmTime;
	private boolean alarm;
	public MutexSem mutex;

	public SharedData() {
		mutex = new MutexSem();
		time = 000000;
		alarm = false;
	}

	public MutexSem getMutexInstance() {
		return mutex;
	}

	public void setTime(int i) {
		mutex.take();
		time = convertToMilliseconds(i);
		mutex.give();
	}

	public void setAlarmTime(int i) {
		mutex.take();
		alarmTime = convertToMilliseconds(i);
		mutex.give();
	}

	public void setAlarm(boolean b) {
		mutex.take();
		alarm = b;
		mutex.give();

	}

	public long getTime() {
		return time;
	}

	public long getAlarmTime() {
		return alarmTime;
	}

	public boolean getAlarm() {
		return alarm;
	}

	private long convertToMilliseconds(int number) {
		int hours = number / 10000;
		int minutes = (number - hours * 10000) / 100;
		int seconds = (number - hours * 10000 - minutes * 100);

		return (hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000);

	}
}
