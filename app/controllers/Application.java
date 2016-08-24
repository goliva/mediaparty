package controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import services.RedisService;
import views.html.index;
import views.html.map;

public class Application extends Controller {

	
	
    public static Result index() {
        return ok(index.render());
    }

    public static Result map(){
    	try{
    		DynamicForm dynamicForm = Form.form().bindFromRequest();
            String nickname = dynamicForm.get("nickname");
            String hashes = dynamicForm.get("hashes");
            RedisService.createChannel(nickname, hashes);
            return ok(map.render(nickname,hashes));
    	}catch (Exception e){
			Logger.error("Some issue pushing to redis: ",e);
			return internalServerError("Ups!");
		}
    	
    }
    
    public static Result getTweets(String nickname){
    	JSONArray dots = RedisService.getDots(nickname);
    	JSONObject response = new JSONObject();
    	response.put("dots", dots);
    	return ok(response.toString());
    }
    
}
