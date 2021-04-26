/**
 * MIT License
 *
 * <p>Copyright (c) 2021 mixmicro
 *
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * <p>The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lyx.component.skinny.compress;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import lyx.component.skinny.Injection;
import lyx.component.skinny.SkinnyParallelCompress;


/**
 * {@link SkinnyPdfCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/15
 */
@Injection(name = "Pdf")
public class SkinnyPdfCompress extends SkinnyParallelCompress {

  private static final String PDF_SUFFIX = "pdf";
  public static final float FACTOR = 0.5f;

  @Override
  public boolean compress(File[] sourceFiles, String filePath, String fileName, boolean isDeleteSourceFile) {
    return compress(sourceFiles, new File(fileName, fileName), isDeleteSourceFile);
  }

  @Override
  public boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile) {
    try {

      for (File sourceFile : sourceFiles) {
        if (!sourceFile.getName().endsWith(PDF_SUFFIX)) {
          throw new IllegalArgumentException("Suffix name error, your input filename is: " + file.getName());
        }

        PdfReader reader = new PdfReader(new FileInputStream(sourceFile));
        int n = reader.getXrefSize();
        PdfObject object;
        PRStream stream;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {
          object = reader.getPdfObject(i);
          if (object == null || !object.isStream()) {
            continue;
          }
          stream = (PRStream) object;
          if (!PdfName.IMAGE.equals(stream.getAsName(PdfName.SUBTYPE))) {
            continue;
          }
          if (!PdfName.DCTDECODE.equals(stream.getAsName(PdfName.FILTER))) {
            continue;
          }
          PdfImageObject image = null;

          image = new PdfImageObject(stream);
          BufferedImage bi = image.getBufferedImage();
          if (bi == null) {
            continue;
          }
          int width = (int) (bi.getWidth() * FACTOR);
          int height = (int) (bi.getHeight() * FACTOR);
          if (width <= 0 || height <= 0) {
            continue;
          }
          BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
          AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
          Graphics2D g = img.createGraphics();
          g.drawRenderedImage(bi, at);
          ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
          ImageIO.write(img, "JPG", imgBytes);
          stream.clear();
          stream.setData(imgBytes.toByteArray(), false, PRStream.NO_COMPRESSION);
          stream.put(PdfName.TYPE, PdfName.XOBJECT);
          stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
          stream.put(PdfName.FILTER, PdfName.DCTDECODE);
          stream.put(PdfName.WIDTH, new PdfNumber(width));
          stream.put(PdfName.HEIGHT, new PdfNumber(height));
          stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
          stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
        }
        reader.removeUnusedObjects();
        // Save altered PDF
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
        stamper.setFullCompression();
        stamper.close();
        reader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public boolean decompress(File file, String targetDir) {
    throw new UnsupportedOperationException("Pdf Decompress haven't been support,but you can compress");
  }

  @Override
  public boolean decompress(File file, File targetDir) {
    throw new UnsupportedOperationException("Pdf Decompress haven't been support,but you can compress");

  }
}
