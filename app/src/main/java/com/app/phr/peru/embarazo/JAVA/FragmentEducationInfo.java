package com.app.phr.peru.embarazo.JAVA;

/**
 * Created by hansol on 2016-08-10.
 * tab layout 중에서 교육자료를 보여줄 fragment
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.app.phr.peru.embarazo.R;
import com.bumptech.glide.Glide;


import uk.co.senab.photoview.PhotoViewAttacher;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class FragmentEducationInfo extends Fragment {
    FragmentMyInfo c = new FragmentMyInfo();
    LinearLayout LL;

    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;
    private String childDate;
    private String childDate2;
    private String name;
    private String resultNumber;
    private String result;
    private String Date;

    private long t;
    private long d;
    private long r;
    private long s;
    TextView test;
    TextView time;
    Context mContext;
    ImageView imageView;
    WebView webView;
    public FragmentEducationInfo() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_educationinfo, container, false);

        ImageView imageView= (ImageView) v.findViewById(R.id.ei);
        ImageView imageView2= (ImageView) v.findViewById(R.id.ei2);
        ImageView imageView3 = (ImageView) v.findViewById(R.id.ei3);
        ImageView imageView4 = (ImageView) v.findViewById(R.id.ei4);
        ImageView imageView5 = (ImageView) v.findViewById(R.id.ei5);
        ImageView imageView6 = (ImageView) v.findViewById(R.id.ei6);
        ImageView imageView7 = (ImageView) v.findViewById(R.id.ei7);
        ImageView imageView8 = (ImageView) v.findViewById(R.id.ei8);
        ImageView imageView9 = (ImageView) v.findViewById(R.id.ei9);
        ImageView imageView10 = (ImageView) v.findViewById(R.id.ei10);
        ImageView imageView11 = (ImageView) v.findViewById(R.id.ei11);
        ImageView imageView12 = (ImageView) v.findViewById(R.id.ei12);
        ImageView imageView13 = (ImageView) v.findViewById(R.id.ei13);
        ImageView imageView14 = (ImageView) v.findViewById(R.id.ei14);
        ImageView imageView15 = (ImageView) v.findViewById(R.id.ei15);
        ImageView imageView16 = (ImageView) v.findViewById(R.id.ei16);
        ImageView imageView17 = (ImageView) v.findViewById(R.id.ei17);
        ImageView imageView18 = (ImageView) v.findViewById(R.id.ei18);
        ImageView imageView19 = (ImageView) v.findViewById(R.id.ei19);
        ImageView imageView20 = (ImageView) v.findViewById(R.id.ei20);
        ImageView imageView21 = (ImageView) v.findViewById(R.id.ei21);
        ImageView imageView22 = (ImageView) v.findViewById(R.id.ei22);
        ImageView imageView23 = (ImageView) v.findViewById(R.id.ei23);
        ImageView imageView24 = (ImageView) v.findViewById(R.id.ei24);
        ImageView imageView25 = (ImageView) v.findViewById(R.id.ei25);
        ImageView imageView26 = (ImageView) v.findViewById(R.id.ei26);
        ImageView imageView27 = (ImageView) v.findViewById(R.id.ei27);
        ImageView imageView28 = (ImageView) v.findViewById(R.id.ei28);
        ImageView imageView29 = (ImageView) v.findViewById(R.id.ei29);
        ImageView imageView30 = (ImageView) v.findViewById(R.id.ei30);
        ImageView imageView31 = (ImageView) v.findViewById(R.id.ei31);
        ImageView imageView32 = (ImageView) v.findViewById(R.id.ei32);
        ImageView imageView33 = (ImageView) v.findViewById(R.id.ei33);
        ImageView imageView34 = (ImageView) v.findViewById(R.id.ei34);
        ImageView imageView35 = (ImageView) v.findViewById(R.id.ei35);
        ImageView imageView36 = (ImageView) v.findViewById(R.id.ei36);
        ImageView imageView37 = (ImageView) v.findViewById(R.id.ei37);
        ImageView imageView38 = (ImageView) v.findViewById(R.id.ei38);
        ImageView imageView39 = (ImageView) v.findViewById(R.id.ei39);
        ImageView imageView40 = (ImageView) v.findViewById(R.id.ei40);
        ImageView imageView41 = (ImageView) v.findViewById(R.id.ei41);
        ImageView imageView42 = (ImageView) v.findViewById(R.id.ei42);
        ImageView imageView43 = (ImageView) v.findViewById(R.id.ei43);
        ImageView imageView44 = (ImageView) v.findViewById(R.id.ei44);
        ImageView imageView45 = (ImageView) v.findViewById(R.id.ei45);
        ImageView imageView46 = (ImageView) v.findViewById(R.id.ei46);
        ImageView imageView47 = (ImageView) v.findViewById(R.id.ei47);
        ImageView imageView48 = (ImageView) v.findViewById(R.id.ei48);
        ImageView imageView49 = (ImageView) v.findViewById(R.id.ei49);
        ImageView imageView50 = (ImageView) v.findViewById(R.id.ei50);
        ImageView imageView51 = (ImageView) v.findViewById(R.id.ei51);
        ImageView imageView52 = (ImageView) v.findViewById(R.id.ei52);
        ImageView imageView53 = (ImageView) v.findViewById(R.id.ei53);
        ImageView imageView54 = (ImageView) v.findViewById(R.id.ei54);
        ImageView imageView55 = (ImageView) v.findViewById(R.id.ei55);
        ImageView imageView56 = (ImageView) v.findViewById(R.id.ei56);
        ImageView imageView57 = (ImageView) v.findViewById(R.id.ei57);
        ImageView imageView58 = (ImageView) v.findViewById(R.id.ei58);
        ImageView imageView59 = (ImageView) v.findViewById(R.id.ei59);
        ImageView imageView60 = (ImageView) v.findViewById(R.id.ei60);
        ImageView imageView61 = (ImageView) v.findViewById(R.id.ei61);
        ImageView imageView62 = (ImageView) v.findViewById(R.id.ei62);
        ImageView imageView63 = (ImageView) v.findViewById(R.id.ei63);
        ImageView imageView64 = (ImageView) v.findViewById(R.id.ei64);
        ImageView imageView65 = (ImageView) v.findViewById(R.id.ei65);
        ImageView imageView66 = (ImageView) v.findViewById(R.id.ei66);
        ImageView imageView67 = (ImageView) v.findViewById(R.id.ei67);
        ImageView imageView68 = (ImageView) v.findViewById(R.id.ei68);
        ImageView imageView69 = (ImageView) v.findViewById(R.id.ei69);
        ImageView imageView70 = (ImageView) v.findViewById(R.id.ei70);
        ImageView imageView71 = (ImageView) v.findViewById(R.id.ei71);
        ImageView imageView72 = (ImageView) v.findViewById(R.id.ei72);
        ImageView imageView73 = (ImageView) v.findViewById(R.id.ei73);
        ImageView imageView74 = (ImageView) v.findViewById(R.id.ei74);
        ImageView imageView75 = (ImageView) v.findViewById(R.id.ei75);
        ImageView imageView76 = (ImageView) v.findViewById(R.id.ei76);
        ImageView imageView77 = (ImageView) v.findViewById(R.id.ei77);

        Glide.with(this).load(R.drawable.e0001).into(imageView);
        Glide.with(this).load(R.drawable.e0002).into(imageView2);
        Glide.with(this).load(R.drawable.e0003).into(imageView3);
        Glide.with(this).load(R.drawable.e0004).into(imageView4);
        Glide.with(this).load(R.drawable.e0005).into(imageView5);
        Glide.with(this).load(R.drawable.e0006).into(imageView6);
        Glide.with(this).load(R.drawable.e0007).into(imageView7);
        Glide.with(this).load(R.drawable.e0008).into(imageView8);
        Glide.with(this).load(R.drawable.e0009).into(imageView9);
        Glide.with(this).load(R.drawable.e0010).into(imageView10);
        Glide.with(this).load(R.drawable.e0011).into(imageView11);
        Glide.with(this).load(R.drawable.e0012).into(imageView12);
        Glide.with(this).load(R.drawable.e0013).into(imageView13);
        Glide.with(this).load(R.drawable.e0014).into(imageView14);
        Glide.with(this).load(R.drawable.e0015).into(imageView15);
        Glide.with(this).load(R.drawable.e0016).into(imageView16);
        Glide.with(this).load(R.drawable.e0017).into(imageView17);
        Glide.with(this).load(R.drawable.e0018).into(imageView18);
        Glide.with(this).load(R.drawable.e0019).into(imageView19);
        Glide.with(this).load(R.drawable.e0020).into(imageView20);
        Glide.with(this).load(R.drawable.e0021).into(imageView21);
        Glide.with(this).load(R.drawable.e0022).into(imageView22);
        Glide.with(this).load(R.drawable.e0023).into(imageView23);
        Glide.with(this).load(R.drawable.e0024).into(imageView24);
        Glide.with(this).load(R.drawable.e0025).into(imageView25);
        Glide.with(this).load(R.drawable.e0026).into(imageView26);
        Glide.with(this).load(R.drawable.e0027).into(imageView27);
        Glide.with(this).load(R.drawable.e0028).into(imageView28);
        Glide.with(this).load(R.drawable.e0029).into(imageView29);
        Glide.with(this).load(R.drawable.e0030).into(imageView30);
        Glide.with(this).load(R.drawable.e0031).into(imageView31);
        Glide.with(this).load(R.drawable.e0032).into(imageView32);
        Glide.with(this).load(R.drawable.e0033).into(imageView33);
        Glide.with(this).load(R.drawable.e0034).into(imageView34);
        Glide.with(this).load(R.drawable.e0035).into(imageView35);
        Glide.with(this).load(R.drawable.e0036).into(imageView36);
        Glide.with(this).load(R.drawable.e0037).into(imageView37);
        Glide.with(this).load(R.drawable.e0038).into(imageView38);
        Glide.with(this).load(R.drawable.e0039).into(imageView39);
        Glide.with(this).load(R.drawable.e0040).into(imageView40);
        Glide.with(this).load(R.drawable.e0041).into(imageView41);
        Glide.with(this).load(R.drawable.e0042).into(imageView42);
        Glide.with(this).load(R.drawable.e0043).into(imageView43);
        Glide.with(this).load(R.drawable.e0044).into(imageView44);
        Glide.with(this).load(R.drawable.e0045).into(imageView45);
        Glide.with(this).load(R.drawable.e0046).into(imageView46);
        Glide.with(this).load(R.drawable.e0047).into(imageView47);
        Glide.with(this).load(R.drawable.e0048).into(imageView48);
        Glide.with(this).load(R.drawable.e0049).into(imageView49);
        Glide.with(this).load(R.drawable.e0050).into(imageView50);
        Glide.with(this).load(R.drawable.e0051).into(imageView51);
        Glide.with(this).load(R.drawable.e0052).into(imageView52);
        Glide.with(this).load(R.drawable.e0053).into(imageView53);
        Glide.with(this).load(R.drawable.e0054).into(imageView54);
        Glide.with(this).load(R.drawable.e0055).into(imageView55);
        Glide.with(this).load(R.drawable.e0056).into(imageView56);
        Glide.with(this).load(R.drawable.e0057).into(imageView57);
        Glide.with(this).load(R.drawable.e0058).into(imageView58);
        Glide.with(this).load(R.drawable.e0059).into(imageView59);
        Glide.with(this).load(R.drawable.e0060).into(imageView60);
        Glide.with(this).load(R.drawable.e0061).into(imageView61);
        Glide.with(this).load(R.drawable.e0062).into(imageView62);
        Glide.with(this).load(R.drawable.e0063).into(imageView63);
        Glide.with(this).load(R.drawable.e0064).into(imageView64);
        Glide.with(this).load(R.drawable.e0065).into(imageView65);
        Glide.with(this).load(R.drawable.e0066).into(imageView66);
        Glide.with(this).load(R.drawable.e0067).into(imageView67);
        Glide.with(this).load(R.drawable.e0068).into(imageView68);
        Glide.with(this).load(R.drawable.e0069).into(imageView69);
        Glide.with(this).load(R.drawable.e0070).into(imageView70);
        Glide.with(this).load(R.drawable.e0071).into(imageView71);
        Glide.with(this).load(R.drawable.e0072).into(imageView72);
        Glide.with(this).load(R.drawable.e0073).into(imageView73);
        Glide.with(this).load(R.drawable.e0074).into(imageView74);
        Glide.with(this).load(R.drawable.e0075).into(imageView75);
        Glide.with(this).load(R.drawable.e0076).into(imageView76);
        Glide.with(this).load(R.drawable.e0077).into(imageView77);



        PhotoViewAttacher mAttacher;
        mAttacher = new PhotoViewAttacher(imageView);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView2);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView3);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView4);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView5);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView6);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView7);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView8);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView9);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView10);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView11);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView12);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView13);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView14);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView15);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView16);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView17);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView18);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView19);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView20);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView21);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView22);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView23);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView24);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView25);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView26);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView27);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView28);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView29);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView30);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView31);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView32);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView33);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView34);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView35);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView36);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView37);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView38);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView39);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView40);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView41);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView42);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView43);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView44);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView45);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView46);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView47);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView48);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView49);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView50);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView51);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView52);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView53);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView54);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView55);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView56);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView57);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView58);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView59);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView60);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView61);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView62);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView63);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView64);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView65);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView66);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView67);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView68);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView69);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView70);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView71);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView72);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView73);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView74);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView75);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView76);
        mAttacher.canZoom();
        mAttacher = new PhotoViewAttacher(imageView77);
        mAttacher.canZoom();
        //  preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);

        //webView = (WebView) v.findViewById(R.id.web);

            //edit = preferences.edit();
           // childDate = preferences.getString(PreferencePutter.DATE, "");

          //  TextView txt1 = (TextView) v.findViewById(R.id.test);

    //    txt1.setText("Por favor, ajuste de la fecha prevista del parto en mi ficha Información");
     //   webView.setWebViewClient(new WebViewClient());
     //   webView.loadUrl("http://terms.naver.com/entry.nhn?docId=2098899&cid=51027&categoryId=51027");

        Calendar calendar1 = Calendar.getInstance();
//        d = Long.valueOf(childDate);
        t = calendar1.getTimeInMillis();
        s = t / (24 * 60 * 60 * 1000);
        r = (d - s) / 30;
        result = String.valueOf(r);
// 노트북, 스마트기기 모델명 제목 수정 043 713 8254 김신영 오후 담주 월요일
        if(r==0){
          //  txt1.setText("Por favor, ajuste de la fecha prevista del parto en mi ficha Información");
          //  webView.setWebViewClient(new WebViewClient());
         //   webView.loadUrl("http://terms.naver.com/entry.nhn?docId=2098899&cid=51027&categoryId=51027");
        }
        if(r==1){
          //  txt1.setText(result +" meses de embarazo");
          //  webView.setWebViewClient(new WebViewClient());
          //  webView.loadUrl("http://www.google.co.kr");
        }
        if(r==2){
       //     txt1.setText(result +" meses de embarazo");
          //  webView.setWebViewClient(new WebViewClient());
           // webView.loadUrl("http://www.cdpkorea.com");
        }



        if(savedInstanceState==null)
        {

        }

        else
        {

         //   resultNumber=savedInstanceState.getString("text");
      //     Calendar calendar = Calendar.getInstance();
        //    d=Long.valueOf(resultNumber);
        //    t=calendar.getTimeInMillis();
         //   s=t/(24*60*60*1000);
          //  r= d - s;
        //    result = String.valueOf(t);
        //    TextView myText = (TextView) v.findViewById(R.id.test);
        //    myText.setText(result);

            }

            //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
           // TextView time = (TextView) v.findViewById(R.id.time);
            //time.setText(currentDateTimeString);
/*

            Bundle bundle = MainTab.getArguments();
            if (bundle != null) {
                name = bundle.getString("name");
                edit = preferences.edit();
                edit.putString(PreferencePutter.DATE, name);
                edit.commit();
                Calendar calendar = Calendar.getInstance();
                TextView txt = (TextView) v.findViewById(R.id.test);
                d = Long.valueOf(name);
                t = calendar.getTimeInMillis();
                s = t / (24 * 60 * 60 * 1000);
                r = (d - s) / 30;
                result = String.valueOf(r);


          //      txt.setText(result);
                if(r==0) {
                    //txt.setText("내 정보 탭에서 출산예정일을 설정해주세요");
                    txt.setText(result);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl("http://terms.naver.com/entry.nhn?docId=2098899&cid=51027&categoryId=51027");

                    //edit = preferences.edit();
                   // edit.putString(PreferencePutter.DATE, name);
                    //edit.commit();
                }
                if(r==1) {
                    txt.setText(result);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl("http://www.naver.com");

                    //edit = preferences.edit();
                   // edit.putString(PreferencePutter.DATE, name);
                    //edit.commit();
                }
                if(r==2) {
                    txt.setText(result);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl("http://www.cdpkorea.com");

//                    edit = preferences.edit();
  //                  edit.putString(PreferencePutter.DATE, name);
    //                edit.commit();
                }
            }

*/


     //   preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);
     //   childDate = preferences.getString(PreferencePutter.CHILD_DATE, "null");

      //  TextView txt = (TextView) v.findViewById(R.id.test);
       // txt.setText(childDate);


      return v;


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


     //   test= (TextView) getActivity().findViewById(R.id.test);
    }
