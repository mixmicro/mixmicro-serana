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
package lyx.component.skinny.archivers;

import java.util.Date;

/**
 * {@link ArchiveEntity} Represent an entry of an archive.
 *
 * @author <a href="mailto:siran0611@gmail.com">Elias.Yao</a>
 * @version ${project.version} - 2021/4/13
 */
public interface ArchiveEntity {

  /**
   * Returns the name of the entry in this archive. May refer to a file or directory or other item.
   *
   * <p> This method returns the raw name as it is stored inside of the archive.</p>
   *
   * @return the name of the entry in this archive.
   */
  String getName();

  /**
   * Return the uncompressed size of the entry. Maybe -1(SIZE_UNKNOWN) if the size is unknown.
   *
   * @return the uncompressed size of the entry.
   */
  long getSize();

  /**
   * Special value indicating that the size is unknown.
   */
  long SIZE_UNKNOWN = -1;

  /**
   * Return true if the entry refers to a directory.
   *
   * @return true if the entry refers to a directory.
   */
  boolean isDirectory();

  /**
   * Returns the last modified date of this entry.
   *
   * @return the last modified date of this entry.
   */
  Date getLastModifiedDate();
}
