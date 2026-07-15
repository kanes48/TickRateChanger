package mc.mod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class TickRateCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("tickrate")
                        .requires(source -> source.hasPermission(2))
                        .then(
                                Commands.argument(
                                                "tps",
                                                IntegerArgumentType.integer(1, 20000)
                                        )
                                        .executes(context -> {

                                            int value =
                                                    IntegerArgumentType.getInteger(
                                                            context,
                                                            "tps"
                                                    );

                                            TickRateManager.setTPS(value);

                                            context.getSource().sendSuccess(
                                                    new TextComponent(
                                                            "Tickrate set to "
                                                                    + value
                                                                    + " TPS"
                                                    ),
                                                    true
                                            );

                                            return 1;
                                        })
                        )
        );
    }
}