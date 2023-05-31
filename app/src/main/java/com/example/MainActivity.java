package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.BtConsts;
import com.example.bluetooth.BtConnection;
import com.example.bluetooth.ConnectThread;

public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;
    private BluetoothAdapter btAdapter;
    private final int ENABLE_REQUEST = 15;
    private SharedPreferences pref;
    private BtConnection btConnection;
    private Button bA;

    private ImageFilterButton button1;
    private final int[] images = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4};
    private int carNum = 0; //номер картинки
    private int g = 1;

    private EditText edit;
    private Button button;
    private TextView text;
    private TextView textConnect;

    private EditText edit2;
    private TextView text2;

    private String a1;
    private String a2;

    private String b1;
    private String b2;

    private String c1;
    private String c2;

    private SharedPreferences prefA1;
    private SharedPreferences prefA2;
    private SharedPreferences prefB1;
    private SharedPreferences prefB2;
    private SharedPreferences prefC1;
    private SharedPreferences prefC2;
    private final String save_key_A1 = "save_A1";
    private final String save_key_A2 = "save_A2";
    private final String save_key_B1 = "save_B1";
    private final String save_key_B2 = "save_B2";
    private final String save_key_C1 = "save_C1";
    private final String save_key_C2 = "save_C2";



    //private String Kode = "A";

//    SharedPreferences A1pref;
//    SharedPreferences A2pref;
//    SharedPreferences B1pref;
//    SharedPreferences B2pref;
//    SharedPreferences C1pref;
//    SharedPreferences C2pref;
//
//    final String SAVED_TEXT = "saved_text";

    //private ConnectThread tConnect = new ConnectThread();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);

        edit2 = findViewById(R.id.edit2);
        text2 = findViewById(R.id.text2);

        textConnect = findViewById(R.id.textConnect);

        prefA1 = getSharedPreferences("BK", MODE_PRIVATE);
        prefA2 = getSharedPreferences("BK", MODE_PRIVATE);
        prefB1 = getSharedPreferences("BK", MODE_PRIVATE);
        prefB2 = getSharedPreferences("BK", MODE_PRIVATE);
        prefC1 = getSharedPreferences("BK", MODE_PRIVATE);
        prefC2 = getSharedPreferences("BK", MODE_PRIVATE);

        //bA = findViewById(R.id.buttonA);
        init();                                        ////////////////
