package com.app.phr.peru.embarazo.JAVA;

import java.io.PipedReader;
import java.util.ArrayList;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by hansol on 2016-08-17.
 */

public class PHR {
    //Todo 임신주기 일단 주석
    //private String PregnancyDueDate_d, PregnancyDueDate_v;
    private String BirthDate;                   // 생년월일
    private String Sex;                         // 성별
    private String Blood;                       // 혈액형(+/-)
    private String Allergy;                     // 알레르기
    //private String AdverseReaction_d, AdverseReaction_v;      // 부작용
    private String PastHistory;                 // 과거 질환이력
    private String FamilyHistory;               // 가족력
    private String SocialHistory;               // 사회생활력(성명, 연령, 성, 국적, 직업, 출생지, 소개의사, 가족구성, 결혼여부, 개인기호)
    private String PM_Height_d, PM_Height_v;                    // 키
    private String PM_Weight_d, PM_Weight_v;                    // 체중
    private String PM_BloodSugarBefore_d, PM_BloodSugarBefore_v;// 공복 혈당
    private String PM_BloodSugarAfter_d, PM_BloodSugarAfter_v;  // 식후 혈당
    private String PM_A1C_d, PM_A1C_v;                          // 당화혈 색소검사
    private String PM_BloodPressure_d, PM_BloodPressure_v;      // 혈압
    private String PM_Cholesterol_d, PM_Cholesterol_v;          // 콜레스테롤
    //private String PM_Pulse_d, PM_Pulse_v;                    // 맥박

    private String Ms_Medication_d, Ms_Medication_c, Ms_Medication_v, Ms_Medication_n;    // 복약
    private String MH_Date, MH_Hospital, MH_Dept, MH_S_CD, MH_S_NM;             // 내원기록
    //private String TeleMedicine_d, TeleMedicine_v;            // 원격협진기록
    //todo comment 주석
    //private String PatientComment;


    //public void setPregnancyDueDate_d(String pregnancyDueDate_d) {this.PregnancyDueDate_d = pregnancyDueDate_d;}
    //public void setPregnancyDueDate_v(String pregnancyDueDate_v) {this.PregnancyDueDate_v = pregnancyDueDate_v;}
    public void setBirthDate(String birthDate) {this.BirthDate = birthDate;}
    public void setSex(String sex) {this.Sex = sex;}
    public void setBlood(String blood) {this.Blood = blood;}
    public void setAllergy(String allergy) {this.Allergy = allergy;}
    //public void setAdverseReaction_d(String adverseReaction_d) {this.AdverseReaction_d = adverseReaction_d;}
    //public void setAdverseReaction_v(String adverseReaction_v) {this.AdverseReaction_v = adverseReaction_v;}
    public void setPastHistory(String pastHistory) {this.PastHistory = pastHistory;}
    public void setFamilyHistory(String familyHistory) {this.FamilyHistory = familyHistory;}
    public void setSocialHistory(String socialHistory) {this.SocialHistory = socialHistory;}
    public void setPM_Height_d(String pm_height_d) {this.PM_Height_d = pm_height_d;}
    public void setPM_Height_v(String pm_height_v) {this.PM_Height_v = pm_height_v;}
    public void setPM_Weight_d(String pm_weight_d) {this.PM_Weight_d = pm_weight_d;}
    public void setPM_Weight_v(String pm_weight_v) {this.PM_Weight_v = pm_weight_v;}
    public void setPM_BloodSugarBefore_d(String pm_bloodSugarBefore_d) {this.PM_BloodSugarBefore_d = pm_bloodSugarBefore_d;}
    public void setPM_BloodSugarBefore_v(String pm_bloodSugarBefore_v) {this.PM_BloodSugarBefore_v = pm_bloodSugarBefore_v;}
    public void setPM_BloodSugarAfter_d(String pm_bloodSugarAfter_d) {this.PM_BloodSugarAfter_d = pm_bloodSugarAfter_d;}
    public void setPM_BloodSugarAfter_v(String pm_bloodSugarAfter_v) {this.PM_BloodSugarAfter_v = pm_bloodSugarAfter_v;}
    public void setPM_A1C_d(String pm_a1C_d) {this.PM_A1C_d = pm_a1C_d;}
    public void setPM_A1C_v(String pm_a1C_v) {this.PM_A1C_v = pm_a1C_v;}
    public void setPM_BloodPressure_d(String pm_bloodPressure_d) {this.PM_BloodPressure_d = pm_bloodPressure_d;}
    public void setPM_BloodPressure_v(String pm_bloodPressure_v) {this.PM_BloodPressure_v = pm_bloodPressure_v;}
    public void setPM_Cholesterol_d(String pm_cholesterol_d) {this.PM_Cholesterol_d = pm_cholesterol_d;}
    public void setPM_Cholesterol_v(String pm_cholesterol_v) {this.PM_Cholesterol_v = pm_cholesterol_v;}
    public void setMs_Medication_d(String ms_medication_d) {this.Ms_Medication_d = ms_medication_d;}
    public void setMs_Medication_c(String ms_medication_c) {this.Ms_Medication_c = ms_medication_c;}
    public void setMs_Medication_v(String ms_medication_v) {this.Ms_Medication_v = ms_medication_v;}
    public void setMH_Date(String mh_date) {this.MH_Date = mh_date;}
    public void setMH_Hospital(String mh_hospital) {this.MH_Hospital = mh_hospital;}
    public void setMH_Dept(String mh_dept) {this.MH_Dept = mh_dept;}
    public void setMH_S_CD(String mh_s_cd) {this.MH_S_CD = mh_s_cd;}
    public void setMH_S_NM(String mh_s_nm) {this.MH_S_NM = mh_s_nm;}


