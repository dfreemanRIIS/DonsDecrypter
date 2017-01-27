package com.example.dfreeman.donsdecrypter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Base64;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText("Starting\n");

        try {

            String decrypted = decrypt("anVvMWVycyh5Pz5l");
            tv.append("username : " + decrypted + "\n");

            decrypted = decrypt("MnNkOXJldW5tZT5l");
            tv.append("password : " + decrypted + "\n");


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String decrypt(String encrypted) {
        encrypted = new String(Base64.decode(encrypted, 0));
        byte[] l = null;
        try {
            l = encrypted.getBytes();
            int i = 0;
            while (i < l.length) {
                writeInt(l, i, readInt(l, i) ^ 346);
                i += 4;
            }
        } catch (Exception e) {
            //
        }
        if(l != null) {
            encrypted = new String(l);
            int index = encrypted.indexOf("?");
            return index > 0 ? encrypted.substring(0, index) : encrypted;
        } else {
            return "null";
        }
    }

    private static void writeInt(byte[] data, int offset, int value) {
        data[offset] = (byte) (value >> 24);
        data[offset + 1] = (byte) (value >> 16);
        data[offset + 2] = (byte) (value >> 8);
        data[offset + 3] = (byte) value;
    }

    private static int readInt(byte[] data, int offset) {
        return (((data[offset] << 24) | ((data[offset + 1] & 255) << 16)) | ((data[offset + 2] & 255) << 8)) | (data[offset + 3] & 255);
    }
}