package com.bismih.server_chat_app.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ZamanBilgisi {
    public static void main(String[] args) {
        // Şu anki tarih ve saat bilgisini al
        LocalDateTime simdikiZaman = LocalDateTime.now();

        // Biçimlendirme için DateTimeFormatter oluştur
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

        // LocalDateTime'i istediğiniz formatta stringe dönüştür
        String zamanBilgisi = simdikiZaman.format(formatter);

        // Elde edilen string zaman bilgisini yazdır
        System.out.println("Zaman bilgisi: " + zamanBilgisi);
    }
}
