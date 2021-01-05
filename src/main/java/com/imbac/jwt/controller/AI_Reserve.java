package com.imbac.jwt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.imbac.jwt.pojo.AI_POJO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AI_Reserve {
     @Value("${api.ai.search.reserve}")
     private String url;

     // @Value("${str.json}")
     // private String json;

     @RequestMapping(value = "/reserve", method = RequestMethod.POST)
     public void postReserve(@RequestHeader("Authorization") String Authorization) throws UnirestException {
          String date = doFormatDate();
          String[] arr_auth = Authorization.split(" ", 0);

          String str_json = "{\"date\":\"17/12/2563\",\"com_name_th\":\"\u0e2d\u0e2d\u0e22 \u0e40\u0e17\u0e2a\",\"com_name_en\":\"OIL TEST\",\"type_process\":\"1\",\"type_juristic\":\"2\",\"user_name\":\"\u0e40\u0e01\u0e23\u0e35\u0e22\u0e07\u0e44\u0e01\u0e23\",\"check_ai\":\"1\",\"company_id\":\"1112630000002\",\"id_code\":\"1315815613521\"}";
          JSONObject jo = new JSONObject(str_json);

          String str_post = post(url, jo.toString()); // call ai
          JSONObject jo_str_post = new JSONObject(str_post);
          JSONObject jo_result = jo_str_post.getJSONObject("result");

          JSONObject new_jo = new JSONObject();
          new_jo.put("token", arr_auth[1]);
          new_jo.put("@datetime", date);
          new_jo.put("prior_suggest", jo_result.getString("prior_suggest"));
          new_jo.put("officer", jo_result.getString("officer"));
          new_jo.put("condition", jo_result.getString("condition"));
          new_jo.put("correspond_reason", jo_result.getString("correspond_reason"));
          new_jo.put("reserve", jo_result.getString("reserve"));

          HttpResponse<String> response = Unirest.post("http://localhost:9200/jwt/json")
                    .header("Content-Type", "application/json").body(new_jo.toString()).asString();
     }

     // call AI
     public String post(String url, String json) {
          String data = "";
          try {
               HttpResponse<String> response = Unirest.post(url).header("content-type", "application/json")
                         .header("cache-control", "no-cache")
                         .header("postman-token", "1b2d3184-9578-56db-f083-2968e2b805b4").body(json).asString();
               data = response.getBody();
          } catch (Exception e) {
               data = e.getMessage();
          }
          return data;
     }

     private String doFormatDate() {
          Date date = new Date();
          String pattern = "yyyy-MM-dd HH:mm:ss";
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
          String str_date = simpleDateFormat.format(date);
          String[] arr_str_date = str_date.split(" ", 0);
          str_date = arr_str_date[0] + "T" + arr_str_date[1];
          return str_date;
     }
}
