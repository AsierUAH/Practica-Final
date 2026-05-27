package practicafinal.logic;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public final class GestorMusica {
    private Synthesizer synth;
    private MidiChannel[] ch;
    private boolean disponible;
    private Thread musicThread;
    private volatile boolean playing;

    public GestorMusica() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            ch = synth.getChannels();
            disponible = true;
        } catch (Exception e) {
            disponible = false;
        }
    }

    public void detenerTodaMusica() {
        playing = false;
        if (musicThread != null) {
            musicThread.interrupt();
            musicThread = null;
        }
        if (ch != null) {
            for (MidiChannel c : ch) {
                if (c != null) c.allNotesOff();
            }
        }
    }

    public void close() {
        detenerTodaMusica();
        if (synth != null && synth.isOpen()) synth.close();
    }

    /* ─── MENU: cello oscuro + pad que sube y baja ─── */
    public void iniciarMusicaMenu() {
        detenerTodaMusica();
        if (!disponible) return;
        playing = true;
        musicThread = new Thread(() -> {
            try {
                ch[0].programChange(42);   // Cello
                ch[1].programChange(89);   // Pad warm
                ch[2].programChange(47);   // Harp
                int[][] acordes = {
                    {45,48,52,57},  // Dm
                    {43,47,50,55},  // C
                    {40,45,48,52},  // Bb
                    {38,43,47,50},  // A
                    {45,48,52,57},  // Dm
                    {47,50,55,59},  // E
                    {43,47,50,55},  // C
                    {45,48,52,57},  // Dm
                };
                int[] melodia = {57,55,52,57,60,52,48,45,43,45,48,52,55,52,48,45};
                int idx = 0;
                while (playing) {
                    for (int[] ac : acordes) {
                        if (!playing) break;
                        for (int n : ac) {
                            ch[1].noteOn(n-12, 25);
                            ch[1].noteOn(n-24, 15);
                        }
                        ch[2].noteOn(melodia[idx % melodia.length], 25);
                        Thread.sleep(180);
                        ch[2].noteOff(melodia[idx % melodia.length]);
                        idx++;
                        for (int n : ac) {
                            ch[0].noteOn(n-12, 30);
                            Thread.sleep(60);
                            ch[0].noteOff(n-12);
                        }
                        Thread.sleep(250);
                        for (int n : ac) {
                            ch[1].noteOff(n-12);
                            ch[1].noteOff(n-24);
                        }
                        Thread.sleep(600);
                    }
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        musicThread.setDaemon(true);
        musicThread.start();
    }

    /* ─── JUEGO: drone tenso + latido bajo + acentos ─── */
    public void iniciarMusicaJuego() {
        detenerTodaMusica();
        if (!disponible) return;
        playing = true;
        musicThread = new Thread(() -> {
            try {
                ch[0].programChange(36);   // Bass
                ch[1].programChange(82);   // Lead
                ch[2].programChange(48);   // Strings
                ch[3].programChange(54);   // Choir
                // drone sostenido
                ch[2].noteOn(40, 15);
                ch[2].noteOn(45, 12);
                ch[2].noteOn(52, 10);
                ch[3].noteOn(28, 9);
                ch[3].noteOn(40, 8);
                int[] bajo = {40,40,38,38,36,36,43,43,45,45,43,43,40,40,38,36};
                int[] acento = {0,0,0,0,0,47,0,0,0,0,0,47,0,0,0,0};
                int[] melodia = {0,0,52,0,0,0,57,0,0,0,60,0,0,0,55,0};
                int idx = 0;
                while (playing) {
                    int n = bajo[idx % bajo.length];
                    ch[0].noteOn(n, 40);
                    if (idx % 8 == 0) ch[9].noteOn(36, 28);
                    if (idx % 8 == 4) ch[9].noteOn(41, 18);
                    Thread.sleep(60);
                    ch[0].noteOff(n);
                    ch[9].noteOff(36);
                    ch[9].noteOff(41);
                    if (acento[idx % acento.length] > 0) {
                        ch[0].noteOn(acento[idx % acento.length], 45);
                        Thread.sleep(40);
                        ch[0].noteOff(acento[idx % acento.length]);
                    }
                    if (melodia[idx % melodia.length] > 0) {
                        ch[1].noteOn(melodia[idx % melodia.length], 35);
                        Thread.sleep(120);
                        ch[1].noteOff(melodia[idx % melodia.length]);
                    }
                    Thread.sleep(180);
                    idx++;
                    if (idx > 64) {
                        ch[2].allNotesOff();
                        ch[3].allNotesOff();
                        ch[2].noteOn(38, 15);
                        ch[2].noteOn(43, 12);
                        ch[2].noteOn(50, 10);
                        ch[3].noteOn(26, 9);
                        ch[3].noteOn(38, 8);
                        idx = 0;
                    }
                }
            } catch (InterruptedException e) {
                if (ch[2] != null) { ch[2].allNotesOff(); }
                if (ch[3] != null) { ch[3].allNotesOff(); }
                Thread.currentThread().interrupt();
            }
        });
        musicThread.setDaemon(true);
        musicThread.start();
    }

    /* ─── VICTORIA: fanfarria oscura con metales y percusion ─── */
    public void reproducirVictoria() {
        detenerTodaMusica();
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[0].programChange(61);   // Brass
                ch[1].programChange(56);   // Trumpet
                ch[2].programChange(47);   // Timpani
                int[] fanfarria = {60,64,67,72,76,72,76,79,84,79,84,88,84,88,91,96};
                int[] ritmo =     {300,200,300,400,300,200,300,400,300,200,300,400,200,200,300,600};
                for (int i = 0; i < fanfarria.length; i++) {
                    ch[0].noteOn(fanfarria[i]-12, 65);
                    ch[1].noteOn(fanfarria[i], 75);
                    if (i % 4 == 0) {
                        ch[2].noteOn(38, 55);
                        Thread.sleep(80);
                        ch[2].noteOff(38);
                    }
                    Thread.sleep(ritmo[i]);
                    ch[0].noteOff(fanfarria[i]-12);
                    ch[1].noteOff(fanfarria[i]);
                }
                Thread.sleep(300);
                for (int n : new int[]{84,88,91,96}) {
                    ch[0].noteOn(n-12, 70);
                    ch[1].noteOn(n, 85);
                }
                ch[2].noteOn(35, 60);
                Thread.sleep(200);
                ch[2].noteOff(35);
                ch[2].noteOn(40, 60);
                Thread.sleep(200);
                ch[2].noteOff(40);
                ch[2].noteOn(35, 70);
                Thread.sleep(200);
                ch[2].noteOff(35);
                ch[2].noteOn(40, 80);
                Thread.sleep(600);
                for (int n : new int[]{84,88,91,96}) {
                    ch[0].noteOff(n-12);
                    ch[1].noteOff(n);
                }
                ch[2].noteOff(40);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    /* ─── DERROTA: cello solo descendente + pads funebres ─── */
    public void reproducirDerrota() {
        detenerTodaMusica();
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[0].programChange(42);   // Cello
                ch[1].programChange(48);   // Strings
                ch[2].programChange(99);   // FX
                int[] melodia = {60,57,55,52,57,55,52,48,55,52,48,45,52,48,45,43};
                int[] ritmo =   {500,400,400,500,400,400,500,600,400,400,500,400,400,500,500,1000};
                int[] campanas = {0,0,0,60,0,0,0,55,0,0,0,52,0,0,0,48};
                ch[1].noteOn(40, 15);
                ch[1].noteOn(45, 12);
                ch[1].noteOn(52, 10);
                for (int i = 0; i < melodia.length; i++) {
                    ch[0].noteOn(melodia[i], 50);
                    if (campanas[i] > 0) {
                        ch[2].noteOn(campanas[i], 35);
                        Thread.sleep(200);
                        ch[2].noteOff(campanas[i]);
                    }
                    Thread.sleep(ritmo[i]);
                    ch[0].noteOff(melodia[i]);
                }
                ch[1].allNotesOff();
                Thread.sleep(800);
                ch[2].noteOn(48, 25);
                ch[0].noteOn(52, 25);
                Thread.sleep(1500);
                ch[2].noteOff(48);
                ch[0].noteOff(52);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    /* ─── EFECTOS ─── */
    public void efectoAtaque() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(122); // Breath/noise para silbido de corte
                ch[8].noteOn(84, 45);
                ch[9].noteOn(49, 30);
                Thread.sleep(35);
                ch[8].noteOff(84);
                ch[8].programChange(56); // Brass hit metalico
                ch[8].noteOn(79, 78);
                Thread.sleep(45);
                ch[8].noteOff(79);
                ch[8].noteOn(74, 65);
                Thread.sleep(55);
                ch[8].noteOff(74);
                ch[9].noteOff(49);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoDano() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(63);
                ch[8].noteOn(48, 60);
                ch[9].noteOn(41, 45);
                Thread.sleep(60);
                ch[8].noteOff(48);
                ch[8].noteOn(43, 55);
                ch[9].noteOff(41);
                Thread.sleep(80);
                ch[8].noteOff(43);
                ch[8].noteOn(40, 50);
                Thread.sleep(100);
                ch[8].noteOff(40);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoPuerta() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(71);
                ch[8].noteOn(40, 35);
                Thread.sleep(200);
                ch[8].noteOff(40);
                ch[8].noteOn(36, 30);
                Thread.sleep(150);
                ch[8].noteOff(36);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoRecoger() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(9);
                ch[8].noteOn(76, 50);
                Thread.sleep(60);
                ch[8].noteOff(76);
                ch[8].noteOn(80, 55);
                Thread.sleep(60);
                ch[8].noteOff(80);
                ch[8].noteOn(84, 55);
                Thread.sleep(50);
                ch[8].noteOff(84);
                ch[8].noteOn(88, 50);
                Thread.sleep(120);
                ch[8].noteOff(88);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoPaso() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[9].noteOn(36, 18);
                ch[8].programChange(117);
                ch[8].noteOn(36, 12);
                Thread.sleep(35);
                ch[8].noteOff(36);
                ch[9].noteOff(36);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoPocion() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(9);
                ch[8].noteOn(72, 55);
                Thread.sleep(80);
                ch[8].noteOff(72);
                ch[8].noteOn(76, 60);
                Thread.sleep(80);
                ch[8].noteOff(76);
                ch[8].noteOn(80, 65);
                Thread.sleep(80);
                ch[8].noteOff(80);
                ch[8].noteOn(84, 70);
                Thread.sleep(200);
                ch[8].noteOff(84);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }

    public void efectoError() {
        if (!disponible) return;
        new Thread(() -> {
            try {
                ch[8].programChange(72);
                ch[8].noteOn(45, 45);
                Thread.sleep(250);
                ch[8].noteOff(45);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }).start();
    }
}
