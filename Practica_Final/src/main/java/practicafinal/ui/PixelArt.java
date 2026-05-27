package practicafinal.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class PixelArt {
    private static final int GRID = 16;

    private PixelArt() {}

    public static Canvas crear(String tipo, int size) {
        Canvas canvas = new Canvas(size, size);
        GraphicsContext g = canvas.getGraphicsContext2D();
        double p = size / (double) GRID;
        dibujar(g, p, tipo);
        return canvas;
    }

    private static void dibujar(GraphicsContext g, double p, String tipo) {
        switch (tipo) {
            case "hero": hero(g, p); break;
            case "heroSword": heroSword(g, p); break;
            case "heroDark": heroDark(g, p); break;
            case "heroDarkSword": heroDarkSword(g, p); break;
            case "heroFire": heroFire(g, p); break;
            case "heroFireSword": heroFireSword(g, p); break;
            case "heroKnight": heroKnight(g, p); break;
            case "heroKnightSword": heroKnightSword(g, p); break;
            case "enemy": enemy(g, p); break;
            case "slime": slime(g, p); break;
            case "skeleton": skeleton(g, p); break;
            case "zombie": zombie(g, p); break;
            case "cultist": cultist(g, p); break;
            case "brute": brute(g, p); break;
            case "boss": boss(g, p); break;
            case "potion": potion(g, p); break;
            case "item": item(g, p); break;
            case "shield": shield(g, p); break;
            case "key": key(g, p); break;
            case "trap": trap(g, p); break;
            case "door": door(g, p); break;
            case "exit": exit(g, p); break;
            case "wall": wall(g, p); break;
            case "shadow": shadow(g, p); break;
            case "slashSword": slashSword(g, p); break;
            case "clawSlash": clawSlash(g, p); break;
            default: floor(g, p); break;
        }
    }

    private static void px(GraphicsContext g, int x, int y, int w, int h, Color c, double p) {
        g.setFill(c);
        g.fillRect(x * p, y * p, w * p, h * p);
    }

    private static void floor(GraphicsContext g, double p) {
        fill(g, Color.rgb(75, 60, 45), p);

        px(g, 1, 1, 4, 2, Color.rgb(90, 74, 56), p);
        px(g, 9, 3, 4, 2, Color.rgb(86, 70, 52), p);
        px(g, 2, 8, 5, 2, Color.rgb(92, 76, 58), p);
        px(g, 10, 10, 4, 3, Color.rgb(84, 68, 50), p);
        px(g, 6, 5, 3, 1, Color.rgb(94, 78, 60), p);
        px(g, 1, 13, 3, 2, Color.rgb(88, 72, 54), p);
        px(g, 12, 1, 3, 2, Color.rgb(86, 70, 52), p);

        px(g, 5, 2, 2, 1, Color.rgb(65, 52, 40), p);
        px(g, 13, 5, 2, 2, Color.rgb(62, 50, 38), p);
        px(g, 1, 6, 2, 2, Color.rgb(68, 55, 42), p);
        px(g, 8, 11, 2, 1, Color.rgb(65, 52, 40), p);
        px(g, 13, 12, 2, 1, Color.rgb(62, 50, 38), p);

        px(g, 3, 4, 2, 1, Color.rgb(55, 42, 32), p);
        px(g, 10, 7, 1, 3, Color.rgb(52, 40, 30), p);
        px(g, 6, 11, 3, 1, Color.rgb(55, 42, 32), p);
        px(g, 8, 13, 2, 1, Color.rgb(52, 40, 30), p);

        px(g, 2, 2, 1, 1, Color.rgb(112, 92, 72), p);
        px(g, 10, 4, 1, 1, Color.rgb(108, 88, 68), p);
        px(g, 3, 9, 1, 1, Color.rgb(116, 95, 74), p);
        px(g, 12, 11, 1, 1, Color.rgb(104, 85, 65), p);
        px(g, 7, 6, 1, 1, Color.rgb(110, 90, 70), p);
        px(g, 14, 8, 1, 1, Color.rgb(100, 82, 62), p);
    }

    private static void wall(GraphicsContext g, double p) {
        fill(g, Color.rgb(26, 28, 38), p);

        px(g, 0, 2, 16, 1, Color.rgb(32, 34, 46), p);
        px(g, 0, 7, 16, 1, Color.rgb(20, 22, 30), p);
        px(g, 0, 12, 16, 1, Color.rgb(32, 34, 46), p);

        px(g, 5, 0, 1, 5, Color.rgb(18, 20, 28), p);
        px(g, 11, 0, 1, 5, Color.rgb(18, 20, 28), p);
        px(g, 3, 7, 1, 6, Color.rgb(18, 20, 28), p);
        px(g, 8, 7, 1, 6, Color.rgb(18, 20, 28), p);
        px(g, 13, 7, 1, 6, Color.rgb(18, 20, 28), p);
        px(g, 6, 12, 1, 4, Color.rgb(18, 20, 28), p);
        px(g, 12, 12, 1, 4, Color.rgb(18, 20, 28), p);

        px(g, 1, 1, 3, 1, Color.rgb(40, 42, 56), p);
        px(g, 7, 3, 4, 1, Color.rgb(38, 40, 54), p);
        px(g, 1, 8, 2, 1, Color.rgb(38, 40, 54), p);
        px(g, 10, 10, 3, 1, Color.rgb(40, 42, 56), p);
        px(g, 3, 13, 4, 1, Color.rgb(38, 40, 54), p);

        px(g, 9, 1, 2, 1, Color.rgb(20, 22, 30), p);
        px(g, 3, 4, 1, 2, Color.rgb(18, 20, 28), p);
        px(g, 12, 8, 1, 2, Color.rgb(20, 22, 30), p);
        px(g, 5, 12, 2, 1, Color.rgb(18, 20, 28), p);

        px(g, 2, 5, 1, 1, Color.rgb(36, 38, 52), p);
        px(g, 8, 5, 1, 1, Color.rgb(40, 42, 56), p);
        px(g, 4, 10, 1, 1, Color.rgb(36, 38, 52), p);
        px(g, 11, 13, 1, 1, Color.rgb(38, 40, 54), p);
        px(g, 14, 3, 1, 1, Color.rgb(36, 38, 52), p);
        px(g, 1, 14, 1, 1, Color.rgb(34, 36, 48), p);
    }

    private static void shadow(GraphicsContext g, double p) {
        fill(g, Color.rgb(6, 7, 12), p);
        px(g, 1, 1, 4, 2, Color.rgb(13, 14, 24), p);
        px(g, 8, 2, 5, 1, Color.rgb(10, 11, 20), p);
        px(g, 2, 5, 3, 2, Color.rgb(11, 12, 21), p);
        px(g, 10, 6, 4, 3, Color.rgb(8, 9, 17), p);
        px(g, 4, 10, 5, 1, Color.rgb(12, 13, 23), p);
        px(g, 1, 13, 3, 1, Color.rgb(9, 10, 18), p);
        px(g, 12, 12, 3, 2, Color.rgb(11, 12, 20), p);
        px(g, 6, 4, 1, 1, Color.rgb(22, 22, 34), p);
        px(g, 13, 3, 1, 1, Color.rgb(18, 18, 28), p);
        px(g, 3, 9, 1, 1, Color.rgb(20, 20, 31), p);
        px(g, 9, 13, 1, 1, Color.rgb(19, 19, 29), p);
    }

    private static void slashSword(GraphicsContext g, double p) {
        transparent(g);
        px(g, 10, 1, 2, 2, Color.rgb(255, 255, 255), p);
        px(g, 9, 3, 2, 2, Color.rgb(230, 235, 245), p);
        px(g, 8, 5, 2, 2, Color.rgb(210, 220, 235), p);
        px(g, 7, 7, 2, 2, Color.rgb(190, 205, 225), p);
        px(g, 6, 9, 2, 2, Color.rgb(170, 185, 205), p);
        px(g, 5, 11, 2, 1, Color.rgb(230, 180, 70), p);
        px(g, 3, 12, 5, 2, Color.rgb(120, 70, 30), p);
        px(g, 2, 13, 2, 2, Color.rgb(70, 40, 22), p);
        px(g, 8, 4, 1, 5, Color.rgb(255, 255, 255), p);
        px(g, 11, 1, 1, 1, Color.rgb(180, 220, 255), p);
    }

    private static void clawSlash(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 2, 2, 10, Color.rgb(255, 220, 210), p);
        px(g, 5, 1, 1, 2, Color.rgb(255, 255, 245), p);
        px(g, 8, 1, 2, 12, Color.rgb(255, 130, 120), p);
        px(g, 9, 0, 1, 2, Color.rgb(255, 230, 220), p);
        px(g, 12, 3, 2, 10, Color.rgb(230, 70, 85), p);
        px(g, 13, 2, 1, 2, Color.rgb(255, 210, 210), p);
        px(g, 3, 12, 2, 2, Color.rgb(110, 15, 25), p);
        px(g, 7, 13, 2, 2, Color.rgb(110, 15, 25), p);
        px(g, 11, 13, 2, 2, Color.rgb(110, 15, 25), p);
    }

    private static void hero(GraphicsContext g, double p) {
        transparent(g);
        px(g, 5, 0, 6, 1, Color.rgb(85, 60, 35), p);
        px(g, 6, 1, 4, 2, Color.rgb(100, 70, 40), p);
        px(g, 5, 2, 1, 1, Color.rgb(90, 65, 38), p);
        px(g, 10, 2, 1, 1, Color.rgb(90, 65, 38), p);
        px(g, 5, 3, 6, 3, Color.rgb(238, 190, 135), p);
        px(g, 6, 4, 1, 1, Color.rgb(20, 30, 55), p);
        px(g, 9, 4, 1, 1, Color.rgb(20, 30, 55), p);
        px(g, 7, 5, 2, 1, Color.rgb(200, 150, 100), p);
        px(g, 5, 6, 6, 1, Color.rgb(85, 45, 35), p);
        px(g, 5, 7, 6, 4, Color.rgb(24, 100, 190), p);
        px(g, 6, 8, 4, 1, Color.rgb(45, 130, 220), p);
        px(g, 4, 7, 1, 3, Color.rgb(200, 220, 245), p);
        px(g, 11, 7, 1, 3, Color.rgb(200, 220, 245), p);
        px(g, 3, 10, 2, 2, Color.rgb(150, 170, 195), p);
        px(g, 11, 10, 2, 2, Color.rgb(95, 145, 210), p);
        px(g, 5, 11, 6, 1, Color.rgb(60, 40, 25), p);
        px(g, 7, 11, 2, 1, Color.rgb(200, 170, 60), p);
        px(g, 5, 12, 2, 3, Color.rgb(25, 45, 80), p);
        px(g, 9, 12, 2, 3, Color.rgb(25, 45, 80), p);
        px(g, 4, 15, 3, 1, Color.rgb(18, 20, 28), p);
        px(g, 9, 15, 3, 1, Color.rgb(18, 20, 28), p);
    }

    private static void heroSword(GraphicsContext g, double p) {
        hero(g, p);
        px(g, 12, 4, 1, 7, Color.rgb(220, 225, 235), p);
        px(g, 13, 3, 1, 2, Color.rgb(255, 255, 255), p);
        px(g, 11, 10, 2, 2, Color.rgb(95, 145, 210), p);
    }

    private static void heroDark(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 0, 8, 1, Color.rgb(20, 10, 30), p);
        px(g, 5, 1, 6, 2, Color.rgb(40, 22, 55), p);
        px(g, 5, 3, 6, 2, Color.rgb(28, 16, 38), p);
        px(g, 6, 4, 1, 1, Color.rgb(180, 50, 255), p);
        px(g, 9, 4, 1, 1, Color.rgb(180, 50, 255), p);
        px(g, 5, 5, 6, 1, Color.rgb(22, 12, 32), p);
        px(g, 6, 6, 4, 1, Color.rgb(40, 25, 50), p);
        px(g, 5, 7, 6, 4, Color.rgb(40, 18, 55), p);
        px(g, 6, 8, 4, 1, Color.rgb(55, 28, 70), p);
        px(g, 4, 7, 1, 3, Color.rgb(55, 30, 70), p);
        px(g, 11, 7, 1, 3, Color.rgb(55, 30, 70), p);
        px(g, 3, 10, 2, 2, Color.rgb(25, 12, 35), p);
        px(g, 11, 10, 2, 2, Color.rgb(25, 12, 35), p);
        px(g, 5, 11, 6, 1, Color.rgb(15, 8, 22), p);
        px(g, 7, 11, 2, 1, Color.rgb(100, 40, 150), p);
        px(g, 5, 12, 2, 3, Color.rgb(20, 10, 30), p);
        px(g, 9, 12, 2, 3, Color.rgb(20, 10, 30), p);
        px(g, 4, 15, 3, 1, Color.rgb(10, 5, 18), p);
        px(g, 9, 15, 3, 1, Color.rgb(10, 5, 18), p);
    }

    private static void heroDarkSword(GraphicsContext g, double p) {
        heroDark(g, p);
        px(g, 12, 3, 1, 8, Color.rgb(160, 80, 255), p);
        px(g, 13, 2, 1, 2, Color.rgb(200, 120, 255), p);
        px(g, 11, 10, 2, 2, Color.rgb(25, 12, 35), p);
    }

    private static void heroFire(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 0, 8, 1, Color.rgb(255, 80, 10), p);
        px(g, 5, 0, 6, 1, Color.rgb(255, 150, 30), p);
        px(g, 6, 1, 4, 2, Color.rgb(255, 100, 20), p);
        px(g, 5, 1, 1, 1, Color.rgb(255, 180, 50), p);
        px(g, 10, 1, 1, 1, Color.rgb(255, 180, 50), p);
        px(g, 5, 3, 6, 3, Color.rgb(240, 170, 110), p);
        px(g, 6, 4, 1, 1, Color.rgb(255, 200, 50), p);
        px(g, 9, 4, 1, 1, Color.rgb(255, 200, 50), p);
        px(g, 7, 5, 2, 1, Color.rgb(200, 100, 50), p);
        px(g, 5, 6, 6, 1, Color.rgb(180, 80, 30), p);
        px(g, 5, 7, 6, 4, Color.rgb(200, 40, 20), p);
        px(g, 6, 8, 4, 1, Color.rgb(230, 60, 30), p);
        px(g, 4, 7, 1, 3, Color.rgb(220, 60, 30), p);
        px(g, 11, 7, 1, 3, Color.rgb(220, 60, 30), p);
        px(g, 3, 10, 2, 2, Color.rgb(230, 150, 90), p);
        px(g, 11, 10, 2, 2, Color.rgb(230, 150, 90), p);
        px(g, 5, 11, 6, 1, Color.rgb(40, 15, 8), p);
        px(g, 7, 11, 2, 1, Color.rgb(200, 100, 30), p);
        px(g, 5, 12, 2, 3, Color.rgb(60, 20, 10), p);
        px(g, 9, 12, 2, 3, Color.rgb(60, 20, 10), p);
        px(g, 4, 15, 3, 1, Color.rgb(30, 10, 5), p);
        px(g, 9, 15, 3, 1, Color.rgb(30, 10, 5), p);
    }

    private static void heroFireSword(GraphicsContext g, double p) {
        heroFire(g, p);
        px(g, 12, 3, 1, 8, Color.rgb(255, 150, 20), p);
        px(g, 13, 2, 1, 3, Color.rgb(255, 220, 50), p);
        px(g, 11, 10, 2, 2, Color.rgb(230, 150, 90), p);
    }

    private static void heroKnight(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 0, 8, 1, Color.rgb(140, 150, 170), p);
        px(g, 5, 1, 6, 2, Color.rgb(180, 190, 210), p);
        px(g, 6, 1, 4, 1, Color.rgb(200, 210, 230), p);
        px(g, 6, 3, 4, 1, Color.rgb(15, 15, 25), p);
        px(g, 6, 3, 1, 1, Color.rgb(130, 210, 255), p);
        px(g, 9, 3, 1, 1, Color.rgb(130, 210, 255), p);
        px(g, 5, 4, 6, 2, Color.rgb(170, 178, 198), p);
        px(g, 6, 5, 4, 1, Color.rgb(100, 105, 125), p);
        px(g, 3, 6, 10, 1, Color.rgb(130, 140, 165), p);
        px(g, 4, 7, 8, 1, Color.rgb(155, 165, 190), p);
        px(g, 5, 7, 6, 4, Color.rgb(160, 175, 200), p);
        px(g, 6, 8, 4, 1, Color.rgb(180, 190, 215), p);
        px(g, 5, 10, 6, 1, Color.rgb(100, 80, 55), p);
        px(g, 7, 10, 2, 1, Color.rgb(200, 170, 100), p);
        px(g, 4, 7, 1, 3, Color.rgb(140, 152, 175), p);
        px(g, 11, 7, 1, 3, Color.rgb(140, 152, 175), p);
        px(g, 3, 10, 2, 2, Color.rgb(110, 120, 145), p);
        px(g, 11, 10, 2, 2, Color.rgb(110, 120, 145), p);
        px(g, 5, 11, 6, 1, Color.rgb(90, 80, 65), p);
        px(g, 7, 11, 2, 1, Color.rgb(200, 180, 80), p);
        px(g, 5, 12, 2, 3, Color.rgb(120, 130, 155), p);
        px(g, 9, 12, 2, 3, Color.rgb(120, 130, 155), p);
        px(g, 4, 15, 3, 1, Color.rgb(90, 100, 125), p);
        px(g, 9, 15, 3, 1, Color.rgb(90, 100, 125), p);
    }

    private static void heroKnightSword(GraphicsContext g, double p) {
        heroKnight(g, p);
        px(g, 12, 4, 2, 7, Color.rgb(200, 210, 230), p);
        px(g, 13, 3, 1, 1, Color.rgb(255, 255, 255), p);
        px(g, 11, 10, 2, 2, Color.rgb(110, 120, 145), p);
    }

    private static void enemy(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 1, 8, 2, Color.rgb(55, 85, 45), p);
        px(g, 5, 3, 6, 2, Color.rgb(72, 145, 70), p);
        px(g, 6, 4, 1, 1, Color.rgb(255, 50, 50), p);
        px(g, 9, 4, 1, 1, Color.rgb(255, 50, 50), p);
        px(g, 4, 5, 8, 3, Color.rgb(60, 120, 58), p);
        px(g, 3, 5, 1, 2, Color.rgb(60, 120, 58), p);
        px(g, 12, 5, 1, 2, Color.rgb(60, 120, 58), p);
        px(g, 5, 7, 2, 1, Color.rgb(240, 60, 40), p);
        px(g, 9, 7, 2, 1, Color.rgb(240, 60, 40), p);
        px(g, 4, 8, 8, 2, Color.rgb(80, 55, 35), p);
        px(g, 5, 10, 6, 2, Color.rgb(55, 35, 22), p);
        px(g, 3, 10, 2, 2, Color.rgb(38, 55, 38), p);
        px(g, 11, 10, 2, 2, Color.rgb(38, 55, 38), p);
        px(g, 4, 12, 3, 2, Color.rgb(38, 55, 38), p);
        px(g, 9, 12, 3, 2, Color.rgb(38, 55, 38), p);
        px(g, 5, 14, 2, 2, Color.rgb(30, 40, 30), p);
        px(g, 9, 14, 2, 2, Color.rgb(30, 40, 30), p);
    }

    private static void slime(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 5, 8, 1, Color.rgb(65, 175, 105), p);
        px(g, 3, 6, 10, 3, Color.rgb(50, 150, 90), p);
        px(g, 2, 9, 12, 4, Color.rgb(42, 125, 82), p);
        px(g, 3, 13, 10, 2, Color.rgb(28, 88, 58), p);
        px(g, 5, 7, 2, 2, Color.rgb(210, 255, 220), p);
        px(g, 10, 7, 2, 2, Color.rgb(210, 255, 220), p);
        px(g, 6, 8, 1, 1, Color.rgb(20, 50, 35), p);
        px(g, 11, 8, 1, 1, Color.rgb(20, 50, 35), p);
        px(g, 7, 11, 3, 1, Color.rgb(20, 70, 45), p);
        px(g, 4, 6, 2, 1, Color.rgb(120, 235, 155), p);
        px(g, 7, 5, 2, 1, Color.rgb(140, 255, 170), p);
    }

    private static void skeleton(GraphicsContext g, double p) {
        transparent(g);
        px(g, 5, 1, 6, 1, Color.rgb(230, 225, 200), p);
        px(g, 4, 2, 8, 5, Color.rgb(205, 198, 170), p);
        px(g, 5, 3, 2, 2, Color.rgb(20, 20, 24), p);
        px(g, 9, 3, 2, 2, Color.rgb(20, 20, 24), p);
        px(g, 7, 5, 2, 1, Color.rgb(80, 70, 55), p);
        px(g, 6, 7, 4, 1, Color.rgb(180, 170, 145), p);
        px(g, 5, 8, 6, 4, Color.rgb(190, 182, 155), p);
        px(g, 6, 9, 1, 2, Color.rgb(80, 70, 55), p);
        px(g, 9, 9, 1, 2, Color.rgb(80, 70, 55), p);
        px(g, 3, 8, 2, 1, Color.rgb(170, 160, 140), p);
        px(g, 11, 8, 2, 1, Color.rgb(170, 160, 140), p);
        px(g, 4, 12, 3, 3, Color.rgb(170, 160, 140), p);
        px(g, 9, 12, 3, 3, Color.rgb(170, 160, 140), p);
    }

    private static void zombie(GraphicsContext g, double p) {
        transparent(g);
        px(g, 5, 1, 6, 2, Color.rgb(48, 85, 58), p);
        px(g, 4, 3, 8, 4, Color.rgb(78, 130, 78), p);
        px(g, 6, 4, 1, 1, Color.rgb(255, 230, 95), p);
        px(g, 9, 4, 1, 1, Color.rgb(255, 230, 95), p);
        px(g, 7, 6, 2, 1, Color.rgb(50, 45, 38), p);
        px(g, 4, 7, 8, 4, Color.rgb(75, 65, 48), p);
        px(g, 5, 8, 6, 1, Color.rgb(100, 85, 62), p);
        px(g, 3, 7, 1, 4, Color.rgb(55, 95, 60), p);
        px(g, 12, 7, 1, 4, Color.rgb(55, 95, 60), p);
        px(g, 5, 11, 2, 4, Color.rgb(38, 62, 48), p);
        px(g, 9, 11, 2, 4, Color.rgb(38, 62, 48), p);
        px(g, 8, 2, 3, 1, Color.rgb(28, 45, 35), p);
    }

    private static void cultist(GraphicsContext g, double p) {
        transparent(g);
        px(g, 5, 0, 6, 2, Color.rgb(95, 15, 28), p);
        px(g, 4, 2, 8, 5, Color.rgb(130, 20, 38), p);
        px(g, 6, 3, 1, 1, Color.rgb(245, 190, 75), p);
        px(g, 9, 3, 1, 1, Color.rgb(245, 190, 75), p);
        px(g, 5, 6, 6, 5, Color.rgb(80, 10, 22), p);
        px(g, 6, 7, 4, 1, Color.rgb(160, 45, 50), p);
        px(g, 4, 7, 1, 4, Color.rgb(55, 8, 18), p);
        px(g, 11, 7, 1, 4, Color.rgb(55, 8, 18), p);
        px(g, 5, 11, 3, 4, Color.rgb(45, 8, 16), p);
        px(g, 8, 11, 3, 4, Color.rgb(45, 8, 16), p);
        px(g, 7, 8, 2, 2, Color.rgb(230, 170, 55), p);
    }

    private static void brute(GraphicsContext g, double p) {
        transparent(g);
        px(g, 3, 1, 10, 2, Color.rgb(75, 42, 24), p);
        px(g, 4, 3, 8, 4, Color.rgb(130, 75, 42), p);
        px(g, 5, 4, 2, 1, Color.rgb(255, 95, 55), p);
        px(g, 9, 4, 2, 1, Color.rgb(255, 95, 55), p);
        px(g, 6, 6, 4, 1, Color.rgb(45, 22, 15), p);
        px(g, 3, 7, 10, 4, Color.rgb(95, 52, 35), p);
        px(g, 2, 7, 2, 5, Color.rgb(110, 65, 38), p);
        px(g, 12, 7, 2, 5, Color.rgb(110, 65, 38), p);
        px(g, 4, 11, 3, 4, Color.rgb(65, 38, 28), p);
        px(g, 9, 11, 3, 4, Color.rgb(65, 38, 28), p);
        px(g, 1, 12, 3, 2, Color.rgb(185, 180, 165), p);
        px(g, 12, 12, 3, 2, Color.rgb(185, 180, 165), p);
    }

    private static void boss(GraphicsContext g, double p) {
        transparent(g);
        px(g, 3, 0, 10, 1, Color.rgb(100, 10, 20), p);
        px(g, 4, 1, 8, 2, Color.rgb(130, 15, 25), p);
        px(g, 3, 1, 1, 1, Color.rgb(80, 8, 15), p);
        px(g, 12, 1, 1, 1, Color.rgb(80, 8, 15), p);
        px(g, 5, 3, 6, 2, Color.rgb(125, 20, 30), p);
        px(g, 6, 3, 1, 1, Color.rgb(255, 220, 80), p);
        px(g, 9, 3, 1, 1, Color.rgb(255, 220, 80), p);
        px(g, 4, 5, 8, 2, Color.rgb(140, 25, 35), p);
        px(g, 3, 5, 1, 2, Color.rgb(100, 15, 25), p);
        px(g, 12, 5, 1, 2, Color.rgb(100, 15, 25), p);
        px(g, 5, 7, 6, 1, Color.rgb(20, 5, 8), p);
        px(g, 3, 8, 10, 4, Color.rgb(55, 18, 60), p);
        px(g, 2, 8, 1, 3, Color.rgb(90, 25, 35), p);
        px(g, 13, 8, 1, 3, Color.rgb(90, 25, 35), p);
        px(g, 4, 12, 8, 2, Color.rgb(45, 12, 50), p);
        px(g, 5, 14, 2, 2, Color.rgb(25, 15, 35), p);
        px(g, 9, 14, 2, 2, Color.rgb(25, 15, 35), p);
        px(g, 1, 4, 3, 1, Color.rgb(255, 190, 70), p);
        px(g, 12, 4, 3, 1, Color.rgb(255, 190, 70), p);
        px(g, 0, 5, 2, 1, Color.rgb(255, 100, 60), p);
        px(g, 14, 5, 2, 1, Color.rgb(255, 100, 60), p);
    }

    private static void potion(GraphicsContext g, double p) {
        transparent(g);
        px(g, 7, 0, 2, 1, Color.rgb(200, 220, 240), p);
        px(g, 6, 1, 4, 2, Color.rgb(180, 210, 230), p);
        px(g, 5, 3, 6, 1, Color.rgb(80, 160, 120), p);
        px(g, 4, 4, 8, 8, Color.rgb(35, 190, 95), p);
        px(g, 5, 5, 6, 6, Color.rgb(65, 235, 130), p);
        px(g, 6, 6, 2, 1, Color.rgb(210, 255, 220), p);
        px(g, 7, 7, 1, 2, Color.rgb(200, 255, 210), p);
        px(g, 4, 12, 8, 1, Color.rgb(20, 110, 55), p);
        px(g, 5, 13, 6, 1, Color.rgb(20, 90, 50), p);
    }

    private static void item(GraphicsContext g, double p) {
        transparent(g);
        px(g, 6, 0, 4, 1, Color.rgb(245, 245, 255), p);
        px(g, 7, 1, 2, 10, Color.rgb(220, 225, 235), p);
        px(g, 6, 2, 1, 2, Color.rgb(255, 255, 255), p);
        px(g, 9, 4, 1, 2, Color.rgb(200, 205, 220), p);
        px(g, 5, 11, 6, 1, Color.rgb(150, 110, 45), p);
        px(g, 4, 12, 8, 2, Color.rgb(95, 60, 28), p);
        px(g, 6, 14, 4, 1, Color.rgb(70, 45, 25), p);
    }

    private static void shield(GraphicsContext g, double p) {
        transparent(g);
        px(g, 4, 1, 8, 1, Color.rgb(200, 220, 240), p);
        px(g, 3, 2, 10, 10, Color.rgb(70, 135, 190), p);
        px(g, 4, 3, 8, 8, Color.rgb(45, 95, 155), p);
        px(g, 7, 2, 2, 8, Color.rgb(225, 235, 245), p);
        px(g, 5, 4, 6, 2, Color.rgb(135, 190, 235), p);
        px(g, 4, 12, 8, 2, Color.rgb(35, 70, 120), p);
        px(g, 6, 14, 4, 1, Color.rgb(25, 45, 80), p);
    }

    private static void key(GraphicsContext g, double p) {
        transparent(g);
        px(g, 3, 5, 4, 1, Color.rgb(255, 235, 135), p);
        px(g, 3, 6, 4, 4, Color.rgb(245, 195, 65), p);
        px(g, 4, 7, 2, 2, Color.rgb(60, 35, 10), p);
        px(g, 7, 6, 7, 2, Color.rgb(245, 195, 65), p);
        px(g, 8, 5, 5, 1, Color.rgb(255, 235, 135), p);
        px(g, 11, 8, 3, 2, Color.rgb(245, 195, 65), p);
        px(g, 10, 8, 1, 3, Color.rgb(245, 195, 65), p);
        px(g, 9, 10, 1, 2, Color.rgb(200, 155, 50), p);
    }

    private static void trap(GraphicsContext g, double p) {
        fill(g, Color.rgb(25, 14, 8), p);
        px(g, 0, 14, 16, 2, Color.rgb(16, 9, 5), p);
        px(g, 1, 12, 14, 2, Color.rgb(45, 25, 14), p);
        px(g, 2, 10, 12, 2, Color.rgb(65, 38, 20), p);
        px(g, 3, 3, 2, 7, Color.rgb(200, 195, 185), p);
        px(g, 7, 1, 2, 9, Color.rgb(220, 215, 205), p);
        px(g, 11, 3, 2, 7, Color.rgb(200, 195, 185), p);
        px(g, 3, 9, 2, 1, Color.rgb(180, 175, 165), p);
        px(g, 7, 9, 2, 1, Color.rgb(200, 195, 185), p);
        px(g, 11, 9, 2, 1, Color.rgb(180, 175, 165), p);
        px(g, 4, 2, 1, 1, Color.rgb(160, 155, 145), p);
        px(g, 11, 2, 1, 1, Color.rgb(160, 155, 145), p);
        px(g, 4, 10, 8, 1, Color.rgb(230, 95, 35), p);
        px(g, 5, 11, 6, 1, Color.rgb(255, 145, 55), p);
    }

    private static void door(GraphicsContext g, double p) {
        fill(g, Color.rgb(25, 14, 6), p);
        px(g, 1, 1, 14, 1, Color.rgb(42, 23, 9), p);
        px(g, 1, 14, 14, 1, Color.rgb(42, 23, 9), p);
        px(g, 2, 2, 12, 12, Color.rgb(105, 62, 24), p);
        px(g, 3, 3, 10, 10, Color.rgb(135, 82, 34), p);
        px(g, 3, 3, 1, 10, Color.rgb(155, 98, 44), p);
        px(g, 3, 12, 10, 1, Color.rgb(155, 98, 44), p);
        px(g, 7, 3, 1, 10, Color.rgb(78, 43, 18), p);
        px(g, 2, 5, 1, 1, Color.rgb(180, 130, 60), p);
        px(g, 12, 5, 1, 1, Color.rgb(180, 130, 60), p);
        px(g, 2, 10, 1, 1, Color.rgb(180, 130, 60), p);
        px(g, 12, 10, 1, 1, Color.rgb(180, 130, 60), p);
        px(g, 11, 8, 1, 1, Color.rgb(245, 196, 75), p);
        px(g, 2, 2, 12, 1, Color.rgb(200, 145, 65), p);
        px(g, 2, 3, 1, 1, Color.rgb(200, 145, 65), p);
        px(g, 13, 3, 1, 1, Color.rgb(200, 145, 65), p);
    }

    private static void exit(GraphicsContext g, double p) {
        fill(g, Color.rgb(5, 20, 15), p);
        px(g, 1, 1, 14, 1, Color.rgb(10, 40, 28), p);
        px(g, 1, 14, 14, 1, Color.rgb(10, 40, 28), p);
        px(g, 1, 1, 1, 14, Color.rgb(10, 40, 28), p);
        px(g, 14, 1, 1, 14, Color.rgb(10, 40, 28), p);
        px(g, 2, 2, 12, 12, Color.rgb(25, 115, 75), p);
        px(g, 3, 3, 10, 10, Color.rgb(50, 210, 130), p);
        px(g, 5, 5, 6, 6, Color.rgb(240, 255, 210), p);
        px(g, 7, 0, 2, 16, Color.rgb(255, 240, 120), p);
        px(g, 0, 7, 16, 2, Color.rgb(255, 240, 120), p);
        px(g, 4, 4, 8, 1, Color.rgb(255, 255, 240), p);
        px(g, 4, 11, 8, 1, Color.rgb(255, 255, 240), p);
        px(g, 4, 7, 1, 2, Color.rgb(255, 255, 240), p);
        px(g, 11, 7, 1, 2, Color.rgb(255, 255, 240), p);
    }

    private static void fill(GraphicsContext g, Color c, double p) {
        px(g, 0, 0, GRID, GRID, c, p);
    }

    private static void transparent(GraphicsContext g) {
        g.clearRect(0, 0, GRID * 4, GRID * 4);
    }
}
