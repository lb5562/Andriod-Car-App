package com.example.car_app;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.car_app.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements JoyStickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private UsbManager usbManager;
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice adino;
    private Boolean connection;
    private int DEVICE_ADDRESS = 0;
    private BluetoothSocket socket;
    private OutputStream output;
    private InputStream input;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //JoyStick joy = new JoyStick(this);
        //setContentView(joy);
       /* binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickConnect(View view) throws IOException {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();

        } else {
            if (!adapter.isEnabled()) {
                Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //Intent check = new Intent(BluetoothAdapter.STATE_DISCONNECTED);
                startActivityForResult(enable, 0);
            }

            @SuppressLint("MissingPermission") Set bonding = adapter.getBondedDevices();
            if (bonding.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Pair the Device", Toast.LENGTH_SHORT).show();

            } else {
                for (Object it : bonding) {
                    if (it instanceof BluetoothDevice) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (((BluetoothDevice) it).getName().equals("Arduino Uno")) {
                            adino = (BluetoothDevice) it;
                            connection = true;

                            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            socket = adino.createRfcommSocketToServiceRecord(PORT_UUID);
                            socket.connect();
                            text.append(adino.getName());
                            socketSetUP();
                            break;
                        }
                        }
                    }
                }

            }

        }



        private void socketSetUP() throws IOException {
            output= socket.getOutputStream();
            input = socket.getInputStream();
        }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        if(id==R.id.joyStickLeft){
                Log.d("Left: X Percent:" + xPercent, " Y percent:" + yPercent);
        } else if(id ==R.id.joyStickRight){
                Log.d("Right: X Percent:" + xPercent, " Y percent:" + yPercent);
    //output.write(text);
        }
        }
}