/*
    public void changeText(String data)
    {


        Calendar calendar = Calendar.getInstance();

        d=Long.valueOf(data);
        if(d>-10000) {
            edit = preferences.edit();
            edit.putString(PreferencePutter.DATE, data);
            edit.commit();
            t = calendar.getTimeInMillis();
            s = t / (24 * 60 * 60 * 1000);
            r = (d - s) / 30;
            result = String.valueOf(r);
            preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);

        //    test.setText(result);
            if(r==0) {
            //    test.setText("내 정보 탭에서 출산예정일을 설정해주세요");
             //   test.setText("Por favor, ajuste de la fecha prevista del parto en mi ficha Información");
            //    webView.setWebViewClient(new WebViewClient());
            //    webView.loadUrl("http://terms.naver.com/entry.nhn?docId=2098899&cid=51027&categoryId=51027");

         //       edit = preferences.edit();
           //     edit.putString(PreferencePutter.DATE, result);
             //   edit.commit();
            }
            if(r==1) {
            //    test.setText(result +" meses de embarazo");
            //    webView.setWebViewClient(new WebViewClient());
             //   webView.loadUrl("http://www.google.co.kr");

            //    edit = preferences.edit();
              //  edit.putString(PreferencePutter.DATE, result);
                //edit.commit();
            }
            if(r==2) {
            //    test.setText(result +" meses de embarazo");
            //    webView.setWebViewClient(new WebViewClient());
             //   webView.loadUrl("http://www.cdpkorea.com");
            }

           //     edit = preferences.edit();
             //   edit.putString(PreferencePutter.DATE, result);
               // edit.commit();
            }

    }
*/
    // on sauve les données dans le cas d'une rotation par exemple



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     //   outState.putString("text",childDate);
    }
/*
    @Override
    public void onDestroy() {
        Log.d("OOMTEST", "onDestroy");
        recycleBitmap(imageView);

        super.onDestroy();
    }

    private static void recycleBitmap(ImageView iv) {
        Drawable d = iv.getDrawable();
        if (d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable)d).getBitmap();
            b.recycle();
        } // 현재로서는 BitmapDrawable 이외의 drawable 들에 대한 직접적인 메모리 해제는 불가능하다.

        d.setCallback(null);
    }
*/
}
