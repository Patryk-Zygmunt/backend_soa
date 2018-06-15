package com.agh.givealift.service.threads;

import com.agh.givealift.configuration.Configuration;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.agh.givealift.model.response.RouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class NotifySubscriptionService extends Thread {

    private static final COD cod = CODFactory.get();
    private RouteResponse route;

    public void setRoute(RouteResponse route) {
        this.route = route;
    }

    @Override
    public void run() {

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            Gson gson = new GsonBuilder()
   .setDateFormat("yyyy-MM-dd HH:mm").create();
            ObjectMapper objectMapper = new ObjectMapper();
            
          //  StringEntity postingString = new StringEntity(gson.toJson(route));
            StringEntity postingString = new StringEntity(objectMapper.writeValueAsString(route), "UTF-8");
            HttpPost post = new HttpPost(Configuration.SUB_SERIVICE);
          //  postingString.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
             postingString.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            
       //     post.setHeader("Authorization", "key=" + route);
            HttpResponse response = httpClient.execute(post);
            cod.c().addShowToString(Enum.class).i("Service3 >> ", route, response.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
