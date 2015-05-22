package ro.pub.cs.systems.pdsd.practicaltest02.model;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;

public class ValueInfo {

	private String value;
	private long timeStamp;
	
	public ValueInfo() {
		this.value = null;
		this.timeStamp   = 0;
	}

	public ValueInfo(
			String value,
			long timeStamp) {
		this.value = value;
		this.timeStamp   = timeStamp;
		}
	
	public String getValue ()
	{
		return this.value;
	}
	public long getTimeS(){
		return this.timeStamp;
	}

}