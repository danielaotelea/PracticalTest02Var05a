package ro.pub.cs.systems.pdsd.practicaltest02var05;

import ro.pub.cs.systems.pdsd.practicaltest02.networking.ClientThread;
import ro.pub.cs.systems.pdsd.practicaltest02.networking.ServerThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02Var05MainActivity extends Activity {
	
		// Server widgets
		private EditText     serverPortEditText       = null;
		private Button       connectButton            = null;
		
		// Client widgets
		private EditText     clientAddressEditText    = null;
		private EditText     clientPortEditText       = null;
		private EditText     comandaEditTest          = null;
		
		private Button       trimite_comanda = null;
		private TextView     rezultat_comandaEditText  = null;
		
		private ServerThread serverThread             = null;
		private ClientThread clientThread             = null;

	private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
	
	private class ConnectButtonClickListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			
			String serverPort = serverPortEditText.getText().toString();
			if (serverPort == null || serverPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Server port should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			serverThread = new ServerThread(Integer.parseInt(serverPort));
			if (serverThread.getServerSocket() != null) {
				Toast.makeText(
						getApplicationContext(),
						"Server pornit!",
						Toast.LENGTH_SHORT
					).show();
				
				serverThread.start();
			} else {
				
			}
			
		}
	}
	
	
  private TrimiteComandatButtonClickListener trimiteComandaButtonClickListener = new TrimiteComandatButtonClickListener();
	
	private class TrimiteComandatButtonClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String adresaIp = clientAddressEditText.getText().toString();
			if(adresaIp == null || adresaIp.isEmpty())
			{
				Toast.makeText(
						getApplicationContext(),
						"Server IP should be filled!",
						Toast.LENGTH_SHORT
					).show();
				return;
			}
			String p = clientPortEditText.getText().toString();
			int port;
			if(p == null || p.isEmpty())
			{
				Toast.makeText(
						getApplicationContext(),
						"Server port should be filled!",
						Toast.LENGTH_SHORT
					).show();
				return;
			} else
			{
				port = Integer.parseInt(p);
			}
			
			String comandaGet = comandaEditTest.getText().toString();
			String [] parseComm = comandaGet.split(",");
			Toast.makeText(
					getApplicationContext(),
					adresaIp + " " +  port + parseComm[0] + " >> " + parseComm[1] + " val ",
					Toast.LENGTH_SHORT
				).show();
	
			
			clientThread = new ClientThread(
					adresaIp,
					port,
					comandaGet,
					rezultat_comandaEditText);
			
			Toast.makeText(
					getApplicationContext(),
					"Client pornit!",
					Toast.LENGTH_SHORT
				).show();
			clientThread.start();
			
		}
		
	
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_var05_main);
		
		 Button connect = (Button)findViewById(R.id.connect_button);
		 connect.setOnClickListener(connectButtonClickListener);
		 serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
			
			
		trimite_comanda = (Button)findViewById(R.id.trimite_comanda_buton);
		trimite_comanda.setOnClickListener(trimiteComandaButtonClickListener);
			
		clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
		clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
		comandaEditTest = (EditText)findViewById(R.id.comanda_edit_text);
		rezultat_comandaEditText  = (TextView)findViewById(R.id.rezultat_comanda_edit_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_var05_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
