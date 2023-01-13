/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.Belajar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
public class sayuranController {

    //menginisialisasi kelas Sayuran
    Sayuran sayur = new Sayuran();
    //menginisialisasi Jpa Controller
    SayuranJpaController jpa = new SayuranJpaController();

    //Menentukan Url permintaan untuk mengakses REST endpoint pada method GET
    @GetMapping(value = "/GET", produces = APPLICATION_JSON_VALUE)
    //membuat method untuk menampilkan data
    public List<Sayuran> showData() {
        List<Sayuran> buffer = new ArrayList<>();//Declare new List Variable
        buffer = jpa.findSayuranEntities();//menginisialisasikan buffer menjadi variabel untuk menampilkan semua data
        return buffer; //show data
    }

    //Menentukan Url permintaan untuk mengakses REST endpoint pada method POST
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)
    //method untuk menambahkan data
    public String tambahData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {

        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String Respon = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        sayur = maper.readValue(datasend.getBody(), Sayuran.class);
        try {
            jpa.create(sayur);
            Respon = sayur.getNamaSayur() + " Berhasil Disimpan";
        } catch (Exception error) {
            Respon = error.getMessage();
        }
        return Respon;
    }

    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String ubahData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {
        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String Respon = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        sayur = maper.readValue(datasend.getBody(), Sayuran.class);
        try {
            jpa.edit(sayur);
            Respon = sayur.getNamaSayur() + " Berhasil Diedit";
        } catch (Exception error) {
            Respon = error.getMessage();
        }
        return Respon;
    }

    //Menentukan Url permintaan untuk mengakses REST endpoint pada method DELETE
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    //method untuk menghapus data 
    public String delData(HttpEntity<String> datasend) throws JsonProcessingException, Exception {
        //membuat variabel sebagai keluaran ketika data tidak berhasil disimpan
        String feedback = "Tidak Ada Perubahan";

        ObjectMapper maper = new ObjectMapper();
        //readValue()untuk menerima bentuk input lain
        sayur = maper.readValue(datasend.getBody(), Sayuran.class);
        try {
            jpa.destroy(sayur.getId()); //delete data berdasarkan id 
            //menampilkan keluaran ketika data berhasil di deleted
            feedback = "Berhasil di-Deleted";
        } catch (Exception error) {
            //menampilkan keluaran pesan error jika data tidak berhasil ditambahkan
            feedback = error.getMessage();
        }

        //menampilkan keluaran dari variabel feedback (tergantung berhasil atau eror)
        return feedback;

    }

}
