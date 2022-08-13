package com.Jvnyor.pdfconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.jonathanlink.PDFLayoutTextStripper;

public class PDFConverter {

	/**
	 * @author Josias Junior https://github.com/Jvnyor
	 */

	public static void main(String[] args) {

		System.out.println("\n" 
				+ ".---. .---. .---.    .--.  .--. .-..-..-..-. .--. .---. .-----. .--. .---. \r\n"
				+ ": .; :: .  :: .--'  : .--': ,. :: `: :: :: :: .--': .; :`-. .-': .--': .; :\r\n"
				+ ":  _.': :: :: `;    : :   : :: :: .` :: :: :: `;  :   .'  : :  : `;  :   .'\r\n"
				+ ": :   : :; :: :     : :__ : :; :: :. :: `' ;: :__ : :.`.  : :  : :__ : :.`.\r\n"
				+ ":_;   :___.':_;     `.__.'`.__.':_;:_; `.,' `.__.':_;:_;  :_;  `.__.':_;:_;");

		try (Scanner scanner = new Scanner(System.in)) {

			String pdfPath = null;

			do {

				System.out.print("\nInsira o caminho do arquivo .pdf: ");

				pdfPath = scanner.nextLine().trim();
				
				String pdfPathToUtf8 = new String(pdfPath.getBytes(), StandardCharsets.UTF_8);
				
				pdfPath = pdfPathToUtf8;
				
			} while (!pdfPath.contains(".pdf") || pdfPath == null || pdfPath.isBlank());

			File file = new File(pdfPath);

			String txtPath = null;

			do {

				System.out.print("\nInsira o caminho onde quer salvar o arquivo .txt: ");

				txtPath = scanner.nextLine().trim();
				
				String txtPathToUtf8 = new String(txtPath.getBytes(), StandardCharsets.UTF_8);

				txtPath = txtPathToUtf8;
				
			} while (!txtPath.contains(".txt") || txtPath == null || txtPath.isBlank());

			PDFParser pdfParser = new PDFParser(new RandomAccessFile(file, "r"));

			pdfParser.parse();

			PDDocument pdDocument = new PDDocument(pdfParser.getDocument());

			PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();

			String textOfPdf = pdfTextStripper.getText(pdDocument);

			PrintWriter out = new PrintWriter(new FileOutputStream(txtPath));

			String lines[] = textOfPdf.split("\\r?\\n");

			for (String line : lines) {

				out.println(line);

			}

			if (out != null) {

				System.out.println("\nArquivo criado em " + txtPath);

			}

			out.flush();

			out.close();

			pdDocument.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
