package com.example.board.security;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Https {

    // Refresh Token 전송
    public void sendRefreshToken(String refreshToken) {
        try {
            // HTTPS 연결
            URL url = new URL("https://example.com/refresh");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            // POST 메소드 설정
            con.setRequestMethod("POST");

            // 요청 헤더 설정
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 요청 파라미터 설정
            String params = "refresh_token=" + refreshToken;

            // 요청 파라미터 전송
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                // 응답 처리
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 응답 결과 출력
                System.out.println(response.toString());
            } else {
                // 오류 처리
                System.out.println("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
