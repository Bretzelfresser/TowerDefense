package code.helper;

import code.map.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {

    public static BufferedImage getImage(String name){
        try {
            BufferedImage img = ImageIO.read(new File(getUsersProjectRootDirectory() + "/src/assets/" + name));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Tile.Type[][] serialize(String path) {
        String useDir = getUsersProjectRootDirectory();
        useDir += "/src/maps/" + path;
        File file = new File(useDir);
        if (file.exists()) {
            try {
                Scanner reader = new Scanner(file);
                ArrayList<Tile.Type[]> map = new ArrayList<>();
                int row = 0;
                while (reader.hasNext()) {
                    String line = reader.nextLine();
                    map.add(new Tile.Type[line.length()]);
                    for (int col = 0; col < line.length(); col++) {
                        map.get(row)[col] = Tile.Type.values()[Character.getNumericValue(line.charAt(col))];
                    }
                    row++;
                }
                reader.close();
                return map.toArray(new Tile.Type[0][0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static void deserialize(String fileName, Tile[][] map) {
        String useDir = getUsersProjectRootDirectory();
        useDir += "/src/maps/" + fileName;
        File file = new File(useDir);
        try {
            if (file.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writeTileMap(map, writer);
                writer.close();
            } else {
                if (JOptionPane.showConfirmDialog(null, "Should the file: " + fileName + " be overridden?") == 0) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writeTileMap(map, writer);
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(useDir);
        }

    }

    private static void writeTileMap(Tile[][] map, BufferedWriter writer) throws IOException {
        for (int col = 0; col < map.length; col++) {
            String line = "";
            for (int row = 0; row < map[0].length; row++) {
                line += map[row][col].getType().ordinal();
            }
            writer.write(line);
            writer.newLine();
        }
    }

    public static String getUsersProjectRootDirectory() {
        return System.getProperty("user.dir");
    }
}
