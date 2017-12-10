package br.edu.unoesc.pdm.motor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

public class MotorActivity extends Activity {

    private String[] bcms = {"BCM4", "BCM17", "BCM27", "BCM22"};
    private Gpio[] portas = new Gpio[bcms.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);
        try {
            PeripheralManagerService manager = new PeripheralManagerService();
            for (int i = 0; i < portas.length; i++) {
                portas[i] = manager.openGpio(bcms[i]);
            //    portas[i].setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            }


            new Thread(new Runnable() {
                @Override
                public void run() {
                    int cont = 0;
                    while (cont < 1000) {
                        cont++;

                   /*     try {
                            configureOutput(portas[0],portas[1]);//, portas[2],portas[3]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }   */


                        for (int i = 0; i < portas.length; i++) {
                            try {
                                if (i + 1 == portas.length)

                                    configureOutput(portas[i], portas[0]);

                               else

                                    configureOutput(portas[i], portas[i +1]);

                                Thread.sleep(300);


                            } catch (Exception e) {
                                Log.e("Motor Activity", e.getMessage());
                            }

                        }

                    }


                }
            }).start();


        } catch (IOException ex) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void configureOutput(Gpio P1, Gpio P2) throws IOException {

        P1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        P1.setActiveType(Gpio.ACTIVE_LOW);
      //  Toast toast = Toast.makeText(getApplicationContext(), P1.getName()+"="+P1.getValue(), Toast.LENGTH_SHORT);
      //  toast.show();


        P2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        P2.setActiveType(Gpio.ACTIVE_LOW);
    //    toast = Toast.makeText(getApplicationContext(), P2.getName()+"="+P2.getValue(), Toast.LENGTH_SHORT);
    //    toast.show();
        P1.setValue(true);
    }
}


