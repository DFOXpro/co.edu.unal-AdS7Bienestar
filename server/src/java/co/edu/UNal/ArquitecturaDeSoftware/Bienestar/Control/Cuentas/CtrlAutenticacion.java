package co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Control.Cuentas;

import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Control.Cuentas.Util.Sesion;
import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Control.Cuentas.Util.Activas;
import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Control.Cuentas.Util.Cifrado;
import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Modelo.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dfoxpro
 */
public class CtrlAutenticacion {

    private static Map<String,String> esperandoLlave;

    public static ArrayList autenticar(
        String usuario,
        String contrasena,
        String cookieHashCode
    ){
        //Usuario u = Persistencia.getUsuario(usuario);
        //@TODO: Persistencia
//TEST
        Usuario u = new Usuario();
        u.setNombre("Nametest");
        u.setUsuario("usertest@unal.edu.co");
        u.setContrasena("test1");
        u.setRol('a');//a dministrador
//END TEST
        try {
            usuario = new String(Cifrado.decodeBASE64(usuario), "UTF-8");
            contrasena = new String(Cifrado.decodeBASE64(contrasena), "UTF-8");
            cookieHashCode = new String(Cifrado.decodeBASE64(cookieHashCode), "UTF-8");
			if (u.getUsuario().equals(usuario)) {
                if (u.getContrasena().equals(contrasena)) {
                    Sesion s = Activas.agregarSesion(usuario, cookieHashCode);
					if (esperandoLlave == null)
						esperandoLlave = new HashMap<>();
					esperandoLlave.put(cookieHashCode, usuario);

                    ArrayList r = new ArrayList();
                    r.add("exitoso");
                    r.add(u.getNombre());
                    r.add(u.getRol());
                    r.add(s.getLlavesServer().publicaToStr());//Devuelve la llave publica generada
                    return r;
                } else {
                    ArrayList r = new ArrayList();
                    r.add("error");
                    r.add("contrasena");
                    return r;
                }
            } else {
                ArrayList r = new ArrayList();
                r.add("error");
                r.add("usuario");
                return r;
            }
        } catch (Exception ex) {
            Logger.getLogger(CtrlAutenticacion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void cerrarSesion(String usuario, String cookieHashCode) {
		try {
			Activas.cerrarSesion(
				new String(Cifrado.decodeBASE64(usuario), "UTF-8"),
				cookieHashCode
			);
		} catch (Exception ex) {
			Logger.getLogger(CtrlAutenticacion.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

	public static ArrayList confirmarCifrado(
        String llaveCliente,
        String cookieHashCode
    ){
		String s = esperandoLlave.get(cookieHashCode);
        if(s != null)
            Activas.getSesion(s).setLlaveCliente(llaveCliente);
        else System.out.println("Warning!: posible ataque en confirmarCifrado: "+cookieHashCode);
		return null;
	}

	public static boolean redirije(String cookieHashCode){
		return esperandoLlave.get(cookieHashCode) != null;
	}
}