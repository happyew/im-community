package com.example.imcommunity.provider;


import com.example.imcommunity.dto.AccessTokenDTO;
import com.example.imcommunity.dto.GithubUserDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        RequestBody body = null;
        try {
            String s = mapper.writeValueAsString(accessTokenDTO);
            body = RequestBody.create(mediaType, s);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert body != null;
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String str = response.body().string().split("&")[0].split("=")[1];
            System.out.println("token:" + str);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GithubUserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().header("Authorization", "token " + accessToken)
                .url("https://api.github.com/user")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            GithubUserDTO githubUserDTO = mapper.readValue(json, GithubUserDTO.class);
            System.out.println(githubUserDTO);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

