package edu.pucmm.market.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Foto;
import edu.pucmm.market.data.Mercado;

public class SimularDatos {

	private final Mercado mercado;
	private final AES256TextEncryptor encriptador;

	public SimularDatos(Mercado mercado, AES256TextEncryptor encriptador) {
		this.mercado = mercado;
		this.encriptador = encriptador;

		this.usuarioDefecto();
		this.productosDefecto();
		this.ventasDefecto();
	}

	public Mercado getMercado() {
		return mercado;
	}

	private void usuarioDefecto() {
		this.mercado.crearUsuario("admin", "admin", encriptador.encrypt("admin"));
	}

	private void productosDefecto() {
		Set<Foto> fotos = new HashSet<Foto>();
		this.mercado.crearEditarProducto(1, "Apio",
				"La base de una dieta equilibrada necesita un aporte extra de vitaminas. La mayor�a de ellas las obtenemos de las frutas y verduras. Una de las hortalizas que mayores beneficios aportan a nuestra salud es el apio.",
				BigDecimal.valueOf(55.25), fotos);
		this.mercado.crearEditarProducto(2, "Tomate",
				"Es dif�cil encontrar a alguien a quien no le guste el tomate, y es que el tomate, no s�lo enriquece nuestras recetas sino que aporta muchos beneficios para la salud.",
				BigDecimal.valueOf(20.00), fotos);
		this.mercado.crearEditarProducto(3, "Pollo",
				"Posiblemente, esta carne sea la m�s popular del mundo. Este ave es muy sana, econ�mica, alta en prote�nas y baja en grasas. Adem�s ofrece mucha versatilidad en la cocina y es apta para todo el mundo, al tratarse de una carne blanca.",
				BigDecimal.valueOf(385.95), fotos);
		this.mercado.crearEditarProducto(4, "Paquete de pan",
				"Incluir el pan en cada una de las comidas que realizamos a diario es una buena forma de cubrir las cinco o seis raciones diarias de hidratos de carbono recomendadas por los especialistas.",
				BigDecimal.valueOf(80.20), fotos);
		this.mercado.crearEditarProducto(5, "Queso",
				"Hay muy poca gente a la que no le guste un buen queso... El queso es un alimento muy asequible, muy agradecido y muy sabroso, y por ello no suele faltar en ninguna cocina o mesa que se precie.",
				BigDecimal.valueOf(90.15), fotos);
		this.mercado.crearEditarProducto(6, "Manzana",
				"La manzana es una fruta de gran valor nutritivo que debe estar presente en nuestra alimentaci�n. �T� salud lo agradecer�! Si a�n no est�s convencida, te damos m�s motivos para comer manzana.",
				BigDecimal.valueOf(15.15), fotos);
		this.mercado.crearEditarProducto(7, "Pi�a",
				"Una fruta tropical que tambi�n se cultiva en Espa�a, en Murcia o en Canarias por ejemplo La pi�a tiene antioxidantes, vitaminas, minerales y otras ventajas saludables.",
				BigDecimal.valueOf(45.00), fotos);
		this.mercado.crearEditarProducto(8, "Mouse",
				"Dise�o ergon�mico, Rat�n USB 3D, rodillo de alta precisi�n, posicionamiento �ptico exacto, uso de largo tiempo, rendimiento fiable, durabilidad, forma c�moda del rat�n de la manija.",
				BigDecimal.valueOf(236.00), fotos);
		this.mercado.crearEditarProducto(9, "Bocina", "�La moda retro en una bocina!", BigDecimal.valueOf(1888.00),
				fotos);
		this.mercado.crearEditarProducto(10, "Headphones",
				"Auriculares de llamativos colores con un dise�o c�modo y seguro, pero sobre todo muy divertido. Protege la audici�n de los menores al limitar el volumen a 85dB, el m�ximo nivel recomendado por los m�dicos.",
				BigDecimal.valueOf(885.00), fotos);
		this.mercado.crearEditarProducto(11, "Laptop", "Lo mejor del mercado", BigDecimal.valueOf(68381.00), fotos);
		this.mercado.crearEditarProducto(12, "C�mara Web",
				"Videoconferencias Full HD 1080p (hasta 1920 x 1080 p�xeles) con la versi�n m�s reciente de Skype para Windows. Videoconferencias HD 720p (1280 x 720 p�xeles) con clientes compatibles. Grabaciones de video Full HD (hasta 1920 x 1080 p�xeles).",
				BigDecimal.valueOf(4130.00), fotos);
	}

	private void ventasDefecto() {
		Carro carro;

		carro = new Carro();
		carro.agregarProducto(this.mercado.buscarExistencias().get(0), 5);
		carro.agregarProducto(this.mercado.buscarExistencias().get(3), 1);
		carro.agregarProducto(this.mercado.buscarExistencias().get(5), 7);
		this.mercado.procesarCompra("Jorge", carro);

		carro = new Carro();
		carro.agregarProducto(this.mercado.buscarExistencias().get(2), 5);
		this.mercado.procesarCompra("Bibi", carro);

		carro = new Carro();
		carro.agregarProducto(this.mercado.buscarExistencias().get(0), 5);
		carro.agregarProducto(this.mercado.buscarExistencias().get(5), 2);
		carro.agregarProducto(this.mercado.buscarExistencias().get(4), 7);
		carro.agregarProducto(this.mercado.buscarExistencias().get(2), 14);
		carro.agregarProducto(this.mercado.buscarExistencias().get(1), 61);
		carro.agregarProducto(this.mercado.buscarExistencias().get(3), 1);
		this.mercado.procesarCompra("John", carro);
	}
}