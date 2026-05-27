package practicafinal.persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import practicafinal.model.Celda;
import practicafinal.model.Enemigo;
import practicafinal.model.EstadoPartida;
import practicafinal.model.Habitacion;
import practicafinal.model.Jugador;
import practicafinal.model.Objeto;
import practicafinal.model.Partida;
import practicafinal.model.TipoCelda;
import practicafinal.model.TipoObjeto;
import practicafinal.structures.Grafo;
import practicafinal.structures.ListaEnlazada;

public class GestorPersistencia {
    private final Gson gson;

    public GestorPersistencia() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(ListaEnlazada.class, new ListaEnlazadaAdapter());
        builder.registerTypeAdapter(Grafo.class, new GrafoAdapter());
        builder.registerTypeAdapter(Habitacion.class, new HabitacionAdapter());
        this.gson = builder.create();
    }

    private static class ListaEnlazadaAdapter implements JsonSerializer<ListaEnlazada<?>>,
                                                           JsonDeserializer<ListaEnlazada<?>> {
        @Override
        public JsonElement serialize(ListaEnlazada<?> src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray array = new JsonArray();
            for (int i = 0; i < src.tamano(); i++)
                array.add(context.serialize(src.obtener(i)));
            return array;
        }

        @Override
        public ListaEnlazada<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ListaEnlazada<Object> lista = new ListaEnlazada<>();
            JsonArray array = json.getAsJsonArray();
            Type elemType = Object.class;
            if (typeOfT instanceof java.lang.reflect.ParameterizedType) {
                Type[] args = ((java.lang.reflect.ParameterizedType) typeOfT).getActualTypeArguments();
                if (args.length > 0) elemType = args[0];
            }
            for (JsonElement element : array)
                lista.agregar(context.deserialize(element, elemType));
            return lista;
        }
    }

    private static class GrafoAdapter implements JsonSerializer<Grafo>, JsonDeserializer<Grafo> {
        @Override
        public JsonElement serialize(Grafo src, Type typeOfSrc, JsonSerializationContext context) {
            ListaEnlazada vertices = src.obtenerVertices();
            JsonArray jsonVertices = new JsonArray();
            for (int i = 0; i < vertices.tamano(); i++)
                jsonVertices.add(context.serialize(vertices.obtener(i)));
            JsonArray jsonAristas = new JsonArray();
            for (int i = 0; i < vertices.tamano(); i++) {
                Object v = vertices.obtener(i);
                ListaEnlazada vecinos = src.obtenerVecinos(v);
                for (int j = 0; j < vecinos.tamano(); j++) {
                    Object w = vecinos.obtener(j);
                    int idxV = indiceEnLista(vertices, v);
                    int idxW = indiceEnLista(vertices, w);
                    if (idxV < idxW) {
                        JsonArray par = new JsonArray();
                        par.add(context.serialize(v));
                        par.add(context.serialize(w));
                        jsonAristas.add(par);
                    }
                }
            }
            JsonObject obj = new JsonObject();
            obj.add("vertices", jsonVertices);
            obj.add("aristas", jsonAristas);
            return obj;
        }

        @Override
        public Grafo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            Grafo grafo = new Grafo();
            if (obj.has("vertices")) {
                JsonArray jsonVertices = obj.getAsJsonArray("vertices");
                for (JsonElement e : jsonVertices)
                    grafo.agregarVertice(context.deserialize(e, Object.class));
            }
            if (obj.has("aristas")) {
                JsonArray jsonAristas = obj.getAsJsonArray("aristas");
                for (JsonElement e : jsonAristas) {
                    JsonArray par = e.getAsJsonArray();
                    Object origen = context.deserialize(par.get(0), Object.class);
                    Object destino = context.deserialize(par.get(1), Object.class);
                    grafo.agregarArista(origen, destino);
                }
            }
            return grafo;
        }

        private static int indiceEnLista(ListaEnlazada lista, Object dato) {
            for (int i = 0; i < lista.tamano(); i++)
                if (lista.obtener(i).equals(dato)) return i;
            return -1;
        }
    }

    private static class HabitacionAdapter implements JsonSerializer<Habitacion>, JsonDeserializer<Habitacion> {
        @Override
        public JsonElement serialize(Habitacion src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", src.getId());
            obj.addProperty("filas", src.getFilas());
            obj.addProperty("columnas", src.getColumnas());
            JsonArray celdas = new JsonArray();
            for (int f = 0; f < src.getFilas(); f++) {
                for (int c = 0; c < src.getColumnas(); c++) {
                    JsonObject celdaObj = new JsonObject();
                    celdaObj.addProperty("fila", f);
                    celdaObj.addProperty("columna", c);
                    celdaObj.addProperty("tipo", src.getCelda(f, c).getTipo().name());
                    celdaObj.addProperty("ocupada", src.getCelda(f, c).estaOcupada());
                    celdas.add(celdaObj);
                }
            }
            obj.add("celdas", celdas);
            return obj;
        }

        @Override
        public Habitacion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String id = obj.get("id").getAsString();
            int filas = obj.get("filas").getAsInt();
            int columnas = obj.get("columnas").getAsInt();
            Habitacion hab = new Habitacion(id, filas, columnas);
            JsonArray celdas = obj.getAsJsonArray("celdas");
            for (JsonElement e : celdas) {
                JsonObject celdaObj = e.getAsJsonObject();
                int f = celdaObj.get("fila").getAsInt();
                int c = celdaObj.get("columna").getAsInt();
                String tipo = celdaObj.get("tipo").getAsString();
                boolean ocupada = celdaObj.get("ocupada").getAsBoolean();
                Celda celda = new Celda(TipoCelda.valueOf(tipo));
                celda.setOcupada(ocupada);
                hab.setCelda(f, c, celda);
            }
            return hab;
        }
    }

    public ConfiguracionDTO cargarConfiguracion(String rutaArchivo) throws IOException {
        try (InputStreamReader reader = abrirConfiguracion(rutaArchivo)) {
            ConfiguracionDTO config = gson.fromJson(reader, ConfiguracionDTO.class);
            if (config == null) throw new IOException("Archivo de configuracion vacio o invalido");
            validarConfiguracion(config);
            return config;
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new IOException("Error al parsear JSON de configuracion: " + e.getMessage(), e);
        }
    }

    void validarConfiguracionParaTest(ConfiguracionDTO config) throws IOException {
        validarConfiguracion(config);
    }

    private InputStreamReader abrirConfiguracion(String rutaArchivo) throws IOException {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) return new FileReader(archivo, StandardCharsets.UTF_8);

        String recurso = rutaArchivo;
        int idx = recurso.replace('\\', '/').lastIndexOf('/');
        if (idx >= 0) recurso = recurso.substring(idx + 1);
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(recurso);
        if (stream == null) stream = GestorPersistencia.class.getResourceAsStream("/" + recurso);
        if (stream == null) throw new IOException("No se encuentra la configuracion: " + rutaArchivo);
        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    private void validarConfiguracion(ConfiguracionDTO config) throws IOException {
        if (config.getHabitaciones() == null || config.getHabitaciones().estaVacia())
            throw new IOException("La configuracion debe incluir al menos una habitacion");
        if (config.getJugador() == null)
            throw new IOException("La configuracion debe incluir datos del jugador");
        if (config.getTurnosMaximos() <= 0)
            throw new IOException("turnosMaximos debe ser mayor que cero");

        for (int i = 0; i < config.getHabitaciones().tamano(); i++) {
            ConfiguracionDTO.DatosHabitacionDTO h = config.getHabitaciones().obtener(i);
            if (h.getId() == null || h.getId().trim().isEmpty())
                throw new IOException("Hay una habitacion sin id");
            if (h.getFilas() <= 0 || h.getColumnas() <= 0)
                throw new IOException("Dimensiones invalidas en habitacion " + h.getId());
            for (int j = i + 1; j < config.getHabitaciones().tamano(); j++) {
                if (h.getId().equals(config.getHabitaciones().obtener(j).getId()))
                    throw new IOException("Id de habitacion duplicado: " + h.getId());
            }
            if (h.getCeldas() != null) {
                for (int c = 0; c < h.getCeldas().tamano(); c++) {
                    ConfiguracionDTO.DatoCeldaDTO celda = h.getCeldas().obtener(c);
                    validarPosicion(h, celda.getFila(), celda.getColumna(), "celda");
                    validarTipoCelda(celda.getTipo(), h.getId());
                }
            }
            if (h.getConexiones() != null) {
                for (int c = 0; c < h.getConexiones().tamano(); c++) {
                    if (buscarHabitacionDTO(config, h.getConexiones().obtener(c)) == null)
                        throw new IOException("Conexion desde " + h.getId() + " a habitacion inexistente: " + h.getConexiones().obtener(c));
                }
            }
        }

        ConfiguracionDTO.DatosJugadorInicialDTO jugador = config.getJugador();
        ConfiguracionDTO.DatosHabitacionDTO inicial = buscarHabitacionDTO(config, jugador.getHabitacionInicial());
        if (inicial == null) throw new IOException("La habitacion inicial del jugador no existe: " + jugador.getHabitacionInicial());
        validarPosicion(inicial, jugador.getFila(), jugador.getColumna(), "jugador");
        if (jugador.getVida() <= 0 || jugador.getMovimiento() <= 0)
            throw new IOException("El jugador debe tener vida y movimiento positivos");

        if (config.getEnemigos() != null) {
            for (int i = 0; i < config.getEnemigos().tamano(); i++) {
                ConfiguracionDTO.DatosEnemigoDTO e = config.getEnemigos().obtener(i);
                ConfiguracionDTO.DatosHabitacionDTO h = buscarHabitacionDTO(config, e.getHabitacionId());
                if (h == null) throw new IOException("Enemigo en habitacion inexistente: " + e.getNombre());
                validarPosicion(h, e.getFila(), e.getColumna(), "enemigo " + e.getNombre());
                if (e.getVida() <= 0) throw new IOException("Enemigo con vida invalida: " + e.getNombre());
            }
        }

        if (config.getObjetos() != null) {
            for (int i = 0; i < config.getObjetos().tamano(); i++) {
                ConfiguracionDTO.DatosObjetoDTO o = config.getObjetos().obtener(i);
                ConfiguracionDTO.DatosHabitacionDTO h = buscarHabitacionDTO(config, o.getHabitacionId());
                if (h == null) throw new IOException("Objeto en habitacion inexistente: " + o.getNombre());
                validarPosicion(h, o.getFila(), o.getColumna(), "objeto " + o.getNombre());
                validarTipoObjeto(o.getTipo(), o.getNombre());
            }
        }

        if (config.getPuertas() != null) {
            for (int i = 0; i < config.getPuertas().tamano(); i++) {
                ConfiguracionDTO.DatosPuertaDTO p = config.getPuertas().obtener(i);
                ConfiguracionDTO.DatosHabitacionDTO origen = buscarHabitacionDTO(config, p.getHabitacionOrigen());
                ConfiguracionDTO.DatosHabitacionDTO destino = buscarHabitacionDTO(config, p.getHabitacionDestino());
                if (origen == null || destino == null)
                    throw new IOException("Puerta con origen o destino inexistente");
                validarPosicion(origen, p.getFila(), p.getColumna(), "puerta origen");
                validarPosicion(destino, p.getFilaEntrada(), p.getColumnaEntrada(), "entrada de puerta");
            }
        }
    }

    private ConfiguracionDTO.DatosHabitacionDTO buscarHabitacionDTO(ConfiguracionDTO config, String id) {
        if (id == null || config.getHabitaciones() == null) return null;
        for (int i = 0; i < config.getHabitaciones().tamano(); i++) {
            ConfiguracionDTO.DatosHabitacionDTO h = config.getHabitaciones().obtener(i);
            if (id.equals(h.getId())) return h;
        }
        return null;
    }

    private void validarPosicion(ConfiguracionDTO.DatosHabitacionDTO h, int fila, int columna, String etiqueta) throws IOException {
        if (fila < 0 || columna < 0 || fila >= h.getFilas() || columna >= h.getColumnas())
            throw new IOException("Posicion fuera de limites para " + etiqueta + " en " + h.getId() + ": (" + fila + "," + columna + ")");
    }

    private void validarTipoCelda(String tipo, String habitacionId) throws IOException {
        try {
            TipoCelda.valueOf(tipo);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IOException("Tipo de celda invalido en " + habitacionId + ": " + tipo, e);
        }
    }

    private void validarTipoObjeto(String tipo, String nombre) throws IOException {
        try {
            TipoObjeto.valueOf(tipo);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IOException("Tipo de objeto invalido para " + nombre + ": " + tipo, e);
        }
    }

    public ListaEnlazada<Habitacion> crearHabitacionesDesdeConfig(ConfiguracionDTO config) {
        ListaEnlazada<Habitacion> lista = new ListaEnlazada<>();
        for (int i = 0; i < config.getHabitaciones().tamano(); i++) {
            ConfiguracionDTO.DatosHabitacionDTO dto = config.getHabitaciones().obtener(i);
            Habitacion hab = new Habitacion(dto.getId(), dto.getFilas(), dto.getColumnas());
            if (dto.getCeldas() != null) {
                for (int j = 0; j < dto.getCeldas().tamano(); j++) {
                    ConfiguracionDTO.DatoCeldaDTO celdaDTO = dto.getCeldas().obtener(j);
                    TipoCelda tipo = TipoCelda.valueOf(celdaDTO.getTipo());
                    hab.setCelda(celdaDTO.getFila(), celdaDTO.getColumna(), new Celda(tipo));
                }
            }
            lista.agregar(hab);
        }
        return lista;
    }

    public Grafo<String> crearGrafoDesdeConfig(ConfiguracionDTO config) {
        Grafo<String> grafo = new Grafo<>();
        for (int i = 0; i < config.getHabitaciones().tamano(); i++)
            grafo.agregarVertice(config.getHabitaciones().obtener(i).getId());
        for (int i = 0; i < config.getHabitaciones().tamano(); i++) {
            ConfiguracionDTO.DatosHabitacionDTO dto = config.getHabitaciones().obtener(i);
            if (dto.getConexiones() != null) {
                for (int j = 0; j < dto.getConexiones().tamano(); j++)
                    grafo.agregarArista(dto.getId(), dto.getConexiones().obtener(j));
            }
        }
        return grafo;
    }

    public Jugador crearJugadorDesdeConfig(ConfiguracionDTO config) {
        ConfiguracionDTO.DatosJugadorInicialDTO dto = config.getJugador();
        Jugador jugador = new Jugador("Jugador", dto.getVida(), dto.getAtaque(),
                                       dto.getDefensa(), dto.getMovimiento());
        jugador.setPosicion(dto.getFila(), dto.getColumna());
        return jugador;
    }

    public ListaEnlazada<Enemigo> crearEnemigosDesdeConfig(ConfiguracionDTO config) {
        ListaEnlazada<Enemigo> lista = new ListaEnlazada<>();
        if (config.getEnemigos() == null) return lista;
        for (int i = 0; i < config.getEnemigos().tamano(); i++) {
            ConfiguracionDTO.DatosEnemigoDTO dto = config.getEnemigos().obtener(i);
            Enemigo e = new Enemigo(dto.getNombre(), dto.getVida(), dto.getAtaque(),
                                     dto.getDefensa(), dto.getFila(), dto.getColumna());
            e.setHabitacionId(dto.getHabitacionId());
            lista.agregar(e);
        }
        return lista;
    }

    public ListaEnlazada<Objeto> crearObjetosDesdeConfig(ConfiguracionDTO config) {
        ListaEnlazada<Objeto> lista = new ListaEnlazada<>();
        if (config.getObjetos() == null) return lista;
        for (int i = 0; i < config.getObjetos().tamano(); i++) {
            ConfiguracionDTO.DatosObjetoDTO dto = config.getObjetos().obtener(i);
            Objeto obj = new Objeto(dto.getNombre(), TipoObjeto.valueOf(dto.getTipo()));
            obj.setAtaque(dto.getAtaque());
            obj.setDefensa(dto.getDefensa());
            obj.setCuracion(dto.getCuracion());
            obj.setHabitacionId(dto.getHabitacionId());
            obj.setFila(dto.getFila());
            obj.setColumna(dto.getColumna());
            lista.agregar(obj);
        }
        return lista;
    }

    public Partida crearPartidaDesdeConfig(ConfiguracionDTO config) {
        ConfiguracionDTO.DatosJugadorInicialDTO dtoJug = config.getJugador();
        Jugador jugador = crearJugadorDesdeConfig(config);
        Partida partida = new Partida(jugador, config.getTurnosMaximos());
        partida.setHabitacionActual(dtoJug.getHabitacionInicial());
        partida.setGrafoHabitaciones(crearGrafoDesdeConfig(config));
        ListaEnlazada<Enemigo> enemigos = crearEnemigosDesdeConfig(config);
        partida.setEnemigos(enemigos);
        return partida;
    }

    public Habitacion buscarHabitacion(ListaEnlazada<Habitacion> habitaciones, String id) {
        for (int i = 0; i < habitaciones.tamano(); i++) {
            Habitacion h = habitaciones.obtener(i);
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    public void guardarPartida(Partida partida, String rutaArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(partida, writer);
        } catch (IOException e) {
            throw new IOException("Error al guardar partida: " + e.getMessage(), e);
        }
    }

    public Partida cargarPartida(String rutaArchivo) throws IOException {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Partida partida = gson.fromJson(reader, Partida.class);
            if (partida == null) throw new IOException("Archivo de partida vacio o invalido");
            return partida;
        } catch (JsonSyntaxException | JsonIOException e) {
            throw new IOException("Error al parsear JSON de partida: " + e.getMessage(), e);
        }
    }

    public String toJson(Object objeto) { return gson.toJson(objeto); }
    public <T> T fromJson(String json, Class<T> clase) { return gson.fromJson(json, clase); }
}
