package com.example.hello.appnav.Map;

import android.content.Context;

import com.example.hello.appnav.R;

import java.util.HashMap;
import java.util.Map;


public class TranslationMap {
    private final Map<String, String> map = new HashMap<>();

    public TranslationMap(Context context) {
        //Nikol Pashinyan//
        map.put("mard_a_vatace", context.getString(R.string.btn0));
        map.put("entexela_mard_vatcel", context.getString(R.string.btn1));
        map.put("hangist_nsteq", context.getString(R.string.btn2));
        map.put("hangstaceq", context.getString(R.string.btn3));
        map.put("hangstaci", context.getString(R.string.btn4));
        map.put("kargi_hravireq", context.getString(R.string.btn5));
        map.put("kastm", context.getString(R.string.btn6));
        map.put("kaychayt", context.getString(R.string.btn7));
        map.put("ashotyan", context.getString(R.string.btn8));
        map.put("shulux", context.getString(R.string.btn9));
        map.put("ste_bazar", context.getString(R.string.btn10));
        map.put("turqy_lsi", context.getString(R.string.btn11));
        map.put("valeryanka", context.getString(R.string.btn12));
        map.put("voti_tak", context.getString(R.string.btn13));
        map.put("ba_sra_hamar", context.getString(R.string.btn14));
        map.put("bussiness", context.getString(R.string.btn15));
        map.put("et_apranqy", context.getString(R.string.btn16));
        map.put("kartofil_berel", context.getString(R.string.btn17));
        map.put("hitsunkg", context.getString(R.string.btn18));
        map.put("mankavarjy", context.getString(R.string.btn19));
        map.put("orinak_pereci", context.getString(R.string.btn20));
        map.put("hazardramov", context.getString(R.string.btn21));
        map.put("kam2000", context.getString(R.string.btn22));
        map.put("kam3000", context.getString(R.string.btn23));
        map.put("plyusminus", context.getString(R.string.btn24));
        map.put("populyar", context.getString(R.string.btn25));
        map.put("problemy_inchumne", context.getString(R.string.btn26));
        map.put("qsanhinghazar", context.getString(R.string.btn27));
        map.put("tatiky", context.getString(R.string.btn28));
        map.put("usanox_jamanak", context.getString(R.string.btn29));
        map.put("uzum_enq", context.getString(R.string.btn30));
        map.put("xndirnery_chen_karox", context.getString(R.string.btn31));
        map.put("hh_hpart", context.getString(R.string.btn32));
        map.put("a_hpart", context.getString(R.string.btn33));
        map.put("spyurqi_hpart", context.getString(R.string.btn34));
        map.put("yot_or", context.getString(R.string.btn35));
        map.put("ashot", context.getString(R.string.btn36));
        map.put("arji", context.getString(R.string.btn37));
        map.put("ev_uremn", context.getString(R.string.btn38));
        map.put("duq_dzez", context.getString(R.string.btn39));
        map.put("parkecnel_asfaltnrin", context.getString(R.string.btn40));
        map.put("barapashari_insult", context.getString(R.string.btn41));
        map.put("bavarar_chen_stana", context.getString(R.string.btn42));
        map.put("cymp", context.getString(R.string.btn43));
        map.put("car_nenasayashiy", context.getString(R.string.btn44));
        map.put("andamaluyts", context.getString(R.string.btn45));
        map.put("varky", context.getString(R.string.btn46));
        map.put("defakto", context.getString(R.string.btn47));
        map.put("deure", context.getString(R.string.btn48));

        //Maestro//
        map.put("hingharurhazar_0", context.getString(R.string.m_btn100));
        map.put("hnaguyn_gorcich_0", context.getString(R.string.m_btn101));
        map.put("shrjapaken_0", context.getString(R.string.m_btn102));
        map.put("hamar_0", context.getString(R.string.m_btn103));
        map.put("tesakety_0", context.getString(R.string.m_btn104));
        map.put("chshmartutyuny_0", context.getString(R.string.m_btn105));
        map.put("anchap_0", context.getString(R.string.m_btn106));
        map.put("shat_lavnenq_0", context.getString(R.string.m_btn107));
        map.put("merkutyuny_0", context.getString(R.string.m_btn108));
        map.put("haytni_dardzan_0", context.getString(R.string.m_btn109));
        map.put("hraparakum_0", context.getString(R.string.m_btn110));
        map.put("anhatakan_chem_0", context.getString(R.string.m_btn111));
        map.put("hambereq_0", context.getString(R.string.m_btn112));
        map.put("eg_ev_vorc_0", context.getString(R.string.m_btn113));
        map.put("anhetetutyunner_0", context.getString(R.string.m_btn114));
        map.put("mardu_uxexy_0", context.getString(R.string.m_btn115));
        map.put("mer_tery_0", context.getString(R.string.m_btn116));
        map.put("qaj_nazary_0", context.getString(R.string.m_btn117));
        map.put("aprelu_iravunq_0", context.getString(R.string.m_btn118));
        map.put("voch_eakan_0", context.getString(R.string.m_btn119));
        map.put("dzer_imacac_goxutyuny_0", context.getString(R.string.m_btn120));
        map.put("goxanal_bary_0", context.getString(R.string.m_btn121));
        map.put("poxharaberven_0", context.getString(R.string.m_btn122));
        map.put("cmah_0", context.getString(R.string.m_btn123));
        map.put("gaxtni_bary_0", context.getString(R.string.m_btn124));
        map.put("erexanery_0", context.getString(R.string.m_btn125));

    }

    public String getButtonName(String soundName) {
        return  map.get(soundName);
    }


}
