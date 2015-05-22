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
					
					ValueInfo val;
					String [] parseComm = comanda.split(",");
					if(parseComm[0].equals("put"))
					{
						/*try {
							  HttpClient httpClient = new DefaultHttpClient();
							  HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
							  
							  ResponseHandler<String> responseHandler = new BasicResponseHandler();
							  String content = httpClient.execute(httpGet, responseHandler);
							  
							  //content
							  if(content != null && !content.isEmpty())
							  {
								  
							  }
							  
							} catch (Exception exception) {
							  Log.e(Constants.TAG, exception.getMessage());
							  if (Constants.DEBUG) {
							    exception.printStackTrace();
							  }
							}
						*/
						
						 serverThread.setData(parseComm[1], new ValueInfo(parseComm[2],(long)5));
						 result = "OK";
						
					} else //get
					{
						//get
						 HashMap<String, ValueInfo> data = serverThread.getData();
						 if(data.containsKey(parseComm[1]))
						 {
							 
							 long actTime = 0;
							 ValueInfo exist = data.get(parseComm[1]);
							 /*
							 	try {
									  HttpClient httpClient = new DefaultHttpClient();
									  HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
									  
									  ResponseHandler<String> responseHandler = new BasicResponseHandler();
									  String content = httpClient.execute(httpGet, responseHandler);
									  
									  //content
									  if(content != null && !content.isEmpty())
									  {
										  if(exist.getTimeS() - actTime < 60)
												 result = exist.getValue();
									  }
									  
									} catch (Exception exception) {
									  Log.e(Constants.TAG, exception.getMessage());
									  if (Constants.DEBUG) {
									    exception.printStackTrace();
									  }
									}
							 	*/
							 
							 if(exist.getTimeS() - actTime < 60)
								 result = exist.getValue();
							 
						 } else
						 {
							  result = "NONE"; 
						 }
					}
					
					printWriter.println(result);
					printWriter.flush();
						
				//socket.close();
			}
		} catch (Exception ioException) 
			{
				
			}
	} else
	{
		
	}
	}
}