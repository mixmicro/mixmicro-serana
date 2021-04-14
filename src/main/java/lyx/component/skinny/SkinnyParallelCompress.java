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

import net.coobird.thumbnailator.geometry.Positions;

/**
 * {@link SkinnyParallelCompress}
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/14
 */
public abstract class SkinnyParallelCompress extends GenericSkinnyCompress {

  @Override
  public boolean compressImage(String originPath, String outputPath, float scale, float outputQuality) {
    return false;
  }

  @Override
  public boolean compressImage(String originPath, String outputPath, int length, int width) {
    return false;
  }

  @Override
  public boolean rotateImage(String originPath, String outputPath, int rotate) {
    return false;
  }

  @Override
  public boolean tailorImage(String originPath, String outputPath, Positions positions, int length, int width) {
    return false;
  }

  @Override
  public boolean convertImageFormat(String originPath, String outputPath, String outputFormat) {
    return false;
  }

  @Override
  public boolean watermarkImage(String originPath, String outputPath, Positions positions, String watermarkPath,
      float outputQuality) {
    return false;
  }
}
