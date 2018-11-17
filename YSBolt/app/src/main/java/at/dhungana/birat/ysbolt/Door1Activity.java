package at.dhungana.birat.ysbolt;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;


import org.w3c.dom.Text;

import java.io.*;
import java.net.*;


public class Door1Activity extends AppCompatActivity{

    Button lockButton;
    Button unlockButton;
    String appName = "YSBolt";


    public final String lockCommand = "CMD"+"\\x00"+"lockdoor";
    public final String unlockCommand = "CMD"+"\\x00"+"unlockdoor";
    private InetAddress IPADDRESS;
    private int portNum = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door1);
        setTitle("Door 1");

        lockButton = (Button)findViewById(R.id.lockbutton);
        unlockButton = (Button)findViewById(R.id.unlockbutton);

        try{
            IPADDRESS = InetAddress.getByName("192.168.0.21");
        }catch(UnknownHostException e){
            e.printStackTrace();
        }



        lockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //Connection bits - send the lock command packet to the RPi
                 Thread lockSender = new Thread(new Sender(IPADDRESS, portNum, lockCommand));
                    lockSender.start();


                    //Notification after door has been locked.
                    NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification.Builder(getApplicationContext()).setContentTitle("Door Locked!").setContentText("Door 1 is now locked.").setContentTitle(appName)
                            .setSmallIcon(R.drawable.jokelogo).build();

                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                    notif.notify(0, notify);

            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Connection bits - send the unlock command packet to the RPi;
                Thread unlockSender = new Thread(new Sender(IPADDRESS, portNum, lockCommand));
                unlockSender.start();


                //Notification after door has been unlocked.
                NotificationManager notif = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification.Builder(getApplicationContext()).setContentTitle("Door Unlocked!").setContentText("Door 1 is now locked.").setContentTitle(appName)
                        .setSmallIcon(R.drawable.jokelogo).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }
        });



        //Getting the data from MainActivity to Door1Activity.
        Intent intent1 = getIntent();
        String message = intent1.getStringExtra("message");
        TextView textView = (TextView)findViewById(R.id.notifications);
        textView.setText(message);

    }

    //------------------------Client for using TCP connection.------------------------------------------

    private class Sender implements Runnable{
        public int port;
        public String message;
        public InetAddress ip;
        private final static int PACKETSIZE = 1; //HAVE to change the packetsize


      public Sender(InetAddress ip, int port, String message){ //Constructor
          this.port=port;
          this.ip=ip;
          this.message=message;
      }

      @Override
      public void run() {

              try {
                  Socket socket = new Socket(ip, port);
                  DataOutputStream cmdOut = new DataOutputStream(socket.getOutputStream());

                  while (true) {
                      byte[] data = message.getBytes();
                      cmdOut.writeBytes(message);
                      cmdOut.flush();

                     // DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                      //socket.send(packet);
                      System.out.println("Packet sent!");

                      //waiting for ACK to receive
                      for (;;) {
                          DatagramPacket ack_Packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
                          //socket.receive(ack_Packet);
                          break;
                      }
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }//end catch

      }//end run


    }//end the nested class Sender


}//end the Door1Activity class


