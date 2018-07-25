package kr.zchat;

import io.vertx.core.Vertx;
import kr.zchat.core.module.service.VertxServerVerticle;

public class VertxServer {
	
	public static void main(String[] args) {
		Vertx.vertx().deployVerticle(new VertxServerVerticle());
	}
	
}
