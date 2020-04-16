package ch.heigvd.easytoolz.models.json;

import net.minidev.json.JSONObject;

public class SuccessResponse extends JSONObject {

    public SuccessResponse(String response) {
        this.put("msg", response);
    }
}
