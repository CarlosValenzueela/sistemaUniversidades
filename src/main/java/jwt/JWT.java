package jwt;

import dao.UsuarioJpaController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import static javax.crypto.Cipher.SECRET_KEY;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import objetosNegocio.Usuario;

@Path("auth")
public class JWT {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    private static final UsuarioJpaController usuarioDAO = new UsuarioJpaController(factory);

    private static final String KEY = "secret";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String SCOPE = "scope";
    private static final String SUBJECT = "SistemasUniversidades";

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    /*public String*/ public static String generarJWT(HttpServletResponse res, Usuario usuario) throws UnsupportedEncodingException {

        long tiempo = System.currentTimeMillis();
        String jwt = Jwts.builder()
                .setSubject(SUBJECT)
                .setId(usuario.getId() + "")
                .setExpiration(new Date(tiempo + 600000))
                .claim(USER, usuario.getUser())
                .claim(PASSWORD, usuario.getPassword())
                .claim(SCOPE, "admin")
                .signWith(SignatureAlgorithm.HS256, KEY.getBytes("UTF-8"))
                .compact();

        System.out.println("-------------------->  " + jwt);

        res.setHeader("Authorization", jwt);

        return jwt;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    /*public String*/ public static boolean validarJWT(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {

        String token = (String) req.getSession().getAttribute("token");

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY.getBytes("UTF-8"))
                    .parseClaimsJws(token);

            System.out.println("------------>" + claims);

            String usuario = claims.getBody().get(USER, String.class);
            String password = claims.getBody().get(PASSWORD, String.class);
            String rol = claims.getBody().get(SCOPE, String.class);

            Usuario usuarioSesion = usuarioDAO.consultarUsuarioInicioSesion(usuario, password);

            if (usuarioSesion == null) {
                return false;
            }

            req.getSession().setAttribute("token", generarJWT(res, usuarioSesion));
            
            return true;

        } catch (ExpiredJwtException expiredJwtException) {
            return false;
        } catch (Exception ex) {
            return false;
        }

//                JsonObject json = Json.createObjectBuilder().add("JWT", jwt).build();                        
        //               Response respuesta = Response.status(Response.Status.CREATED).entity(json).build();
    }

}
