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
package lyx.component.skinny;

import java.io.File;
import java.util.List;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * {@link Compress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/14
 */
public interface Compress {

  /**
   * Special for picture compression.
   *
   * @param originPath    the original path
   * @param outputPath    the output path
   * @param scale         specifies the image size. The value is between 0 and 1, 1f is the original image size and 0.5 is half
   *                      the
   *                      original image size.
   * @param outputQuality the quality of the picture is also between 0 and 1. The closer it is to 1, the better the quality is.
   *                      The closer it is to 0,the worse the quality is.
   * @return
   */
  boolean compressImage(String originPath, String outputPath, float scale, float outputQuality);

  /**
   * Special for picture compression with specified length and width.
   *
   * @param originPath the original path
   * @param outputPath the output path
   * @return
   */
  boolean compressImage(String originPath, String outputPath, int length, int width);

  /**
   * Rotate picture,
   *
   * @param rotate     angle positive: clockwise, negative: counterclockwise.
   * @param originPath the original path
   * @param outputPath the output path
   * @return
   */
  boolean rotateImage(String originPath, String outputPath, int rotate);

  /**
   * Tailor picture.
   *
   * @param originPath the original path
   * @param outputPath the output path
   * @param positions  the position to be clipped. i.e. {@see Positions.CENTER}
   * @param length     The cut length
   * @param width      the cut width
   * @return
   */
  boolean tailorImage(String originPath, String outputPath, Positions positions, int length, int width);

  /**
   * Convert image format.
   *
   * @param originPath   the original path
   * @param outputPath   the output path
   * @param outputFormat i.e. png, jpg etc.
   * @return
   */
  boolean convertImageFormat(String originPath, String outputPath, String outputFormat);

  /**
   * Watermark
   *
   * @param originPath    the original path
   * @param outputPath    the output path
   * @param positions     the position to be clipped. i.e. {@see Positions.CENTER}
   * @param watermarkPath the watermark path
   * @param outputQuality image transparency
   * @return
   */
  boolean watermarkImage(String originPath, String outputPath, Positions positions, String watermarkPath, float outputQuality);

  /**
   * Pack the file into a zip archive file
   *
   * @param sourceFiles        A list of multiple files to compress. Only support files, can not have a directory, otherwise throw
   *                           exception.
   * @param filePath           Compress file path. The file may not exist,but the directory must exist i.e. xxx/xxx/,
   *                           otherwise throw
   *                           exception.
   * @param fileName           Compress file name, i.e. output.zip or output.7z
   * @param isDeleteSourceFile whether to delete the source file after compress completed.
   * @return
   */
  boolean compress(File[] sourceFiles, String filePath, String fileName, boolean isDeleteSourceFile);

  /**
   * Pack the file into a zip archive file
   *
   * @param sourceFiles        A list of multiple files to compress. Only support files, can not have a directory, otherwise throw
   *                           exception.
   * @param file               Compress file path. The file may not exist,but the directory must exist i.e. xxx/xxx/output.zip,
   *                           otherwise throw
   *                           exception.
   * @param isDeleteSourceFile whether to delete the source file after compress completed.
   * @return
   */
  boolean compress(File[] sourceFiles, File file, boolean isDeleteSourceFile);

  /**
   * Unpack the zip package into a file and place it in the specified folder.
   *
   * @param file      The compressed file to be extracted.
   * @param targetDir Destination where files are stored after unzipping.
   * @return
   */
  boolean decompress(File file, String targetDir);

  /**
   * Unpack the zip package into a file and place it in the specified folder.
   *
   * @param file      The compressed file to be extracted.
   * @param targetDir Destination where files are stored after unzipping.
   * @return
   */
  boolean decompress(File file, File targetDir);

  /**
   * Returns list of filename in compressed package.
   *
   * @return list of filename in compressed package.
   */
  List<String> listFiles();

  void injectContext(final SkinnyContext context);
}
