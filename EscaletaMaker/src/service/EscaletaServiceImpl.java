package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import model.Escaleta;
import model.Noticia;

public class EscaletaServiceImpl implements EscaletaService {
	private List<Noticia> videojuegos;
	private List<Noticia> comics;
	private List<Noticia> juegosDeMesa;
	private List<Noticia> entretenimiento;
	private List<Noticia> mundoGeek;
	private List<Noticia> topEngorile;
	private Map<String, List<Noticia>> grupo;
	private String html;
	
	static {
		
		
	}
	
	public EscaletaServiceImpl() {
		this.html = generadorHtml();
		videojuegos = new ArrayList<>();
		comics = new ArrayList<>();
		juegosDeMesa = new ArrayList<>();
		entretenimiento = new ArrayList<>();
		mundoGeek = new ArrayList<>();
		topEngorile = new ArrayList<>();
		grupo = new HashMap<String, List<Noticia>>();
	}
	

	@Override
	public void grupoNoticias(Noticia n) {
		
		switch (n.getCategoria()) {
		
			case "videojuegos":
				if (grupo.get("videojuegos") == null) {
					videojuegos.add(n);
					grupo.put(n.getCategoria(), videojuegos);
				}
				else {
					grupo.get("videojuegos").add(n);
				}						
				break;
			case "comics":
				if (grupo.get("comics") == null) {
					comics.add(n);
					grupo.put(n.getCategoria(), comics);
				}
				else {
					grupo.get("comics").add(n);
				}				
				break;
			case "juegosMesa":
				if (grupo.get("juegosMesa") == null) {
					juegosDeMesa.add(n);
					grupo.put(n.getCategoria(), juegosDeMesa);
				}
				else {
					grupo.get("juegosMesa").add(n);
				}			
				break;
			case "entretenimiento":
				if (grupo.get("entretenimiento") == null) {
					entretenimiento.add(n);
					grupo.put(n.getCategoria(), entretenimiento);
				}
				else {
					grupo.get("entretenimiento").add(n);
				}				
				break;
			case "mundoGeek":
				if (grupo.get("mundoGeek") == null) {
					mundoGeek.add(n);
					grupo.put(n.getCategoria(), mundoGeek);
				}
				else {
					grupo.get("mundoGeek").add(n);
				}				
				break;
			case "topEngorile":
				if (grupo.get("topEngorile") == null) {
					topEngorile.add(n);
					grupo.put(n.getCategoria(), topEngorile);
				}
				else {
					grupo.get("topEngorile").add(n);
				}				
				break;
			}
	}


	@Override
	public Map<String, List<Noticia>> getNoticias() {
		return grupo;
	}
	
	@Override
	public void setNoticias(Map<String, List<Noticia>> noticias) {
		grupo = noticias;
	}

