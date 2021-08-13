package br.com.TrueComercializadora;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {

        DataInputStream dis;


            extracted();

            File file = new File("TERM.DAT");
            FileInputStream fis = null;
            BufferedInputStream bis = null;

           
                final String DEFAULT_PATTERN_CSV = " NUM NOME          POT  FCMX    TEIF   IP    <-------------------- GTMIN PARA O PRIMEIRO ANO DE ESTUDO ------------------------|D+ ANOS\n" +
                        " XXX XXXXXXXXXXXX  XXXX. XXX.  XXX.XX XXX.XX JAN.XX FEV.XX MAR.XX ABR.XX MAI.XX JUN.XX JUL.XX AGO.XX SET.XX OUT.XX NOV.XX DEZ.XX XXX.XX";

                String path = System.getProperty("user.dir");
                final String PATH_DIR = String.format(path+"\\src\\main\\java\\br\\com\\crud\\api\\mod\\encad-true-%s.csv", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))).trim();

                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                dis = new DataInputStream(bis);
                


                List<String> idEncadTermicas = new ArrayList<>();

                Stream<String> termDAT = Files.lines(Paths.get("TERM.DAT")).skip(2L);
                Stream<String> encadTERM = Files.lines(Paths.get("encad-termicas.csv")).skip(1L);

                List<String> listaDeTerm = termDAT.map(x -> x.replaceAll("\\s+", " ").trim()).collect(Collectors.toList());
                Map<Integer, String> extractedEncadTerm = extracted(idEncadTermicas, encadTERM.collect(Collectors.toList()));

                BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_DIR, true));
                bw.write(DEFAULT_PATTERN_CSV);
                bw.newLine();
                for (String usinaTerm : listaDeTerm) {
                    String valueKey = usinaTerm.split(" ")[0];
                    if (extractedEncadTerm.containsKey(Integer.valueOf(valueKey))) {
                        bw.write(" "+extractedEncadTerm.get(Integer.valueOf(valueKey)).replaceAll(",", " "));
                        bw.newLine();
                    } else {
                        bw.write(" "+usinaTerm);
                        bw.newLine();
                    }

                }
                bw.close();
                closedStreams(dis, fis, bis);
	}

	private static void extracted() throws MalformedURLException, IOException, FileNotFoundException {
		URL u;
		InputStream is;

		u = new URL("https://datawarehouse-true.s3-sa-east-1.amazonaws.com/teste-true/teste_true_term.zip");

		is = u.openStream();

		ZipInputStream zin = new ZipInputStream(is);
		ZipEntry ze = null;

		while ((ze = zin.getNextEntry()) != null) {
			System.out.println("Unzipping " + ze.getName());
			FileOutputStream fout = new FileOutputStream(ze.getName());
			for (int c = zin.read(); c != -1; c = zin.read()) {
				fout.write(c);
			}
			zin.closeEntry();
			fout.close();
		}
		zin.close();
	}

	private static void closedStreams(DataInputStream dis, FileInputStream fis, BufferedInputStream bis)
			throws IOException {
		fis.close();
		bis.close();
		dis.close();
	}

	private static Map<Integer, String> extracted(List<String> idEncadTermicas, List<String> listaEncadTerm) {
		Map<Integer, String> listResult = new HashMap<>();
		for (String usina : listaEncadTerm) {
			String idUsina = usina.split(",")[0];
			listResult.put(Integer.valueOf(idUsina), usina);
		}
		return listResult;
	}
}
