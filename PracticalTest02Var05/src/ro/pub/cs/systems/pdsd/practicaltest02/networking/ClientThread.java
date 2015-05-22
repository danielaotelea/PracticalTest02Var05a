package ro.pub.cs.systems.pdsd.practicaltest02.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.pdsd.practicaltest02.general.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.general.Utilities;
import android.util.Log;
import android.widget.TextView;


public class ClientThread extends Thread {
	
	private String   address;
	private int      port;
	private String   comanda;
	private TextView rezultat_comandaEditText;
	
	private Socket   socket;
	
	public ClientThread(
			String address,
			int port,
			String parseComm,
			TextView rezultat_comandaEditText) {
		this.address                 = address;
		this.port                    = port;
		this.comanda                    = parseComm;
		this.rezultat_comandaEditText = rezultat_comandaEditText;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(address, port);
			if (socket == null) {
				Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
			}
			
			Log.i(Constants.TAG, "[CLIENT THREAD] ");
			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter    printWriter    = Utilities.getWriter(socket);
			if (bufferedReader != null && printWriter != null) {
				printWriter.println(comanda);
				printWriter.flush();
			
				String getRezultat = bufferedReader.readLine();
					final String info = getRezultat;
					rezultat_comandaEditText.post(new Runnable() {
						@Override
						public void run() {
							rezultat_comandaEditText.setText(info + "\n");
						}
					});
				socket.close();
				Log.e(Constants.TAG,"socket close");
			} else {
				Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
			}
			socket.close();
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

}
