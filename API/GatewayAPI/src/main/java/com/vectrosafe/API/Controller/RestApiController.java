package com.vectrosafe.API.Controller;

import com.google.gson.Gson;

import com.vectrosafe.BackEnd.Model.*;
import com.vectrosafe.API.RabbitMQ.*;
import com.vectrosafe.BackEnd.Util.JwtUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/android")
public class RestApiController {

    public final RestApiRecv restApiReceive = new RestApiRecv();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getRoot(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // -------------------Get an User-------------------------------------------

    @RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") String id, @RequestHeader ("Authorization") String headers) throws ExecutionException, InterruptedException {
        ApiResponse objResponse = new ApiResponse();
        String response = "";
        if (headers!=null){
            String[] header = headers.split(" ");
            System.out.println(header[1]);
            if (verifyJWT(header[1])){
                RestApiSend.getAuth(id);
                response = restApiReceive.connectRabbitMQ("android_response_getAuth");
                if (!response.equals("Error")){
                    ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                } else {
                    objResponse.setStatus(404);
                    objResponse.setMessage("Get Data Fail");
                    return new ResponseEntity<>(objResponse,HttpStatus.OK);
                }
            } else {
                objResponse.setStatus(401);
                objResponse.setMessage("Unauthorized");
                return new ResponseEntity<>(objResponse,HttpStatus.OK);
            }
        } else{
            objResponse.setStatus(400);
            objResponse.setMessage("Bad Request");
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }



    }

    // -------------------add Trx-------------------------------------------

    @RequestMapping(value = "/trx/add", method = RequestMethod.POST)
    public ResponseEntity<?> addTrx(@RequestBody Object user,@RequestHeader ("Authorization") String headers) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        if (headers!=null){
            String[] header = headers.split(" ");
            if (verifyJWT(header[1])){
                RestApiSend.addTrx(new Gson().toJson(user));
                response = restApiReceive.connectRabbitMQ("android_response_addTrx");
                System.out.print("Loading Add Trx");
                if(!response.equals("Error")){
                    System.out.println(response);
                    ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                }else {
                    objResponse.setStatus(404);
                    objResponse.setMessage("Not Found");
                    return new ResponseEntity<>(objResponse,HttpStatus.OK);
                }
            }else {
                objResponse.setStatus(401);
                objResponse.setMessage("Unauthorized");
                return new ResponseEntity<>(objResponse,HttpStatus.OK);
            }
        }else{
            objResponse.setStatus(400);
            objResponse.setMessage("Bad Request");
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/trx/{id}-from{fromdate}-to{todate}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrx(@PathVariable("id") Long id,
                                    @PathVariable("fromdate") @DateTimeFormat(pattern = "yyyy-MM-dd") String fromDate,
                                    @PathVariable("todate") @DateTimeFormat(pattern = "yyyy-MM-dd") String toDate,
                                    @RequestHeader ("Authorization") String headers) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        System.out.println("masuk get trx");
        if (headers!=null){
            String[] header = headers.split(" ");
            if (verifyJWT(header[1])){
                System.out.println("masuk if jwt verify");
                String trx = ""+id+" "+fromDate+" "+toDate;
                RestApiSend.getTrx(trx);
                response = restApiReceive.connectRabbitMQ("android_response_getTrx");
                if(!response.equals("Error")){
                    System.out.println("masuk if !error");
                    System.out.println(response);

                    ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                }else {
                    objResponse.setStatus(404);
                    objResponse.setMessage("Not Found");
                    //objResponse.setData(new Auth());
                    return new ResponseEntity<>(objResponse,HttpStatus.OK);
                }
            }else {
                objResponse.setStatus(401);
                objResponse.setMessage("Unauthorized");
                //objResponse.setData(new Auth());
                return new ResponseEntity<>(objResponse,HttpStatus.OK);
            }
        }else{
            objResponse.setStatus(400);
            objResponse.setMessage("Bad Request");
            //objResponse.setData(new Auth());
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }
    }

