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
 *  - Restructured loading system to utilize Android's native loading capabilities.
 *  - Fixed style.
 *--------------------------------------------------------------------------*/

package org.sebastiaan.snappy_android;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * <b>Internal only - Do not use this class.</b> This class loads a native
 * library of snappy-java (snappyjava.dll, libsnappy.so, etc.) according to the
 * user platform (<i>os.name</i> and <i>os.arch</i>). The natively compiled
 * libraries bundled to snappy-java contain the codes of the original snappy and
 * JNI programs to access Snappy.
 *
 * This SnappyLoader loads libraries using android's System.loadLibrary function.
 *
 * @author Sebastiaan
 */
public class SnappyLoader
{
    private static boolean isLoaded = false;

    private static volatile SnappyApi snappyApi = null;

    static void cleanUpExtractedNativeLib() {
        snappyApi = null;
    }

    /** Set the `snappyApi` instance. */
    static synchronized void setSnappyApi(SnappyApi apiImpl) {
        snappyApi = apiImpl;
    }

    static synchronized SnappyApi loadSnappyApi() {
        if (snappyApi != null) {
            return snappyApi;
        }
        loadNativeLibrary();
        setSnappyApi(new SnappyNative());

        return snappyApi;
    }

    /** Load native library of snappy-android */
    private synchronized static void loadNativeLibrary() {
        if (!isLoaded) {
            checkNativeLibrary();
            try {
                System.loadLibrary("snappy_android");
            } catch (Exception e) {
                e.printStackTrace();
                throw new SnappyError(SnappyErrorCode.FAILED_TO_LOAD_NATIVE_LIBRARY, e.getMessage());
            }
            isLoaded = true;
        }
    }

    /**
     * Returns `true` if at least 1 of our native libraries is present in an ABI dir, `false` otherwise.
     * ABI directories are locations where android searches for native libraries.
     */
    static boolean hasNativeLibrariesShipped() {
        String[] abis = {"armeabi-v7a", "arm64-v8a", "x86", "x86_64"};
        for (String abi : abis) {
            if (SnappyLoader.class.getResource("/lib/"+abi+"/libsnappy_android.so") != null) {
                return true;
            }
        }
        return false;
    }

    static void checkNativeLibrary() {
        if (!hasNativeLibrariesShipped()) {
            String errorMessage = String.format("Native library was not shipped");
            throw new SnappyError(SnappyErrorCode.FAILED_TO_LOAD_NATIVE_LIBRARY, errorMessage);
        }
    }
}
