package ro.pub.cs.systems.pdsd.practicaltest02.networking;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.general.Utilities;
import ro.pub.cs.systems.pdsd.practicaltest02.model.ValueInfo;
import android.util.Log;
import android.widget.Toast;

public class CommunicationThread extends Thread {
	
	private ServerThread serverThread;
	private Socket       socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				String result = "NONE";
				if (bufferedReader != null && printWriter != null) {
				
					String comanda            = bufferedReader.readLine();
					Log.i(Constants.TAG, "Command is " + comanda);
					ValueInfo val;
					String [] parseComm = comanda.split(",");
					if(parseComm[0].equals("put"))
					{
						Log.i(Constants.TAG, "Command is put " + comanda);
						try {
							  HttpClient httpClient = new DefaultHttpClient();
							  HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
							  
							  ResponseHandler<String> responseHandler = new BasicResponseHandler();
							  String content = httpClient.execute(httpGet, responseHandler);
							  
							  
							} catch (Exception exception) {
							  Log.e(Constants.TAG, exception.getMessage());
							  if (Constants.DEBUG) {
							    exception.printStackTrace();
							  }
							}
						Log.i(Constants.TAG, "Putting data in server thread" + parseComm[1] + "*" + parseComm[2]);
						 serverThread.setData(parseComm[1], new ValueInfo(parseComm[2],(long)5));
						 result = "OK";
						
					} else if(parseComm[0].equals("get"))
					{
						//get
						Log.i(Constants.TAG, "Command is get " + comanda);
						 HashMap<String, ValueInfo> data = serverThread.getData();
						 String content = "";
						 if(data.containsKey(parseComm[1]))
						 {
							 Log.i(Constants.TAG, "hashmap contains key ");
							 long actTime = 0;
							 ValueInfo exist = data.get(parseComm[1]);
							 
							 	try {
									  HttpClient httpClient = new DefaultHttpClient();
									  HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
									  
									  ResponseHandler<String> responseHandler = new BasicResponseHandler();
									   content = httpClient.execute(httpGet, responseHandler);
									  
									  //content
									  if(content != null && !content.isEmpty())
									  {
										  Log.i(Constants.TAG, "content " + content);
										  /*if(actTime - exist.getTimeS() < 60)
												 result = exist.getValue();
										  */
									  }
									  
									} catch (Exception exception) {
									  Log.e(Constants.TAG, exception.getMessage());
									  if (Constants.DEBUG) {
									    exception.printStackTrace();
									  }
									}
							 	
							 	Log.i(Constants.TAG, "actTime " + actTime + "exist.getTimeS()" + exist.getTimeS());
							 if(exist.getTimeS() - actTime < 60) {
								 Log.i(Constants.TAG, "result is " + comanda);
								 if(content != null && !content.isEmpty())
								  {
									 result = exist.getValue() + " TimeStamp: " + content;
								  } else
								  {
									  result = exist.getValue();
								  }
							 }
							 
						 } else
						 {
							 Log.i(Constants.TAG, "result is none ");
							  result = "NONE"; 
						 }
					} else
					
					Log.i(Constants.TAG, "sending to server " + result);
					printWriter.println(result);
					printWriter.flush();
			}
		} catch (Exception ioException) 
			{
				
			}
	} else
	{
		
	}
	}
}