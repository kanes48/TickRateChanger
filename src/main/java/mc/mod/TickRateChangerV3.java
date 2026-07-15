package mc.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TickRateChangerV3 implements ModInitializer {

	public static final String MOD_ID = "tickratechangerv3";

	public static final Logger LOGGER =
			LogManager.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		LOGGER.info("TickRateChangerV3 loaded!");

		CommandRegistrationCallback.EVENT.register(
				(dispatcher, dedicated) ->
						TickRateCommand.register(dispatcher)
		);
	}
}