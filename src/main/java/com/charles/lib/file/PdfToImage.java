package com.charles.lib.file;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PdfToImage {
    private static final String PDF_FILENAME = "pdf/ddd_starter_edition.pdf";
    private static final String OUTPUT_DIR = System.getProperty("java.io.tmpdir");
    private static final int DEFAULT_DPI = 300;

    public static void main(String[] args) {
        System.out.println("OUTPUT_DIR = " + OUTPUT_DIR);
        // File file = new File(getPdfFilePath());
        File pdfFileDir = new File("/Users/Charles/Downloads/pdf");
        for (File file : Objects.requireNonNull(pdfFileDir.listFiles())) {
            pdfFirstPageToImage(file);
        }
        // pdfAllPagesToImages(file);
    }

    private static void pdfFirstPageToImage(File file) {
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, DEFAULT_DPI);
            File imageFile = new File(OUTPUT_DIR, FileType.PNG.filename(file.getName()));
            // String imageFileName = FileType.PNG.concatFilePath(OUTPUT_DIR, "pdf_first_image");
            ImageIOUtil.writeImage(bufferedImage, imageFile.getAbsolutePath(), DEFAULT_DPI);
            // ImageIO.write(bufferedImage, "png", imageFile);
            bufferedImage.flush();
        } catch (IOException e) {
            System.err.println("Error extracting PDF Document => " + e);
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                System.err.println("Error extracting PDF Document => " + e);
            }
        }
    }

    private static void pdfAllPagesToImages(File file) {
        try (final PDDocument document = PDDocument.load(file)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, DEFAULT_DPI, ImageType.RGB);
                String imageFileName = FileType.PNG.concatFilePath(OUTPUT_DIR, "image-" + page);
                ImageIOUtil.writeImage(bufferedImage, imageFileName, DEFAULT_DPI);
            }
        } catch (IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

    private static String getPdfFilePath() throws NullPointerException {
        return Objects.requireNonNull(PdfToImage.class.getClassLoader().getResource(PDF_FILENAME)).getFile();
    }

}