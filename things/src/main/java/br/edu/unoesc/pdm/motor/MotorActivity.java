package br.edu.unoesc.pdm.motor;

import android.app.Activity;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;


import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

public class MotorActivity extends Activity {

    private long TimeExecucao = 0;
    private MotorPulso motorCarrinho;
    private String MsgControle;
    private String Direcao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);

        Direcao = "moveFrente";
        TimeExecucao = 1870; // vai dantar um metro para frente
        Movimento();
                    // 2120 milisegundos  360 Graus 
                    // 1060 milisegundos  180 Graus
                    //  530 milisegundos   90 Graus
                    //  1870 milisegundos   1 Metro para traz ou frente
        
     
      }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void Movimento() {
        motorCarrinho = new MotorPulso();
        motorCarrinho.definePinosMotor();

        MsgControle = "Motor Ligado ->";

        new Thread(new Runnable() {

            @Override
            public void run() {

                MsgControle = MsgControle + motorCarrinho.MotorMover(Direcao);

                try {
                    Thread.sleep(TimeExecucao);
                    // 2120 milisegundos  360 Graus 
                    // 1060 milisegundos  180 Graus
                    //  530 milisegundos   90 Graus
                    //  1870 milisegundos   1 Metro para traz ou frente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                motorCarrinho.Break();

            }

        }).start();
        Toast.makeText(getApplicationContext(), MsgControle+ " Fim Movimento ", Toast.LENGTH_SHORT).show();
    }

}


