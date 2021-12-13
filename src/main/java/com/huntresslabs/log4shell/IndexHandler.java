package com.huntresslabs.log4shell;

import java.util.UUID;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class IndexHandler implements HttpHandler {

    private String stub_uuid;
    private String url;
    private String indexHTML;

    public IndexHandler(String url, String stub_uuid) {
        this.stub_uuid = stub_uuid;
        this.url = url;
        this.indexHTML = App.readResource("index.html");
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {

        // Connect to redis cache
//        StatefulRedisConnection<String, String> conn = redis.connect();
//        RedisCommands<String, String> commands = conn.sync();

        // Generate a random UUID
        String uuid = this.stub_uuid;

        // Store the GUID
//        commands.lpush(uuid, "exists");

        String response = indexHTML.replace("GUID", uuid);
        response = response.replace("PAYLOAD", "${jndi:"+this.url+"/"+uuid+"}");

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        exchange.getResponseSender().send(response.toString());

//        conn.close();
    }
}