//        bA.setOnClickListener(v -> {
//            btConnection.sendMessage("A");
//        });
        //textConnect.setText("Неподключено");

        //textConnect.setText("Подключено");
        button1 = findViewById(R.id.ib_car);
        System.out.println(btConnection.getCon() + "????????????????????????????????");
        if(btConnection.getCon() == 1){
            textConnect.setText("Подключено");
        } else {
            textConnect.setText("Не подключено");
        }
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(btConnection.getCon() == 1){
                    textConnect.setText("Подключено");
                } else {
                    textConnect.setText("Не подключено");
                }
                nextCar();
                if (carNum == 0){
                    text.setText(" ");
                    text2.setText(" ");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, R.string.nichego, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (carNum == 1) {
                    if (prefA1.getString(save_key_A1, "") != null && prefA2.getString(save_key_A2, "") != null) {
                        if (prefA1.getString(save_key_A1, "").length() > 12) {
                            text.setText("1.1  " + prefA1.getString(save_key_A1, "") + "...");
                        } else {
                            text.setText("1.1  " + prefA1.getString(save_key_A1, ""));
                        }
                        if (prefA2.getString(save_key_A2, "").length() > 12) {
                            text2.setText("1.2  " + prefA2.getString(save_key_A2, "") + "...");
                        } else {
                            text2.setText("1.2  " + prefA2.getString(save_key_A2, ""));
                        }
                    } else {
                        text.setText("1.1");
                        text2.setText("1.2");
                    }
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btConnection.getCon() == 1) {    /////////////////////////////////////////////////////////////////////////////////
                                    System.out.println(btConnection.getCon() + "????????????????????????????????  1");
                                    if ((edit.getText().toString().trim().equals("")) || (edit2.getText().toString().trim().equals(""))) {
                                        Toast.makeText(MainActivity.this, R.string.ficha, Toast.LENGTH_SHORT).show();
                                    } else {
                                        a1 = edit.getText().toString();
                                        a2 = edit2.getText().toString();


                                        char[] a1Convert = new char[a1.length()];
                                        for (int i = 0; i < a1.length(); i++) {
                                            a1Convert[i] = a1.charAt(i);
                                        }
                                        convert(a1Convert);
                                        String strA1Convert = new String(a1Convert);
                                        //System.out.println(strA1Convert);

                                        char[] a2Convert = new char[a2.length()];
                                        for (int i = 0; i < a2.length(); i++) {
                                            a2Convert[i] = a2.charAt(i);
                                        }
                                        convert(a2Convert);
                                        String strA2Convert = new String(a2Convert);
                                        //System.out.println(strA2Convert);


                                        btConnection.sendMessage("a1 " + strA1Convert + " ");
                                        btConnection.sendMessage("a2 " + strA2Convert + " ");


//                                        SharedPreferences.Editor editA2 = prefA2.edit();
//                                        editA2.putString(save_key, a2);
//                                        editA2.apply();




                                        if (a1.length() > 16) {
                                            String a1obr = a1.substring(0, 13);
                                            SharedPreferences.Editor editA1obr = prefA1.edit();
                                            editA1obr.putString(save_key_A1, a1obr);
                                            editA1obr.apply();
                                            text.setText("1.1  " + prefA1.getString(save_key_A1, "") + "...");
                                            //text.setText("1.1  " + a1obr + "...");
                                        } else {
                                            SharedPreferences.Editor editA1 = prefA1.edit();
                                            editA1.putString(save_key_A1, a1);
                                            editA1.apply();
                                            text.setText("1.1  " + prefA1.getString(save_key_A1, "no"));
                                            //text.setText("1.1  " + a1);
                                        }
                                        if (a2.length() > 16) {
                                            String a2obr = a2.substring(0, 13);
                                            SharedPreferences.Editor editA2obr = prefA2.edit();
                                            editA2obr.putString(save_key_A2, a2obr);
                                            editA2obr.apply();
                                            text2.setText("1.2  " + prefA2.getString(save_key_A2, "") + "...");
                                            //text2.setText("1.2  " + a2obr + "...");
                                        } else {
                                            SharedPreferences.Editor editA2 = prefA2.edit();
                                            editA2.putString(save_key_A2, a2);
                                            editA2.apply();
                                            text2.setText("1.2  " + prefA2.getString(save_key_A2, ""));
                                            //text2.setText("1.2  " + a2);
                                        }
                                    }
                                } else {  /////////////////////////////////////////////////////////////////////////////////
                                    Toast.makeText(MainActivity.this, "Не подключено", Toast.LENGTH_SHORT).show(); /////////////////////////////////////////////////////////////////////////////////
                                } /////////////////////////////////////////////////////////////////////////////////
                            }
                        });
                    }
                if (carNum == 2) {
                    if (prefB1.getString(save_key_B1, "") != null && prefB2.getString(save_key_B2, "") != null) {
                        if (prefB1.getString(save_key_B1, "").length() > 12) {
                            text.setText("2.1  " + prefB1.getString(save_key_B1, "") + "...");
                            //text.setText("1.1  " + b1obr + "...");
                        } else {
                            text.setText("2.1  " + prefB1.getString(save_key_B1, ""));
                            //text.setText("1.1  " + b1);
                        }
                        if (prefB2.getString(save_key_B2, "").length() > 12) {
                            text2.setText("2.2  " + prefB2.getString(save_key_B2, "") + "...");
                            //text2.setText("1.2  " + b2obr + "...");
                        } else {
                            text2.setText("2.2  " + prefB2.getString(save_key_B2, ""));
                            //text2.setText("1.2  " + b2);
                        }
                    } else {
                        text.setText("2.1");
                        text2.setText("2.2");
                    }
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btConnection.getCon() == 1) { /////////////////////////////////////////////////////////////////////////////////
                                    System.out.println(btConnection.getCon() + "????????????????????????????????    2");
                                    if ((edit.getText().toString().trim().equals("")) || (edit2.getText().toString().trim().equals(""))) {
                                        Toast.makeText(MainActivity.this, R.string.ficha, Toast.LENGTH_SHORT).show();
                                    } else {
                                        b1 = edit.getText().toString();
                                        b2 = edit2.getText().toString();


                                        char[] b1Convert = new char[b1.length()];
                                        for (int i = 0; i < b1.length(); i++) {
                                            b1Convert[i] = b1.charAt(i);
                                        }
                                        convert(b1Convert);
                                        String strB1Convert = new String(b1Convert);
                                        //System.out.println(strB1Convert);

                                        char[] b2Convert = new char[b2.length()];
                                        for (int i = 0; i < b2.length(); i++) {
                                            b2Convert[i] = b2.charAt(i);
                                        }
                                        convert(b2Convert);
                                        String strB2Convert = new String(b2Convert);
                                        //System.out.println(strB2Convert);


                                        btConnection.sendMessage("b1 " + strB1Convert + " ");
                                        btConnection.sendMessage("b2 " + strB2Convert + " ");


                                        if (b1.length() > 16) {
                                            String b1obr = b1.substring(0, 13);
                                            SharedPreferences.Editor editB1obr = prefB1.edit();
                                            editB1obr.putString(save_key_B1, b1obr);
                                            editB1obr.apply();
                                            text.setText("2.1  " + prefB1.getString(save_key_B1, "") + "...");
                                            //text.setText("1.1  " + b1obr + "...");
                                        } else {
                                            SharedPreferences.Editor editB1 = prefB1.edit();
                                            editB1.putString(save_key_B1, b1);
                                            editB1.apply();
                                            text.setText("2.1  " + prefB1.getString(save_key_B1, ""));
                                            //text.setText("1.1  " + a1);
                                        }
                                        if (b2.length() > 16) {
                                            String b2obr = b2.substring(0, 13);
                                            SharedPreferences.Editor editB2obr = prefB2.edit();
                                            editB2obr.putString(save_key_B2, b2obr);
                                            editB2obr.apply();
                                            text2.setText("2.2  " + prefB2.getString(save_key_B2, "") + "...");
                                            //text2.setText("1.2  " + b2obr + "...");
                                        } else {
                                            SharedPreferences.Editor editB2 = prefB2.edit();
                                            editB2.putString(save_key_B2, b2);
                                            editB2.apply();
                                            text2.setText("2.2  " + prefB2.getString(save_key_B2, ""));
                                            //text2.setText("1.2  " + b2);
                                        }
                                    }
                                } else { /////////////////////////////////////////////////////////////////////////////////
                                    Toast.makeText(MainActivity.this, "Не подключено", Toast.LENGTH_SHORT).show(); /////////////////////////////////////////////////////////////////////////////////
                                } /////////////////////////////////////////////////////////////////////////////////
                            }
                        });
                    }
                if (carNum == 3) {
                    if (prefC1.getString(save_key_C1, "") != null && prefC2.getString(save_key_C2, "") != null) {
                        if (prefC1.getString(save_key_C1, "").length() > 12) {
                            text.setText("3.1  " + prefC1.getString(save_key_C1, "") + "...");
                            //text.setText("1.1  " + c1obr + "...");
                        } else {
                            text.setText("3.1  " + prefC1.getString(save_key_C1, ""));
                            //text.setText("1.1  " + c1);
                        }
                        if (prefC2.getString(save_key_C2, "").length() > 12) {
                            text2.setText("3.2  " + prefC2.getString(save_key_C2, "") + "...");
                            //text2.setText("1.2  " + c2obr + "...");
                        } else {
                            text2.setText("3.2  " + prefC2.getString(save_key_C2, ""));
                            //text2.setText("1.2  " + b2);
                        }
                    } else {
                        text.setText("3.1");
                        text2.setText("3.2");
                    }
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btConnection.getCon() == 1) { /////////////////////////////////////////////////////////////////////////////////
                                    System.out.println(btConnection.getCon() + "????????????????????????????????   3");
                                    if ((edit.getText().toString().trim().equals("")) || (edit2.getText().toString().trim().equals(""))) {
                                        Toast.makeText(MainActivity.this, R.string.ficha, Toast.LENGTH_SHORT).show();
                                    } else {
                                        c1 = edit.getText().toString();
                                        c2 = edit2.getText().toString();


                                        char[] c1Convert = new char[c1.length()];
                                        for (int i = 0; i < c1.length(); i++) {
                                            c1Convert[i] = c1.charAt(i);
                                        }
                                        convert(c1Convert);
                                        String strC1Convert = new String(c1Convert);
                                        //System.out.println(strC1Convert);

                                        char[] c2Convert = new char[c2.length()];
                                        for (int i = 0; i < c2.length(); i++) {
                                            c2Convert[i] = c2.charAt(i);
                                        }
                                        convert(c2Convert);
                                        String strC2Convert = new String(c2Convert);
                                        //System.out.println(strB2Convert);


                                        btConnection.sendMessage("c1 " + strC1Convert + " ");
                                        btConnection.sendMessage("c2 " + strC2Convert + " ");


                                        if (c1.length() > 16) {
                                            String c1obr = c1.substring(0, 13);
                                            SharedPreferences.Editor editC1obr = prefC1.edit();
                                            editC1obr.putString(save_key_C1, c1obr);
                                            editC1obr.apply();
                                            text.setText("3.1  " + prefC1.getString(save_key_C1, "") + "...");
                                            //text.setText("1.1  " + c1obr + "...");
                                        } else {
                                            SharedPreferences.Editor editC1 = prefC1.edit();
                                            editC1.putString(save_key_C1, c1);
                                            editC1.apply();
                                            text.setText("3.1  " + prefC1.getString(save_key_C1, ""));
                                            //text.setText("1.1  " + c1);
                                        }
                                        if (c2.length() > 16) {
                                            String c2obr = c2.substring(0, 13);
                                            SharedPreferences.Editor editC2obr = prefC2.edit();
                                            editC2obr.putString(save_key_C2, c2obr);
                                            editC2obr.apply();
                                            text2.setText("3.2  " + prefC2.getString(save_key_C2, "") + "...");
                                            //text2.setText("1.2  " + c2obr + "...");
                                        } else {
                                            SharedPreferences.Editor editC2 = prefC2.edit();
                                            editC2.putString(save_key_C2, c2);
                                            editC2.apply();
                                            text2.setText("3.2  " + prefC2.getString(save_key_C2, ""));
                                            //text2.setText("1.2  " + c2);
                                        }
                                    }
                                } else { /////////////////////////////////////////////////////////////////////////////////
                                    Toast.makeText(MainActivity.this, "Не подключено", Toast.LENGTH_SHORT).show(); /////////////////////////////////////////////////////////////////////////////////
                                } /////////////////////////////////////////////////////////////////////////////////
                            }
                        });
                    }
                edit.setText("");
                edit2.setText("");
            }
        });
    }

    private void nextCar() {
        carNum = ++carNum % 4;
        button1.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), images[carNum]));
        System.out.println(carNum);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);          /////////////
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_bt_button) {
            if (!btAdapter.isEnabled()) {
                enableBt();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return false;
                }
                btAdapter.disable();
                menuItem.setIcon(R.drawable.bluetooth2);
            }
        } else if (item.getItemId() == R.id.id_menu) {
            if(btAdapter.isEnabled()) {
                Intent i = new Intent(MainActivity.this, BtListActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Включите блютуз", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.id_connect) {
            btConnection.conect();
            if(btConnection.getCon() == 1){
                textConnect.setText("Подключено");
            } else {
                textConnect.setText("Не подключено");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_REQUEST) {
            if (resultCode == RESULT_OK) {
                setBtIcon();
            }
        }
    }

    private void setBtIcon() {
        if (btAdapter.isEnabled()) {
            menuItem.setIcon(R.drawable.bluetooth1);
        } else {
            menuItem.setIcon(R.drawable.bluetooth2);
        }
    }

    private void init() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);

    }

    private void enableBt() {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivityForResult(i, ENABLE_REQUEST);
    }

    public static void convert(char[] charsRussian){
        String sw = "'";
        char Sw = sw.charAt(0);
        for (int i = 0; i < charsRussian.length; i++) {
            switch (charsRussian[i]) {
                case 'ё': charsRussian[i] = '`'; break;
                case '1': charsRussian[i] = '1'; break;
                case '2': charsRussian[i] = '2'; break;
                case '3': charsRussian[i] = '3'; break;
                case '4': charsRussian[i] = '4'; break;
                case '5': charsRussian[i] = '5'; break;
                case '6': charsRussian[i] = '6'; break;
                case '7': charsRussian[i] = '7'; break;
                case '8': charsRussian[i] = '8'; break;
                case '9': charsRussian[i] = '9'; break;
                case '0': charsRussian[i] = '0'; break;
                case '-': charsRussian[i] = '-'; break;
                case '=': charsRussian[i] = '='; break;

                case 'Ё': charsRussian[i] = '~'; break;
                case '!': charsRussian[i] = '!'; break;
                case '"': charsRussian[i] = '@'; break;
                case '№': charsRussian[i] = '#'; break;
                case ';': charsRussian[i] = '$'; break;
                case '%': charsRussian[i] = '%'; break;
                case ':': charsRussian[i] = '^'; break;
                case '?': charsRussian[i] = '&'; break;
                case '*': charsRussian[i] = '*'; break;
                case '(': charsRussian[i] = '('; break;
                case ')': charsRussian[i] = ')'; break;
                case '_': charsRussian[i] = '_'; break;
                case '+': charsRussian[i] = '+'; break;




                case 'й': charsRussian[i] = 'q'; break;
                case 'ц': charsRussian[i] = 'w'; break;
                case 'у': charsRussian[i] = 'e'; break;
                case 'к': charsRussian[i] = 'r'; break;
                case 'е': charsRussian[i] = 't'; break;
                case 'н': charsRussian[i] = 'y'; break;
                case 'г': charsRussian[i] = 'u'; break;
                case 'ш': charsRussian[i] = 'i'; break;
                case 'щ': charsRussian[i] = 'o'; break;
                case 'з': charsRussian[i] = 'p'; break;
                case 'х': charsRussian[i] = '['; break;
                case 'ъ': charsRussian[i] = ']'; break;
                //case '\': charsRussian[i] = '\'; break;
                case 'ф': charsRussian[i] = 'a'; break;
                case 'ы': charsRussian[i] = 's'; break;
                case 'в': charsRussian[i] = 'd'; break;
                case 'а': charsRussian[i] = 'f'; break;
                case 'п': charsRussian[i] = 'g'; break;
                case 'р': charsRussian[i] = 'h'; break;
                case 'о': charsRussian[i] = 'j'; break;
                case 'л': charsRussian[i] = 'k'; break;
                case 'д': charsRussian[i] = 'l'; break;
                case 'ж': charsRussian[i] = ';'; break;
                case 'э': charsRussian[i] = Sw; break;
                case 'я': charsRussian[i] = 'z'; break;
                case 'ч': charsRussian[i] = 'x'; break;
                case 'с': charsRussian[i] = 'c'; break;
                case 'м': charsRussian[i] = 'v'; break;
                case 'и': charsRussian[i] = 'b'; break;
                case 'т': charsRussian[i] = 'n'; break;
                case 'ь': charsRussian[i] = 'm'; break;
                case 'б': charsRussian[i] = ','; break;
                case 'ю': charsRussian[i] = '.'; break;
                case '.': charsRussian[i] = '/'; break;

                case 'Й': charsRussian[i] = 'Q'; break;
                case 'Ц': charsRussian[i] = 'W'; break;
                case 'У': charsRussian[i] = 'E'; break;
                case 'К': charsRussian[i] = 'R'; break;
                case 'Е': charsRussian[i] = 'T'; break;
                case 'Н': charsRussian[i] = 'Y'; break;
                case 'Г': charsRussian[i] = 'U'; break;
                case 'Ш': charsRussian[i] = 'I'; break;
                case 'Щ': charsRussian[i] = 'O'; break;
                case 'З': charsRussian[i] = 'P'; break;
                case 'Х': charsRussian[i] = '{'; break;
                case 'Ъ': charsRussian[i] = '}'; break;
                case '/': charsRussian[i] = '|'; break;
                case 'Ф': charsRussian[i] = 'A'; break;
                case 'Ы': charsRussian[i] = 'S'; break;
                case 'В': charsRussian[i] = 'D'; break;
                case 'А': charsRussian[i] = 'F'; break;
                case 'П': charsRussian[i] = 'G'; break;
                case 'Р': charsRussian[i] = 'H'; break;
                case 'О': charsRussian[i] = 'J'; break;
                case 'Л': charsRussian[i] = 'K'; break;
                case 'Д': charsRussian[i] = 'L'; break;
                case 'Ж': charsRussian[i] = ':'; break;
                case 'Э': charsRussian[i] = '"'; break;
                case 'Я': charsRussian[i] = 'Z'; break;
                case 'Ч': charsRussian[i] = 'X'; break;
                case 'С': charsRussian[i] = 'C'; break;
                case 'М': charsRussian[i] = 'V'; break;
                case 'И': charsRussian[i] = 'B'; break;
                case 'Т': charsRussian[i] = 'N'; break;
                case 'Ь': charsRussian[i] = 'M'; break;
                case 'Б': charsRussian[i] = '<'; break;
                case 'Ю': charsRussian[i] = '>'; break;
                case ',': charsRussian[i] = '?'; break;

                case ' ': charsRussian[i] = ' '; break;
                default: break;
            }
        }
//        for (int i = 0; i < charsRussian.length; i++) {
//            System.out.print(charsRussian[i]);
//        }
    }
}