package com.agh.givealift.service.threads;

import com.agh.givealift.configuration.Configuration;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.response.PushNotificationResponses;
import com.google.gson.Gson;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class NotifySubscriptionService extends Thread {

    private static final COD cod = CODFactory.get();
    private Route route;

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public void run() {

        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            StringEntity postingString = new StringEntity(new Gson().toJson(route));
            HttpPost post = new HttpPost(Configuration.SUB_SERIVICE);
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
