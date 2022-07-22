import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        //Fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
       
        //Extrair só os dados que interessam (título, poster e classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        //Exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");

            int posIni = urlImagem.indexOf("._");
            int posFim = urlImagem.indexOf("_.");
            String textoRetirado = urlImagem.substring(posIni, posFim + 2);

            urlImagem = urlImagem.replace(textoRetirado, ".");

            String titulo = filme.get("title");
            titulo = titulo.replace(":", "_");
            String texto = "";

            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = "saida/" + titulo + ".png";

            GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();

            if (Float.parseFloat(filme.get("imDbRating")) >= 5 && Float.parseFloat(filme.get("imDbRating")) < 9) {
                texto = "Mais ou menos!!!";
            } else if (Float.parseFloat(filme.get("imDbRating")) < 5) {
                texto = "Xiiii!!!";
            } else {
                texto = "Topzera!!!";
            }

            geradora.cria(inputStream, nomeArquivo, texto);

            System.out.println(titulo);
            System.out.println(urlImagem);
            //System.out.println(filme.get("image"));
            //System.out.println(filme.get("imDbRating"));
            System.out.println();
        }
    }
}
