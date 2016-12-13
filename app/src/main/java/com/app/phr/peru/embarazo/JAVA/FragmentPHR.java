package com.app.phr.peru.embarazo.JAVA;

/**
 * Created by hansol on 2016-08-10.
 * phr xml parsing
 * PHR.java 를 통하여 메소드에 항목별로 변수 지정 됨
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.app.phr.peru.embarazo.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class FragmentPHR extends Fragment {
    private PHR phr;
    private boolean getPHR = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private XmlParser parser;
    private String mID = "";
    private String mKey = "";
    private requestPHRTask requestTask = null;
    private boolean flag = false;
    private String Phr;
    TableLayout tb0, tb1, tb2, tb3;
    public String title;
    public FragmentPHR() {
        parser = new XmlParser();
    }

    private void requestPHR() {  //request phr data to server
        Log.d("frag", "request");
        if (requestTask != null) {
            return;
        }
        mID = preferences.getString(PreferencePutter.PREF_ID, "error");
        mKey = preferences.getString(PreferencePutter.PREF_KEY, "error");
        requestTask = new requestPHRTask();
        requestTask.execute((Void) null);
    }

    private void parse_putPHR(String result) {  //parse and put phr data in activirty through using xml parser
        phr = parser.getPhrObject(result);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(PreferencePutter.PREF_FILE_NAME, Activity.MODE_PRIVATE);

        if (NetworkUtil.getConnectivityStatusBoolean((getActivity()))) {   //PHR fragment가 create 되었을때 network 상태 유무를 판단해서 network가 연결되어있으면 server에 phr 요청 후 받은 response로 layour 구성
            //network가 연결 안될시에는 SharedPreference에 저장했던 response xml data 를 parsing해서 띄우기. (network에게 response를 받아올때마다 sharedPreference에 저장)
          requestPHR();


            // 데이터 receive 성공 유무 판단 !!
            Log.d("frag", "get phr");
        } else {
          setLayoutWithout_Net();
        }
    }
    private void setLayoutWithout_Net(){
        Log.d("frag", "networkless");
        Phr = preferences.getString(PreferencePutter.PHR, "null");

        if (Phr.equals("null")) {
            //show need to network for receiving data
            Log.d("frag", "null phr");
            getPHR = false;
        } else if (Phr.equals("non_record")) {
            getPHR = false;
        } else {
            //parsing result & show
            parse_putPHR(Phr);  //insert phr into layout
            Log.d("non net", Phr);
            getPHR = true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // hideKeyboard();

        View rootView;
        if(getPHR) {
            rootView = inflater.inflate(R.layout.fragment_phr, container, false);
            phr = new PHR();
            tb0 = (TableLayout) rootView.findViewById(R.id.table0);
            tb0.setStretchAllColumns(true);
            tb1 = (TableLayout) rootView.findViewById(R.id.table1);
            tb1.setStretchAllColumns(true);
            tb2 = (TableLayout) rootView.findViewById(R.id.table2);
            tb2.setStretchAllColumns(true);
            tb3 = (TableLayout) rootView.findViewById(R.id.table3);
            tb3.setStretchAllColumns(true);


            //항목별로 매소드 구성
            BirthDateParsing();
            SexParsing();
            BloodParsing();
            AllergyParsing();
            //AdverseReactionParsing();
            PastHistoryParsing();
            FamilyHistoryParsing();
            SocialHistoryParsing();
            PM_HeightParsing();
            PM_WeightParsing();
            PM_BloodSugarBeforeParsing();
            PM_BloodSugarAfterParsing();
            PM_A1CParsing();
            PM_BloodPressureParsing();
            PM_CholesterolParsing();
            Ms_MedicationParsing();
            MH_Parsing();
            //TeleMedicineParsing();




        } else {
            rootView = inflater.inflate(R.layout.fragment_phr, container, false);

           FragmentTransaction ft = getFragmentManager().beginTransaction();
           ft.detach(this).attach(this).commit();



        }
        return rootView;

    }


    public class requestPHRTask extends AsyncTask<Void, Void, String> {  //thread to connect to server
        private HTTPClient client;
        private XmlWriter writer;

        @Override
        protected void onPreExecute() {

            writer = new XmlWriter();
            client = new HTTPClient();
            Log.d("http send", writer.getForPhr(mID, mKey).toString());
            client.setDoc(writer.getForPhr(mID, mKey));
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            // Simulate network access.
            result = client.connect();


            if (result.equals("connection error")) {
                setLayoutWithout_Net();
            } else {
                int response = parser.checkResponse(result);
                //save result in object and show to activity.
                if (response == 100) {
                    Log.d("phr", "response msg : 100");
                    //layout = R.layout.fragment_phr;
                    editor = preferences.edit();
                    editor.putString(PreferencePutter.PHR, result);
                    editor.commit();
                    Phr = result;
                    getPHR = true;
                } else if (response == 202) {
                    //show 진료된 기록이 없습니다.
//               Log.d("phr", "response msg : 100");
                    editor = preferences.edit();
                    editor.putString(PreferencePutter.PHR, "non_record");
                    editor.commit();
                    getPHR = false;

                } else {
                    Log.d("phr","response 500");
                    setLayoutWithout_Net();
                }
            }
            flag = true;
            requestTask = null;
            return "";

        }

        @Override
        protected void onCancelled() {
            flag = true;
            requestTask = null;
            //showProgress(false);
        }

    }

    public void BirthDateParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //노드 읽어오기
            Element element = doc.getDocumentElement();
            //노드 개수 파악
            NodeList BirthDate = element.getElementsByTagName("PatientSummary");

            //항목이 0개가 아니면 title 부르기
            if (BirthDate.getLength() != 0) {
                setTitle0("Fecha de nacimiento");

                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("BirthDate");//여기 변경
                int count = nodeList.getLength();
                for (int i = 0; i < count; i++) {
                    Node node = nodeList.item(i);
                    String Value = node.getFirstChild().getNodeValue();


                    phr.setBirthDate(Value);

                    setXmlParsing1(phr.getBirthDate());

                }



            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void SexParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //노드 읽어오기
            Element element = doc.getDocumentElement();
            //노드 개수 파악
            NodeList Sex = element.getElementsByTagName("PatientSummary");

            //항목이 0개가 아니면 title 부르기
            if (Sex.getLength() != 0) {
                setTitle0("Sexo");

                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("Sex");//여기 변경
                int count = nodeList.getLength();
                for (int i = 0; i < count; i++) {
                    Node node = nodeList.item(i);
                    String Value = node.getFirstChild().getNodeValue();//여기변경 홀수 단위로 생각하기.


                    phr.setSex(Value);

                    setXmlParsing1(phr.getSex());
                }



            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void BloodParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //노드 읽어오기
            Element element = doc.getDocumentElement();
            //노드 개수 파악
            NodeList blood = element.getElementsByTagName("PatientSummary");

            //항목이 0개가 아니면 title 부르기
            if (blood.getLength() != 0) {
                setTitle0("Tipo de sangre");


                //노드-자식노드를 통하여 값을 파싱 함.
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("Blood");//여기 변경
                int count = nodeList.getLength();
                for (int i = 0; i < count; i++) {
                    Node node = nodeList.item(i);
                    String Value = node.getFirstChild().getNodeValue();
                    phr.setBlood(Value);
                    setXmlParsing1(phr.getBlood());

                }
            }

        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void AllergyParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //노드 읽어오기
            Element element = doc.getDocumentElement();
            //노드 개수 파악
            NodeList allergys = element.getElementsByTagName("Allergy");

            //항목이 0개가 아니면 title 부르기
            if (allergys.getLength() != 0) {
                setTitle0("Alergia");

                //노드-자식노드를 통하여 값을 파싱 함.
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("Allergy");//여기 변경

                int count = nodeList.getLength();

                for (int i = 0; i < count; i++) {
                    Node node = nodeList.item(i);
                    String Value = node.getFirstChild().getNodeValue();
                    phr.setAllergy(Value);
                    setXmlParsing1(phr.getAllergy());

                }

            }
        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
        public void AdverseReactionParsing() {
            try {
                String xml = Phr;
                //xml 읽어오기
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
                Document doc = builder.parse(istream);

                //xml element 읽어오기
                Element element = doc.getDocumentElement();
                //노드list 선언
                NodeList AdverseReactions = element.getElementsByTagName("AdverseReaction");

                //항목이 0개가 아니면 title 부르기
                if (AdverseReactions.getLength() != 0) {
                    setTitle("AdverseReaction");
                    setSubtitle2();
                }

                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("AdverseReaction");//여기 변경
                int count = nodeList.getLength();
                for (int i = 0; i < count; i++) {
                    Node node = nodeList.item(i);
                    String Date = node.getChildNodes().item(1).getFirstChild().getNodeValue();//여기변경
                    String Value = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                    phr.setAdverseReaction_d(Date);
                    phr.setAdverseReaction_v(Value);
                    setXmlParsing2(phr.getAdverseReaction_d(), phr.getAdverseReaction_v());

                }


            } catch (Exception e) {
                failTb();
                //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    */
    public void PastHistoryParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList PastHisory = element.getElementsByTagName("PastHistory");

            //항목이 0개가 아니면 title 부르기
            if (PastHisory.getLength() != 0) {
                setTitle0("Antecedentes");
            }



            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("PastHistory");//여기 변경

            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String Value = node.getFirstChild().getNodeValue();
                phr.setPastHistory(Value);
                setXmlParsing1(phr.getPastHistory());
            }

        } catch (Exception e) {

            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void FamilyHistoryParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList FamilyHistorys = element.getElementsByTagName("FamilyHistory");

            //항목이 0개가 아니면 title 부르기
            if (FamilyHistorys.getLength() != 0) {
                setTitle0("Antecedentes clínicos familiares");

            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("FamilyHistory");//여기 변경
            int count = nodeList.getLength();

            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String Value = node.getFirstChild().getNodeValue();

                phr.setFamilyHistory(Value);
                setXmlParsing1(phr.getFamilyHistory());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void SocialHistoryParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList SocialHistorys = element.getElementsByTagName("PatientSummary");

            //항목이 0개가 아니면 title 부르기
            if (SocialHistorys.getLength() != 0) {
                setTitle0("Antecedentes clínicos sociales");

            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("SocialHistory");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String Value = node.getFirstChild().getNodeValue();
                phr.setSocialHistory(Value);
                setXmlParsing1(phr.getSocialHistory());
            }

        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_HeightParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);


            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList Heights = element.getElementsByTagName("Height");

            //항목이 0개가 아니면 title 부르기
            if (Heights.getLength() != 0) {
                setTitle("Altura");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Height");//여기 변경

            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                //String Value = node.getFirstChild().getNodeValue();
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_Height_d(date);
                phr.setPM_Height_v(value);
                setXmlParsing2(phr.getPM_Height_d(), phr.getPM_Height_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_WeightParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList Weights = element.getElementsByTagName("Weight");

            //항목이 0개가 아니면 title 부르기
            if (Weights.getLength() != 0) {
                setTitle("Peso");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Weight");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_Weight_d(date);
                phr.setPM_Weight_v(value);
                setXmlParsing2(phr.getPM_Weight_d(), phr.getPM_Weight_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_BloodSugarBeforeParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList BloodSugarBefores = element.getElementsByTagName("BloodSugarBefore");

            //항목이 0개가 아니면 title 부르기
            if (BloodSugarBefores.getLength() != 0) {
                setTitle("Glucemia en ayunas");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("BloodSugarBefore");//여기 변경

            int count = nodeList.getLength();

            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_BloodSugarBefore_d(date);
                phr.setPM_BloodSugarBefore_v(value);
                setXmlParsing2(phr.getPM_BloodSugarBefore_d(), phr.getPM_BloodSugarBefore_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_BloodSugarAfterParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList BloodSugarAfters = element.getElementsByTagName("BloodSugarAfter");

            //항목이 0개가 아니면 title 부르기
            if (BloodSugarAfters.getLength() != 0) {
                setTitle("Glucemia postprandial");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("BloodSugarAfter");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_BloodSugarAfter_d(date);
                phr.setPM_BloodSugarAfter_v(value);
                setXmlParsing2(phr.getPM_BloodSugarAfter_d(), phr.getPM_BloodSugarAfter_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_A1CParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList A1Cs = element.getElementsByTagName("A1C");

            //항목이 0개가 아니면 title 부르기
            if (A1Cs.getLength() != 0) {
                setTitle("Hemoglobina A1c(HbA1c)");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("A1C");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_A1C_d(date);
                phr.setPM_A1C_v(value);
                setXmlParsing2(phr.getPM_A1C_d(), phr.getPM_A1C_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_BloodPressureParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList BloodPressures = element.getElementsByTagName("BloodPressure");

            //항목이 0개가 아니면 title 부르기
            if (BloodPressures.getLength() != 0) {
                setTitle("Presión arterial");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("BloodPressure");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date =  node.getChildNodes().item(0).getFirstChild().getNodeValue();
                String value =  node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_BloodPressure_d(date);
                phr.setPM_BloodPressure_v(value);
                setXmlParsing2(phr.getPM_BloodPressure_d(), phr.getPM_BloodPressure_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PM_CholesterolParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList Cholesterols = element.getElementsByTagName("Cholesterol");

            //항목이 0개가 아니면 title 부르기
            if (Cholesterols.getLength() != 0) {
                setTitle("Colesterol");
                setSubtitle2();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Cholesterol");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date = node.getChildNodes().item(0).getFirstChild().getNodeValue();//여기변경
                String value = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                phr.setPM_Cholesterol_d(date);
                phr.setPM_Cholesterol_v(value);
                setXmlParsing2(phr.getPM_Cholesterol_d(), phr.getPM_Cholesterol_v());
            }

        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Ms_MedicationParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList Medications = element.getElementsByTagName("Medications");

            //항목이 0개가 아니면 title 부르기
            if (Medications.getLength() != 0) {
                setTitle2("Medicación");
                setSubtitle3();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Medication");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date = node.getChildNodes().item(0).getFirstChild().getNodeValue();//여기변경
                String value = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                String QCD = node.getChildNodes().item(2).getFirstChild().getNodeValue();
                phr.setMs_Medication_d(date);
                phr.setMs_Medication_v(value);
                phr.setMs_Medication_c(QCD);
                setXmlParsing3(phr.getMs_Medication_d(), phr.getMs_Medication_v(), phr.getMs_Medication_c());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void MH_Parsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList Medications = element.getElementsByTagName("MedicalHistories");

            //항목이 0개가 아니면 title 부르기
            if (Medications.getLength() != 0) {
                setTitle3("Registro hospitalario");
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("MedicalHistory");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date = node.getChildNodes().item(0).getFirstChild().getNodeValue();//여기변경
                String hospital = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                String dept = node.getChildNodes().item(2).getFirstChild().getNodeValue();
                String scd = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                String snm = node.getChildNodes().item(4).getFirstChild().getNodeValue();
                phr.setMH_Date(date);
                phr.setMH_Hospital(hospital);
                phr.setMH_Dept(dept);
                phr.setMH_S_CD(scd);
                phr.setMH_S_NM(snm);
                setXmlParsing4(phr.getMH_Date(), phr.getMH_Hospital(), phr.getMH_Dept(), phr.getMH_S_CD(), phr.getMH_S_NM());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
    public void TeleMedicineParsing() {
        try {
            String xml = Phr;
            //xml 읽어오기
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
            Document doc = builder.parse(istream);

            //xml element 읽어오기
            Element element = doc.getDocumentElement();
            //노드list 선언
            NodeList AdverseReactions = element.getElementsByTagName("TeleMedicine");

            //항목이 0개가 아니면 title 부르기
            if (AdverseReactions.getLength() != 0) {
                setTitleLast("TeleMedicine");
         //       setSubtitleLast();
            }

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("TeleMedicine");//여기 변경
            int count = nodeList.getLength();
            for (int i = 0; i < count; i++) {
                Node node = nodeList.item(i);
                String date = node.getChildNodes().item(1).getFirstChild().getNodeValue();//여기변경
                String value = node.getChildNodes().item(3).getFirstChild().getNodeValue();
                phr.setTeleMedicine_d(date);
                phr.setTeleMedicine_v(value);
                setXmlParsingLast(phr.getTeleMedicine_d(), phr.getTeleMedicine_v());

            }


        } catch (Exception e) {
            failTb();
            //Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    */

    //xml 파싱 단게에서 오류 났을 때 출력하는 매소드
    public void failTb() {

        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tb1_t.setText("code xml parsing fail");
    }

    public void setTitle0(String title) {
        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.rgb(194, 194, 194));
        tb1_t.setPadding(40, 10, 0, 10);
        tb1_t.setText(title);
        tb1_t.setTextColor(Color.rgb(2, 46, 43));
        tb1_t.setTextSize(20);
        tr_ti.addView(tb1_t);
        tb0.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setTitle(String title) {
        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.rgb(194, 194, 194));
        tb1_t.setPadding(40, 10, 0, 10);
        tb1_t.setText(title);
        tb1_t.setTextColor(Color.rgb(2, 46, 43));
        tb1_t.setTextSize(22);
        tr_ti.addView(tb1_t);
        tb1.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void setTitle2(String title) {
        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.rgb(194, 194, 194));
        tb1_t.setPadding(40, 10, 0, 10);
        tb1_t.setText(title);
        tb1_t.setTextColor(Color.rgb(2, 46, 43));
        tb1_t.setTextSize(22);
        tr_ti.addView(tb1_t);
        tb2.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setTitle3(String title) {
        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.rgb(194, 194, 194));
        tb1_t.setPadding(40, 10, 0, 10);
        tb1_t.setText(title);
        tb1_t.setTextColor(Color.rgb(2, 46, 43));
        tb1_t.setTextSize(19);
        tr_ti.addView(tb1_t);
        tb2.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void setTitleLast(String title) {
        TableRow tr_ti = new TableRow(getActivity());
        tr_ti.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tb1_t = new TextView(getActivity());
        tr_ti.setBackgroundColor(Color.rgb(194, 194, 194));
        tb1_t.setPadding(40, 10, 0, 10);
        tb1_t.setText(title);
        tb1_t.setTextColor(Color.rgb(2, 46, 43));
        tb1_t.setTextSize(23);
        tr_ti.addView(tb1_t);
        tb3.addView(tr_ti, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 1개짜리(value only)
    public void setSubtitle1() {
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView value = new TextView(getActivity());
        value.setText("");
        value.setTextColor(Color.BLACK);
        value.setPadding(0, 10, 0, 10);
        value.setTextSize(19);
        tr_head.addView(value);
        tb0.addView(tr_head, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 2개짜리(date, value only)
    public void setSubtitle2() {
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 10f);
        tr_head.setLayoutParams(params1);

        TextView date = new TextView(getActivity());
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 6f);
        date.setLayoutParams(params2);
        date.setText("Fecha");
        date.setTextColor(Color.BLACK);
        date.setTextSize(19);
        date.setPadding(40, 10, 0, 10);
        tr_head.addView(date);


        TextView value = new TextView(getActivity());
        TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f);
        value.setLayoutParams(params3);
        value.setText("Valor");
        value.setTextColor(Color.BLACK);
        value.setPadding(0, 10, 0, 10);
        value.setTextSize(19);
        tr_head.addView(value);
        tb1.addView(tr_head, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 2개짜리(date, value only)
    public void setSubtitleLast() {
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView date = new TextView(getActivity());
        date.setText("Fecha");
        date.setTextSize(19);
        date.setTextColor(Color.BLACK);
        date.setTextSize(19);
        date.setPadding(40, 10, 0, 10);
        tr_head.addView(date);
        TextView value = new TextView(getActivity());
        value.setText("Valor");
        value.setTextColor(Color.BLACK);
        value.setPadding(0, 10, 0, 10);
        value.setTextSize(18);
        tr_head.addView(value);
        tb3.addView(tr_head, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 1개짜리(date, value only) 파싱
    public void setXmlParsing1(String value) {


        TextView valueView = new TextView(getActivity());
        valueView.setText(value);
        valueView.setTextSize(20);
        valueView.setPadding(40, 0, 0, 0);
        TableRow tr = new TableRow(getActivity());
        tr.setPadding(0, 10, 0, 10);
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.addView(valueView);

        tb0.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 2개짜리(date, value only) 파싱
    public void setXmlParsing2(String date, String value) {

        TableRow tr = new TableRow(getActivity());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 20f);
        tr.setLayoutParams(params1);

        TextView dateView = new TextView(getActivity());
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 12f);
        dateView.setLayoutParams(params2);
        dateView.setText(date);
        dateView.setTextSize(18);
        dateView.setPadding(40, 0, 0, 0);
        TextView valueView = new TextView(getActivity());
        TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 8f);
        valueView.setLayoutParams(params3);
        valueView.setText(value);
        valueView.setTextSize(18);
        valueView.setPadding(0, 0, 0, 0);

        tr.setPadding(0, 10, 0, 10);
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.addView(dateView);
        tr.addView(valueView);
        tb1.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //항목 4개짜리(date, code, value, number of day  only)
    public void setSubtitle3() {
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 30f);
        tr_head.setLayoutParams(params1);

        TextView date = new TextView(getActivity());
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 9f);
        date.setLayoutParams(params2);
        date.setText("Fecha");
        date.setTextColor(Color.BLACK);
        date.setTextSize(19);
        date.setPadding(10, 10, 0, 0);
        tr_head.addView(date);



        TextView value = new TextView(getActivity());
        TableRow.LayoutParams params4 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 17f);
        value.setLayoutParams(params4);
        value.setText("Medicamento");
        value.setTextColor(Color.BLACK);
        value.setTextSize(19);
        value.setPadding(10, 0, 0, 0);
        tr_head.addView(value);

        TextView numberOfDay = new TextView(getActivity());
        TableRow.LayoutParams params5 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f);
        numberOfDay.setLayoutParams(params5);
        numberOfDay.setText("QCD");
        numberOfDay.setTextColor(Color.BLACK);
        numberOfDay.setTextSize(19);
        //   numberOfDay.setPadding(0, 0, 0, 0);
        tr_head.addView(numberOfDay);
        tb2.addView(tr_head, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

    }

    //항목 4개짜리(date, code, value, number of day only) 파싱
    public void setXmlParsing3(String date, String value, String QCD) {
        TableRow tr = new TableRow(getActivity());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 30f);
        tr.setLayoutParams(params1);

        TextView dateView = new TextView(getActivity());
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 9f);
        dateView.setLayoutParams(params2);
        dateView.setText(date);
        dateView.setTextSize(16);
        dateView.setPadding(10, 0, 0, 0);

        TextView valueView = new TextView(getActivity());
        TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,17f);
        valueView.setLayoutParams(params3);
        valueView.setText(value);
        valueView.setTextSize(16);
        valueView.setPadding(10, 0, 0, 0);

        TextView QCDView = new TextView(getActivity());
        TableRow.LayoutParams params4 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f);
        QCDView.setLayoutParams(params4);
        QCDView.setText(QCD);
        QCDView.setTextSize(16);
        QCDView.setPadding(0, 0, 0, 0);

        tr.setPadding(0, 10, 0, 10);
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.addView(dateView);
        tr.addView(valueView);
        tr.addView(QCDView);
        tb2.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void setXmlParsing4(String date, String hosptial, String dept, String scd, String snm) {
        TextView dateView = new TextView(getActivity());
        dateView.setPadding(30, 0, 0, 0);
        dateView.setTextSize(16);
        //   dateView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        dateView.setText(date);



        TextView hopitalView = new TextView(getActivity());
        hopitalView.setPadding(30, 0, 0, 0);
        hopitalView.setTextSize(16);
        //       valueView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        hopitalView.setText(hosptial);

        TextView deptView = new TextView(getActivity());
        deptView.setPadding(30, 0, 0, 0);
        deptView.setTextSize(16);
        //       valueView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        deptView.setText(dept);


        TextView scdView = new TextView(getActivity());
        //  numberOfDayView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        scdView.setPadding(30, 0, 0, 0);
        scdView.setTextSize(16);
        scdView.setText(scd);

        TextView snmView = new TextView(getActivity());
        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 50f);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 49f);
        //  numberOfDayView.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        snmView.setPadding(30, 0, 0, 0);
        snmView.setLayoutParams(params2);
        snmView.setTextSize(16);
        snmView.setText(snm);

        TableRow tr = new TableRow(getActivity());
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr.addView(dateView);
        TableRow tr2 = new TableRow(getActivity());

        tr2.addView(hopitalView);
        TableRow tr3 = new TableRow(getActivity());
        tr3.addView(deptView);
        TableRow tr4 = new TableRow(getActivity());
        tr4.addView(scdView);
        TableRow tr5 = new TableRow(getActivity());
        tr5.addView(snmView);
        tr5.setLayoutParams(params1);
        TableRow tr6 = new TableRow(getActivity());
        tr6.setBackgroundColor(Color.GRAY);
        tr6.setPadding(0, 0, 0, 2);

        tb2.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb2.addView(tr2, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb2.addView(tr3, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb2.addView(tr4, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb2.addView(tr5, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb2.addView(tr6, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setXmlParsingLast(String date, String value) {
        TextView dateView = new TextView(getActivity());
        dateView.setText(date);
        dateView.setTextSize(16);
        dateView.setPadding(40, 0, 0, 0);
        dateView.setTypeface(null, Typeface.BOLD_ITALIC);

        TextView valueView = new TextView(getActivity());
        valueView.setText(value);
        valueView.setTextSize(16);
        valueView.setPadding(40, 0, 0, 0);
        valueView.setMaxLines(20);
        TableRow tr = new TableRow(getActivity());
        TableRow tr2 = new TableRow(getActivity());

        tr.setPadding(0, 10, 0, 10);
        tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tr2.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tr.addView(dateView);
        tr2.addView(valueView);
        tb3.addView(tr, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tb3.addView(tr2, new TableLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}
