package com.kuraserver.effexor.utils;

import cn.nukkit.Server;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.SerializedImage;
import org.apache.logging.log4j.core.util.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ResourceReader {

    public static BufferedImage getBufferedImage(String path) {
        try {
            return ImageIO.read(ResourceReader.class.getResourceAsStream(path));
        }catch (IOException e){
            Server.getInstance().getLogger().logException(e);
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        }
    }

    public static String getGeometry(String path){
        return new Scanner(ResourceReader.class.getResourceAsStream(path), "UTF-8").useDelimiter("\\A").next();
    }

    private static String readAll(final String path) throws IOException {
        return Files.lines(Paths.get(path), StandardCharsets.UTF_8)
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

}
