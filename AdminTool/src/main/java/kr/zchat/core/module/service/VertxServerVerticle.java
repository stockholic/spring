package kr.zchat.core.module.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

@Component
public class VertxServerVerticle extends AbstractVerticle {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	SystemInfo systemInfo;

	@Override
	public void start() throws Exception {
		
		super.start();

		vertx.createHttpServer().requestHandler(router()::accept).listen(5892);
		
		systemInfo = new SystemInfo();
		systemInfo();
	}
	
	private Router router() {
		
		Router router = Router.router(vertx);
		setRouter(router, "sytem", "systemInfo");

		// Serve the static resources
		router.route().handler(StaticHandler.create());
		
		return router;

	}
	
	private void setRouter(Router router, String path, String address) {
		
		BridgeOptions options = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddress(address));

		router.route("/" + path + "/*").handler(SockJSHandler.create(vertx).bridge(options, event -> {

			if (event.type() == BridgeEventType.SOCKET_CREATED) {
				 logger.debug("A socket was created");
			} else if (event.type() == BridgeEventType.SOCKET_CLOSED) {
				logger.debug("A socket was closed");
			}

			// This signals that it's ok to process the event
			event.complete(true);

		}));
	}
	
	
	private void systemInfo() {
		vertx.setPeriodic(1000, t -> vertx.eventBus().publish("systemInfo", getSystemData()));
	}
	
	private String getSystemData() {
		
		HardwareAbstractionLayer hal = systemInfo.getHardware();
	    //OperatingSystem os = si.getOperatingSystem();
		
		String cpu1 = String.format("CPU load: %.1f%% (counting ticks)%n", hal.getProcessor().getSystemCpuLoadBetweenTicks() * 100);
		String cpu2 =String.format("CPU load: %.1f%% (OS MXBean)%n", hal.getProcessor().getSystemCpuLoad() * 100);
		
		double[] load = hal.getProcessor().getProcessorCpuLoadBetweenTicks();
		StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
        }
        
        
        long total = hal.getMemory().getTotal();
		long available = hal.getMemory().getAvailable();
		String memory = "Memory : " + FormatUtil.formatBytes(total - available) + "/"+ FormatUtil.formatBytes(total) + "("  + String.format("%.1f%%", ((total - available) / (double)total) * 100) + ")";
		
	    return hal.getProcessor()  + "<br>" + cpu1 + "<br>" + cpu2 + "<br>" + procCpu.toString() + "<hr />" + memory;
	    
	}
	
	
}