    public boolean verifyJWT(String jwts) {
        boolean verified=false;
        Auth u = new JwtUtil().parseToken(jwts);
        if (u!=null){
            verified=true;
        }
        return verified;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Object user) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        RestApiSend.addNasabah(new Gson().toJson(user));
        response = restApiReceive.connectRabbitMQ("android_response_RegisUser");
        if (!response.equals("Error")){
            ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }else{
            objResponse.setStatus(404);
            objResponse.setMessage("Not Found");
            //objResponse.setData(new Object());
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody Object user) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        RestApiSend.loginNasabah(new Gson().toJson(user));
        response = restApiReceive.connectRabbitMQ("android_response_loginNasabah");
        System.out.println("isi Response: "+response);
        if(response.equals("Error")){
            objResponse.setStatus(404);
            objResponse.setMessage("User Not Found");
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        } else {
            LoginResponse logresponse = new Gson().fromJson(response, LoginResponse.class);
            return new ResponseEntity<>(logresponse, HttpStatus.OK);

        }
    }

    @RequestMapping(value = "/nasabah/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getNasabah(@PathVariable("id") String id,@RequestHeader ("Authorization") String headers) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        if (headers!=null){
            String[] header = headers.split(" ");
            if (verifyJWT(header[1])){
                RestApiSend.getNasabah(id);
                response = restApiReceive.connectRabbitMQ("android_response_getNasabah");
                if (!response.equals("Error")){
                    System.out.println(response);
                    ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                }else {
                    objResponse.setStatus(404);
                    objResponse.setMessage("Not Found");
                    //objResponse.setData(new Nasabah());
                    return new ResponseEntity<>(objResponse,HttpStatus.OK);
                }
            } else {
                objResponse.setStatus(401);
                objResponse.setMessage("Unauthorized");
                //objResponse.setData(new Nasabah());
                return new ResponseEntity<>(objResponse,HttpStatus.OK);
            }
        } else {
            objResponse.setStatus(400);
            objResponse.setMessage("Bad Request");
            //objResponse.setData(new Nasabah());
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/getTelkomsel", method = RequestMethod.GET)
    public ResponseEntity<?> getTelkomsel() throws ExecutionException, InterruptedException {
        String response = "";
        RestApiSend.getTelkomsel();
        response = restApiReceive.connectRabbitMQ("android_response_getTelkomsel");
        if(!response.equals("Error")){
            ApiResponse apiResponses = new Gson().fromJson(response,ApiResponse.class);
            return new ResponseEntity<>(apiResponses, HttpStatus.OK);

        }else {
            ApiResponse objResponse = new ApiResponse();
            objResponse.setStatus(404);
            objResponse.setMessage("Not Found");
            //objResponse.setData(new Nasabah());
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/buy_VoucherPrabayar", method = RequestMethod.POST)
    public ResponseEntity<?> buyVoucher(@RequestBody Object buy,@RequestHeader ("Authorization") String headers) throws ExecutionException, InterruptedException {
        String response = "";
        ApiResponse objResponse = new ApiResponse();
        if (headers!=null) {
            String[] header = headers.split(" ");
            if (verifyJWT(header[1])) {
                RestApiSend.buyVoucher(new Gson().toJson(buy));
                response = restApiReceive.connectRabbitMQ("android_response_buyVoucher");
                if(!response.equals("Error")){
                    System.out.println(response);
                    ApiResponse apiResponse = new Gson().fromJson(response,ApiResponse.class);
                    return new ResponseEntity<>(apiResponse , HttpStatus.OK);
                } else {
                    //Order order = new Gson().fromJson(restApiReceive.getBuyVoucher_resp(),Order.class);
                    objResponse.setStatus(404);
                    objResponse.setMessage("Not Found");
                    //objResponse.setData(new Object());
                    return new ResponseEntity<>(objResponse,HttpStatus.OK);
                }
            } else {
                objResponse.setStatus(401);
                objResponse.setMessage("Unauthorized");
                //objResponse.setData(new Object());
                return new ResponseEntity<>(objResponse,HttpStatus.OK);
            }
        } else {
            objResponse.setStatus(400);
            objResponse.setMessage("Bad Request");
            //objResponse.setData(new Object());
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getXL", method = RequestMethod.GET)
    public ResponseEntity<?> getXL() throws ExecutionException, InterruptedException {
        String response = "";
        RestApiSend.getXL();
        response = restApiReceive.connectRabbitMQ("android_response_getXL");
        if(!response.equals("Error")){
            ApiResponse apiResponses = new Gson().fromJson(response, ApiResponse.class);
            return new ResponseEntity<>(apiResponses, HttpStatus.OK);
        }else {
            ApiResponse objResponse = new ApiResponse();
            objResponse.setStatus(404);
            objResponse.setMessage("Not Found");
            return new ResponseEntity<>(objResponse,HttpStatus.OK);
        }
    }

}