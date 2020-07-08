package edu.pucmm.market.handlers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.Map;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Mercado;
import io.javalin.Javalin;

public class ProductoControlador extends ServerHandler {

    public ProductoControlador(Mercado mercado, Javalin app) {
	super(mercado, app);
    }

    @Override
    public void rutas() {

	getApp().routes(() -> {

	    path("/productos", () -> {
		get(ctx -> {
		    int pagina;
		    Map<String, Object> modelo = new HashMap<String, Object>();
		    modelo.put("usuario", ctx.sessionAttribute("usuario"));
		    modelo.put("existencias_cantidad",
			    (int) Math.ceil((double) getMercado().buscarExistencias().size() / 10));
		    modelo.put("carrito_cantidad", (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));

		    if (ctx.queryParam("pagina") != null) {
			pagina = ctx.queryParam("pagina", Integer.class).get();
			modelo.put("existencias", getMercado().buscarPaginaExistencia(pagina));
		    } else {
			pagina = 1;
			modelo.put("existencias", getMercado().buscarPaginaExistencia(pagina));
		    }

		    modelo.put("pagina", pagina);

		    ctx.render("/html/index.html", modelo);
		});
	    });

	    path("/producto", () -> {
		path("/info", () -> {
		    get(ctx -> {
			int id;
			Map<String, Object> modelo;

			if (ctx.queryParam("id") == null) {
			    ctx.redirect("/productos");
			} else {
			    modelo = new HashMap<String, Object>();
			    modelo.put("usuario", ctx.sessionAttribute("usuario"));
			    modelo.put("carrito_cantidad",
				    (((Carro) ctx.sessionAttribute("carrito")).cantidadProductos()));
			    id = ctx.queryParam("id", Integer.class).get();
			    modelo.put("existencia", getMercado().buscarExistencia(id));
			    modelo.put("comentarios", getMercado().buscarComentarios(id));
			    
			    ctx.render("/html/producto.html", modelo);
			}
		    });

		    post("/agregar", ctx -> {
			int id = Integer.parseInt(ctx.formParam("id"));
			int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

			((Carro) ctx.sessionAttribute("carrito")).agregarProducto(getMercado().buscarExistencia(id),
				cantidad);

			ctx.redirect("/producto/info?id=" + id);
		    });
		});
		
		path("/comentario", () -> {
		    post("/agregar", ctx -> {
			int id = Integer.parseInt(ctx.formParam("id"));
			String persona = ctx.formParam("persona");
			String titulo = ctx.formParam("titulo");
			String descripcion = ctx.formParam("descripcion");
			
			getMercado().comentarProducto(id, persona, titulo, descripcion);
			
			ctx.redirect("/producto/info?id=" + id);
		    });
		    
		    post("/eliminar", ctx -> {
			int producto = Integer.parseInt(ctx.formParam("producto"));
			int comentario = Integer.parseInt(ctx.formParam("comentario"));
			
			getMercado().eliminarComentario(comentario);
			
			ctx.redirect("/producto/info?id=" + producto);
		    });
		});
	    });
	});
    }
}