package com.imbac.jwt.pojo;

import java.util.Date;

public class AI_POJO {
     private Date date;
     private String com_name_th;
     private String com_name_en;
     private String type_process;
     private String type_juristic;
     private String user_name;
     private String check_ai;
     private String company_id;
     private String id_code;

     public Date getDate() {
          return this.date;
     }

     public void setDate(String str_date) {
          this.date = new Date(str_date);
     }

     public String getCom_name_th() {
          return this.com_name_th;
     }

     public void setCom_name_th(String com_name_th) {
          this.com_name_th = com_name_th;
     }

     public String getCom_name_en() {
          return this.com_name_en;
     }

     public void setCom_name_en(String com_name_en) {
          this.com_name_en = com_name_en;
     }

     public String getType_process() {
          return this.type_process;
     }

     public void setType_process(String type_process) {
          this.type_process = type_process;
     }

     public String getType_juristic() {
          return this.type_juristic;
     }

     public void setType_juristic(String type_juristic) {
          this.type_juristic = type_juristic;
     }

     public String getUser_name() {
          return this.user_name;
     }

     public void setUser_name(String user_name) {
          this.user_name = user_name;
     }

     public String getCheck_ai() {
          return this.check_ai;
     }

     public void setCheck_ai(String check_ai) {
          this.check_ai = check_ai;
     }

     public String getCompany_id() {
          return this.company_id;
     }

     public void setCompany_id(String company_id) {
          this.company_id = company_id;
     }

     public String getId_code() {
          return this.id_code;
     }

     public void setId_code(String id_code) {
          this.id_code = id_code;
     }

}
