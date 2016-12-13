package com.app.phr.peru.embarazo.JAVA;

/**
 * Created by hansol on 2016-08-10.
 * 나의 정보 부분이 있는 곳 비밀번호 바꾸기, 로그아웃 버튼 있음.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.app.phr.peru.embarazo.R;

import java.util.Calendar;

public class FragmentMyInfo extends Fragment {
    private static final String TAG = "sss";
    private EditText oldPW;
    private EditText newPW1, newPW2;
    private EditText childBirth;
    private Button btn, log_out, input;
    private String key, id;
    private PwChangeTask task = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private String childDate;

    private Handler mHandler;
    TextView test;
    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=1;        //디데이 연월일 변수
    private int dMonth=1;
    private int dDay=1;


    private long d;
    private long t;
    private long r;
    private int resultNumber=0;

    Communicator comm;

    public FragmentMyInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //call kekCD, id from sharedPreference

        preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
        key = preferences.getString(PreferencePutter.PREF_KEY, "null");
        id = preferences.getString(PreferencePutter.PREF_ID, "null");
      //  childDate = preferences.getString(PreferencePutter.CHILD_DATE, "null");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        input = (Button) getActivity().findViewById(R.id.input);
        test= (TextView) getActivity().findViewById(R.id.childbirth);
        //creation de la reference a la classe mainActivity contenant
        // la méthode de l'interface implémentée
        comm = (Communicator) getActivity();

        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment dpf =  new DatePickerFragment();

                dpf.show(getFragmentManager(), "datePicker");

                /*

                if (childBirth.getText().toString().length() == 0) {
                    childDate = "null";
                    Bundle arguments = new Bundle();
                    arguments.putString("name", childDate);

                    MainTab.setArguments(arguments);
                    comm.respond(childDate);
                    Toast.makeText(getActivity(), "널", Toast.LENGTH_LONG).show();

                } else {


                    childDate = childBirth.getText().toString();

                    //SharedPreferences.Editor editor = preferences.edit();
                    //   editor.putString(PreferencePutter.CHILD_DATE, childDate);
                    //editor.commit();
                    childDate = childBirth.getText().toString();
                    Bundle arguments = new Bundle();
                    arguments.putString("name", childDate);
                    MainTab.setArguments(arguments);

                    comm.respond(childDate);
                    Toast.makeText(getActivity(), "인풋", Toast.LENGTH_LONG).show();

                }*/

            }

        });

       // Calendar calendar =Calendar.getInstance();              //현재 날짜 불러옴
       // tYear = calendar.get(Calendar.YEAR);
       // tMonth = calendar.get(Calendar.MONTH);
        //tDay = calendar.get(Calendar.DAY_OF_MONTH);

        //Calendar dCalendar =Calendar.getInstance();
        //dCalendar.set(dYear,dMonth, dDay);

       // t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
       // d=dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
       // r=(d-t)/(24*60*60*1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

       // resultNumber=(int)r+1;
     //   updateDisplay();
    }

    @Override
    public void onResume() {
        super.onResume();

        oldPW = (EditText) getActivity().findViewById(R.id.MyInfo_password);
        newPW1 = (EditText) getActivity().findViewById(R.id.MyInfo_NewPassword1);
        newPW2 = (EditText) getActivity().findViewById(R.id.MyInfo_NewPassword2);
        log_out = (Button) getActivity().findViewById(R.id.log_out);
        input = (Button) getActivity().findViewById((R.id.input));
        //
        //
        //
        // childBirth = (EditText) getActivity().findViewById(R.id.expected_date);
        reset();

        btn = (Button) getActivity().findViewById(R.id.changePW);

/*
        input.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( childBirth.getText().toString().length() == 0 ) {
                  // childDate = "null";
                  //  Bundle arguments = new Bundle();
                  //  arguments.putString("name", childDate);
                  //  MainTab.setArguments(arguments);

                  //  SharedPreferences.Editor editor = preferences.edit();
                  //  editor.putString(PreferencePutter.CHILD_DATE, childDate);
                  //  editor.commit();

                    comm = (communicator) getActivity();
                    Toast.makeText(getActivity(), "널", Toast.LENGTH_LONG).show();

                } else {

                    //childDate = childBirth.getText().toString();
                    //Bundle arguments = new Bundle();
                    //arguments.putString("name", childDate);

                    //MainTab.setArguments(arguments);

                    //SharedPreferences.Editor editor = preferences.edit();
                 //   editor.putString(PreferencePutter.CHILD_DATE, childDate);
                    //editor.commit();
                    childDate = childBirth.getText().toString();

                    Toast.makeText(getActivity(), "인풋", Toast.LENGTH_LONG).show();

                }

                }

        }));
*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkUtil.getConnectivityStatusBoolean(getActivity())) {  //check the network to connect with Server
                    String old, new1, new2;
                    View focusView = null;
                    boolean cancel = false;

                    old = oldPW.getText().toString();
                    new1 = newPW1.getText().toString();
                    new2 = newPW2.getText().toString();
                    SharedPreferences preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
                    if (new2.equals("")) {   //입력받지 않은 edittext에 입력하라고 요구하기 위한 focus설정
                        focusView = newPW2;
                        cancel = true;
                    }
                    if (new1.equals("")) {
                        focusView = newPW1;
                        cancel = true;
                    }
                    if (old.equals("")) {
                        focusView = oldPW;
                        cancel = true;
                    }
                    if(cancel){
                        focusView.requestFocus();
                    }
                    else  {
                        if (!new1.equals(new2)) {  //new password1, 과 new password2가 서로 일치하게 입력했는지 check
                            Toast.makeText(getActivity().getApplicationContext(), "Esta contraseña no coincide con la introducida anteriormente.", Toast.LENGTH_SHORT).show();
                            newPW1.setText("");
                            newPW2.setText("");
                            newPW1.requestFocus();
                        } else {
                            request(old, new1);
                            hideKeyboard();   //new1 과 new2가 일치하다면 서버에 요청
                        }
                    }

                    // old password가 틀렸을때 (판단을 어디서?)

                } else {   //network 연결 안되있다면 toast
                    oldPW.setText("");
                    newPW1.setText("");
                    newPW2.setText("");

                    Toast.makeText(getActivity().getApplicationContext(), "Necesita conexion a network", Toast.LENGTH_SHORT).show();

                }
            }

        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //log out button listenr -> 로그아웃 버튼 클릭시 sharedPreference에 저장되있는 log in check 변경 필수 (자동로그인 설정)
                edit = preferences.edit();
                edit.putBoolean(PreferencePutter.LOG_IN, false);
                edit.commit();
                Toast.makeText(getActivity(), "Finalizar la sesión", Toast.LENGTH_SHORT).show();
                Intent myAct1 = new Intent(getActivity().getApplicationContext(), Login.class);
                myAct1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(myAct1);
                //버튼 눌렀을때 액티비티 초기화
            }
        });

        newPW2.setOnEditorActionListener(new TextView.OnEditorActionListener() {  //keyboard안의 완료 button click시 server에 request 보낼수 있도록 listent 설정
            @Override
            public boolean onEditorAction(TextView view, int keyCode, KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_DONE) {
                    String old, new1, new2;
                    View focusView = null;
                    boolean cancel = false;
                    old = oldPW.getText().toString();
                    new1 = newPW1.getText().toString();
                    new2 = newPW2.getText().toString();
                    SharedPreferences preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
                    if (new1.equals("")) {
                        focusView = newPW2;
                        cancel = true;
                    }
                    if (new2.equals("")) {
                        focusView = newPW1;
                        cancel = true;
                    }
                    if (old.equals("")) {
                        focusView = oldPW;
                        cancel = true;
                    }
                    if(cancel){
                        focusView.requestFocus();
                    }
                    else {
                        if (!new1.equals(new2)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Esta contraseña no coincide con la introducida anteriormente.", Toast.LENGTH_LONG).show();
                            newPW1.setText("");
                            newPW2.setText("");
                        } else {
                            request(old, new1);
                            hideKeyboard();
                        }
                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Necesita conexion a network", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_myinfo, container, false);
        preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
        childDate = preferences.getString(PreferencePutter.DATE, "");

        TextView txt1 = (TextView) v.findViewById(R.id.childbirth);
        if(childDate.equals("0")){
            txt1.setText(" ");
        }
        else {
            txt1.setText(childDate);
        }

        return v;
    }

    public void changeText(String data)
    {

        test.setText(data);
        edit = preferences.edit();
        edit.putString(PreferencePutter.DATE, data);
        edit.commit();
    }

    public void hideKeyboard()  //keyboard 숨기기 method
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }





    private void request(String old, String newPW) {  //서버에 요청하는 thread call
        if (task != null) {
            return;
        }
        task = new PwChangeTask(old, newPW);
        task.execute();

    }

    public class PwChangeTask extends AsyncTask<Void, Void, String> {

        private HTTPClient client;
        private String result;
        XmlParser parser;
        String old, newPW;

        PwChangeTask(String old, String newPW) {
            result = "";
            this.old = old;
            this.newPW = newPW;
            XmlWriter writer = new XmlWriter();

            client = new HTTPClient();
            client.setDoc(writer.getXmlForChange(id, key, old, newPW));  //xml Writer class를 통해서 change password 양식에 맞는 xmldata 생성후 http connction할때에 write해줌
        }

        @Override
        protected String doInBackground(Void... params) {
            result = client.connect();


            //get keyCD  result = client.getKey();
            return result;
        }


        @Override
        protected void onPostExecute(final String result) {
            task = null;
            //showProgress(false);
            parser = new XmlParser();
            if (result.equals("connection error")) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            } else {
                int response = parser.checkResponse(result);  //XML Parser를 통해서 server의 response msg 분석 후 처리
                if (response == 200) {
                    Toast.makeText(getActivity().getApplicationContext(), "La contraseña que ha escrito no coincide con la clave.", Toast.LENGTH_LONG).show();
                }
                if (response == 100) {
                    Log.d("myinfo", "change success");
                    Toast.makeText(getActivity().getApplicationContext(), "Cambio la contraseña", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(PreferencePutter.PREF_PW, old);
                    editor.commit();
                    hideKeyboard();
                    reset();
                    // server에게 login 인증 후 phr data 요청
                    //start next Activity

                }
                if (response == 505) {
                    Toast.makeText(getActivity().getApplicationContext(), "El cambio de contraseña ha fallado. Cierre de sesión y inicie una sesión de nuevo.", Toast.LENGTH_LONG).show();
                }
                if (response == 506) {
                    Toast.makeText(getActivity().getApplicationContext(), "La contraseña debe tener entre 5 y 15 caracteres.", Toast.LENGTH_SHORT).show();
                }


    }
        }

        @Override
        protected void onCancelled() {
            task = null;
            //showProgress(false);
        }
    }

    private void reset() {
        newPW1.setText("");
        newPW2.setText("");
        oldPW.setText("");
        if (newPW1.isFocused())
            newPW1.clearFocus();
        if (newPW2.isFocused())
            newPW2.clearFocus();
        if (oldPW.isFocused())
            oldPW.clearFocus();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {    //fragment가 넘어갈때(tab 클릭시) 기존에 띄어주고 있던 keyboard 숨기기
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    hideKeyboard();
                    Log.d("frag","hide keyboard");
                } else {

                }
            }
        }
    }

}
