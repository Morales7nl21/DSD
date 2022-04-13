import java.io.*;
import java.net.*;
import java.util.Date;
import java.math.BigInteger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Scanner;
import java.sql.Timestamp;
import java.lang.reflect.Type;
import java.util.Base64;
import com.google.gson.*;

class JavaWebS {
    public static class Usuario {
        String nombre, email, apellido_paterno, apellido_materno;
        String fecha_nacimiento;
        String telefono;
        String genero;
        byte[] foto = null;

        Usuario(String email, String nombre, String apellido_paterno, String apellido_materno,
                String fecha_nacimiento, String telefono, String genero) {
            this.nombre = nombre;
            this.email = email;
            this.apellido_paterno = apellido_paterno;
            this.apellido_materno = apellido_materno;
            this.fecha_nacimiento = fecha_nacimiento;
            this.telefono = telefono;
            this.genero = genero;

        }

        public String toString() {
            return "Nombre: " + nombre + "\nemail: " + email + "\napellido paterno: " + apellido_paterno
                    + "\napellido materno: " + apellido_materno + "\nfecha nacimiento: " + fecha_nacimiento.toString()
                    + "\ntelefono: " + String.valueOf(telefono) + "\ngenero: " + genero;

        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setApellidoPaterno(String apellido_paterno) {
            this.apellido_paterno = apellido_paterno;
        }

        public void setApellidoMaterno(String apellido_materno) {
            this.apellido_materno = apellido_materno;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFechaNacimiento(String fecha_nacimiento) {
            this.fecha_nacimiento = fecha_nacimiento;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }
    }

    public static class AdaptadorGsonBase64 implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.getEncoder().encodeToString(src));
        }

        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            // jax-rs reemplaza cada "+" por " ", pero el decodificador Base64 no reconoce "
            // "
            String s = json.getAsString().replaceAll("\\ ", "+");
            return Base64.getDecoder().decode(s);
        }
    }

    public static void registra_usuario() {
        Usuario nuevoUsuario = new Usuario("", "", "", "", "", "", "");
        Scanner datosUsuarioReader = new Scanner(System.in);
        System.out.println("Indique el email de usuario");
        String dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setEmail(dus);
        System.out.println("Indique el nombre de usuario");
        String dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setNombre(dus);
        System.out.println("Indique el apellido paterno");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setApellidoPaterno(dus);
        System.out.println("Indique el apellido materno");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setApellidoMaterno(dus);

        System.out.println("Indique el telefono");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setTelefono(dus);

        System.out.println("Indique el genero (M/F)");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setGenero(dus);

        System.out.println("Indique la fecha de nacimiento yyyy-MM-dd HH:mm:ss.SS");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            nuevoUsuario.setFechaNacimiento(dus);
        Gson gson = new Gson();
        String usuarioJson = gson.toJson(nuevoUsuario);
        try {
            URL url = new URL("http://20.232.32.191:8080/Servicio/rest/ws/alta_usuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "usuario=" + URLEncoder.encode(usuarioJson, "UTF-8");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            if (conexion.getResponseCode() == 200) {
                System.out.println("OK");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                String respuesta;
                while ((respuesta = br.readLine()) != null)
                    System.out.println(respuesta);
            }
            conexion.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifica_usuario(Usuario usuario) {
        Scanner datosUsuarioReader = new Scanner(System.in);
        System.out.println("Indique el nombre de usuario");
        String dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setNombre(dus);
        System.out.println("Indique el apellido paterno");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setApellidoPaterno(dus);
        System.out.println("Indique el apellido materno");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setApellidoMaterno(dus);

        System.out.println("Indique el telefono");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setTelefono(dus);

        System.out.println("Indique el genero (M/F)");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setGenero(dus);

        System.out.println("Indique la fecha de nacimiento yyyy-MM-dd HH:mm:ss.SS");
        dus = datosUsuarioReader.nextLine();
        if (!dus.isEmpty())
            usuario.setFechaNacimiento(dus);
        Gson gson = new Gson();
        String usuarioJson = gson.toJson(usuario);

        try {
            URL url = new URL("http://20.232.32.191:8080/Servicio/rest/ws/modifica_usuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setDoOutput(true);
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "usuario=" + URLEncoder.encode(usuarioJson, "UTF-8");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            if (conexion.getResponseCode() == 200) {
                System.out.println("OK");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                String respuesta;
                while ((respuesta = br.readLine()) != null)
                    System.out.println(respuesta);
            }
            conexion.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void consulta_usuario() {
        try {
            // URL url = new
            URL url = new URL("http://20.232.32.191:8080/Servicio/rest/ws/consulta_usuario");
            // URL url = new URL("http://20.232.32.191:8080/prueba.html");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            // true si se va a enviar un "body", en este caso el "body" son los parámetros
            conexion.setDoOutput(true);
            // en este caso utilizamos el método POST de HTTP
            conexion.setRequestMethod("POST");
            // indica que la petición estará codificada como URL
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // el método web "consulta_usuario" recibe como parámetro el email de un
            // usuario, en este caso el email es a@c
            Scanner leer = new Scanner(System.in);
            System.out.println("Introduce el correo electronico para hacer la consulta: ");
            String emalToRead = leer.nextLine();
            String parametros = "email=" + URLEncoder.encode(emalToRead, "UTF-8");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();// se debe verificar si hubo error
            Usuario usuario = null;
            if (conexion.getResponseCode() == 200) {
                // no hubo error

                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
                String respuesta;
                // el método web regresa una string en formato JSON
                while ((respuesta = br.readLine()) != null) {
                    // System.out.println(respuesta);
                    Gson gson = new GsonBuilder().registerTypeAdapter(byte[].class, new AdaptadorGsonBase64())
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
                    usuario = (Usuario) gson.fromJson(respuesta, Usuario.class);
                }
                System.out.println(usuario.toString());

            } else {
                // hubo error
                BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getErrorStream())));
                String respuesta; // el método web regresa una instancia de la clase Error en formato JSON
                while ((respuesta = br.readLine()) != null)
                    System.out.println(respuesta);
            }
            Scanner lecturaModificacion = new Scanner(System.in);
            String modificaPregunta;
            System.out.println("Desea modificar el usuario (s/n) ?: ");
            modificaPregunta = lecturaModificacion.nextLine();
            if (modificaPregunta.equals("s")) {
                conexion.disconnect();
                modifica_usuario(usuario);
                // conexion.disconnect();
            } else if (modificaPregunta.equals("n")) {
                System.out.println("");
            } else {
                System.err.println("respuesta no valida");

            }
            conexion.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        char opc = 'X';
        Scanner leer = new Scanner(System.in);
        while (opc != 'd') {
            System.out.print("MENU\nSelecciona una opcion:  \n");
            System.out.println("\ta. Alta usuario\n\tb. Consulta usuario");
            System.out.println("\tc. Borra usuario\n\td.Salir");
            opc = leer.next().charAt(0);
            switch (opc) {
                case 'a':
                    registra_usuario();
                    break;
                case 'b':
                    consulta_usuario();
                    break;
                case 'c':
                    break;
                case 'd':
                    break;
                default:
                    break;
            }
        }
        leer.close();
    }

}
