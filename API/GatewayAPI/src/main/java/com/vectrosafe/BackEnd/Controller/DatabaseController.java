package com.vectrosafe.BackEnd.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vectrosafe.BackEnd.Model.*;
import com.vectrosafe.BackEnd.RabbitMQ.BackEndSend;
import com.vectrosafe.BackEnd.Util.Encrypt;
import com.vectrosafe.BackEnd.Util.JwtUtil;
import okhttp3.*;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatabaseController {
    private final BackEndSend send = new BackEndSend();
    OkHttpClient client;
    public DatabaseController(){
        okhttpconnect();
    }
    public void okhttpconnect(){
        client = new OkHttpClient().newBuilder()
                .connectTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .callTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }
    public void addUser(String userString) throws IOException, InterruptedException {
        //okhttpconnect();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, userString);
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/nasabah/add")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        String sendMessage=jsonData;
        if (!jsonData.equals("Error")) {
            Auth auth = new Gson().fromJson(jsonData,Auth.class);
            System.out.println(jsonData);
            ApiResponse regisResponse = response("Register Success",200,auth);
            String strResponse = new Gson().toJson(regisResponse);
            sendMessage = strResponse;
        }
        response.body().close();
        send.response_RegisUser(sendMessage);
    }

    public ApiResponse response(String message, int status, Object object){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(message);
        apiResponse.setStatus(status);
        apiResponse.setData(object);
        return apiResponse;
    }

    public void login (String userlogin) throws IOException, InterruptedException {
        System.out.println("login json :"+userlogin);
        Auth authLogin = new Gson().fromJson(userlogin, Auth.class);
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/username/"+authLogin.getUsername())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response3 = client.newCall(request).execute();
        while (response3==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response3.body().string();
        String sendMessage = jsonData;
        response3.body().close();
        if (!jsonData.equals("Error")){
            Auth authdb = new Gson().fromJson(jsonData, Auth.class);
            try {
                authdb.setPassword(new Encrypt().decrypt(authdb.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (authdb.getUsername().equals(authLogin.getUsername())
                    && authdb.getPassword().equals(authLogin.getPassword())){
                String token = new JwtUtil().generateToken(authdb);
                LoginResponse respObject = new LoginResponse();
                respObject.setToken(token);
                respObject.setMessage("Login Success");
                respObject.setStatus("200");
                respObject.setData(authdb);
                String strRespObj = new Gson().toJson(respObject);
                sendMessage = strRespObj;
            }
        }
        send.response_loginNasabah(sendMessage);
    }


    public void addTransaksi(String trxString) throws IOException, InterruptedException {
        //okhttpconnect();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, trxString);
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/trx/add")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        String sendMessage = jsonData;
        if (!jsonData.equals("Error")) {
            Transaksi trx = new Gson().fromJson(jsonData,Transaksi.class);
            System.out.println(jsonData);
            ApiResponse objResponse = response("Transaction Success",200,trx);
            String strResponse = new Gson().toJson(objResponse);
            sendMessage = strResponse;
        }
        response.body().close();
        send.response_addTrx(sendMessage);
    }

    public void getTransaksi(String trxString) throws IOException, InterruptedException {
        //okhttpconnect();
        String[] trx = trxString.split(" ");
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/getTrx/"+trx[0]+"-from"+trx[1]+"-to"+trx[2])
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        String sendMessage = jsonData;
        System.out.println(jsonData);
        if (!jsonData.equals("Error")) {
            List<?> trxList = new Gson().fromJson(jsonData, new TypeToken<List<Transaksi>>(){}.getType());
            System.out.println(jsonData);
            ApiResponse objResponse = response("Get Data",200,trxList);
            String strResponse = new Gson().toJson(objResponse);
            sendMessage = strResponse;
        }
        response.body().close();
        send.response_getTrx(sendMessage);
    }

    public void buyVoucher(String trxString) throws IOException, InterruptedException {
        Order order = new Gson().fromJson(trxString, Order.class);
        System.out.println(order.getOperator());
        System.out.println(order.getId_produk());
        //okhttpconnect();
        Request request = new Request.Builder()
                .url("http://localhost:9027/operatorpulsa/"+order.getOperator()+"/"+order.getId_produk())
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        response.body().close();
        String sendMessage = jsonData;
        if (!jsonData.equals("Error")) {
            System.out.println("json != Error : "+jsonData);
            Voucher voucher= new Gson().fromJson(jsonData, Voucher.class);
            System.out.println(voucher.getHarga_produk());
            String trx = "{\n" +
                    "    \"id_nasabah\" : "+order.getId_nasabah()+" ,\n" +
                    "    \"tipe\" : \"Kredit\",\n" +
                    "    \"mutasi\" : "+voucher.getHarga_produk()+",\n" +
                    "    \"keterangan\" : \"pembelian voucher pulsa "+order.getOperator()+"\"\n" +
                    "}";
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, trx);
            Request request2 = new Request.Builder()
                    .url("http://localhost:9090/api/trx/add")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response2 = client.newCall(request2).execute();
            while (response2==null){
                TimeUnit.MILLISECONDS.sleep(10);
            }

            String strResp = response2.body().string();
            Transaksi objtrx = new Gson().fromJson(strResp,Transaksi.class);
            ApiResponse objResponse = response("Buy Voucher Success",200,objtrx);
            String strResponse = new Gson().toJson(objResponse);
            response2.body().close();
            String trx3 = "{\"id_produk\" : "+order.getId_produk()+" ,\"no_hp\" : \""+order.getNo_hp()+"\" }";
            MediaType mediaType3 = MediaType.parse("application/json");
            RequestBody body3 = RequestBody.create(mediaType3, trx3 );
            Request request3 = new Request.Builder()
                    .url("http://localhost:9027/operatorpulsa/"+order.getOperator()+"/isipulsa")
                    .method("POST", body3)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response3 = client.newCall(request3).execute();
            while (response3==null){
                TimeUnit.MILLISECONDS.sleep(10);
            }
            response3.body().close();
            sendMessage = strResponse;
            System.out.println(sendMessage);
        }
        send.response_buyVoucher(sendMessage);
    }



    public void getAuth(String userString) throws IOException, InterruptedException {
        //okhttpconnect();
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/auth/"+userString)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        String sendMessage = jsonData;
        if (!jsonData.equals("Error")){
            Auth auth = new Gson().fromJson(jsonData,Auth.class);
            System.out.println(jsonData);
            ApiResponse objResponse = response("Get Data Success",200,auth);
            String strResponse = new Gson().toJson(objResponse);
            sendMessage = strResponse;
        }
        response.body().close();
        send.response_getAuth(sendMessage);
    }

    public void getXL() throws IOException, InterruptedException {
        //okhttpconnect();
        Request request = new Request.Builder()
                .url("http://localhost:9027/operatorpulsa/xl")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        List<?> voucher = new Gson().fromJson(jsonData, new TypeToken<List<Voucher>>(){}.getType());
        System.out.println(jsonData);
        ApiResponses objResponse = responses("Get Data Success",200,voucher);
        String strResponse = new Gson().toJson(objResponse);
        send.response_getXL(strResponse);
        response.body().close();
    }
    public ApiResponses responses(String message, int status, List<?> object){
        ApiResponses apiResponse = new ApiResponses();
        apiResponse.setMessage(message);
        apiResponse.setStatus(status);
        apiResponse.setData(object);
        return apiResponse;
    }

    public void getTelkomsel() throws IOException, InterruptedException {
        //okhttpconnect();
        Request request = new Request.Builder()
                .url("http://localhost:9027/operatorpulsa/telkomsel")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        List<?> voucher = new Gson().fromJson(jsonData, new TypeToken<List<Voucher>>(){}.getType());
        System.out.println(jsonData);
        ApiResponses objResponse = responses("Get Data Success",200,voucher);
        String strResponse = new Gson().toJson(objResponse);
        send.response_getTelkomsel(strResponse);
        response.body().close();
    }

    public void getNasabah(String userString) throws IOException, InterruptedException {
        //okhttpconnect();
        Request request = new Request.Builder()
                .url("http://localhost:9090/api/nasabah/"+userString)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        while (response==null) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
        String jsonData = response.body().string();
        String sendMessage = jsonData;
        if (!jsonData.equals("Error")){
            Nasabah nasabah = new Gson().fromJson(jsonData,Nasabah.class);
            System.out.println(jsonData);
            ApiResponse objResponse = response("Get Data Success",200,nasabah);
            String strResponse = new Gson().toJson(objResponse);
            sendMessage = strResponse;
        }
        response.body().close();
        send.response_getNasabah(sendMessage);
    }

}