	@Override
	public boolean guardarEscaleta(Escaleta escaleta, String ruta, String nPrograma) {	
		try (FileOutputStream fos = new FileOutputStream(ruta+"/"+nPrograma+".html");
				OutputStreamWriter out = new OutputStreamWriter(fos, Charset.forName("UTF-8").newEncoder())){
			Gson gson = new Gson();
			String json = gson.toJson(escaleta);
			String html2 = "	<script>\r\n" + 
					"		var app=angular.module('escaleta', []);\r\n" + 
					"		app.controller('controller', function($scope){\r\n" + 
					"			var escaleta = JSON.parse(JSON.stringify("+json+"));\r\n" + 
					"			$scope.presentacion = escaleta.tituloPrograma;\r\n" + 
					"			$scope.mundoGeek = escaleta.noticias.mundoGeek;\r\n" + 
					"			$scope.videojuegos = escaleta.noticias.videojuegos;\r\n" + 
					"			$scope.entretenimiento = escaleta.noticias.entretenimiento;\r\n" + 
					"			$scope.comics = escaleta.noticias.comics;\r\n" + 
					"			$scope.juegosMesa = escaleta.noticias.juegosMesa;\r\n" + 
					"			$scope.topEngorile = escaleta.noticias.topEngorile;\r\n" + 
					"\r\n" + 
					"            $scope.tachado=function(estilo, categoria, len, index){\r\n" + 
					"                if(len -1 != index){\r\n" + 
					"                    document.getElementById(estilo+index).style.textDecoration = \"line-through\";\r\n" + 
					"                }\r\n" + 
					"                else{\r\n" + 
					"\r\n" + 
					"                    document.getElementById(estilo+index).style.textDecoration = \"line-through\";\r\n" + 
					"                    document.getElementById(categoria).style.backgroundColor = \"darkgray\";\r\n" + 
					"                }\r\n" + 
					"            };\r\n" + 
					"        });\r\n" + 
					"		</script>\r\n" + 
					"	</body>\r\n" + 
					"</html>";
			out.write(html+html2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String generadorHtml() {
		return "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"    <head>\r\n" + 
				"        <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/>\r\n" + 
				"       <script src=\"https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js\"></script>\r\n" + 
				"        <style>\r\n" + 
				"            #MundoGeek{\r\n" + 
				"            border: 2px solid tomato;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #MundoGeek h2{\r\n" + 
				"                color: crimson;\r\n" + 
				"            }\r\n" + 
				"            #MundoGeek a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #MundoGeek h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #Videojuegos{\r\n" + 
				"            border: 2px solid darkblue;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #Videojuegos h2{\r\n" + 
				"                color: darkblue;\r\n" + 
				"            }\r\n" + 
				"            #Videojuegos a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #Videojuegos h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #Entretenimiento{\r\n" + 
				"            border: 2px solid darkgreen;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #Entretenimiento h2{\r\n" + 
				"                color: darkgreen;\r\n" + 
				"            }\r\n" + 
				"            #Entretenimiento a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #Entretenimiento h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #Comics{\r\n" + 
				"            border: 2px solid darkslateblue;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #Comics h2{\r\n" + 
				"                color: darkslateblue\r\n" + 
				"            }\r\n" + 
				"            #Comics a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #Comics h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #Juegos{\r\n" + 
				"            border: 2px solid coral;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #Juegos h2{\r\n" + 
				"                color: coral\r\n" + 
				"            }\r\n" + 
				"            #Juegos a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #Juegos h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #TopEngorile{\r\n" + 
				"            border: 2px solid blueviolet;\r\n" + 
				"            padding-left: 1px;\r\n" + 
				"            padding-top: 1px;\r\n" + 
				"            padding-right: 1px;\r\n" + 
				"            padding-bottom: 1px;\r\n" + 
				"            margin-left: 400px;\r\n" + 
				"            margin-right: 400px;\r\n" + 
				"            background-color: #8095BC;\r\n" + 
				"            }\r\n" + 
				"            #TopEngorile h2{\r\n" + 
				"                color:  blueviolet\r\n" + 
				"            }\r\n" + 
				"            #TopEngorile a{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration:none;\r\n" + 
				"            }\r\n" + 
				"            #TopEngorile h3{\r\n" + 
				"                color:black;\r\n" + 
				"                text-decoration: none;\r\n" + 
				"            }\r\n" + 
				"        </style>\r\n" + 
				"       \r\n" + 
				"    </head>\r\n" + 
				"    <body style=\"background-color: #BD7471\">\r\n" + 
				"        <div align=\"center\" ng-app=\"escaleta\" ng-controller=\"controller\" ><h1 ng-model=\"presentacion\">{{presentacion}}</h1>\r\n" + 
				"            <h3 dir=\"ltr\">-Presentacion con Engoriles/Desgoriles</h3>\r\n" + 
				"\r\n" + 
				"            <div id=\"MundoGeek\" ng-if=\"mundoGeek != null\">\r\n" + 
				"                <h2 id=\"MundoGeek h2\" ng-model=\"cabecera\" dir=\"ltr\">MUNDO GEEK</h2>\r\n" + 
				"                <div ng-repeat=\"mg in mundoGeek track by $index\">\r\n" + 
				"                     <a id=\"estiloGeek{{$index}}\" href=\"{{mg.link}}\" ng-if=\"mg.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloGeek', 'MundoGeek', mundoGeek.length, $index)\">\r\n" + 
				"                        <h3 dir=\"ltr\">- {{mg.titulo}}<br></h3>\r\n" + 
				"                    </a>\r\n" + 
				"                    <a id=\"estiloGeek{{$index}}\" href=\"{{mg.link}}\" ng-if=\"mg.link=='#'\" ng-click=\"tachado('estiloGeek', 'MundoGeek', mundoGeek.length, $index)\">\r\n" + 
				"                        <h3 dir=\"ltr\">- {{mg.titulo}}<br></h3>\r\n" + 
				"                    </a>\r\n" + 
				"                </div>\r\n" + 
				"            </div>\r\n" + 
				"\r\n" + 
				"            <br/><br/>\r\n" + 
				"\r\n" + 
				"            <div id=\"Videojuegos\" ng-if=\"videojuegos != null\">\r\n" + 
				"                <h2 id=\"Videojuegos h2\" ng-model=\"cabecera\" dir=\"ltr\">VIDEOJUEGOS</h2>\r\n" + 
				"                <div ng-repeat=\"v in videojuegos track by $index\">\r\n" + 
				"                    <a id=\"estiloVideojuegos{{$index}}\" href=\"{{v.link}}\" ng-if=\"v.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloVideojuegos', 'Videojuegos', videojuegos.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{v.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"                   <a id=\"estiloVideojuegos{{$index}}\" href=\"{{v.link}}\" ng-if=\"v.link=='#'\" ng-click=\"tachado('estiloVideojuegos', 'Videojuegos', videojuegos.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{v.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"               </div>\r\n" + 
				"            </div>\r\n" + 
				"            \r\n" + 
				"            <br/><br/>\r\n" + 
				"\r\n" + 
				"            <div id=\"Entretenimiento\" ng-if=\"entretenimiento != null\">\r\n" + 
				"                <h2 id=\"Entretenimiento h2\" ng-model=\"cabecera\" dir=\"ltr\">CINE Y SERIES</h2>\r\n" + 
				"                <div ng-repeat=\"e in entretenimiento track by $index\">\r\n" + 
				"                    <a id=\"estiloEntretenimiento{{$index}}\" href=\"{{e.link}}\" ng-if=\"e.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloEntretenimiento', 'Entretenimiento', entretenimiento.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{e.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"                   <a id=\"estiloEntretenimient{{$index}}\" href=\"{{e.link}}\" ng-if=\"e.link=='#'\" ng-click=\"tachado('estiloEntretenimiento', 'Entretenimiento', entretenimiento.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{e.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"               </div>\r\n" + 
				"            </div>\r\n" + 
				"            \r\n" + 
				"            <br/><br/>\r\n" + 
				"\r\n" + 
				"            <div id=\"Comics\" ng-if=\"comics != null\">\r\n" + 
				"                <h2 id=\"Comics h2\" ng-model=\"cabecera\" dir=\"ltr\">COMICS</h2>\r\n" + 
				"                <div ng-repeat=\"c in comics track by $index\">\r\n" + 
				"                    <a id=\"estiloComics{{$index}}\" href=\"{{c.link}}\" ng-if=\"c.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloComics', 'Comics', comics.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{c.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"                   <a id=\"estiloComics{{$index}}\" href=\"{{c.link}}\" ng-if=\"c.link=='#'\" ng-click=\"tachado('estiloComics', 'Comics', comics.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{c.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"               </div>\r\n" + 
				"            </div>\r\n" + 
				"            \r\n" + 
				"            <br/><br/>\r\n" + 
				"\r\n" + 
				"            <div id=\"Juegos\" ng-if=\"juegosMesa != null\">\r\n" + 
				"                <h2 id=\"Juegos h2\" ng-model=\"cabecera\" dir=\"ltr\">JUEGOS DE MESA</h2>\r\n" + 
				"                <div ng-repeat=\"j in juegosMesa track by $index\">\r\n" + 
				"                    <a id=\"estiloJuegos{{$index}}\" href=\"{{j.link}}\" ng-if=\"j.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloJuegos', 'Juegos', juegosMesa.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{j.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"                   <a id=\"estiloJuegos{{$index}}\" href=\"{{j.link}}\" ng-if=\"j.link=='#'\" ng-click=\"tachado('estiloJuegos', 'Juegos', juegosMesa.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{j.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"               </div>\r\n" + 
				"            </div>\r\n" + 
				"            \r\n" + 
				"            <br/><br/>\r\n" + 
				"\r\n" + 
				"            <div id=\"TopEngorile\" ng-if=\"topEngorile != null\">\r\n" + 
				"                <h2 id=\"TopEngorile h2\" ng-model=\"cabecera\" dir=\"ltr\">TOP ENGORILE</h2>\r\n" + 
				"                <div ng-repeat=\"t in topEngorile track by $index\">\r\n" + 
				"                    <a id=\"estiloTop{{$index}}\" href=\"{{t.link}}\" ng-if=\"t.link!='#'\" target=\"_blank\" ng-click=\"tachado('estiloTop', 'TopEngorile', topEngorile.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{j.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"                   <a id=\"estiloTop{{$index}}\" href=\"{{t.link}}\" ng-if=\"t.link=='#'\" ng-click=\"tachado('estiloTop', 'TopEngorile', topEngorile.length, $index)\">\r\n" + 
				"                       <h3 dir=\"ltr\">- {{t.titulo}}<br></h3>\r\n" + 
				"                   </a>\r\n" + 
				"               </div>\r\n" + 
				"            </div>\r\n" + 
				"        </div>\r\n";
	}
}
