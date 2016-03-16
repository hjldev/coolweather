package com.coolweather.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.db.dbdao.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huangjinlong on 2016/2/16.
 */
public class Utility {
    /**
     * 将返回的省信息解析
     */
    public synchronized static boolean handlerProvincesResponse (CoolWeatherDB coolWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province  = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 将返回的市信息解析
     */
    public synchronized static boolean handlerCitiesResponse (CoolWeatherDB coolWeatherDB, String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    City city  = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 将返回的县信息解析
     */
    public synchronized static boolean handlerCountiesResponse (CoolWeatherDB coolWeatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0){
                for (String p : allProvinces){
                    String[] array = p.split("\\|");
                    County county  = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 将返回的具体县的天气json信息解析
     */
    public static void handlerWeatherResponse(Context context, String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject object = jsonObject.getJSONObject("weatherinfo");
            String cityName = object.getString("city");
            String weatherCode = object.getString("cityid");
            String temp1 = object.getString("temp1");
            String temp2 = object.getString("temp2");
            String weatherDesp = object.getString("weather");
            String publishTime = object.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.apply();
    }
}
