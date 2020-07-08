package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Foto;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Producto;
import edu.pucmm.market.data.ProductoExistencia;
import edu.pucmm.market.data.Usuario;
import io.javalin.Javalin;

public class AdministrarControlador extends ServerHandler {

    public AdministrarControlador(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {
	getApp().routes(() -> {
	    path("/administrar", () -> {
		before(ctx -> {
		    if ((Usuario) ctx.sessionAttribute("usuario") == null) {
			ctx.redirect("/cuenta/logear");
		    }
		});

		get("/productos", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencias", getMercado().buscarExistencias());
		    modelo.put("carrito_cantidad", (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crud.html", modelo);
		});

		post("/producto/eliminar", ctx -> {
		    int id = Integer.parseInt(ctx.formParam("id"));

		    getMercado().quitar(getMercado().buscarProducto(id));

		    ctx.redirect("/administrar/productos");
		});

		get("/producto/crear", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencia",
			    new ProductoExistencia(new Producto("", "", new HashSet<Foto>()), new BigDecimal(0)));
		    modelo.put("carrito_cantidad", (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/crear-editar.html", modelo);
		});

		get("/producto/editar", ctx -> {
		    int id;

		    if (ctx.queryParam("id") == null
			    || getMercado().buscarExistencia(Integer.valueOf(ctx.queryParam("id"))) == null) {
			ctx.redirect("/administrar/producto/crear");
		    } else {
			id = Integer.parseInt(ctx.queryParam("id"));
			Map<String, Object> modelo = new HashMap<String, Object>();
			modelo.put("usuario", ctx.sessionAttribute("usuario"));
			modelo.put("existencia", getMercado().buscarExistencia(id));
			modelo.put("carrito_cantidad", (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

			ctx.render("/html/crear-editar.html", modelo);
		    }
		});

		post("/producto/crear-editar", ctx -> {
		    int id = Integer.valueOf(ctx.formParam("id"));
		    String nombre = ctx.formParam("nombre");
		    String descripcion = ctx.formParam("descripcion");
		    BigDecimal precio = BigDecimal.valueOf(Double.valueOf(ctx.formParam("precio")));
		    Set<Foto> fotos = new HashSet<Foto>();

		    ctx.uploadedFiles("foto").forEach(uploadedFile -> {
			try {
			    byte[] bytes = uploadedFile.getContent().readAllBytes();
			    String encodedString = Base64.getEncoder().encodeToString(bytes);
			    Foto foto = new Foto(uploadedFile.getFilename(), uploadedFile.getContentType(),
				    encodedString);
			    fotos.add(foto);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    });

		    getMercado().crearEditarProducto(id, nombre, descripcion, precio, fotos);

		    ctx.redirect("/administrar/productos");
		});

		get("/producto/crear-editar/foto/eliminar", ctx -> {
		    int id;
		    String nombre;

		    if (ctx.queryParam("id") == null || ctx.queryParam("nombre") == null) {
			ctx.redirect("/administrar/productos");
		    } else {
			id = Integer.parseInt(ctx.queryParam("id"));
			nombre = ctx.queryParam("nombre");
			
			getMercado().eliminarFotoProducto(id, nombre);

			ctx.redirect("/administrar/producto/editar?id=" + ctx.queryParam("id"));
		    }
		});

		get("/info/ventas", ctx -> {
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("ventas", getMercado().buscarVentas());
		    modelo.put("carrito_cantidad", (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    ctx.render("/html/ventas.html", modelo);
		});
	    });
	});
    }
}