package net.theoneandonlydansan.deathlocation;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class DeathLocation implements ClientModInitializer {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static String message = "";
    public static Formatting formatting = Formatting.GOLD;
    public static File configFile = new File( (FabricLoader.getInstance().getConfigDir()) + "/DeathLocation.txt");

    @Override
    public void onInitializeClient() {
        try {

            if(!configFile.exists()) {
                configFile.createNewFile();
                updateConfig("gold");
            }

            setColor(Files.readAllLines(configFile.toPath()).get(0));


            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                literal("DeathLocation")
                        .then(literal("aqua").executes(context -> command("aqua")))
                        .then(literal("black").executes(context -> command("black")))
                        .then(literal("blue").executes(context -> command("blue")))
                        .then(literal("gray").executes(context -> command("gray")))
                        .then(literal("green").executes(context -> command("green")))
                        .then(literal("red").executes(context -> command("red")))
                        .then(literal("white").executes(context -> command("white")))
                        .then(literal("yellow").executes(context -> command("yellow")))
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int command(String s) {
        setColor(s);
        updateConfig(s);
        return 1;
    }

    public static void setMessage() {
        Vec3d pos = client.player.getPos();
        DimensionType dimensionType = client.world.getDimension();
        String dimension;
        Language language = Language.getInstance();

        if (dimensionType.respawnAnchorWorks()) {
            dimension = language.get("deathlocation.nether");
        } else if (dimensionType.bedWorks()) {
            dimension = language.get("deathlocation.overworld");
        } else {
            dimension = language.get("deathlocation.end");
        }

        message = String.format(language.get("deathlocation.deathmessage"), (int) pos.x, (int) pos.y, (int) pos.z, dimension);
    }

    public static void setColor(String s) {
        switch (s) {
            case "aqua" -> formatting = Formatting.AQUA;
            case "black" -> formatting = Formatting.BLACK;
            case "blue" -> formatting = Formatting.BLUE;
            case "gray" -> formatting = Formatting.GRAY;
            case "green" -> formatting = Formatting.GREEN;
            case "red" -> formatting = Formatting.RED;
            case "white" -> formatting = Formatting.WHITE;
            case "yellow" -> formatting = Formatting.YELLOW;
        }
    }

    public static void updateConfig(String s) {
        try {
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(s);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
