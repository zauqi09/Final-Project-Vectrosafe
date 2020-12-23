package com.vectrosafe.API.Controller;

import com.google.gson.Gson;
import com.vectrosafe.API.RabbitMQ.RestApiRecv;
import com.vectrosafe.API.RabbitMQ.RestApiSend;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class RestApiController {
    public final RestApiRecv restApiReceive = new RestApiRecv();

    // -------------------Get an User-------------------------------------------

    @RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        RestApiSend.getAuth(id);
        String response = restApiReceive.connectRabbitMQ("response_getAuth");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------add Trx-------------------------------------------

    @RequestMapping(value = "/trx/add", method = RequestMethod.POST)
    public ResponseEntity<?> addTrx(@RequestBody Object user) throws ExecutionException, InterruptedException {
        RestApiSend.addTrx(new Gson().toJson(user));
        String response = restApiReceive.connectRabbitMQ("response_addTrx");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // -------------------get Trx-------------------------------------------

    @RequestMapping(value = "/getTrx/{id}-from{fromdate}-to{todate}", method = RequestMethod.GET)
    public ResponseEntity<?> getTrx(@PathVariable("id") Long id, @PathVariable("fromdate") @DateTimeFormat(pattern = "yyyy-MM-dd") String fromDate, @PathVariable("todate") @DateTimeFormat(pattern = "yyyy-MM-dd") String toDate) throws ExecutionException, InterruptedException {
        String strTrx = "{\"id_nasabah\" : "+id+",\"fromDate\" : \""+fromDate+"\", \"toDate\" : \""+toDate+"\"}";
        RestApiSend.getTrx(strTrx);
        String response = restApiReceive.connectRabbitMQ("response_getTrx");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserbyUsername(@PathVariable("username") String username) throws ExecutionException, InterruptedException {
        RestApiSend.getAuthByUsername(username);
        String response = restApiReceive.connectRabbitMQ("response_getAuthByUsername");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/nasabah/add", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Object user) throws ExecutionException, InterruptedException {
        RestApiSend.addNasabah(new Gson().toJson(user));
        String response = restApiReceive.connectRabbitMQ("response_RegisUser");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/nasabah/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getNasabah(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        RestApiSend.getNasabah(id);
        String response = restApiReceive.connectRabbitMQ("response_getNasabah");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}