/*--------------------------------------------------------------------------
 *  Copyright 2022 Sebastiaan Alvarez Rodriguez
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *--------------------------------------------------------------------------*
 * Note that this file was modified from the snappy-java project.
 * Changes:
 *  - Fixed style.
 *--------------------------------------------------------------------------*/

package org.sebastiaan.snappy_android;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * JNI interface of the {@link Snappy} implementation. The native method in this class is
 * defined in SnappyNative.h (genereted by javah) and SnappyNative.cpp
 * <p/>
 * <p>
 * <b> DO NOT USE THIS CLASS since the direct use of this class might break the
 * native library code loading in {@link SnappyLoader}. </b>
 * </p>
 * @author leo
 */
public class SnappyNative implements SnappyApi {

    public native String nativeLibraryVersion();

    // ------------------------------------------------------------------------
    // Generic compression/decompression routines.
    // ------------------------------------------------------------------------
    @Override
    public native long rawCompress(long inputAddr, long inputSize, long destAddr) throws IOException;

    @Override
    public native long rawUncompress(long inputAddr, long inputSize, long destAddr) throws IOException;

    @Override
    public native int rawCompress(ByteBuffer input, int inputOffset, int inputLength, ByteBuffer compressed, int outputOffset) throws IOException;

    @Override
    public native int rawCompress(Object input, int inputOffset, int inputByteLength, Object output, int outputOffset) throws IOException;

    @Override
    public native int rawUncompress(ByteBuffer compressed, int inputOffset, int inputLength, ByteBuffer uncompressed, int outputOffset) throws IOException;

    @Override
    public native int rawUncompress(Object input, int inputOffset, int inputLength, Object output, int outputOffset) throws IOException;

    // Returns the maximal size of the compressed representation of
    // input data that is "source_bytes" bytes in length;
    @Override
    public native int maxCompressedLength(int source_bytes);

    // This operation takes O(1) time.
    @Override
    public native int uncompressedLength(ByteBuffer compressed, int offset, int len) throws IOException;

    @Override
    public native int uncompressedLength(Object input, int offset, int len) throws IOException;

    @Override
    public native long uncompressedLength(long inputAddr, long len) throws IOException;

    @Override
    public native boolean isValidCompressedBuffer(ByteBuffer compressed, int offset, int len) throws IOException;

    @Override
    public native boolean isValidCompressedBuffer(Object input, int offset, int len) throws IOException;

    @Override
    public native boolean isValidCompressedBuffer(long inputAddr, long offset, long len) throws IOException;

    @Override
    public native void arrayCopy(Object src, int offset, int byteLength, Object dest, int dOffset) throws IOException;

    public void throw_error(int errorCode) throws IOException {
        throw new IOException(String.format("%s(%d)", SnappyErrorCode.getErrorMessage(errorCode), errorCode));
    }
}
