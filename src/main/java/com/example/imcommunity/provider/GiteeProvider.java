package com.example.imcommunity.provider;

import com.example.imcommunity.dto.GithubTokenDTO;
import com.example.imcommunity.dto.GiteeTokenDTO;
import com.example.imcommunity.dto.GiteeUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Gitee验证用户相关逻辑
 */
@Component
public class GiteeProvider {

    /**
     * 获取token
     *
     * @param githubTokenDTO 包含gitee回调的code，和本应用相关数据
     * @return 字符串token
     */
    public String getAccessToken(GithubTokenDTO githubTokenDTO) {
        // 创建okhttp客户端
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 创建json映射器
        ObjectMapper mapper = new ObjectMapper();
        // 设置转换策略：驼峰下划线
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        RequestBody body = null;
        try {
            // 转换成字符串json
            String s = mapper.writeValueAsString(githubTokenDTO);
            // 设置请求体
            body = RequestBody.create(mediaType, s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assert body != null;
        // 创建post请求
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code")
                .post(body)
                .build();
        // 接收响应
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // 把json封装成对象
            GiteeTokenDTO giteeTokenDTO = mapper.readValue(string, GiteeTokenDTO.class);
            // 返回dto
            return giteeTokenDTO.getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 失败返回null
        return null;
    }

    /**
     * 获取Gitee用户信息
     *
     * @param accessToken Gitee用户的token
     * @return Gitee用户信息
     */
    public GiteeUserDTO getUser(String accessToken) {
        // 创建okhttp客户端
        OkHttpClient client = new OkHttpClient();
        // 建立请求传入accessToken
        Request request = new Request.Builder().header("Content-Type", "application/json;charset=UTF-8")
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        // 接收响应
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            // 把json封装成对象，并返回
            return mapper.readValue(json, GiteeUserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 失败返回null
        return null;
    }
}
