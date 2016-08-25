package services;

import org.json.JSONArray;

import play.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

public class RedisService {

	private static final Pool<Jedis> jedisPool;
	
	static{
		jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
	}
	
	public static void createChannel(String nickname, String hashes) throws Exception{
		Jedis jedisConnector = null;
		try{
			jedisConnector = jedisPool.getResource();
			String[] splitHashes = hashes.split(" ");
			for (String hash : splitHashes) {
				jedisConnector.sadd(hash, nickname);
			}
			
		}finally{
			jedisPool.returnResource(jedisConnector);
		}
			
		
	}

	public static JSONArray getDots(String nickname) {
		JSONArray dots = new JSONArray();
		Jedis jedisConnector = null;
		try{
			jedisConnector = jedisPool.getResource();
			for (int i = 0; i < 200; i++) {
				String msg = jedisConnector.rpop("user:"+nickname);
				if (msg != null){
					dots.put(msg);
					
				}
			}
			
		}catch(Exception e){
			Logger.error("Problem getting from redis: ",e);
		}finally{
			jedisPool.returnResource(jedisConnector);
		}
		return dots;
		
	}
}
