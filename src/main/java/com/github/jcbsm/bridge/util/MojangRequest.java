package com.github.jcbsm.bridge.util;

import com.github.jcbsm.bridge.exceptions.InvalidMinecraftName;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

public class MojangRequest {

    private static final Logger logger = LoggerFactory.getLogger(MojangRequest.class.getSimpleName());


    private static Map<String, Object> execute(String url) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new Gson();

        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        ResponseBody response = call.execute().body();

        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();  // TODO: beta feature apparently - find stable version
        return gson.fromJson(response.string(), mapType);

    }

    /**
     * Get the UUID to a corresponding username.
     * @param username of the minecraft account you want to query the uuid for.
     * @return UUID of the queried minecraft account.
     * @throws IOException Unexpected results - i.e. no internet.
     */
    @Nullable
    public static String usernameToUUID(String username) throws IOException {
        Map<String, Object> response = execute(String.format("https://api.mojang.com/users/profiles/minecraft/%s", username));

        if (response.containsKey("errorMessage")) { return null; }  // If error (no such player exists) return null
        return (String) response.get("id");
    }

    @Nullable
    public static String uuidToUsername(String uuid) throws IOException {
        Map<String, Object> response = execute(String.format("https://api.mojang.com/user/profile/%s", uuid));

        if (response.containsKey("errorMessage")) { return null; }
        return (String) response.get("name");
    }
}