    //public void setPM_Pulse_d(String pm_pulse_d) {this.PM_Pulse_d = pm_pulse_d;}
    //public void setPM_Pulse_v(String pm_pulse_v) {this.PM_Pulse_v = pm_pulse_v;}
    //public void setTeleMedicine_d(String teleMedicine_d) {this.TeleMedicine_d = teleMedicine_d;}
    //public void setTeleMedicine_v(String teleMedicine_v) {this.TeleMedicine_v = teleMedicine_v;}
    //public void setPatientComment(String patientComment) {this.PatientComment = patientComment;}


    //public String getPregnancyDueDate_d() {return  this.PregnancyDueDate_d;}
    //public String getPregnancyDueDate_v() {return  this.PregnancyDueDate_v;}
    public String getBirthDate() {return this.BirthDate;}
    public String getSex() {return this.Sex;}
    public String getBlood() {return this.Blood;}
    public String getAllergy() {return this.Allergy;}
    //public String getAdverseReaction_d() {return this.AdverseReaction_d;}
    //public String getAdverseReaction_v() {return this.AdverseReaction_v;}
    public String getPastHistory() {return this.PastHistory;}
    public String getFamilyHistory() {return this.FamilyHistory;}
    public String getSocialHistory() {return this.SocialHistory;}
    public String getPM_Height_d() {return this.PM_Height_d;}
    public String getPM_Height_v() {return this.PM_Height_v;}
    public String getPM_Weight_d() {return this.PM_Weight_d;}
    public String getPM_Weight_v() {return this.PM_Weight_v;}
    public String getPM_BloodSugarBefore_d() {return this.PM_BloodSugarBefore_d;}
    public String getPM_BloodSugarBefore_v() {return this.PM_BloodSugarBefore_v;}
    public String getPM_BloodSugarAfter_d() {return this.PM_BloodSugarAfter_d;}
    public String getPM_BloodSugarAfter_v() {return this.PM_BloodSugarAfter_v;}
    public String getPM_A1C_d() {return this.PM_A1C_d;}
    public String getPM_A1C_v() {return this.PM_A1C_v;}
    public String getPM_BloodPressure_d() {return this.PM_BloodPressure_d;}
    public String getPM_BloodPressure_v() {return this.PM_BloodPressure_v;}
    public String getPM_Cholesterol_d() {return this.PM_Cholesterol_d;}
    public String getPM_Cholesterol_v() {return this.PM_Cholesterol_v;}

    //public String getPM_Pulse_d() {return this.PM_Pulse_d;}
    //public String getPM_Pulse_v() {return this.PM_Pulse_v;}

    public String getMs_Medication_d() {return this.Ms_Medication_d;}
    public String getMs_Medication_c() {return this.Ms_Medication_c;}
    public String getMs_Medication_v() {return this.Ms_Medication_v;}
    public String getMH_Date(){return this.MH_Date;}
    public String getMH_Hospital(){return this.MH_Hospital;}
    public String getMH_Dept(){return this.MH_Dept;}
    public String getMH_S_CD(){return this.MH_S_CD;}
    public String getMH_S_NM(){return this.MH_S_NM;}
    //public String getTeleMedicine_d() {return this.TeleMedicine_d;}
    //public String getTeleMedicine_v() {return this.TeleMedicine_v;}
    //public String getPatientComment() {return this.PatientComment;}

}