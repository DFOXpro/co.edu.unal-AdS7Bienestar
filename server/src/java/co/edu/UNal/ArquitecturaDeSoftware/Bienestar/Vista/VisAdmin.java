package co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Vista;

import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.AccesoDatos.Entity.UsuarioEntity;
import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.Control.Cuentas.CtrlAdmin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author awake
 */
public class VisAdmin extends VisUsuario{
    CtrlAdmin ctrlAdmin = new CtrlAdmin();

    protected void crearUsuario(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        System.out.println(request.getParameter("5"));
        ArrayList r = CtrlAdmin.crearUsuario(
                request.getParameter("1"),//Nombre
                request.getParameter("2"),//Apellidos
                request.getParameter("3"),//Tipo de documento
                Integer.parseInt(request.getParameter("4")),//documento
                request.getParameter("5"),//Correo= usuario
                request.getParameter("6"),//passworld
                request.getParameter("7").charAt(0)//rol
        );

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                if(r.get(1)=="usuario"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El usuario ya existe");
                } else if(r.get(1)=="contrasena"){
                        obj.put("isError",true); 
                        obj.put("errorDescrip","La contraseña es invalida");
                } else if(r.get(1)=="documento"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El documento ya está registrado");
                } else if(r.get(1)=="tipoDocumento"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El tipo de documento es invalido");
                } else if(r.get(1)=="correo"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El correo ya está registrado");
                } else if(r.get(1)=="correo1"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El correo no es valido");
                } else if(r.get(1)=="nombre"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","Los nombres o apellidos son incorrectos");
                } else if(r.get(1)=="rol"){
                        obj.put("isError",true);
                        obj.put("errorDescrip","El rol es invalido, los posibles valores son: E, P y A");
                } else Util.errordeRespuesta(r, out);
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);
    }

    protected void editarUsuario(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        System.out.println(request.getParameter("5"));
        ArrayList r = CtrlAdmin.editarUsuario(
                Integer.parseInt(request.getParameter("0")),//IdUsuario
                request.getParameter("1"),//Nombre
                request.getParameter("2"),//Apellidos
                request.getParameter("3"),//Tipo de documento
                Integer.parseInt(request.getParameter("4")),//documento
                request.getParameter("5"),//Correo= usuario
                request.getParameter("6"),//passworld
                request.getParameter("7").charAt(0)//rol
        );

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		JSONObject obj=new JSONObject();
        if(r.get(0)=="error"){
				obj.put("isError",true);
				boolean errorSerio = false;
                if(r.get(1)=="usuario"){
                        obj.put("errorDescrip","El usuario ya existe");
                } else if(r.get(1)=="contrasena"){
                        obj.put("errorDescrip","La contraseña es invalida");
                } else if(r.get(1)=="documento"){
                        obj.put("errorDescrip","El documento ya está registrado");
                } else if(r.get(1)=="tipoDocumento"){
                        obj.put("errorDescrip","El tipo de documento es invalido");
                } else if(r.get(1)=="correo"){
                        obj.put("errorDescrip","El correo ya está registrado");
                } else if(r.get(1)=="correo1"){
                        obj.put("errorDescrip","El correo no es valido");
                } else if(r.get(1)=="nombre"){
                        obj.put("errorDescrip","Los nombres o apellidos son incorrectos");
                } else if(r.get(1)=="rol"){
                        obj.put("errorDescrip","El rol es invalido, los posibles valores son: E, P y A");
                } else {
					Util.errordeRespuesta(r, out);
					errorSerio = true;
				}
				if(!errorSerio) out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);
    }
    
    protected void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = CtrlAdmin.eliminarUsuario(Integer.parseInt(request.getParameter("1"))); // id_usuario

        response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            if(r.get(0)=="error"){
                    JSONObject obj=new JSONObject();
                    if(r.get(1)=="usuario"){
                            obj.put("isError",true);
                            obj.put("errorDescrip","El usuario no existe");

                    } else Util.errordeRespuesta(r, out);
                    out.print(obj);
            } else if(r.get(0)=="isExitoso"){
                    JSONObject obj=new JSONObject();
                    obj.put("Exitoso",true);
                    out.print(obj);
            } else Util.errordeRespuesta(r, out);
    }
    
    protected void leerUsuarioId(HttpServletRequest request, HttpServletResponse response) throws IOException{
        UsuarioEntity e = ctrlAdmin.leerUsuarioId(Integer.parseInt(request.getParameter("1"))); // id del usuario
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        JSONObject obj = new JSONObject();
        obj.put("id", e.getIdUsuario());
        obj.put("nombre", e.getNombres());
        obj.put("apellido", e.getApellidos());
        obj.put("tipoDocumento", e.gettDocumento());
        obj.put("documento", e.getDocumento());
        obj.put("contrasena", e.getPassword());
        obj.put("rol", ""+e.getRol());
        obj.put("email", e.getUsername());
        out.print(obj);
    } 
    
    protected void leerUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException{
        UsuarioEntity e = ctrlAdmin.leerUsuario(request.getParameter("1")); // String nombre del usuario
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        JSONObject obj = new JSONObject();
        obj.put("id", e.getIdUsuario());
        obj.put("nombre", e.getNombres());
        obj.put("apellido", e.getApellidos());
        obj.put("tipoDocumento", e.gettDocumento());
        obj.put("documento", e.getDocumento());
        obj.put("contrasena", e.getPassword());
        obj.put("rol", e.getRol());
        out.print(obj);
    }
        
    protected void leerUsuariosMultiplesId(HttpServletRequest request, HttpServletResponse response) throws IOException{  
        ArrayList<UsuarioEntity> usuarios = new ArrayList<>();
        usuarios = ctrlAdmin.leerMultiplesUsuarios(Integer.parseInt(request.getParameter("1")), Integer.parseInt(request.getParameter("2"))); // id del usuario
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        JSONArray list1 = new JSONArray();
        for(int i = 0; i < usuarios.size(); i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", usuarios.get(i).getIdUsuario());
            obj.put("titulo", usuarios.get(i).getNombres() +" "+usuarios.get(i).getApellidos());
            list1.add(obj);
        }
        out.print(list1);
    }
    
    protected void obtenerTotalUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int numC = ctrlAdmin.obtenerTotalUsuarios();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        obj.put("total", numC);
        out.print(obj);
    }
    
    protected void crearConvocatoria(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.crearConvocatoria(
            request.getParameter("1"), // nombre
            request.getParameter("2"), // descripción
            request.getParameter("3"), // fin
            Integer.parseInt(request.getParameter("4")) // cupos
        );
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);
    }
    
    protected void actualizarConvocatoria(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.actualizarConvocatoria(
            Integer.parseInt(request.getParameter("1")), // id
            request.getParameter("2"), // nombre
            request.getParameter("3"), // descripción
            request.getParameter("4"), // fin
            Integer.parseInt(request.getParameter("5")) // cupos
        );
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);        
    }

    protected void eliminarConvocatoria(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.eliminarConvocatoria(Integer.parseInt(request.getParameter("1"))); // id
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);  
    }
        
    protected void crearTaller(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.crearTaller(
            request.getParameter("1"), // nombre
            request.getParameter("2"), // descripción
            request.getParameter("3"), // fin registro (Fecha hasta donde está permitido registrarse)
            request.getParameter("4"), // inicio del taller
            request.getParameter("5"), // fin del taller
            Integer.parseInt(request.getParameter("6")), // costo
            Integer.parseInt(request.getParameter("7")) // cupos
        );
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);
    }
    
    protected void actualizarTaller(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.actualizarTaller(
            Integer.parseInt(request.getParameter("1")), // id
            request.getParameter("2"), // nombre
            request.getParameter("3"), // descripción
            request.getParameter("4"), // fin registro (Fecha hasta donde está permitido registrarse)
            request.getParameter("5"), // inicio del taller
            request.getParameter("6"), // fin del taller
            Integer.parseInt(request.getParameter("6")), // costo
            Integer.parseInt(request.getParameter("7")) // cupos
        );
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);        
    }

    protected void eliminarTaller(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList r = ctrlAdmin.eliminarTaller(Integer.parseInt(request.getParameter("1"))); // id
         
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(r.get(0)=="error"){
                JSONObject obj=new JSONObject();
                obj.put("isError",true);
                obj.put("errorDescrip",r.get(1));
                out.print(obj);
        } else if(r.get(0)=="isExitoso"){
                JSONObject obj=new JSONObject();
                obj.put("Exitoso",true);
                out.print(obj);
        } else Util.errordeRespuesta(r, out);  
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (null != request.getParameter("tipo"))
            switch (request.getParameter("tipo")) {
//CRUD USUARIO
                case "crearUsuario":{
                        crearUsuario(request, response);
                        break;
                }case "editarUsuario":{
                        editarUsuario(request, response);
                        break;
                }case "eliminarUsuario":{
                        eliminarUsuario(request, response);
                        break;
                }case "Usuario":{
                        leerUsuarioId(request, response);
                        break;
                }case "usuarios":{
                        leerUsuariosMultiplesId(request, response);
                        break;
                }

//CRUD CONVOCATORIA
                case "convocatoria":{
                        leerConvocatoria(request, response);
                        break;
                }case "/evento/convocatorias":{
                        leerMultiplesConvocatorias(request, response);
                        break;
                }case "numConvocatorias":{
                        obtenerTotalConvocatorias(request, response);
                        break;
                }case "inscritosConvocatoria":{
                        obtenerInscritosConv(request, response);
                        break;
                } case "crearConvocatoria":{
                        crearConvocatoria(request,response);
                        break;
                } case "actualizarConvocatoria":{
                        actualizarConvocatoria(request,response);
                        break;
                } case "eliminarConvocatoria":{
                        eliminarConvocatoria(request,response);
                        break;
				} case "regUsuarioConv":{
                        registrarUsuarioConvocatoria(request,response);
                        break;
                } case "eleminarUsuarioConv":{
                        quitarUsuarioConvocatoria(request, response);
                        break;
                }

//CRUD TALLER
				case "taller":{
                        leerTaller(request, response);
						break;
                }case "/evento/talleres":{
                        leerMultiplesTalleres(request, response);
                        break;
                } case "numTalleres":{
                        obtenerTotalTalleres(request,response);
                        break;
                } case "inscritosTaller":{
                        obtenerInscritosTaller(request,response);
                        break;
                } case "crearTaller":{
                        crearTaller(request,response);
                        break;
                } case "actualizarTaller":{
                        actualizarTaller(request,response);
                        break;
                } case "eliminarTaller":{
                        eliminarTaller(request,response);
                        break;
                } case "regUsuarioTaller":{
                        registrarUsuarioTaller(request,response);
                        break;
                } case "regDocente":{
                        registrarDocenteTaller(request, response);
                        break;
                } case "regDocenteDocumento":{
                        registrarATallerDocenteByDoc(request, response);
                        break;
                } case "eliminarUsuarioTaller":{
                        quitarUsuarioTaller(request, response);
                        break;
                }
                default:
                        System.err.print("tipo de request invalido: " + request.getParameter("tipo"));
            }
        else System.err.print("VisAdmin.doPost: request inválido");
    }

    /**
     * Returns a short description of the servlet
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
            return "Vista de Cuenta.Admin";
    }
}
