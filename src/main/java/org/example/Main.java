package org.example;

import org.example.Model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {

        //вывод юзеров
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);

        //получение JSESSIONID
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String cookies = forEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(cookies);

        //создание нового пользователя
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpHeaders headersPost = new HttpHeaders();
        headersPost.setContentType(MediaType.APPLICATION_JSON);
        headersPost.set("Cookie", cookies);
        HttpEntity<User> entityPost = new HttpEntity<>(newUser, headersPost);
        ResponseEntity<String> responseEntityPost = restTemplate.exchange(url, HttpMethod.POST, entityPost, String.class);
        System.out.println(responseEntityPost.getBody());

        //обновление пользователя
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        HttpEntity<User> entityPut = new HttpEntity<>(newUser, headersPost);
        ResponseEntity<String> responseEntityPut = restTemplate.exchange(url, HttpMethod.PUT, entityPut, String.class);
        System.out.println(responseEntityPut.getBody());

        //удаление пользователя
        String urlDelete = "http://94.198.50.185:7081/api/users/3";
        ResponseEntity<String> responseDelete = restTemplate.exchange(urlDelete, HttpMethod.DELETE, entityPut, String.class);
        System.out.println(responseDelete.getBody());
    }
}