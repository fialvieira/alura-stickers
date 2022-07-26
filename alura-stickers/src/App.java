import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        //Fazer uma conexão HTTP e buscar os top 250 filmes

        //String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY&start_date=2022-06-12&end_date=2022-06-14";
        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

        //String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        //ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        String url = "http://localhost:8080/linguagens";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDeLinguagens();

        String body = new ClienteHttp(url).buscaDados();

        //Exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(body);

        for (int i = 0; i < conteudos.size(); i++) {
            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();

            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";

            GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo, "TOPZERA");

            System.out.println(conteudo.getTitulo());
            System.out.println();
        }
    }
}
