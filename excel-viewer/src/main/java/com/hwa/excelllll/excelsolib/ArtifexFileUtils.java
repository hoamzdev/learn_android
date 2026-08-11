
package com.hwa.excelllll.excelsolib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;

import com.hwa.excelllll.SOLib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class ArtifexFileUtils {

    private static SOSecureFS secureFileSystem;
    private static String documentsPath;

    public static boolean copyFile(String sourcePath, String destinationPath, boolean overwrite) {
        if (sourcePath == null || destinationPath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs == null
                || !(secureFs.isSecurePath(sourcePath)
                || secureFileSystem.isSecurePath(destinationPath))) {

            File destinationFile = new File(destinationPath);
            File sourceFile = new File(sourcePath);

            if (!overwrite && destinationFile.exists()) {
                return false;
            }

            if (overwrite && destinationFile.exists() && !deleteFile(destinationPath)) {
                return false;
            }

            return copyRegularFile(sourceFile, destinationFile);
        }

        if (!secureFileSystem.isSecurePath(destinationPath)) {

            File destinationFile = new File(destinationPath);

            if (!overwrite && destinationFile.exists()) {
                return false;
            }

            if (overwrite && destinationFile.exists() && !deleteFile(destinationPath)) {
                return false;
            }

        } else {

            if (!overwrite && secureFileSystem.fileExists(destinationPath)) {
                return false;
            }

            if (overwrite
                    && secureFileSystem.fileExists(destinationPath)
                    && !secureFileSystem.deleteFile(destinationPath)) {
                return false;
            }
        }

        return secureFileSystem.copyFile(sourcePath, destinationPath);
    }

    public static String copyContentUriToTempFile(Context context, Uri uri) {

        StringBuilder errorMessage;
        String errorPrefix;

        String fileName = getFileNameFromUri(context, uri);

        if (getFileExtension(fileName).equals("")) {
            fileName = fileName + "." + getFileExtensionFromUri(context, uri);
        }

        try {
            InputStream inputStream = context
                    .getContentResolver()
                    .openInputStream(uri);

            try {
                String tempDirectory =
                        getTempPath(context) + "/shared/" + UUID.randomUUID();

                createDirectory(tempDirectory);

                String outputPath = tempDirectory + "/" + fileName;

                if (fileExists(outputPath)) {
                    deleteFile(outputPath);
                }

                SOSecureFS secureFs = secureFileSystem;

                Object outputStream =
                        (secureFs == null || !secureFs.isSecurePath(outputPath))
                                ? new FileOutputStream(outputPath)
                                : secureFs.getFileHandleForWriting(outputPath);

                if (outputStream == null) {
                    return "---";
                }

                byte[] buffer = new byte[4096];

                while (true) {
                    int bytesRead = inputStream.read(buffer);

                    if (bytesRead == -1) {
                        break;
                    }

                    if (outputStream instanceof FileOutputStream) {

                        ((FileOutputStream) outputStream)
                                .write(buffer, 0, bytesRead);

                    } else {

                        secureFileSystem.writeToFile(
                                outputStream,
                                Arrays.copyOf(buffer, bytesRead)
                        );
                    }
                }

                if (outputStream instanceof FileOutputStream) {
                    ((FileOutputStream) outputStream).close();
                    return outputPath;
                }

                secureFileSystem.closeFile(outputStream);

                return outputPath;

            } catch (IOException exception) {

                exception.printStackTrace();

                errorMessage = new StringBuilder();
                errorPrefix = "---IOException ";

                errorMessage.append(errorPrefix);
                errorMessage.append(exception.getMessage());

                return errorMessage.toString();

            } catch (SecurityException exception) {

                exception.printStackTrace();

                errorMessage = new StringBuilder();
                errorPrefix = "---SecurityException ";

                errorMessage.append(errorPrefix);
                errorMessage.append(exception.getMessage());

                return errorMessage.toString();

            } catch (Exception exception) {

                exception.printStackTrace();

                errorMessage = new StringBuilder();
                errorPrefix = "---Exception ";

                errorMessage.append(errorPrefix);
                errorMessage.append(exception.getMessage());

                return errorMessage.toString();
            }

        } catch (Exception unused) {
            return "---fileOpen";
        }
    }

    @SuppressLint("Range")
    private static String getFileNameFromUri(Context context, Uri uri) {

        Uri contentUri;
        String scheme = uri.getScheme();
        String path = uri.getPath();
        String uriString = uri.toString();

        if (scheme != null
                && scheme.equalsIgnoreCase("content")) {

            String displayName = null;

            try {
                contentUri = uri;

                try {
                    Cursor cursor = context
                            .getContentResolver()
                            .query(
                                    contentUri,
                                    new String[]{"_display_name"},
                                    null,
                                    null,
                                    null
                            );

                    if (cursor != null && cursor.moveToFirst()) {

                        displayName = cursor.getString(
                                cursor.getColumnIndex("_display_name")
                        );

                        cursor.close();
                    }

                } catch (Exception ignored) {
                }

            } catch (Exception unused) {
                contentUri = uri;
            }

            if (displayName != null) {
                return displayName;
            }

            String defaultName =
                    (uriString.contains("attachment")
                            || uriString.contains("mail"))
                            ? "attachment"
                            : "content";

            String extension = getFileExtensionFromUri(context, contentUri);

            return (extension == null || extension.length() <= 0)
                    ? defaultName
                    : defaultName + "." + extension;
        }

        return path != null
                ? new File(path).getName()
                : "file";
    }

    public static boolean hasExternalStorageAccess(Context context) {

        if (secureFileSystem != null) {
            return false;
        }

        boolean hasWritePermission =
                ContextCompat.checkSelfPermission(
                        context,
                        "android.permission.WRITE_EXTERNAL_STORAGE"
                ) == 0;

        boolean hasReadPermission =
                ContextCompat.checkSelfPermission(
                        context,
                        "android.permission.READ_EXTERNAL_STORAGE"
                ) == 0;

        if (!hasReadPermission || !hasWritePermission) {
            return false;
        }

        String externalStorageState =
                Environment.getExternalStorageState();

        return "mounted".equals(externalStorageState)
                || "mounted_ro".equals(externalStorageState);
    }

    public static boolean deleteFile(String filePath) {

        if (filePath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs != null && secureFs.isSecurePath(filePath)) {
            return secureFs.deleteFile(filePath);
        }

        try {
            File file = new File(
                    new File(filePath).getAbsolutePath()
            );

            if (!file.exists()) {
                return true;
            }

            file.delete();

            return true;

        } catch (Exception unused) {
            return false;
        }
    }

    public static Object openFileForReading(String filePath) {

        if (filePath == null) {
            return null;
        }

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs != null) {
            return secureFs.getFileHandleForReading(filePath);
        }

        try {
            return new FileInputStream(filePath);

        } catch (FileNotFoundException | SecurityException unused) {
            return null;
        }
    }

    public static String getFileExtension(String filePath) {

        if (filePath == null) {
            return "";
        }

        String[] parts = filePath.split("\\.");

        return parts.length <= 1
                ? ""
                : parts[parts.length - 1].toLowerCase();
    }

    public static boolean isSupportedDocumentType(String filePath) {

        return hasExtension(filePath, "pdf")
                || hasExtension(filePath, "epub")
                || hasExtension(filePath, "svg")
                || hasExtension(filePath, "xps")
                || hasExtension(filePath, "fb2")
                || hasExtension(filePath, "cbz")
                || hasExtension(filePath, "xhtml");
    }

    private static boolean deleteRecursively(String filePath) {

        File file = new File(filePath);

        try {

            if (file.isDirectory()) {

                for (File childFile : Objects.requireNonNull(file.listFiles())) {
                    deleteRecursively(childFile.getPath());
                }
            }

            file.delete();

            return true;

        } catch (NullPointerException unused) {

            Log.e(
                    "FileUtils",
                    "deleteRecursive() failed ["
                            + filePath
                            + "]: Have storage permissions been granted"
            );

            return false;

        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean removeDirectory(String filePath) {

        if (filePath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        return (secureFs == null || !secureFs.isSecurePath(filePath))
                ? deleteRecursively(filePath)
                : secureFs.recursivelyRemoveDirectory(filePath);
    }

    public static String getTempPath(Context context) {

        SOSecureFS secureFs = secureFileSystem;

        return secureFs != null
                ? secureFs.getTempPath()
                : context.getFilesDir().toString();
    }

    public static String readAssetText(Context context, String assetName) {

        try {

            InputStream inputStream =
                    context.getAssets().open(assetName);

            byte[] buffer =
                    new byte[inputStream.available()];

            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, "UTF-8");

        } catch (IOException exception) {

            exception.printStackTrace();

            return null;
        }
    }

    public static boolean createDirectory(String directoryPath) {

        if (directoryPath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs != null
                && secureFs.isSecurePath(directoryPath)) {

            return secureFs.createDirectory(directoryPath);
        }

        try {
            return new File(directoryPath).mkdirs();

        } catch (SecurityException unused) {
            return false;
        }
    }

    public static boolean hasExtension(String filePath, String extension) {

        return filePath
                .toLowerCase()
                .endsWith("." + extension);
    }

    public static long getLastModifiedTime(String filePath) {

        if (filePath == null) {
            return 0L;
        }

        SOSecureFS secureFs = secureFileSystem;

        return secureFs != null
                ? secureFs.getFileAttributes(filePath).lastModified
                : new File(filePath).lastModified();
    }

    public static String getFileExtensionFromUri(Context context, Uri uri) {
        return getFileExtensionFromUri(context, uri, (String) null);
    }

    public static String getFileExtensionFromUri(
            Context context,
            Uri uri,
            String mimeType
    ) {

        String scheme = uri.getScheme();

        if (scheme != null
                && scheme.equalsIgnoreCase("content")) {

            MimeTypeMap mimeTypeMap =
                    MimeTypeMap.getSingleton();

            String detectedMimeType =
                    context.getContentResolver().getType(uri);

            if (detectedMimeType != null) {
                mimeType = detectedMimeType;
            }

            String extension =
                    mimeTypeMap.getExtensionFromMimeType(mimeType);

            if (extension != null) {
                return extension;
            }
        }

        return getFileExtensionFromUriPath(uri);
    }

    public static String copyAssetToSecureStorage(
            Context context,
            String assetName
    ) throws IOException {

        String tempPath = getTempPath(context);

        if (!fileExists(tempPath) && !createDirectory(tempPath)) {
            throw new IOException();
        }

        String assetPath =
                new File(tempPath, assetName).getPath();

        if (fileExists(assetPath)) {
            deleteFile(assetPath);
        }

        Object outputHandle =
                secureFileSystem.getFileHandleForWriting(assetPath);

        if (outputHandle == null) {
            throw new IOException();
        }

        try {

            InputStream inputStream =
                    context.getAssets().open(assetName);

            try {

                try {

                    byte[] buffer = new byte[1024];

                    while (true) {

                        int bytesRead =
                                inputStream.read(buffer);

                        if (bytesRead <= 0) {

                            secureFileSystem.closeFile(outputHandle);
                            inputStream.close();

                            return assetPath;
                        }

                        secureFileSystem.writeToFile(
                                outputHandle,
                                Arrays.copyOf(buffer, bytesRead)
                        );
                    }

                } catch (Throwable throwable) {

                    secureFileSystem.closeFile(outputHandle);

                    throw throwable;
                }

            } catch (Throwable throwable) {

                inputStream.close();

                throw throwable;
            }

        } catch (IOException exception) {

            exception.printStackTrace();

            return null;
        }
    }

    private static String getFileExtensionFromUriPath(Uri uri) {

        String fileName =
                new File(Objects.requireNonNull(uri.getPath())).getName();

        int extensionIndex =
                fileName.lastIndexOf(46);

        if (extensionIndex > 0) {
            return fileName.substring(extensionIndex + 1);
        }

        return null;
    }

    public static void initializeSecureFileSystem(Context context) {

        String errorMessage;

        try {

            SOSecureFS initializedSecureFs = SOLib.f();

            secureFileSystem = initializedSecureFs;

            if (initializedSecureFs != null) {
                // SecureFS available
            } else {
                throw new ClassNotFoundException();
            }

        } catch (ClassNotFoundException unused) {

            Log.i(
                    "FileUtils",
                    "SecureFS implementation unavailable"
            );

        } catch (ExceptionInInitializerError unused) {

            errorMessage =
                    "init() experienced unexpected exception [ExceptionInInitializerError]";

            Log.e("FileUtils", errorMessage);

        } catch (LinkageError unused) {

            errorMessage =
                    "init() experienced unexpected exception [LinkageError]";

            Log.e("FileUtils", errorMessage);

        } catch (SecurityException unused) {

            errorMessage =
                    "init() experienced unexpected exception [SecurityException]";

            Log.e("FileUtils", errorMessage);
        }
    }

    public static boolean copyRegularFile(
            File sourceFile,
            File destinationFile
    ) {

        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;

        try {

            byte[] buffer = new byte[32768];

            inputStream =
                    new BufferedInputStream(
                            new FileInputStream(sourceFile)
                    );

            outputStream =
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    destinationFile,
                                    false
                            )
                    );

            int bytesRead = 0;

            while (bytesRead != -1) {

                bytesRead = inputStream.read(buffer);

                if (bytesRead > 0) {
                    outputStream.write(
                            buffer,
                            0,
                            bytesRead
                    );
                }
            }

            inputStream.close();
            outputStream.close();

            return true;

        } catch (Exception unused) {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ignored) {
            }

            return false;

        } catch (Throwable throwable) {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ignored) {
            }

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ignored) {
            }

            throw throwable;
        }
    }

    public static boolean closeFile(Object fileHandle) {

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs != null) {
            return secureFs.closeFile(fileHandle);
        }

        if (fileHandle != null) {

            try {

                ((FileInputStream) fileHandle).close();

            } catch (IOException unused) {
                return false;
            }
        }

        return fileHandle != null;
    }

    public static boolean renameFile(
            String sourcePath,
            String destinationPath
    ) {

        if (sourcePath == null || destinationPath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        return secureFs != null
                ? secureFs.renameFile(
                sourcePath,
                destinationPath
        )
                : new File(sourcePath)
                  .renameTo(
                          new File(destinationPath)
                  );
    }

    public static int readFile(
            Object fileHandle,
            byte[] buffer
    ) {

        if (fileHandle == null) {
            return -1;
        }

        SOSecureFS secureFs = secureFileSystem;

        if (secureFs != null) {
            return secureFs.readFromFile(
                    fileHandle,
                    buffer
            );
        }

        try {

            return ((FileInputStream) fileHandle)
                    .read(buffer);

        } catch (IOException unused) {
            return -1;
        }
    }

    public static File getDocumentsDirectory(Context context) {

        String configuredDocumentsPath = documentsPath;

        if (configuredDocumentsPath == null) {

            return new File(
                    Environment.getExternalStorageDirectory(),
                    "Documents"
            );
        }

        if (!fileExists(configuredDocumentsPath)) {
            createDirectory(configuredDocumentsPath);
        }

        return new File(configuredDocumentsPath);
    }

    public static String copyAssetToCache(
            Context context,
            String assetName
    ) {

        if (secureFileSystem != null) {

            try {
                return copyAssetToSecureStorage(context, assetName);

            } catch (IOException exception) {

                exception.printStackTrace();

                return null;
            }
        }

        File outputFile =
                new File(
                        context.getExternalCacheDir(),
                        assetName
                );

        try {

            InputStream inputStream =
                    context.getAssets().open(assetName);

            try {

                FileOutputStream outputStream =
                        new FileOutputStream(outputFile);

                try {

                    byte[] buffer = new byte[1024];

                    while (true) {

                        int bytesRead =
                                inputStream.read(buffer);

                        if (bytesRead <= 0) {

                            outputStream.close();
                            inputStream.close();

                            return outputFile.getAbsolutePath();
                        }

                        outputStream.write(
                                buffer,
                                0,
                                bytesRead
                        );
                    }

                } catch (Throwable throwable) {

                    outputStream.close();

                    throw throwable;
                }

            } catch (Throwable throwable) {

                inputStream.close();

                throw throwable;
            }

        } catch (IOException exception) {

            exception.printStackTrace();

            return null;
        }
    }

    public static boolean fileExists(String filePath) {

        if (filePath == null) {
            return false;
        }

        SOSecureFS secureFs = secureFileSystem;

        return secureFs != null
                ? secureFs.fileExists(filePath)
                : new File(filePath).exists();
    }

    public static boolean replaceFile(
            String sourcePath,
            String destinationDirectory
    ) {

        if (!fileExists(sourcePath)) {
            return false;
        }

        boolean destinationExists =
                fileExists(destinationDirectory);

        String temporaryPath =
                new File(
                        destinationDirectory,
                        "xxx"
                ).getPath();

        if (fileExists(temporaryPath)) {
            return false;
        }

        if (destinationExists
                && !renameFile(
                destinationDirectory,
                temporaryPath
        )) {

            return false;
        }

        if (copyFile(
                sourcePath,
                destinationDirectory,
                true
        )) {

            deleteFile(temporaryPath);

            return true;
        }

        if (destinationExists) {
            renameFile(
                    temporaryPath,
                    destinationDirectory
            );
        }

        return false;
    }
}
