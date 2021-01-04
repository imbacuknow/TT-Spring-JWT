package com.imbac.jwt.controller;

import com.imbac.jwt.pojo.AI_POJO;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AI_Reserve {

     @RequestMapping(value = "/reserve", method = RequestMethod.POST)
     String postReserve(@RequestHeader("Authorization") String Authorization,
               @CookieValue(value = "JSESSIONID") String ck, @RequestBody AI_POJO body) throws UnirestException {

          // System.out.println("Authorization: " + Authorization);
          // System.out.println("\nCookie: " + ck);

          JSONObject jo_body = new JSONObject();
          jo_body.put("date", body.getDate());
          jo_body.put("com_name_th", body.getCom_name_th());
          jo_body.put("com_name_en", body.getCom_name_en());
          jo_body.put("type_process", body.getType_process());
          jo_body.put("type_juristic", body.getType_juristic());
          jo_body.put("user_name", body.getUser_name());
          jo_body.put("check_ai", body.getCheck_ai());
          jo_body.put("company_id", body.getCompany_id());
          jo_body.put("id_code", body.getId_code());

          // JSONObject jo = new JSONObject();
          // jo.put("result", jo_body);

          HttpResponse<String> response = Unirest.post("http://localhost:9200/jwt/json")
                    .header("Authorization", Authorization).header("Content-Type", "application/json")
                    .header("Cookie", ck).body(jo_body.toString()).asString();

          return jo_body.toString();
          // return "Successful";
     }
}
