package net.theoneandonlydansan.deathlocation;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class DeathLocation implements ClientModInitializer {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static String message = "";
    public static Formatting formatting = Formatting.GOLD;
    public static boolean copyDimension = true;
    public static File configFile = new File( (FabricLoader.getInstance().getConfigDir()) + "/DeathLocation.txt");
    public static List<String> strings = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        try {
            if(!configFile.exists()) {
                configFile.createNewFile();
                updateConfig("gold", 0);
                updateConfig("true", 1);
            }

            strings = Files.readAllLines(configFile.toPath());

            if(strings.size() == 0) {
                updateConfig("gold", 0);
            }

            if(strings.size() == 1) {
                updateConfig("true", 1);
            }

            setColor(strings.get(0));
            copyDimension = Boolean.parseBoolean(strings.get(1));

            ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                literal("DeathLocation")
                    .then(literal("Color")
                        .then(literal("aqua").executes(context -> commandColor("aqua")))
                        .then(literal("black").executes(context -> commandColor("black")))
                        .then(literal("blue").executes(context -> commandColor("blue")))
                        .then(literal("gray").executes(context -> commandColor("gray")))
                        .then(literal("green").executes(context -> commandColor("green")))
                        .then(literal("red").executes(context -> commandColor("red")))
                        .then(literal("white").executes(context -> commandColor("white")))
                        .then(literal("yellow").executes(context -> commandColor("yellow"))))
                    .then(literal("CopyDimension")
                        .then(literal("off").executes(context -> commandCopyDimension("false")))
                        .then(literal("on").executes(context -> commandCopyDimension("true"))))
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int commandColor(String s) {
        setColor(s);
        updateConfig(s, 0);
        return 1;
    }

    public static int commandCopyDimension(String s) {
        copyDimension = Boolean.parseBoolean(s);
        updateConfig(s, 1);
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

    public static void updateConfig(String s, int line) {
        try {
            while (line >= strings.size()) {
                strings.add("");
            }

            strings.set(line, s);
            Files.write(configFile.toPath(), strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
