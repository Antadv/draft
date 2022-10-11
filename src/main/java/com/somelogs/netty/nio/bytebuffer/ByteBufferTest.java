package com.somelogs.netty.nio.bytebuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Channel 往 Buffer 中读数据。
 * 注意，NIO 中，读写的解释如下：
 *
 * read：Reads a sequence of bytes from this channel into the given buffer.
 * write：Writes a sequence of bytes to this channel from the given buffer.
 *
 * @author LBG - 2022/10/11
 */
public class ByteBufferTest {

	/**
	 * Using a Buffer to read and write data typically follows this little 4-step process:
	 *
	 * Write data into the Buffer
	 * Call buffer.flip()
	 * Read data out of the Buffer
	 * Call buffer.clear() or buffer.compact()
	 */
	public static void main(String[] args) throws Exception {
		URL resource = ByteBufferTest.class.getClassLoader().getResource("nio/bytebuffer/ByteBufferTest.txt");

		// 0. Returns the unique FileChannel object associated with this file input stream.
		try (FileChannel channel = new FileInputStream(new File(resource.toURI())).getChannel()) {

			while (true) {
				// 1. Allocates a new byte buffer. 10 bytes capacity
				ByteBuffer buffer = ByteBuffer.allocate(10);

				// 2. Reads a sequence of bytes from this channel into the given buffer.
				int byteRead = channel.read(buffer);

				// return -1 if end-of-stream
				if (byteRead == -1) {
					break;
				}

				// switch the buffer from writing mode into reading mode
				buffer.flip();

				// 3. Tells whether there are any elements between the current position and the limit.
				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					System.out.println((char) b);
				}

				// Once you have read all the data, you need to clear the buffer, to make it ready for writing again.
				// You can do this in two ways: By calling clear() or by calling compact().
				// The clear() method clears the whole buffer.
				// The compact() method only clears the data which you have already read.
				// Any unread data is moved to the beginning of the buffer,
				// and data will now be written into the buffer after the unread data.

				buffer.clear(); // clear all buffer
				//buffer.compact(); // only clears the data which you have already read.
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
