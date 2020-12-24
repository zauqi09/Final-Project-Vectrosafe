package com.vectrosafe.controller;

import com.vectrosafe.model.*;
import com.vectrosafe.repository.IsiPulsaTsel;
import com.vectrosafe.repository.IsiPulsaXL;
import com.vectrosafe.repository.TelkomselRepository;
import com.vectrosafe.repository.XLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/operatorpulsa")
public class Controller {

    @Autowired
    XLRepository XLRepository;

    @Autowired
    TelkomselRepository telkomselRepository;

    @Autowired
    IsiPulsaXL isiPulsaXL;

    @Autowired
    IsiPulsaTsel isiPulsaTsel;

    @GetMapping("/xl")
    public ResponseEntity<List<XL>> getXL(@RequestParam(required = false) String args){
        List<XL> object = new ArrayList<XL>();

        XLRepository.findAll().forEach(object::add);

        if(object.isEmpty()){
            return new ResponseEntity<>(object, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(object, HttpStatus.OK);
    }
    @GetMapping("/XL/{id}")
    public ResponseEntity<XL> getaXL(@PathVariable("id") Long id){
        Optional<XL> xl = XLRepository.findById(id);

        if (xl.isPresent()) {
            return new ResponseEntity<>(xl.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/Telkomsel/{id}")
    public ResponseEntity<Telkomsel> getaTelkomsel(@PathVariable("id") Long id){
        Optional<Telkomsel> telkomsel = telkomselRepository.findById(id);

        if (telkomsel.isPresent()) {
            return new ResponseEntity<>(telkomsel.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUser/{operator}-{nomor}")
    public ResponseEntity<?> getUser(@PathVariable("nomor") String nomor, @PathVariable("operator") String operator){
        if (operator.equals("Telkomsel")){
            Optional<NomorTsel> tsel = isiPulsaTsel.findByNumber(nomor);
            if (tsel.isPresent()) {
                System.out.print(tsel.get().getMasa_aktif());
                ApiResponse apiresp = new ApiResponse();
                apiresp.setData(tsel.get());
                apiresp.setMessage("Success");
                apiresp.setStatus(200);
                return new ResponseEntity<>(apiresp, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else if (operator.equals("XL")){
            Optional<NomorXL> xl = isiPulsaXL.findByNumber(nomor);
            if (xl.isPresent()) {
                System.out.print(xl.get().getMasa_aktif());
                ApiResponse apiresp = new ApiResponse();
                apiresp.setData(xl.get());
                apiresp.setMessage("Success");
                apiresp.setStatus(200);
                return new ResponseEntity<>(apiresp, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/Telkomsel/isipulsa")
    public ResponseEntity<NomorTsel> isiTelkomsel(@RequestBody IsiPulsaRequest request){
        IsiPulsaRequest isireq = request;
        Optional<NomorTsel> tsel = isiPulsaTsel.findByNumber(isireq.getNo_hp());
        Long pulsa = 0L;
        Long day = 0L;
        if (isireq.getId_produk() == 1) {
            pulsa = 10000L;
            day = 15L;
        } else if (isireq.getId_produk() == 2) {
            pulsa = 20000L;
            day = 30L;
        } else if (isireq.getId_produk() == 3) {
            pulsa = 30000L;
            day = 45L;
        } else if (isireq.getId_produk() == 4) {
            pulsa = 50000L;
            day = 60L;
        } else if (isireq.getId_produk() == 5) {
            pulsa = 100000L;
            day = 75L;
        } else if (isireq.getId_produk() == 6) {
            pulsa = 200000L;
            day = 90L;
        }

        Long time = tsel.get().getMasa_aktif().getTime() + (day * 1000 * 60 * 60 * 24);
        if (tsel.isPresent()) {
            NomorTsel _tsel = tsel.get();
            _tsel.setPulsa(tsel.get().getPulsa() + pulsa);
            _tsel.setMasa_aktif(new Date(time));
            return new ResponseEntity<>(isiPulsaTsel.save(_tsel), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/XL/isipulsa")
    public ResponseEntity<NomorXL> isiXL(@RequestBody IsiPulsaRequest request){
        IsiPulsaRequest isireq = request;
        Optional<NomorXL> xl = isiPulsaXL.findByNumber(isireq.getNo_hp());
        Long pulsa = 0L;
        Long day = 0L;
        if (isireq.getId_produk() == 1) {
            pulsa = 10000L;
            day = 15L;
        } else if (isireq.getId_produk() == 2) {
            pulsa = 20000L;
            day = 30L;
        } else if (isireq.getId_produk() == 3) {
            pulsa = 30000L;
            day = 45L;
        } else if (isireq.getId_produk() == 4) {
            pulsa = 50000L;
            day = 60L;
        } else if (isireq.getId_produk() == 5) {
            pulsa = 100000L;
            day = 75L;
        } else if (isireq.getId_produk() == 6) {
            pulsa = 200000L;
            day = 90L;
        }

        Long time = xl.get().getMasa_aktif().getTime() + (day * 1000 * 60 * 60 * 24);
        if (xl.isPresent()) {
            NomorXL _xl = xl.get();
            _xl.setPulsa(xl.get().getPulsa() + pulsa);
            _xl.setMasa_aktif(new Date(time));
            return new ResponseEntity<>(isiPulsaXL.save(_xl), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/telkomsel")
    public ResponseEntity<List<Telkomsel>> getTelkomsel(@RequestParam(required = false) String args){
        List<Telkomsel> object = new ArrayList<Telkomsel>();

        telkomselRepository.findAll().forEach(object::add);

        if(object.isEmpty()){
            return new ResponseEntity<>(object, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(object, HttpStatus.OK);
    }

}
